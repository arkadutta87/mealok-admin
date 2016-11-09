package com.mealok.admin.common;

/**
 * Created by arkadutta on 24/10/16.
 */
public class TokenRedisModel {

    private String username;
    private long id;
    private String  pwd;

    public TokenRedisModel(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String  pwd) {
        this.pwd = pwd;
    }
}
