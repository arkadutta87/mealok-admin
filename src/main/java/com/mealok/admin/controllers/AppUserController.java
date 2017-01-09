package com.mealok.admin.controllers;

import com.mealok.admin.common.InputEnum;
import com.mealok.admin.common.MealOKPermission;
import com.mealok.admin.model.AppPermission;
import com.mealok.admin.model.AppUser;
import com.mealok.admin.payload.*;
import com.mealok.admin.service.AppUserService;
import com.mealok.admin.service.LoginService;
import com.mealok.admin.utils.Constants;
import com.mealok.admin.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by arkadutta on 09/11/16.
 */
@RestController
public class AppUserController {

    private static final String REQUEST_DATA_ABSENT = "All fields in request not present";
    private static final String APP_USER_ID_INVALID = "AppUser Id provided is invalid. ";

    @Autowired
    AppUserService appUserService;

    @MealOKPermission(operator = "AND" , permissionCodes = {"AppUser:add"})
    @RequestMapping(value = "/index/appuser/add/", method = RequestMethod.POST)
    public ResponseEntity<AppUserAddResponse> addAppUser(@RequestBody AppUserAddRequest request, HttpServletRequest req) {
        AppUserAddResponse response = new AppUserAddResponse();

        if(request == null || request.getUsername() == null || request.getPassword() == null){
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        }else {
            String username = request.getUsername();
            String password = request.getPassword();

            if(!Util.verifyIfFieldQualifies(username, InputEnum.USERNAME)){
                response.setCode(2);
                response.setMessage("The username : " + username + " provided is not a valid email-id . Please Enter a valid email id - ");
            }else if(!Util.verifyIfFieldQualifies(password, InputEnum.PASSWORD)) {
                response.setCode(2);
                response.setMessage("The password provided is not a valid password . " +
                        "Please enter a password which is atleast 8 characters long, a digit must occur at least once," +
                        "a lower case letter must occur at least once ,an upper case letter must occur at least once, " +
                        "a special character must occur at least once, no whitespace allowed in the entire string ");
            }else{
                AppUser user = new AppUser();
                user.setUsername(username);
                user.setPassword(Util.encrypt(password));

                user.setIs_email_verified(true);
                user.setIsotp(true);

                user.setCreated_on(new Date());
                user.setIsstaff(true);
                user.setIsactive(true);

                user.setFirstname("");
                user.setLastname("");

                user.setUpdated_on(new Date());

                long owner_id = (long)req.getAttribute(Constants.APP_USER_ID);
                boolean flag = false;
                try{
                    AppUser usr = appUserService.createAppUser(user,owner_id);
                    flag = true;

                    response.setId(usr.getId());
                    response.setCode(0);
                    response.setMessage("User Created Successfully.");
                }catch(Exception e ){
                    e.printStackTrace();
                    response.setCode(5);
                    response.setMessage("Internal Server Error.");
                }


            }

        }

        return new ResponseEntity<AppUserAddResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/index/admin/permissions/", method = RequestMethod.GET)
    public ResponseEntity<PermissionPayLoadResponse> addAppUserStep2(HttpServletRequest req) {
        PermissionPayLoadResponse response = new PermissionPayLoadResponse();

        List<AppPermission> list = appUserService.listPermissions();
        if(list == null && list.isEmpty()){
            response.setCode(1);
            response.setMessage("Some Internal Issue");
        }else{
            List<PermissionPayLoad> list2 = new ArrayList<>();
            for(AppPermission perm : list){
                PermissionPayLoad aObj = new PermissionPayLoad();
                aObj.setId(perm.getId());
                aObj.setName(perm.getName());
                aObj.setCodeName(perm.getCodename());
                list2.add(aObj);
            }

            response.setCode(0);
            response.setMessage("SUCCESS");
            response.setPermissions(list2);
        }

        return new ResponseEntity<PermissionPayLoadResponse>(response, HttpStatus.OK);
    }

    @MealOKPermission(operator = "AND" , permissionCodes = {"AppUser:add"})
    @RequestMapping(value = "/index/appuser/add/step2/", method = RequestMethod.POST)
    public ResponseEntity<AppUserAddStep2Response> addAppUserStep2(@RequestBody AppUserAddRequestStep2 request, HttpServletRequest req) {
        AppUserAddStep2Response response = new AppUserAddStep2Response();

        if(request == null || request.getFirstName() == null || request.getLastName() == null){
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        }else {
            long app_id = request.getApp_user_id();
            AppUser user = appUserService.getAppUserFromID(app_id);
            if(user == null){
                response.setCode(2);
                response.setMessage(APP_USER_ID_INVALID);
            }else{
                long owner_id = (long)req.getAttribute(Constants.APP_USER_ID);

                String firstName = request.getFirstName();
                String lastName = request.getLastName();
                String email = request.getEmail();

                user.setFirstname(firstName);
                user.setLastname(lastName);
                user.setEmail(email);

                user.setUpdated_on(new Date());
                boolean is_super_user = request.is_super_user();
                if(is_super_user){
                    user.setIssuperuser(true);
                }else{
                    long[] perIds = request.getPermissionsId();
                    if(perIds!= null && perIds.length > 0 ){
                        try{
                            List<AppPermission> pList  = appUserService.getAppPermissionListFromIds(perIds);
                            if(pList!= null){
                                Set<AppPermission> set = new HashSet<AppPermission>(pList);
                                user.setUserPermissions(set);
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    }

                }

                AppUser user2 = appUserService.createAppUserUpdate(user,owner_id,"App User finally created with all permissions");
                response.setCode(0);
                response.setMessage("SUCCESS");

                response.setFirstName(user2.getFirstname());
                response.setLastName(user2.getLastname());
                response.setApp_user_id(user2.getId());
                response.setEmail(user2.getEmail());

                response.setIs_super_user(user2.issuperuser());

                List<Long> perLst = new ArrayList<Long>();

                Set<AppPermission> pts = user2.getUserPermissions();
                if(pts != null){
                    for(AppPermission aPobj : pts){
                        perLst.add(new Long(aPobj.getId()));
                    }
                }

                long[] result = perLst.stream().mapToLong(l -> l).toArray();
                response.setPermissionsId(result);


            }
        }
        return new ResponseEntity<AppUserAddStep2Response>(response, HttpStatus.OK);
    }

    //Need to code : Complete by tonight
    @RequestMapping(value = "/index/appuser/list/", method = RequestMethod.POST)
    public ResponseEntity<AppUserListResponse> addAppUser(@RequestBody AppUserListRequest request, HttpServletRequest req) {

        AppUserListResponse response = new AppUserListResponse();

        if (request == null || request.getStep() <= 0 || request.getFilters() == null) {
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        }else{

        }

        return new ResponseEntity<AppUserListResponse>(response, HttpStatus.OK);
    }
}
