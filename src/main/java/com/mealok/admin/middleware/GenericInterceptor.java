package com.mealok.admin.middleware;

import com.mealok.admin.cache.CacheUtil;
import com.mealok.admin.service.LoginService;
import com.mealok.admin.utils.Constants;
import com.mealok.admin.utils.Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by arkadutta on 24/10/16.
 */
public class GenericInterceptor extends HandlerInterceptorAdapter {

    class HackResponse {
        private int code;
        private String message;
        private String redirectURL;

        public HackResponse() {

        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getRedirectURL() {
            return redirectURL;
        }

        public void setRedirectURL(String redirectURL) {
            this.redirectURL = redirectURL;
        }
    }

    @Autowired
    LoginService loginService;  //Service which will do all data retrieval/manipulation work

    //@Autowired
    //private RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Autowired
    private ApplicationContext appContext;


    private static final String COOKIE_SESSION = "epme_session";
    private static final String INDEX_URL = "/epme/index/";
    private static final String DASHBOARD_URL = "/epme/index/dashboard/";
    private static final String[] allPass = {"/index/login/", "/index/changePassword/","/index/logout/"
            ,"/index/test/"};//temp

    private static final Logger logger = Logger.getLogger(GenericInterceptor.class);

    private boolean isallPass(String uri) {
        //boolean flag = false;

        for (String str : allPass) {
            if (uri.contains(str)) {
                return true;
            }
        }

        return false;
    }

    private boolean isSessionActive(String sessionid) {
        //responsible for checking whether the session is active or not
        if (sessionid.trim().isEmpty()) {
            return false;
        } else {
            return loginService.isSessionActive(sessionid);

        }
        //return false;
    }

    //Important Code
//    private String getMethodRequestMapping(Method method) {
//        Assert.notNull(method, "'method' must not be null");
//        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
//        if (requestMapping == null) {
//            throw new IllegalArgumentException("No @RequestMapping on: " + method.toGenericString());
//        }
//        String[] paths = requestMapping.path();
//        if (ObjectUtils.isEmpty(paths) || StringUtils.isEmpty(paths[0])) {
//            return "/";
//        }
//        /*if (paths.length > 1 && logger.isWarnEnabled()) {
//            logger.warn("Multiple paths on method " + method.toGenericString() + ", using first one");
//        }*/
//        return paths[0];
//    }
//
//    private void printRequestHandlerDetails(){
//
//        //WebApplicationContext context = new GenericWebApplicationContext();
//        RequestMappingHandlerMapping requestMappingHandlerMapping = appContext.getBean(RequestMappingHandlerMapping.class);
//        Map<RequestMappingInfo, HandlerMethod> handlerMethods =
//                requestMappingHandlerMapping.getHandlerMethods();
//
//        for(Map.Entry<RequestMappingInfo, HandlerMethod> item : handlerMethods.entrySet()) {
//            RequestMappingInfo mapping = item.getKey();
//            HandlerMethod method = item.getValue();
//
//            for (String urlPattern : mapping.getPatternsCondition().getPatterns()) {
//                System.out.println(
//                        method.getBeanType().getName() + "#" + method.getMethod().getName() +
//                                " <-- " + urlPattern);
//
//                /*if (urlPattern.equals("some specific url")) {
//                    //add to list of matching METHODS
//                }*/
//            }
//        }
//    }

    private void setupResponseForHack(HttpServletResponse response, int code) throws Exception{

        HackResponse res = new HackResponse();
        res.setCode(code);//15 no api key
        res.setMessage("You are trying to hack in. Better luck next time");
        res.setRedirectURL(INDEX_URL);

        String json = Util.convertToJSON(res);
        response.setContentType("text/x-json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write(json);

    }

    //In place of Cookie the header MealOKSession : SessionStr ; InternalAPIKey : key needs to be checked for validity
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println(" ---- I am inside the interceptor ----- ");
        System.out.println("Request URL::" + request.getRequestURL().toString()
                + ":: Start Time=" + System.currentTimeMillis() + ",  request URI -- " + request.getRequestURI() + ", other data - "
                + request);

        //logic
        String uri = request.getRequestURI();

//        printRequestHandlerDetails();

        String apiCollaboratorKey = request.getHeader(Constants.HEADER_API_KEY);
        if(apiCollaboratorKey!= null){
            System.out.println("Header collaboration key ---- $$$$$$$$$$$$$$$$$$$$  --- "+apiCollaboratorKey);
            if(!apiCollaboratorKey.equals(CacheUtil.getInstance().getAPIKey())){

                setupResponseForHack(response,15);
                return false;

            }
        }else{
            setupResponseForHack(response,15);
            return false;
        }

        if (isallPass(uri)) {
            return true;
        } else {

            String sessionId = request.getHeader(Constants.SESSION_HEADER);
            if(sessionId == null){
                if (uri.equals(INDEX_URL)) {
                    return true;
                } else if (uri.equals(DASHBOARD_URL)) {
                    //redirect him to index url
                    response.sendRedirect(INDEX_URL);
                    //return false;
                } else {
                    //any other url send the {code : 11 , message : You are trying to hack in ,redirectURL : '/epme/index/'}
                    HackResponse res = new HackResponse();
                    res.setCode(11);
                    res.setMessage("You are trying to hack in. Better luck next time");
                    res.setRedirectURL(INDEX_URL);

                    String json = Util.convertToJSON(res);
                    response.setContentType("text/x-json;charset=UTF-8");
                    response.setHeader("Cache-Control", "no-cache");
                    response.getWriter().write(json);
                    //json.write(response.getWriter());
                    //return true;
                }
            }


            System.out.println("Session value -- " + sessionId);
            boolean flag = isSessionActive(sessionId);
            if (!flag) {
                if (uri.equals(INDEX_URL)) {
                    return true;
                } else if (uri.equals(DASHBOARD_URL)) {
                    //redirect him to index url
                    response.sendRedirect(INDEX_URL);
                    //return false;
                } else {
                    //any other url send the {code : 11 , message : You are trying to hack in ,redirectURL : '/epme/index/'}
                    HackResponse res = new HackResponse();
                    res.setCode(11);
                    res.setMessage("You are trying to hack in. Better luck next time");
                    res.setRedirectURL(INDEX_URL);

                    String json = Util.convertToJSON(res);
                    response.setContentType("text/x-json;charset=UTF-8");
                    response.setHeader("Cache-Control", "no-cache");
                    response.getWriter().write(json);
                    //json.write(response.getWriter());
                    //return true;
                }
            } else {
                if (uri.equals(INDEX_URL)) {
                    //redirect to dashboard
                    response.sendRedirect(DASHBOARD_URL);
                } else if (uri.equals(DASHBOARD_URL)) {
                    //add a session attribute as part of request
                    request.setAttribute(Constants.SESSION_ID, sessionId);

                    return true;
                } else {
                    request.setAttribute(Constants.SESSION_ID, sessionId);
                    return true;
                }

            }


        }


        //request.setAttribute("startTime", startTime);
        //if returned false, we need to make sure 'response' is sent
        return false;
    }


}
