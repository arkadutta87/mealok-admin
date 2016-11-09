package com.mealok.admin.payload;

/**
 * Created by arkadutta on 26/10/16.
 */
public class ChangePasswordRequestLogged {

    private String newPassword;
    private boolean isSuperUser;
    private long userId;

    public ChangePasswordRequestLogged() {

    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public boolean isSuperUser() {
        return isSuperUser;
    }

    public void setSuperUser(boolean superUser) {
        isSuperUser = superUser;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ChangePasswordRequestLogged{" +
                "newPassword='" + newPassword + '\'' +
                ", isSuperUser=" + isSuperUser +
                ", userId=" + userId +
                '}';
    }
}
