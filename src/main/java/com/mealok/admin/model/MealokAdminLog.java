package com.mealok.admin.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

import org.hibernate.type.LongType;

/**
 * Created by arkadutta on 22/10/16.
 */
@Entity
@Table(name = "mealok_admin_log")
public class MealokAdminLog {

    /*
    `id` int(11) NOT NULL AUTO_INCREMENT,
  `action_time` datetime NOT NULL,
  `user_id` int(11) NOT NULL,
  `content_type_id` int(11) DEFAULT NULL,
  `object_id` longtext,
  `object_repr` varchar(200) NOT NULL,
  `action_flag` smallint(5) unsigned NOT NULL,
  `change_message` longtext NOT NULL,
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "action_time", columnDefinition = "DATETIME", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date action_time;

    @Column(name = "object_id")
    @Lob
    private String object_id;

    @Column(name = "change_message")
    @Lob
    private String change_message;//need to check whether this is correct or not

    @Column(name = "object_repr")
    private String object_repr;

    @Column(name = "action_flag", columnDefinition = "SMALLINT")
//    @Type(type = "org.hibernate.type.NumericBooleanType")
    private int action_flag;// need to check whether this is correct or not

//    @Column(name = "user_id")
//    private long user_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

//    @Column(name = "content_type_id")
//    private long content_type_id;

    @ManyToOne
    @JoinColumn(name = "content_type_id", nullable = false)
    private MealokContentType contentType;

    public MealokAdminLog() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getAction_time() {
        return action_time;
    }

    public void setAction_time(Date action_time) {
        this.action_time = action_time;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

    public String getChange_message() {
        return change_message;
    }

    public void setChange_message(String change_message) {
        this.change_message = change_message;
    }

    public String getObject_repr() {
        return object_repr;
    }

    public void setObject_repr(String object_repr) {
        this.object_repr = object_repr;
    }

    public int getAction_flag() {
        return action_flag;
    }

    public void setAction_flag(int action_flag) {
        this.action_flag = action_flag;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public MealokContentType getContentType() {
        return contentType;
    }

    public void setContentType(MealokContentType contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "MealokAdminLog{" +
                "id=" + id +
                ", action_time=" + action_time +
                ", object_id='" + object_id + '\'' +
                ", change_message='" + change_message + '\'' +
                ", object_repr='" + object_repr + '\'' +
                ", action_flag=" + action_flag +
                '}';
    }
}


