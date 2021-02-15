package model;

import config.GetPropertyValues;
import json.Barrier;

public class ConvertAndAnalyze {

    //Recibe la respuesta de la barrera en un Array de byte y lo devuelve en formato String
    public static String byteString(byte[] bfrs, int nbytes) {
        String message = "";
        for (int i = 0; i < nbytes; i++) {
            message += bfrs[i];
        }
        return message;
    }

    //Analiza el id de la barrera
    public static String analyzeIdBarrier(String id_barrera, String orden) throws Exception {

        //Recibe el array de barreras del .properties
        String[] arrayBarriers = GetPropertyValues.getArrayBarriers();
        int id = 0;
        int host = 1;
        int puerto = 2;
        for (int i = 0; i < arrayBarriers.length; i++) {
            String[] splitArray = arrayBarriers[i].split("/");

            //Analiza si la id de la barrera pedida esta registrada en el .properties
            if (splitArray[id].equals(id_barrera)) {
                try {
                    ClientBarrier clientBarrier = new ClientBarrier();
                    clientBarrier.connectController(splitArray[host], Integer.parseInt(splitArray[puerto]));
                    clientBarrier.setUp();
                    return analyzeOrden(clientBarrier, orden);

                    //Si no hay conexion con la barrera devuelve error
                } catch (Exception e) {
                    return "Error... No se puede conectar a la barrera.";
                }
            }
        }

        //Si no se reconoce la barrera en el .properties devuelve error
        return "Error... la barrera no esta registrada.";
    }

    //Analiza la orden recibida para setear el string que se va a mandar como trama
    public static String analyzeOrden(ClientBarrier clientBarrier, String orden) {

        //Si la orden es "status" se enviara añadido un booleano true, si es cualquier otra false
        switch (orden) {

            case "subir":
                clientBarrier.setCharOrder("$99U");
                return String.valueOf(clientBarrier.requestBarrier(false));

            case "bajar":
                clientBarrier.setCharOrder("$99D");
                return String.valueOf(clientBarrier.requestBarrier(false));

            case "status":
                clientBarrier.setCharOrder("$99K");
                return String.valueOf(clientBarrier.requestBarrier(true));

            //Si la orden es distinta a las ya especificadas devuelve error
            default:
                return "Error... la orden no se reconoce";
        }
    }

    //Organiza el mensaje Json a devolver, devuelve si se presento un error o no
    public static boolean setBarrierJsonMensaje(Barrier br) {

        //Orden subir y bajar se ejecuto correctamente
        if (br.getEstatus_orden().equals("true") && !br.getOrden().equals("status")) {
            br.setMensaje("");
            return false;

        } //Orden subir y bajar fallo al ejecutarse
        else if (!br.getEstatus_orden().equals("true") && !br.getOrden().equals("status")) {
            br.setMensaje(br.getEstatus_orden());
            br.setEstatus_orden("false");
            return true;

        } //Orden status se ejecuto correctamente
        else if (!br.getEstatus_orden().contains("Error")) {
            br.setMensaje(br.getEstatus_orden());
            br.setEstatus_orden("true");
            return false;

        } //Orden status fallo al ejecutarse
        else {
            br.setMensaje(br.getEstatus_orden());
            br.setEstatus_orden("false");
            return true;
        }
    }

    //Analiza la respuesta del controlador para (subir o bajar)
    public static String analyzeResponse(String message) {

        //Si la respuesta es (335757 = !99) entonces la respuesta fue exitosa
        if (message.contains("335757")) {
            return "true";

            //Si la respuesta es diferente a (335757 = !99) entonces la barrera no pudo realizar la accion
        } else {
            return "Error... la barrera no pudo realizar la accion";
        }
    }

    //Analiza la respuesta del controlador para (status)
    public static String analyzeResponseStatus(String message) {

        //Si la respuesta contiene (335757755748 = !99K90) entonces la barrera esta arriba
        if (message.contains("335757755748")) {
            return "up";

            //Si la respuesta contiene (335757754848 = !99K00) entonces la barrera esta abajo
        } else if (message.contains("335757754848")) {
            return "down";

            //Si la respuesta tiene un angulo distinto a (90 o 00) entonces se esta moviendo
        } else if (message.contains("33575775")) {
            return "Error... la barrera esta en movimiento";

            //Si la respuesta res distinta a (!99K) entonces la barrera no pudo realizar la accion
        } else {
            return "Error... la barrera no pudo realizar la accion";
        }
    }
}
