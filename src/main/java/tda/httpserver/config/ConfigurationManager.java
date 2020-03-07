package tda.httpserver.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import tda.httpserver.util.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {
    private static ConfigurationManager configurationManager;
    private static Configuration currentConfiguration;

    private ConfigurationManager(){

    }

    public static ConfigurationManager getInstance(){
        if(configurationManager==null)
            configurationManager = new ConfigurationManager();
        return configurationManager;
    }

    /**
    * Used to read a configuration file by path provided
    */

    public void loadConfigurationFile(String file){
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException("File not found");
        }
        StringBuffer sb = new StringBuffer();
        int i;
        while(true){
            try {
                if (!((i=fileReader.read())!=-1)) break;
            } catch (IOException e) {
                throw new HttpConfigurationException("Error in reading file", e);
            }
            sb.append((char)i);
        }

        JsonNode node = null;
        try {
            node = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error in parsing the configuration file", e);
        }
        try {
            currentConfiguration = Json.fromJson(node, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error in parsing the configuration file, internal", e);
        }
    }

    /**
     * return the current loaded configuration
     */
    public Configuration getCurrentConfiguration() throws HttpConfigurationException {
        if(currentConfiguration==null)
            throw new HttpConfigurationException("No current configuration set.");
        return currentConfiguration;
    }

}
