package com.mealok.admin.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by arkadutta on 24/10/16.
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e){
        e.printStackTrace();
        response.setContentType("text/x-json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");

        String json = "{\"code\" : 15}";

        try {
            response.getWriter().write(json);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
