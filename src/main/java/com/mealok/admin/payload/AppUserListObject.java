package com.mealok.admin.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by arkadutta on 11/11/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppUserListObject {

    private long id;
    private String username;
    private boolean isActive;
    private boolean is_super_user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean is_super_user() {
        return is_super_user;
    }

    public void setIs_super_user(boolean is_super_user) {
        this.is_super_user = is_super_user;
    }
}
