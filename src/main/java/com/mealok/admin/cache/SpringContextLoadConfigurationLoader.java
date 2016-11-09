package com.mealok.admin.cache;

import com.mealok.admin.common.ConstantContainer;

import javax.annotation.PostConstruct;
import javax.persistence.SecondaryTable;
import java.util.List;
import java.util.Set;

/**
 * Created by arkadutta on 25/10/16.
 */
public class SpringContextLoadConfigurationLoader  {

    @PostConstruct
    public void initIt() throws Exception {

        String key  = ConstantContainer.getInstance().getAPI_COLLABORATION_KEY();
        CacheUtil.getInstance().getAPIKey();
        System.out.println("Init method after properties are set -- key value $$$$$$$$$$ --  : " + key);

        CacheUtil cache = CacheUtil.getInstance();
        System.out.println("The base package -- "+cache.getBasePackage());

        Set<String> arr = cache.getClassIgnored();
        System.out.println("The classes to be ignored --- ");
        for(String str : arr){
            System.out.println("Class : "+str);
        }


    }
}
