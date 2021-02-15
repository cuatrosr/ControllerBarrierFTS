package ui;

import java.net.URI;
import java.net.URISyntaxException;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import config.GetPropertyValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {

    private static final Logger logger = LoggerFactory.getLogger(Test.class); ;

    public static void main(String[] args) throws Exception {
        
        GetPropertyValues properties = new GetPropertyValues();
        properties.setPropValues();

        String URL = properties.getURL();

        Test.startServer(URL);
        logger.info("Server active. URL:{}", URL);
    }
    
    public static HttpServer startServer(String URL) throws URISyntaxException {
        final ResourceConfig rc = new ResourceConfig().packages("api");
        URI uri = new java.net.URI("http", "//" + URL, null);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(String.valueOf(uri)), rc);
    }
}
