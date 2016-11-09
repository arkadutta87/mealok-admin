package com.mealok.admin.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mealok.admin.common.ConstantContainer;
import com.mealok.admin.utils.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.concurrent.TimeUnit;

import java.util.*;

/**
 * Created by arkadutta on 25/10/16.
 */
public class CacheUtil {

    private static  LoadingCache<String, String> cache ;
    private String basePackage = null;
    private Set<String> classIgnored = new HashSet<String>();

    private static CacheUtil ourInstance = null;

    private CacheUtil(){

        cache = CacheBuilder.newBuilder()
                .maximumSize(100) // maximum 100 records can be cached // cache will expire after 30 minutes of access
                .build(new CacheLoader<String, String>(){ // build the cacheloader

                    @Override
                    public String load(String empId) throws Exception {
                        //make the expensive call
                        //return getAPIKey(empId);
                        return ConstantContainer.getInstance().getAPI_COLLABORATION_KEY();
                    }
                });

        //parse the migration.xml to find out the base package and classes whose content_type and permission need not be published
        try{
            parseMigrationXML();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void parseMigrationXML() throws  Exception{
        //File fXmlFile = new File("/Users/mkyong/staff.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(CacheUtil.class.getClassLoader().getResourceAsStream("migration.xml"));

        if(doc!= null) {
            doc.getDocumentElement().normalize();
            //base-package
            NodeList nList = doc.getElementsByTagName("base-package");

            if (nList != null && nList.getLength() != 0) {
                Node node = nList.item(0);
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) node;
                    String base_package = eElement.getTextContent();
                    basePackage = base_package;

                }

            }

            nList = doc.getElementsByTagName("class");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node node = nList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) node;
                    String ignoredClass = eElement.getTextContent();
                    classIgnored.add(ignoredClass);

                }

            }

        }

        doc = null;
        dBuilder = null;
        dbFactory = null;
    }

    public static CacheUtil getInstance() {
        if(ourInstance == null){
            synchronized (CacheUtil.class){
                if(ourInstance == null){
                    ourInstance = new CacheUtil();
                }
            }
        }

        return ourInstance;
    }

    public String getAPIKey(){
        String key = "";
        try{
            key = cache.get(Constants.COLLABORATION_API_KEY);
        }catch(Exception e){
            e.printStackTrace();
        }

        return key;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public Set<String> getClassIgnored() {
        return classIgnored;
    }
}
