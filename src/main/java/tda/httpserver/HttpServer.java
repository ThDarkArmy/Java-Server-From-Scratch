package tda.httpserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tda.httpserver.config.Configuration;
import tda.httpserver.config.ConfigurationManager;
import tda.httpserver.core.ServerListenerThread;

import java.io.IOException;

public class HttpServer {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);
    public static void main(String[] args) throws IOException {
        System.out.println("Server is running....");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration config = ConfigurationManager.getInstance().getCurrentConfiguration();
        LOGGER.info(Integer.toString(config.getPort()));
        LOGGER.info(config.getWebroot());

        try{
            ServerListenerThread serverListenerThread = new ServerListenerThread(config.getPort(), config.getWebroot());
            serverListenerThread.start();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

}
