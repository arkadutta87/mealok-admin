package com.mealok.admin.service;

import com.mealok.admin.model.AppUser;
import com.mealok.admin.model.MealokSession;

public interface LoginService {

    public AppUser getAppUser(String username, String password);

    public boolean saveSession(AppUser user, MealokSession session);

    public void updateUser(AppUser user);

    public AppUser getAppUserByUsername(String username);

    public MealokSession getSession(String sessionStr);

    public void updateSession(MealokSession sess);

    public boolean isSessionActive(String sessionId);

    public AppUser getUserFromSession(String sessionId);

    public AppUser getAppUserFromId(long id);
}
