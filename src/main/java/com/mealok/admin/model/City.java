package com.mealok.admin.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by arkadutta on 08/11/16.
 */
@Entity
@Table(name = "city")
public class City {

    /*
    CREATE TABLE `city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
  `is_enabled` tinyint(1) NOT NULL DEFAULT '0',
  `state_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL DEFAULT '',
  `name_id` varchar(80) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_city` (`name`,`is_enabled`),
  KEY `city_refs_id_d043b34a` (`state_id`),
  CONSTRAINT `city_refs_id_d043b34a` FOREIGN KEY (`state_id`) REFERENCES `state` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "created_on", columnDefinition = "DATETIME" ,updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_on;

    @Column(name = "is_enabled", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean is_enabled;

    @Column(name = "is_dummy", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean is_dummy;

    @Column(name = "name")
    private String name;

    @Column(name = "name_id")
    private String name_id;

    @ManyToOne
    @JoinColumn(name="state_id", nullable=false)
    private State state;

    @OneToMany(mappedBy = "city",fetch = FetchType.LAZY)
    private Set<Area> areas  = new HashSet<Area>(0);

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }

    public boolean is_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(boolean is_enabled) {
        this.is_enabled = is_enabled;
    }

    public boolean is_dummy() {
        return is_dummy;
    }

    public void setIs_dummy(boolean is_dummy) {
        this.is_dummy = is_dummy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_id() {
        return name_id;
    }

    public void setName_id(String name_id) {
        this.name_id = name_id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Set<Area> getAreas() {
        return areas;
    }

    public void setAreas(Set<Area> areas) {
        this.areas = areas;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", created_on=" + created_on +
                ", is_enabled=" + is_enabled +
                ", is_dummy=" + is_dummy +
                ", name='" + name + '\'' +
                ", name_id='" + name_id + '\'' +
                '}';
    }
}
