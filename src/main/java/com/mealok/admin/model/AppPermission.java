package com.mealok.admin.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by arkadutta on 22/10/16.
 */
@Entity
@Table(name = "app_permission")
public class AppPermission {

    /*
     `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `content_type_id` int(11) NOT NULL,
  `codename` varchar(100) NOT NULL,
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "codename")
    private String codename;

    @ManyToOne
    @JoinColumn(name="content_type_id", nullable=false)
    private MealokContentType contentType;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "permissions")
    private Set<AppGroup> groups = new HashSet<AppGroup>(0);

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userPermissions")
    private Set<AppUser> users = new HashSet<AppUser>(0);


    public AppPermission(){

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

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public MealokContentType getContentType() {
        return contentType;
    }

    public void setContentType(MealokContentType contentType) {
        this.contentType = contentType;
    }

    public Set<AppGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<AppGroup> groups) {
        this.groups = groups;
    }

    public Set<AppUser> getUsers() {
        return users;
    }

    public void setUsers(Set<AppUser> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "AppPermission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", codename='" + codename + '\'' +
                '}';
    }
}
