package com.mealok.admin.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by arkadutta on 21/10/16.
 */
@Entity
@Table(name = "app_user")
public class AppUser {

    /*
    `id` int(11) NOT NULL AUTO_INCREMENT,//
    `password` varchar(50) NOT NULL,//
    `last_login` datetime DEFAULT NULL,//
    `is_superuser` tinyint(1) NOT NULL default '0' ,//
     `username` varchar(100) NOT NULL default '',//
     `first_name` varchar(30) NOT NULL default '',//
     `last_name` varchar(30) NOT NULL default '',//
     `email` varchar(254) DEFAULT NULL,//
     `is_staff` tinyint(1) NOT NULL default '1',//
     `is_active` tinyint(1) NOT NULL default '1',//
      `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',//
       `updated_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01'//
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Pattern(regexp=".+@.+\\..+", message="Username should be email")
    @Column(name = "username")
    private String username;//should be of type email

    @Column(name = "passwd")
    private String password;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Pattern(regexp=".+@.+\\..+", message="Wrong email!")
    @Column(name = "email")
    private String email;

    @Column(name = "is_superuser", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean issuperuser;

    @Column(name = "is_active", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isactive;

    @Column(name = "is_staff", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isstaff;

    @Column(name = "is_otp", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isotp;

    @Column(name = "is_email_verified", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean is_email_verified;

    @Column(name = "updated_on", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_on;

    @Column(name = "created_on", columnDefinition = "DATETIME" ,updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_on;

    @Column(name = "last_login", columnDefinition = "DATETIME" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date last_login;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<MealokSession> sessions  = new HashSet<MealokSession>(0);

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "app_user_user_permissions", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "permission_id",
                    nullable = false, updatable = false) })
    private Set<AppPermission> userPermissions = new HashSet<AppPermission>(0);

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "app_user_group", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "grp_id",
                    nullable = false, updatable = false) })
    private Set<AppGroup> userGroups = new HashSet<AppGroup>(0);

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<MealokAdminLog> logs  = new HashSet<MealokAdminLog>(0);

    public AppUser(){

    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean issuperuser() {
        return issuperuser;
    }

    public void setIssuperuser(boolean issuperuser) {
        this.issuperuser = issuperuser;
    }

    public boolean isactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    public boolean isstaff() {
        return isstaff;
    }

    public void setIsstaff(boolean isstaff) {
        this.isstaff = isstaff;
    }

    public boolean isotp() {
        return isotp;
    }

    public void setIsotp(boolean isotp) {
        this.isotp = isotp;
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

    public Date getLast_login() {
        return last_login;
    }

    public void setLast_login(Date last_login) {
        this.last_login = last_login;
    }

    public Set<MealokSession> getSessions() {
        return sessions;
    }

    public void setSessions(Set<MealokSession> sessions) {
        this.sessions = sessions;
    }

    public Set<AppPermission> getUserPermissions() {
        return userPermissions;
    }

    public void setUserPermissions(Set<AppPermission> userPermissions) {
        this.userPermissions = userPermissions;
    }

    public Set<AppGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Set<AppGroup> userGroups) {
        this.userGroups = userGroups;
    }

    public Set<MealokAdminLog> getLogs() {
        return logs;
    }

    public void setLogs(Set<MealokAdminLog> logs) {
        this.logs = logs;
    }

    public boolean is_email_verified() {
        return is_email_verified;
    }

    public void setIs_email_verified(boolean is_email_verified) {
        this.is_email_verified = is_email_verified;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", issuperuser=" + issuperuser +
                ", isactive=" + isactive +
                ", isstaff=" + isstaff +
                ", isotp=" + isotp +
                ", is_email_verified=" + is_email_verified +
                ", updated_on=" + updated_on +
                ", created_on=" + created_on +
                ", last_login=" + last_login +
                '}';
    }
}
