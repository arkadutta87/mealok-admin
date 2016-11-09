package com.mealok.admin.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by arkadutta on 24/10/16.
 */
public class ConstantContainer {

    private static ConstantContainer ourInstance = new ConstantContainer();

    public static ConstantContainer getInstance() {
        return ourInstance;
    }

    private static final String CONFIGURATION_FILE = "/etc/env/mealok-admin.properties";
    private String REDIS_URI = null;
    private String API_COLLABORATION_KEY = null;//kysWRQI@1530


    private ConstantContainer() {

        FileInputStream reader;

        try{
            reader = new FileInputStream(CONFIGURATION_FILE);

            Properties prop = new Properties();
            try{
                prop.load(reader);
                /*String dishCoreName = prop.getProperty("dishSuggesterCore");
                String gcGscCoreName = prop.getProperty("gcGSCCore");
                String dishItemCoreName = prop.getProperty("dishItemCore");
                CATEGORY_CORE = prop.getProperty("categorizationCore");
                dishSuggestionCore = new HttpSolrClient(dishCoreName);
                gcGscCore = new HttpSolrClient(gcGscCoreName);
                dishItemCore = new HttpSolrClient(dishItemCoreName);*/
                REDIS_URI = prop.getProperty("redisUri");
                API_COLLABORATION_KEY = prop.getProperty("api-collaborator-key");


            }catch(IOException e1){
                System.out.println(e1.getMessage());
            }


        } catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public String getREDIS_URI(){
        return REDIS_URI;
    }

    public String getAPI_COLLABORATION_KEY(){
        return API_COLLABORATION_KEY;
    }
}
