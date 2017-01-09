package com.mealok.admin.service;

import com.mealok.admin.common.MealOKPermission;
import com.mealok.admin.model.*;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by arkadutta on 10/11/16.
 */

@Service("appUserService")
@Transactional
public class AppUserServiceImpl implements AppUserService{

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    LoginService loginService;

    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    private List<MealokContentType> getAllContentTypes(){
        Session session = getCurrentSession();
        Criteria query = session.createCriteria(MealokContentType.class);

        return query.list();
    }

    private MealokContentType getContentTypeID(List<MealokContentType> list , String className){
        for(MealokContentType aObj : list){
            String model = aObj.getModel().trim();
            if(model.equalsIgnoreCase(className)){
                return aObj;
            }
        }

        return null;
    }

    private AppUser getOwner(long id){
        Session session = getCurrentSession();
        AppUser m = null;
        try {
            m = (AppUser) session.load(AppUser.class, id);
            System.out.println("AppUser loaded -- " + m);
        } catch (Exception e) {
            e.printStackTrace();
            m = null;
        }


        return m;
    }

    @Override
    public AppUser createAppUser(AppUser user,long owner_id) {

        Session session = getCurrentSession();
        session.save(user);
        System.out.println("AppUser added successfully, AppUser Details= " + user);

        List<MealokContentType> cTp = getAllContentTypes();
        MealokContentType obj = getContentTypeID(cTp,"AppUser");

        MealokAdminLog log = new MealokAdminLog();
        log.setAction_time(new Date());
        log.setContentType(obj);
        //log.setUser(user);

        log.setChange_message("User have been added");
        log.setObject_id(user.getId()+"");
        log.setObject_repr("AppUser");

        AppUser owner = getOwner(owner_id);
        log.setUser(owner);

        session.persist(log);
        System.out.println("Admin Log added successfully, Log Details= " + log);



        return user;
    }

    @Override
    public AppUser createAppUserUpdate(AppUser user,long owner_id,String message){
        Session session = getCurrentSession();
        session.update(user);
        System.out.println("AppUser updated successfully, AppUser Details= " + user);

        List<MealokContentType> cTp = getAllContentTypes();
        MealokContentType obj = getContentTypeID(cTp,"AppUser");

        MealokAdminLog log = new MealokAdminLog();
        log.setAction_time(new Date());
        log.setContentType(obj);
        //log.setUser(user);

        log.setChange_message(message);
        log.setObject_id(user.getId()+"");
        log.setObject_repr("AppUser");

        AppUser owner = getOwner(owner_id);
        log.setUser(owner);

        session.persist(log);
        System.out.println("Admin Log added successfully, Log Details= " + log);



        return user;
    }

    @Override
    public AppUser getUserFromSession(String sessionId){
        AppUser user = null;
        try{
            MealokSession sess = loginService.getSession(sessionId);
            //AppUser user = null;
            if(sess != null){
                user = sess.getUser();
                System.out.println("User came - getUserFromSession -- "+user);
                Set<AppPermission> set = user.getUserPermissions();

                for(AppPermission perm : set){
                    System.out.println(perm
                    );
                }


            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally {
            return user;
        }
    }

    public List<AppPermission> listPermissions(){

        Session session = getCurrentSession();
        Criteria query = session.createCriteria(AppPermission.class);

        return query.list();

    }

    public AppUser getAppUserFromID(long id){

        return getOwner(id);
    }

    public List<AppPermission> getAppPermissionListFromIds(long[] ids){

        Session session = getCurrentSession();
        Criteria query = session.createCriteria(AppPermission.class);

        List<Long> list = new ArrayList<Long>();
        for(long id : ids){
            list.add(new Long(id));
        }
        Criterion q11 = Restrictions.in("id",list);
        query.add(q11);

        return query.list();

    }
}
