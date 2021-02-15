package config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.io.FileInputStream;

public class GetPropertyValues {

    private String URL;
    private static int ammountBarriers;
    private static String[] arrayBarriers;
    private static Properties prop;
    
    public void setPropValues() throws IOException {
        try {
            String propFileName = "configuration.properties";
            InputStream input = new FileInputStream(propFileName);
            prop = new Properties();

            if (input != null) {
                prop.load(input);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            
            ammountBarriers = Integer.parseInt(prop.getProperty("ammountBarriers"));
            arrayBarriers = new String[ammountBarriers];
            fillArrayListBarriers();
            
            URL = prop.getProperty("URL");
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
    
    //Pone todas las barreras en un array con el formato (ID/HOST/PUERTO)
    public static void fillArrayListBarriers() {
        for (int i = 0; i < ammountBarriers; i++) {
            String idBarrier = prop.getProperty("ID_BARRIER_" + String.valueOf(i + 1));
            String hostBarrier = prop.getProperty("HOST_BARRIER_" + String.valueOf(i + 1));
            String puertoBarrier = prop.getProperty("PUERTO_BARRIER_" + String.valueOf(i + 1));
            arrayBarriers[i] = (idBarrier + "/" + hostBarrier + "/" + puertoBarrier);
        }
    }

    public static String[] getArrayBarriers() {
        return arrayBarriers;
    }
    
    public String getURL() {
        return URL;
    }
}
