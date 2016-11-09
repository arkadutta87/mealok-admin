package com.mealok.admin.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by arkadutta on 22/10/16.
 */
@Entity
@Table(name = "app_group")
public class AppGroup {

    /*
    id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(80) NOT NULL DEFAULT 'dummy',
  `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
  `updated_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "updated_on", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_on;

    @Column(name = "created_on", columnDefinition = "DATETIME" ,updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_on;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "app_group_permissions", joinColumns = {
            @JoinColumn(name = "group_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "permission_id",
                    nullable = false, updatable = false) })
    private Set<AppPermission> permissions = new HashSet<AppPermission>(0);

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userGroups")
    private Set<AppUser> users = new HashSet<AppUser>(0);

    public AppGroup(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Set<AppUser> getUsers() {
        return users;
    }

    public void setUsers(Set<AppUser> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "AppGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", updated_on=" + updated_on +
                ", created_on=" + created_on +
                '}';
    }
}
