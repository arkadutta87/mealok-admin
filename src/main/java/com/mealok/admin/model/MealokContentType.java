package com.mealok.admin.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by arkadutta on 22/10/16.
 */
@Entity
@Table(name = "mealok_content_type")
public class MealokContentType {

    /*
    `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_label` varchar(100) NOT NULL,
  `model` varchar(100) NOT NULL,
  `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
  `updated_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "app_label")
    private String app_label;

    @Column(name = "model")
    private String model;

    @Column(name = "updated_on", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_on;

    @Column(name = "created_on", columnDefinition = "DATETIME" ,updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_on;

    @OneToMany(mappedBy = "contentType",fetch = FetchType.LAZY)
    private Set<AppPermission> permissions  = new HashSet<AppPermission>(0);

    @OneToMany(mappedBy = "contentType",fetch = FetchType.LAZY)
    private Set<MealokAdminLog> logs  = new HashSet<MealokAdminLog>(0);

    public MealokContentType(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApp_label() {
        return app_label;
    }

    public void setApp_label(String app_label) {
        this.app_label = app_label;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(Date updated_on) {
        this.updated_on = updated_on;
    }

    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }

    public Set<AppPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<AppPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<MealokAdminLog> getLogs() {
        return logs;
    }

    public void setLogs(Set<MealokAdminLog> logs) {
        this.logs = logs;
    }

    @Override
    public String toString() {
        return "MealokContentType{" +
                "id=" + id +
                ", app_label='" + app_label + '\'' +
                ", model='" + model + '\'' +
                ", updated_on=" + updated_on +
                ", created_on=" + created_on +
                '}';
    }
}
