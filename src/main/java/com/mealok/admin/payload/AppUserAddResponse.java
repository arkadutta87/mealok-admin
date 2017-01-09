package com.mealok.admin.payload;

/**
 * Created by arkadutta on 10/11/16.
 */
public class AppUserAddResponse {

    private long id; //app user id
    private int code;
    private String message;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
