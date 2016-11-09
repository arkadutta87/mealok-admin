package com.mealok.admin.controllers;

import com.mealok.admin.payload.LogoutRequest;
import com.mealok.admin.payload.LogoutResponse;
import com.mealok.admin.payload.TestRequest;
import com.mealok.admin.payload.TestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by arkadutta on 25/10/16.
 */
@RestController
public class TestController {

    @RequestMapping(value = "/index/test/", method = RequestMethod.POST)
    public ResponseEntity<TestResponse> loginAppUser(@RequestBody TestRequest request) {
        TestResponse response = new TestResponse();

        response.setCode(0);
        response.setMessage("Its somehow working");

        return new ResponseEntity<TestResponse>(response, HttpStatus.OK);
    }


}
