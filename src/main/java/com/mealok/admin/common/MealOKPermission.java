package com.mealok.admin.common;

/**
 * Created by arkadutta on 10/11/16.
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MealOKPermission {

    String   operator(); //OR and AND
    String[] permissionCodes();
}
