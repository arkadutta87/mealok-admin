package com.mealok.admin.service;

import com.mealok.admin.common.MealOKPermission;
import com.mealok.admin.model.AppPermission;
import com.mealok.admin.model.AppUser;
import com.mealok.admin.model.MealokSession;

import java.util.List;

/**
 * Created by arkadutta on 10/11/16.
 */
public interface AppUserService {

    public AppUser createAppUser(AppUser user,long owner_id);

    public AppUser getUserFromSession(String sessionId);

    public List<AppPermission> listPermissions();

    public AppUser getAppUserFromID(long id);

    public List<AppPermission> getAppPermissionListFromIds(long[] ids);

    public AppUser createAppUserUpdate(AppUser user,long owner_id,String message);
}
