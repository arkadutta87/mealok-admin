package com.mealok.admin.bin;

import com.mealok.admin.cache.CacheUtil;
import com.mealok.admin.cache.SpringContextLoadConfigurationLoader;
import com.mealok.admin.common.InputEnum;
import com.mealok.admin.model.AppPermission;
import com.mealok.admin.model.AppUser;
import com.mealok.admin.model.MealokContentType;
import com.mealok.admin.utils.HibernateAnnotationUtil;
import com.mealok.admin.utils.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.ClassArrayEditor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;

import java.util.*;

/**
 * Created by arkadutta on 08/11/16.
 */
public class MigrationUtil {

    private static SessionFactory sessionFactory = null;
    private static Session session = null;
    private static Transaction tx = null;

    public MigrationUtil(){
        sessionFactory = HibernateAnnotationUtil.getSessionFactory();
    }

    private void createContentType(String className, String tableName) {


        session = sessionFactory.getCurrentSession();
        System.out.println("Session created");

        //start transaction
        tx = session.beginTransaction();

        MealokContentType user = new MealokContentType();
        user.setApp_label(tableName);
        user.setModel(className);

        user.setCreated_on(new Date());
        user.setUpdated_on(new Date(0));

        try{
                session.save(user);
        }catch(Exception e){
            e.printStackTrace();

        }finally {
            try {
                // The real work is here
                tx.commit();
            } catch (Exception ex) {
                // Log the exception here
                ex.printStackTrace();
                tx.rollback();
                //throw ex;
            }
            finally {
                session.close();
            }



        }


        //HibernateAnnotationUtil.shutDown();
    }

    private void createPermission(String className, String tableName) {


        session = sessionFactory.getCurrentSession();
        System.out.println("Session created");

        //start transaction
        tx = session.beginTransaction();

        //find content type
        String hql = "from MealokContentType u where u.app_label=:id1  and u.model=:model1";
        System.out.println(hql);
        Query query = session.createQuery(hql);
        query.setParameter("id1", tableName);
        query.setParameter("model1", className);
        //query.setParameter("pwdStr", password);

        List<MealokContentType> results = query.list();
        MealokContentType aUsr = null;
        if(results!= null && !results.isEmpty()){
            aUsr = results.get(0);
        }


        List<String> lst = new ArrayList<>();
        for(int i = 0 ; i< 3 ; i++){

        }
        for(int i =0 ; i<3 ; i++){
            AppPermission user = new AppPermission();
            if(i==0){
                user.setName("Add "+className);
                user.setCodename(className+":add");
            }else if(i==1){
                user.setName("Update "+className);
                user.setCodename(className+":update");
            }else{
                user.setName("Delete "+className);
                user.setCodename(className+":delete");
            }
            user.setContentType(aUsr);

            try{
                session.save(user);
            }catch(Exception e){
                e.printStackTrace();

            }
        }


        try {
            // The real work is here
            tx.commit();
        } catch (Exception ex) {
            // Log the exception here
            ex.printStackTrace();
            tx.rollback();
            //throw ex;
        }
        finally {
            session.close();
        }
        //HibernateAnnotationUtil.shutDown();
    }

    private void closeConnectionToDB(){
        HibernateAnnotationUtil.shutDown();
    }


    /**
     * Scans all classes accessible from the context class loader which belong
     * to the given package and subpackages.
     *
     * @param packageName
     *            The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws URISyntaxException
     */
    private  Iterable<Class> getClasses(String packageName) throws ClassNotFoundException, IOException, URISyntaxException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements())
        {
            URL resource = resources.nextElement();
            URI uri = new URI(resource.toString());
            dirs.add(new File(uri.getPath()));
        }
        List<Class> classes = new ArrayList<Class>();
        for (File directory : dirs)
        {
            classes.addAll(findClasses(directory, packageName));
        }

        return classes;
    }

    /**
     * Recursive method used to find all classes in a given directory and
     * subdirs.
     *
     * @param directory
     *            The base directory
     * @param packageName
     *            The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private  List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException
    {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists())
        {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files)
        {
            if (file.isDirectory())
            {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            }
            else if (file.getName().endsWith(".class"))
            {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    private  boolean isAnnotationEntityPresent(Class aCls){
        return aCls.isAnnotationPresent(Entity.class);
    }

    public static void main(String[] args) {

        MigrationUtil migrate = new MigrationUtil();

        CacheUtil cache = CacheUtil.getInstance();
        String packgName = cache.getBasePackage();

        try{
            Iterable<Class> clss = migrate.getClasses(packgName);

            List<Class> clss2 = new ArrayList<>();

            System.out.println("Classes in package --- "+packgName);
            for(Class aCl : clss){
                if(migrate.isAnnotationEntityPresent(aCl)  && !cache.getClassIgnored().contains(aCl.getName()) ){

                    Annotation ann = aCl.getAnnotation(Table.class);
                    String tableName = null;
                    if(ann != null){
                        tableName = ((Table) ann).name();

                        String clsName = aCl.getName().replace(packgName+".","").trim();
                        try {
                            migrate.createContentType(clsName, tableName);
                        }catch(Exception e){
                            e.printStackTrace();
                            //tx.commit();
                        }
                        try {
                            migrate.createPermission(clsName, tableName);
                        }catch(Exception e){
                            e.printStackTrace();
                            //tx.commit();
                        }



                        System.out.println("Class Name : "+aCl.getName() + ", Canonical Name -- "+aCl.getCanonicalName());
                    }



                }



            }

            migrate.closeConnectionToDB();
            System.out.println("Migration completed");
        }catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
