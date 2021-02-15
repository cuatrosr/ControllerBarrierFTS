package model;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientBarrier extends Thread {

    private Socket socket;
    private DataInputStream inputTcp;
    private static BufferedWriter bw;
    private static String charOrder;
    
    //Se conecta al controlador mediante el host y el puerto
    public void connectController(String HOST, int PUERTO) {
        try {
            socket = new Socket(HOST, PUERTO);
        } catch (Exception e) {
        }
    }
    
    //Crea los flujos para recibir y enviar datos
    public void setUp() {
        try {
            inputTcp = new DataInputStream(socket.getInputStream());

            OutputStream out = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(out);
            bw = new BufferedWriter(osw);
        } catch (IOException e) {
        }
    }
    
    //Setea la orden para el envio de trama
    public void setCharOrder(String order) {
        charOrder = order;
    }
    
    //Envia la trama de la orden pedida y recibe la respuesta
    public String requestBarrier(boolean restStatus) {
        try {
            char[] datos = {charOrder.charAt(0), charOrder.charAt(1), charOrder.charAt(2), charOrder.charAt(3), 0x0D, 0x0A};
            bw.write(datos);
            bw.flush();
            return receiveBarrierResponse(restStatus);
        } catch (IOException e) {
            return "false";
        }
    }
    
    //Recibe la respuesta y reenvia a un metodo dependiendo si la orden es "status" o no
    public String receiveBarrierResponse(boolean restStatus) {
        byte[] bfrs = new byte[512];
        try {
            int nbytes = inputTcp.read(bfrs);
            String message = ConvertAndAnalyze.byteString(bfrs, nbytes);
            if (restStatus) {
                return ConvertAndAnalyze.analyzeResponseStatus(message);
            } else {
                return ConvertAndAnalyze.analyzeResponse(message);
            }
        } catch (IOException ex) {
            return "false";
        }
    }
}
