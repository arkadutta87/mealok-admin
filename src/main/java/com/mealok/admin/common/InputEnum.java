package com.mealok.admin.common;

/**
 * Created by arkadutta on 24/10/16.
 */
public enum InputEnum {

    USERNAME("username") ,
    FIRST_NAME("firstname"),
    LAST_NAME("lastname"),
    MOBILE("mobile"),
    PASSWORD("password"),
    EMAIL("email"),
    RETYPE_PASSWORD("retypepassword"),
    NULL("NULL");

    public final String value;

    private InputEnum(String value){
        this.value = value;
    }
}

