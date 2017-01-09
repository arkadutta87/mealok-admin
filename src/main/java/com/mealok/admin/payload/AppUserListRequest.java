package com.mealok.admin.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by arkadutta on 11/11/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppUserListRequest {

    private int step;
    private AppUserFilter filters;

    public AppUserListRequest(){

    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public AppUserFilter getFilters() {
        return filters;
    }

    public void setFilters(AppUserFilter filters) {
        this.filters = filters;
    }
}
