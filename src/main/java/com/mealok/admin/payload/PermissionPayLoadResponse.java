package com.mealok.admin.payload;

import java.util.List;

/**
 * Created by arkadutta on 11/11/16.
 */
public class PermissionPayLoadResponse {

    private int code;
    private String message;

    private List<PermissionPayLoad> permissions;

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

    public List<PermissionPayLoad> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionPayLoad> permissions) {
        this.permissions = permissions;
    }
}
