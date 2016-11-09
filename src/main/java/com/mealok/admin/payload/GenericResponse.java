package com.mealok.admin.payload;

/**
 * Created by arkadutta on 26/10/16.
 */
public class GenericResponse {

    private int code;
    private String message;

    public GenericResponse(){

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

    @Override
    public String toString() {
        return "PackageDeleteResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
