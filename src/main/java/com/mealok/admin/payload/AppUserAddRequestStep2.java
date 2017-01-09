package com.mealok.admin.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by arkadutta on 10/11/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppUserAddRequestStep2 {

    private long app_user_id;
    private String firstName;
    private String lastName;

    private String email;
    private boolean is_super_user;

    private long[] groupId;
    private long[] permissionsId;


    public AppUserAddRequestStep2(){

    }

    public long getApp_user_id() {
        return app_user_id;
    }

    public void setApp_user_id(long app_user_id) {
        this.app_user_id = app_user_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long[] getGroupId() {
        return groupId;
    }

    public void setGroupId(long[] groupId) {
        this.groupId = groupId;
    }

    public long[] getPermissionsId() {
        return permissionsId;
    }

    public void setPermissionsId(long[] permissionsId) {
        this.permissionsId = permissionsId;
    }

    public boolean is_super_user() {
        return is_super_user;
    }

    public void setIs_super_user(boolean is_super_user) {
        this.is_super_user = is_super_user;
    }
}
