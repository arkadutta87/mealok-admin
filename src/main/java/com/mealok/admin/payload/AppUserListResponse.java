package com.mealok.admin.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

/**
 * Created by arkadutta on 11/11/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppUserListResponse {

    private int code ;
    private String message;

    private List<AppUserListObject> data;


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

    public List<AppUserListObject> getData() {
        return data;
    }

    public void setData(List<AppUserListObject> data) {
        this.data = data;
    }
}
