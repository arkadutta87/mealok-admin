package com.mealok.admin.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


import com.mealok.admin.common.TokenRedisModel;
import com.mealok.admin.model.AppUser;
import com.mealok.admin.model.MealokSession;
import com.mealok.admin.payload.*;
import com.mealok.admin.service.LoginService;
import com.mealok.admin.utils.Constants;
import com.mealok.admin.utils.RedisUtility;
import com.mealok.admin.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {


    @Autowired
    LoginService loginService;  //Service which will do all data retrieval/manipulation work

    private static final String REQUEST_DATA_ABSENT = "All fields in request not present";
    private static final String CREDENTIALS_INCORRECT = "The credentials are incorrect";
    private static final String ACCOUNT_DEACTIVATED = "The account has been deactivated. Contact your administrator";
    private static final String INTERNAL_SERVER_ERROR = "Internal Server Error. Contact your administrator";
    private static final String PWD_NOT_MATCHING_SECURITY = "Enter your password: Please enter a password which is atleast 8 characters long, a digit must occur at least once," +
            "a lower case letter must occur at least once ,an upper case letter must occur at least once, " +
            "a special character must occur at least once, no whitespace allowed in the entire string ";
    private static final String OTP_PWD = "Password is OTP. Need to change it for better security";
    private static final String LOGIN_SUCCESSFULL = "Login Successfull. Get to work";

    private static final String TOKEN_EXPIRED = "Token has expired";
    private static final String USER_DELETED = "User have been deleted from the system";

    private static final String HOME_PAGE = "dashboard/";
    private static final String TOKEN_PREFIX = "token_mealok_";
    private static final String DATA_NOT_PRESENT = "No data present";
    private static final String SUCCESS = "success";

    private static final int TOKEN_DURATION = 20*60;



    @RequestMapping(value = "/index/login/", method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> loginAppUser(@RequestBody LoginRequest request) {

        LoginResponse response = new LoginResponse();
        if(request == null || request.getUsername() == null || request.getPassword() == null){
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        }else {
            String username = request.getUsername();
            String password = request.getPassword();

            String encpytPwd = Util.encrypt(password);

            AppUser aUser = loginService.getAppUser(username,encpytPwd);
            if(aUser == null){
                response.setCode(3);
                response.setMessage(CREDENTIALS_INCORRECT);
            }else{
                if(!aUser.isactive()){
                    response.setCode(2);
                    response.setMessage(ACCOUNT_DEACTIVATED);
                }else{
                    if(aUser.isotp()){
                        //generate the token
                        String uniqueID = Util.generateSession();

                        //save the token to redis
                        TokenRedisModel aObj = new TokenRedisModel();
                        aObj.setUsername(username);
                        aObj.setId(aUser.getId());
                        aObj.setPwd(encpytPwd);

                        String key = TOKEN_PREFIX+uniqueID;
                        String val = Util.convertToJSON(aObj);

                        if(val == null){
                            response.setCode(5);
                            response.setMessage(INTERNAL_SERVER_ERROR);

                        }else{
                            boolean flag = RedisUtility.getInstance().setKeyValTTL(key,val,TOKEN_DURATION);
                            if(!flag){
                                response.setCode(5);
                                response.setMessage(INTERNAL_SERVER_ERROR);
                            }else{
                                response.setCode(1);
                                response.setMessage(OTP_PWD);
                                response.setToken(key);
                            }
                        }
                    }else{
                        //generate session , save the session in db , add the session to response,add redirect-url and then enjoy
                        String sessionStr = Util.generateSession();

                        //save session in db
                        MealokSession sess = new MealokSession();
                        sess.setSession_id(sessionStr);
                        sess.setIs_enabled(true);
                        sess.setLogged_in(new Date());
                        sess.setLogged_out(new Date(0));

                        loginService.saveSession(aUser,sess);

                        //add the session to response
                        response.setCode(0);
                        response.setMessage(LOGIN_SUCCESSFULL);
                        response.setToken(sessionStr);
                        response.setRedirectUrl(HOME_PAGE);
                    }

                }
            }
        }

        return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/index/changePassword/", method = RequestMethod.POST)
    public ResponseEntity<ChangePasswordResponse> loginAppUser(@RequestBody ChangePasswordRequest request) {

        ChangePasswordResponse response = new ChangePasswordResponse();
        if(request == null || request.getToken() == null || request.getNewPassword() == null){
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        }else{
            String pwd = request.getNewPassword();


                String token = request.getToken().trim();

                //hit redis to get the data back
                String jsonObjStr = RedisUtility.getInstance().getVal(token);
                if(jsonObjStr == null || jsonObjStr.isEmpty()){
                    response.setCode(1);
                    response.setMessage(TOKEN_EXPIRED);
                }else{
                    //deserialize the jsonStr to Object
                    boolean isEligibleFlag = Util.isPwdEligible(pwd);
                    if(!isEligibleFlag){
                        response.setCode(6);
                        response.setMessage(PWD_NOT_MATCHING_SECURITY);
                    }else{
                        Object obj = Util.convertToObject(TokenRedisModel.class,jsonObjStr);
                        TokenRedisModel mod = null;
                        if(obj instanceof  TokenRedisModel){
                            mod = (TokenRedisModel) obj;
                        }

                        if(mod == null){
                            response.setCode(5);
                            response.setMessage(INTERNAL_SERVER_ERROR);
                        }else{
                            String username = mod.getUsername();
                            String oldEncryptPwd = mod.getPwd();

                            AppUser user = loginService.getAppUserByUsername(username);
                            if(user == null){
                                response.setCode(3);
                                response.setMessage(USER_DELETED);
                            }else{
                                if(!user.isactive()){
                                    response.setCode(2);
                                    response.setMessage(ACCOUNT_DEACTIVATED);
                                }else{
                                    String encypPwd = Util.encrypt(pwd);
                                    //generate session
                                    if(oldEncryptPwd.equals(encypPwd)){
                                        response.setCode(7);
                                        response.setMessage("New password same as old password");
                                    }else{
                                        String sessionStr = Util.generateSession();

                                        //save session in db
                                        MealokSession sess = new MealokSession();
                                        sess.setSession_id(sessionStr);
                                        sess.setIs_enabled(true);
                                        sess.setLogged_in(new Date());
                                        sess.setLogged_out(new Date(0));

                                        Date currDate = new Date();
                                        //set the latest package expiry date to 31st Dec ,2116
                                        Calendar c = Calendar.getInstance();
                                        c.setTime(currDate); // Now use today date.
                                        c.add(Calendar.YEAR, 2);//Add 100 year

                                        Date aDt = c.getTime();
                                        sess.setExpire_date(aDt);

                                        loginService.saveSession(user,sess);

                                        //update user with new password
                                        user.setUpdated_on(new Date());
                                        user.setPassword(encypPwd);
                                        user.setIsotp(false);

                                        loginService.updateUser(user);

                                        //delete the key from redis
                                        RedisUtility.getInstance().deleteKey(token);

                                        //return the response
                                        response.setCode(0);
                                        response.setMessage(LOGIN_SUCCESSFULL);
                                        response.setToken(sessionStr);
                                        response.setRedirectUrl(HOME_PAGE);
                                    }

                                }
                            }
                        }
                    }


                }

        }


        return new ResponseEntity<ChangePasswordResponse>(response, HttpStatus.OK);

    }

    @RequestMapping(value = "/index/home/changePassword/", method = RequestMethod.POST)
    public ResponseEntity<GenericResponse> loginAppUser(@RequestBody ChangePasswordRequestLogged request, HttpServletRequest request2) {

        GenericResponse response = new GenericResponse();
        if (request == null || request.getNewPassword() == null) {
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        } else {
            String pwd = request.getNewPassword();
            boolean isEligibleFlag = Util.isPwdEligible(pwd);
            if(!isEligibleFlag){
                response.setCode(6);
                response.setMessage(PWD_NOT_MATCHING_SECURITY);
            }else{
                boolean superUser = request.isSuperUser();
                AppUser user = null;
                if(!superUser){
                    String sessionID = (String) request2.getAttribute(Constants.SESSION_ID);
                    user = loginService.getUserFromSession(sessionID);
                }else{
                    long userId = request.getUserId();
                    user = loginService.getAppUserFromId(userId);
                }

                if (user == null) {
                    System.out.println(" ---- change password : logged-in user : Possible attempt to hack in. ---- ");
                    response.setCode(5);
                    response.setMessage(DATA_NOT_PRESENT);
                } else {
                    if (!user.isactive()) {
                        response.setCode(2);
                        response.setMessage(ACCOUNT_DEACTIVATED);
                    } else {
                        String encypPwd = Util.encrypt(pwd);
                        user.setUpdated_on(new Date());
                        user.setPassword(encypPwd);
                        //user.setIsotp(false);

                        loginService.updateUser(user);
                        response.setCode(0);
                        response.setMessage(SUCCESS);
                    }
                }

            }


        }
        return new ResponseEntity<GenericResponse>(response, HttpStatus.OK);
    }

}
