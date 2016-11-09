CREATE TABLE app_user(
id int(11) NOT NULL AUTO_INCREMENT,passwd varchar(50) NOT NULL,last_login datetime DEFAULT NULL,is_superuser tinyint(1) NOT NULL default '0' ,username varchar(100) NOT NULL default '',first_name varchar(30) NOT NULL default '',last_name varchar(30) NOT NULL default '', email varchar(254) DEFAULT NULL,is_staff tinyint(1) NOT NULL default '1', is_active tinyint(1) NOT NULL default '1', created_on datetime NOT NULL DEFAULT '1970-01-01 00:00:01', updated_on datetime NOT NULL DEFAULT '1970-01-01 00:00:01', PRIMARY KEY (`id`), UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

ALTER TABLE `app_user` ADD `is_otp` tinyint(1) NOT NULL default '0';

CREATE TABLE `app_group` (`id` int(11) NOT NULL AUTO_INCREMENT,`name` varchar(80) NOT NULL default 'dummy',`created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',`updated_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',PRIMARY KEY (`id`),UNIQUE KEY `name` (`name`)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE `mealok_content_type` (`id` int(11) NOT NULL AUTO_INCREMENT,`app_label` varchar(100) NOT NULL,`model` varchar(100) NOT NULL,`created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',`updated_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',PRIMARY KEY (`id`),UNIQUE KEY `app_label` (`app_label`,`model`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `mealok_session` (`id` int(11) NOT NULL AUTO_INCREMENT,`session_id` varchar(100) NOT NULL Default '',`logged_in` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',`logged_out` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',`is_enabled` tinyint(1) NOT NULL default '1',`app_user_id` int(11)  NOT NULL,`expire_date` datetime NOT NULL,PRIMARY KEY (`id`),KEY `app_user_id_1` (`app_user_id`),KEY `mealok_session_b7b81f0c` (`expire_date`),CONSTRAINT `mealok_session_ibfk_1` FOREIGN KEY (`app_user_id`) REFERENCES `app_user` (`id`)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE `app_permission` (`id` int(11) NOT NULL AUTO_INCREMENT,`name` varchar(255) DEFAULT NULL,`content_type_id` int(11) NOT NULL,`codename` varchar(100) NOT NULL,PRIMARY KEY (`id`),UNIQUE KEY `content_type_id` (`content_type_id`,`codename`),KEY `auth_permission_37ef4eb4` (`content_type_id`),CONSTRAINT `content_type_id_refs_id_d043b34a` FOREIGN KEY (`content_type_id`) REFERENCES `mealok_content_type` (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `app_group_permissions` (`id` int(11) NOT NULL AUTO_INCREMENT,`group_id` int(11) NOT NULL,`permission_id` int(11) NOT NULL,PRIMARY KEY (`id`),UNIQUE KEY `group_id` (`group_id`,`permission_id`),KEY `auth_group_permissions_5f412f9a` (`group_id`),KEY `auth_group_permissions_83d7f98b` (`permission_id`),CONSTRAINT `group_id_refs_id_f4b32aac` FOREIGN KEY (`group_id`) REFERENCES `app_group` (`id`),CONSTRAINT `permission_id_refs_id_6ba0f519` FOREIGN KEY (`permission_id`) REFERENCES `app_permission` (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `app_user_user_permissions` (`id` int(11) NOT NULL AUTO_INCREMENT,`user_id` int(11) NOT NULL,`permission_id` int(11) NOT NULL,PRIMARY KEY (`id`),UNIQUE KEY `user_id` (`user_id`,`permission_id`),KEY `auth_user_user_permissions_6340c63c` (`user_id`),KEY `auth_user_user_permissions_83d7f98b` (`permission_id`),CONSTRAINT `permission_id_refs_id_35d9ac25` FOREIGN KEY (`permission_id`) REFERENCES `app_permission` (`id`),CONSTRAINT `user_id_refs_id_4dc23c39` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `app_user_group`(`id` int(11) NOT NULL AUTO_INCREMENT,`user_id` int(11) NOT NULL,`grp_id` int(11) NOT NULL , PRIMARY KEY (`id`),UNIQUE KEY `app_user_group_1` (`user_id`,`grp_id`),KEY `app_user_group_6340c63c` (`user_id`),KEY `app_user_group_83d7f98b` (`grp_id`),CONSTRAINT `app_user_group_id_refs_id_35d9ac25` FOREIGN KEY (`grp_id`) REFERENCES `app_group` (`id`),CONSTRAINT `app_user_group_id_refs_id_4dc23c39` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `mealok_admin_log` (`id` int(11) NOT NULL AUTO_INCREMENT,`action_time` datetime NOT NULL,`user_id` int(11) NOT NULL,`content_type_id` int(11) DEFAULT NULL,`object_id` longtext,`object_repr` varchar(200) NOT NULL, `action_flag` smallint(5) unsigned NOT NULL,`change_message` longtext NOT NULL,PRIMARY KEY (`id`), KEY `django_admin_log_6340c63c` (`user_id`), KEY `django_admin_log_37ef4eb4` (`content_type_id`),CONSTRAINT `content_type_id_refs_id_93d2d1f8` FOREIGN KEY (`content_type_id`) REFERENCES `mealok_content_type` (`id`),CONSTRAINT `user_id_refs_id_c0d12874` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `meal_ok_user`
(`id` int(11) NOT NULL AUTO_INCREMENT,
`uid` varchar(80) NOT NULL default 'dummy',
`created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
`is_enabled` tinyint(1) NOT NULL default '0',
`referred_by` int(11) default NULL,
`approval_code_id` int(11) NOT NULL,
`mealok_user_account_id` int(11) NOT NULL,
`meal_ok_user_vendor_id` int(11) NOT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `uniq_1` (`uid`,`is_enabled`,`approval_code_id`),
CONSTRAINT `meal_ok_user_refs_id_d043b34a` FOREIGN KEY (`mealok_user_account_id`) REFERENCES `meal_ok_user_account` (`id`),
CONSTRAINT `meal_ok_user_refs_id_d043b34b` FOREIGN KEY (`approval_code_id`) REFERENCES `approval_code` (`id`),
CONSTRAINT `meal_ok_user_refs_id_d043b34c` FOREIGN KEY (`meal_ok_user_vendor_id`) REFERENCES `vendor` (`id`),
CONSTRAINT `meal_ok_user_refs_id_d043b34e` FOREIGN KEY (`referred_by`) REFERENCES `meal_ok_user` (`id`))
 ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE `vendor_profile_pic`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `vendor_id` int(11) NOT NULL,
   `image_url` longtext,
   `is_main` tinyint(1) NOT NULL default '0',
   PRIMARY KEY (`id`),
   CONSTRAINT `vendor_area_refs_id_d043b34a` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;//done

CREATE TABLE `vendor_educational_qualification`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `uid` varchar(80) default '',
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `vendor_id` int(11) NOT NULL,
   `details` longtext,
   `details_id` varchar(70) default '',
   PRIMARY KEY (`id`),
   CONSTRAINT `vendor_educational_qualification_refs_id_d043b34a` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;//done

CREATE TABLE `vendor_professional_qualification`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
      `uid` varchar(80) default '',
      `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
      `is_enabled` tinyint(1) NOT NULL default '0',
      `vendor_id` int(11) NOT NULL,
      `details` longtext,
      `details_id` varchar(70) default '',
      PRIMARY KEY (`id`),
      CONSTRAINT `vendor_professional_qualification_refs_id_d043b34a` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`id`)
      )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;//done

CREATE TABLE `vendor_acheivements`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
      `uid` varchar(80) default '',
      `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
      `is_enabled` tinyint(1) NOT NULL default '0',
      `vendor_id` int(11) NOT NULL,
      `details` longtext,
      `details_id` varchar(70) default '',
      PRIMARY KEY (`id`),
      CONSTRAINT `vendor_acheivements_refs_id_d043b34a` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`id`)
      )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;//done

CREATE TABLE `vendor_area`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `vendor_id` int(11) NOT NULL,
   `area_id` int(11) NOT NULL,
   PRIMARY KEY (`id`),
   CONSTRAINT `vendor_area_refs_id_d043b34a` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`id`),
   CONSTRAINT `vendor_area_refs_id_d043b34b` FOREIGN KEY (`area_id`) REFERENCES `area` (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;//done

CREATE TABLE `vendor_language`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `vendor_id` int(11) NOT NULL,
   `language_id` int(11) NOT NULL,
   PRIMARY KEY (`id`),
   CONSTRAINT `vendor_language_refs_id_d043b34a` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`id`),
   CONSTRAINT `vendor_language_refs_id_d043b34b` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;//done

CREATE TABLE `vendor_extra_functionalities`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `vendor_id` int(11) NOT NULL,
   `extra_func_id` int(11) NOT NULL,
   PRIMARY KEY (`id`),
   CONSTRAINT `vendor_extra_functionalities_refs_id_d043b34a` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`id`),
   CONSTRAINT `vendor_extra_functionalities_refs_id_d043b34b` FOREIGN KEY (`extra_func_id`) REFERENCES `extra_functionalities` (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;//done

CREATE TABLE `vendor_cuisine_expert`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `vendor_id` int(11) NOT NULL,
   `cuisine_sub_group_id` int(11) NOT NULL,
   PRIMARY KEY (`id`),
   CONSTRAINT `vendor_cuisine_expert_refs_id_d043b34a` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`id`),
   CONSTRAINT `vendor_cuisine_expert_refs_id_d043b34b` FOREIGN KEY (`cuisine_sub_group_id`) REFERENCES `cuisine_sub_group` (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;//done

CREATE TABLE `vendor_cuisine_confortable`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `vendor_id` int(11) NOT NULL,
   `cuisine_sub_group_id` int(11) NOT NULL,
   PRIMARY KEY (`id`),
   CONSTRAINT `vendor_cuisine_confortable_refs_id_d043b34a` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`id`),
      CONSTRAINT `vendor_cuisine_confortable_refs_id_d043b34b` FOREIGN KEY (`cuisine_sub_group_id`) REFERENCES `cuisine_sub_group` (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;//done

CREATE TABLE `vendor_time_of_day`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `vendor_id` int(11) NOT NULL,
   `time_id_day_id` int(11) NOT NULL,
   PRIMARY KEY (`id`),
   CONSTRAINT `vendor_time_of_day_refs_id_d043b34a` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`id`),
   CONSTRAINT `vendor_time_of_day_refs_id_d043b34b` FOREIGN KEY (`time_id_day_id`) REFERENCES `time_of_day` (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `vendor`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `uid` varchar(80) NOT NULL default '',
   `category_id` int(11) NOT NULL ,
   `experience_id` int(11) NOT NULL ,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `date_of_submission` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `popularName` longtext,
   `popularName_id` varchar(70) default '',
   `small_description` longtext,
   `small_description_id` varchar(70) default '',
   `long_description` longtext,
   `long_description_id` varchar(70) default '',
   `country_id` int(11) NOT NULL,
   `city_id` int(11) NOT NULL,
   `rate_id` int(11) NOT NULL,
   `approval_code_id` int(11) NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_recipe` (`uid`,`is_enabled`,`approval_code_id`),
   CONSTRAINT `vendor_refs_id_d043b34a` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
   CONSTRAINT `vendor_refs_id_d043b34b` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
   CONSTRAINT `vendor_refs_id_d043b34c` FOREIGN KEY (`approval_code_id`) REFERENCES `approval_code` (`id`),
   CONSTRAINT `vendor_refs_id_d043b34d` FOREIGN KEY (`rate_id`) REFERENCES `rate` (`id`))
    ENGINE=InnoDB  DEFAULT CHARSET=utf8; //done

CREATE TABLE `id_proof_category`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `category` longtext,
   `category_id` varchar(70) default '',
   PRIMARY KEY (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `experience`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `data` int(11) NOT NULL ,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `experience_unit_id` int(11) NOT NULL ,
   PRIMARY KEY (`id`),
   CONSTRAINT `experience_refs_id_d043b34a` FOREIGN KEY (`experience_unit_id`) REFERENCES `experience_unit` (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `experience_unit`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `value` longtext,
   `value_id` varchar(70) default '',
   PRIMARY KEY (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `address`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `country_id` int(11) NOT NULL,
   `state_id` int(11) NOT NULL,
   `city_id` int(11) NOT NULL,
   `pincode` int(10) NOT NULL default '0',
   `type_id` int(11) NOT NULL,
   `lineOne` longtext,
   `lineOne_id` varchar(70) default '',
   `lineTwo` longtext,
   `lineTwo_id` varchar(70) default '',
   `meal_ok_account_id` int(11) NOT NULL,
   PRIMARY KEY (`id`),
   CONSTRAINT `address_refs_id_d043b34a` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
   CONSTRAINT `address_refs_id_d043b34b` FOREIGN KEY (`state_id`) REFERENCES `state` (`id`),
   CONSTRAINT `address_refs_id_d043b34c` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
   CONSTRAINT `address_refs_id_d043b34d` FOREIGN KEY (`type_id`) REFERENCES `address_category` (`id`),
   CONSTRAINT `address_refs_id_d043b34e` FOREIGN KEY (`meal_ok_account_id`) REFERENCES `meal_ok_user_account` (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `address_category`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `type` longtext,
   `type_id` varchar(70) default '',
   PRIMARY KEY (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;//done

CREATE TABLE `language`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `name` longtext,
   `name_id` varchar(70) default '',
   `association_text` longtext,
   `association_text_id` varchar(70) default '',
   PRIMARY KEY (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;//done

CREATE TABLE `extra_functionalities`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `text` longtext,
   `text_id` varchar(70) default '',
   `someExtraText` longtext,
   `someExtraText_id` varchar(70) default '',
   PRIMARY KEY (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;//done

CREATE TABLE `language_encoding`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `default_tab` tinyint(1) NOT NULL default '0',
   `name` longtext,
   `name_id` varchar(70) default '',
   PRIMARY KEY (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;//done

CREATE TABLE `vendor_category`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `default_tab` tinyint(1) NOT NULL default '0',
   `name` longtext,
   `name_id` varchar(70) default '',
   `priority` int(7) NOT NULL,
   PRIMARY KEY (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `rate`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `rate_unit_id` int(11) NOT NULL,
   `person` int(9) NOT NULL,
   `value` double(11,2) DEFAULT '0.00',
   PRIMARY KEY (`id`),
   CONSTRAINT `rate_refs_id_d043b34a` FOREIGN KEY (`rate_unit_id`) REFERENCES `rate_unit` (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;//done

CREATE TABLE `rate_unit`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `unit` varchar(100) NOT NULL default '',
   `unit_id` varchar(80) NOT NULL default '',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_rate_unit` (`unit`,`is_enabled`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;//done

CREATE TABLE `gender`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `text` varchar(100) NOT NULL default '',
   `text_id` varchar(80) NOT NULL default '',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_ingredient_nutrients` (`text`,`is_enabled`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `meal_ok_user_account`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `date_of_birth` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `firstName` varchar(100),
   `firstName_id` varchar(70) default '',
   `lastName` varchar(100),
   `lastName_id` varchar(70) default '',
   `main_contact` varchar(50),
   `alternate_contact` varchar(50),
   `email` varchar(100),
   `gender_id` int(11) NOT NULL ,
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_season` (`firstName`,`lastName`,`is_enabled`),
   CONSTRAINT `meal_ok_user_account_refs_id_d043b34a` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`))
    ENGINE=InnoDB  DEFAULT CHARSET=utf8; //done

CREATE TABLE `ingredient_nutrients`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `name` varchar(100) NOT NULL default '',
   `name_id` varchar(80) NOT NULL default '',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_ingredient_nutrients` (`name`,`is_enabled`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `ingredient_nutrient_map`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `ingredient_uid` varchar(80) NOT NULL default '',
   `nutrient_id` int(11) NOT NULL,
   `value_unit_id` int(11) NOT NULL,
   `quantity` double(9,2) DEFAULT '0.00',
   PRIMARY KEY (`id`),
   CONSTRAINT `ingredient_nutrient_map_cost_refs_id_d043b34a` FOREIGN KEY (`nutrient_id`) REFERENCES `ingredient_nutrients` (`id`),
   CONSTRAINT `ingredient_nutrient_map_cost_refs_id_d043b34b` FOREIGN KEY (`value_unit_id`) REFERENCES `value_unit` (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `country`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `name` varchar(100) NOT NULL default '',
   `name_id` varchar(80) NOT NULL default '',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_country` (`name`,`is_enabled`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `state`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `country_id` int(11) NOT NULL,
   `name` varchar(100) NOT NULL default '',
   `name_id` varchar(80) NOT NULL default '',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_state` (`name`,`is_enabled`),
   CONSTRAINT `state_refs_id_d043b34a` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `city`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `state_id` int(11) NOT NULL,
   `name` varchar(100) NOT NULL default '',
   `name_id` varchar(80) NOT NULL default '',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_city` (`name`,`is_enabled`),
   CONSTRAINT `city_refs_id_d043b34a` FOREIGN KEY (`state_id`) REFERENCES `state` (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `area`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `city_id` int(11) NOT NULL,
   `name` varchar(100) NOT NULL default '',
   `name_id` varchar(80) NOT NULL default '',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_area` (`name`,`is_enabled`),
   CONSTRAINT `area_refs_id_d043b34a` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `ingredient_cost`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `uid` varchar(80) NOT NULL default '',
   `season_uid` varchar(80) NOT NULL default '',
   `ingredient_uid` varchar(80) NOT NULL default '',
   `price_unit_id` int(11) NOT NULL,
   `state_id` int(11) NOT NULL,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `name` varchar(100) NOT NULL default '',
   `name_id` varchar(80) NOT NULL default '',
   `ingredient_price` double(11,2) DEFAULT '0.00',
   `approval_code_id` int(11) NOT NULL,
   PRIMARY KEY (`id`),
   CONSTRAINT `ingredient_cost_refs_id_d043b34a` FOREIGN KEY (`state_id`) REFERENCES `state` (`id`),
   CONSTRAINT `ingredient_cost_refs_id_d043b34b` FOREIGN KEY (`price_unit_id`) REFERENCES `price_unit` (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `price_unit`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `name` varchar(100) NOT NULL default '',
   `name_id` varchar(80) NOT NULL default '',
   PRIMARY KEY (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `ingredient_picture`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `ingredient_uid` varchar(80) NOT NULL default '',
   `image_url` longtext,
   PRIMARY KEY (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;//done

CREATE TABLE `ingredient_season`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `ingredient_uid` varchar(80) NOT NULL default '',
   `season_uid` int(11) NOT NULL,
   PRIMARY KEY (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `months`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `name` varchar(100) NOT NULL default '',
   `name_id` varchar(80) NOT NULL default '',
   `month_order` int(6) NOT NULL default '0',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_type_of_ingredient` (`name`,`is_enabled`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `season`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `uid` varchar(80) NOT NULL default '',
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `text` longtext,
   `text_id` varchar(70) default '',
   `start_month_id` int(11) NOT NULL,
   `end_month_id` int(11) NOT NULL,
   `approval_code_id` int(11) NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_season` (`uid`,`is_enabled`,`approval_code_id`),
   CONSTRAINT `season_refs_id_d043b34a` FOREIGN KEY (`start_month_id`) REFERENCES `months` (`id`),
   CONSTRAINT `season_refs_id_d043b34b` FOREIGN KEY (`end_month_id`) REFERENCES `months` (`id`),
   CONSTRAINT `season_refs_id_d043b34c` FOREIGN KEY (`approval_code_id`) REFERENCES `approval_code` (`id`))
    ENGINE=InnoDB  DEFAULT CHARSET=utf8; //done

CREATE TABLE `type_of_ingredient`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `name` varchar(100) NOT NULL default '',
   `name_id` varchar(80) NOT NULL default '',
   `approval_code_id` int(11) NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_type_of_ingredient` (`name`,`is_enabled`,`approval_code_id`),
   CONSTRAINT `type_of_ingredient_refs_id_d043b34b` FOREIGN KEY (`approval_code_id`) REFERENCES `approval_code` (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `ingredient_type_ingredient`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `ingredient_uid` varchar(80) NOT NULL default '',
   `type_of_ingredient_id` int(11) NOT NULL,
   PRIMARY KEY (`id`),
   CONSTRAINT `ingredient_type_ingredient_refs_id_d043b34b` FOREIGN KEY (`type_of_ingredient_id`) REFERENCES `type_of_ingredient` (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `cuisine_super_group`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `name` varchar(100) NOT NULL default '',
   `name_id` varchar(80) NOT NULL default '',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_cuisine_super_group` (`name`,`is_enabled`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;//done

CREATE TABLE `cuisine_sub_group`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `cusines_super_group_id` int(11) NOT NULL ,
   `name` varchar(100) NOT NULL default '',
   `name_id` varchar(80) NOT NULL default '',
   `alternate_name` varchar(100) NOT NULL default '',
   `alternate_name_id` varchar(80) NOT NULL default '',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_cuisine_sub_group` (`name`,`alternate_name`,`is_enabled`),
   CONSTRAINT `cuisine_super_group_refs_id_d043b34c` FOREIGN KEY (`cusines_super_group_id`) REFERENCES `cuisine_super_group` (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `recipe_cuisine`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `cuisine_sub_group_id` int(11) NOT NULL ,
   `recipe_uid` varchar(80) NOT NULL default '',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_recipe_cuisine` (`recipe_uid`,`cuisine_sub_group_id`,`is_enabled`),
   CONSTRAINT `recipe_cuisine_super_group_refs_id_d043b34c` FOREIGN KEY (`cuisine_sub_group_id`) REFERENCES `cuisine_sub_group` (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `recipe_picture`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `recipe_uid` varchar(80) NOT NULL default '',
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `image_url` longtext,
   PRIMARY KEY (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `value_unit`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `unit` longtext,
   `unit_id` varchar(70) default '',
   PRIMARY KEY (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `ingredient`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `uid` varchar(80) NOT NULL default '',
   `owner_uid` varchar(80) NOT NULL default '',
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `date_of_submission` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `name` longtext,
   `name_id` varchar(70) default '',
   `popular_name` longtext,
   `popular_name_id` varchar(70) default '',
   `baseQuantity` double(9,2) DEFAULT '0.00',
   `colories` double(9,2) DEFAULT '0.00',
   `ingredient_value_unit_id` int(11) NOT NULL,
   `approval_code_id` int(11) NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_ingredient` (`uid`,`is_enabled`,`approval_code_id`),
   CONSTRAINT `ingredient_refs_id_d043b34b` FOREIGN KEY (`ingredient_value_unit_id`) REFERENCES `value_unit` (`id`),
   CONSTRAINT `ingredient_refs_id_d043b34c` FOREIGN KEY (`approval_code_id`) REFERENCES `approval_code` (`id`))
    ENGINE=InnoDB  DEFAULT CHARSET=utf8; //done

 CREATE TABLE `recipe_ingredient`
    (`id` int(11) NOT NULL AUTO_INCREMENT,
    `recipe_uid` varchar(80) NOT NULL default '',
    `ingredient_uid` varchar(80) NOT NULL default '',
    `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
    `is_enabled` tinyint(1) NOT NULL default '0',
    `quantity` double(9,2) DEFAULT '0.00',
    `value_unit_id` int(11) NOT NULL,
    `lame_user_text` longtext,
    `lame_user_text_id` varchar(70) default '',
    `approval_code_id` int(11) NOT NULL,
    `display_order` int(8) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_recipe_ingredient` (`recipe_uid`,`ingredient_uid`,`approval_code_id` ,`display_order`, `is_enabled`),
    CONSTRAINT `recipe_ingredient_refs_id_d043b34b` FOREIGN KEY (`value_unit_id`) REFERENCES `value_unit` (`id`),
    CONSTRAINT `recipe_ingredient_refs_id_d043b34c` FOREIGN KEY (`approval_code_id`) REFERENCES `approval_code` (`id`)
    )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `recipe`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `uid` varchar(80) NOT NULL default '',
   `owner_uid` varchar(80) NOT NULL default '',
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `date_of_submission` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `name` longtext,
   `name_id` varchar(70) default '',
   `small_description` longtext,
   `small_description_id` varchar(70) default '',
   `long_description` longtext,
   `long_description_id` varchar(70) default '',
   `label` longtext,
   `label_id` varchar(70) default '',
   `avg_rating` double(5,2) DEFAULT '0.00',
   `buffer_cooking_time_mins` double(9,2) DEFAULT '0.00',
   `buffer_preparation_time_mins` double(9,2) DEFAULT '0.00',
   `buffer_marination_time_mins` double(9,2) DEFAULT '0.00',
   `serving_size` int(8) NOT NULL default '1',
   `recipe_category_id` int(11) NOT NULL,
   `egg_non_veg_id` int(11) NOT NULL,
   `approval_code_id` int(11) NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_recipe` (`uid`,`is_enabled`,`approval_code_id`),
   CONSTRAINT `recipe_refs_id_d043b34a` FOREIGN KEY (`recipe_category_id`) REFERENCES `recipe_category` (`id`),
   CONSTRAINT `recipe_refs_id_d043b34b` FOREIGN KEY (`egg_non_veg_id`) REFERENCES `veg_egg_non_veg` (`id`),
   CONSTRAINT `recipe_refs_id_d043b34c` FOREIGN KEY (`approval_code_id`) REFERENCES `approval_code` (`id`))
    ENGINE=InnoDB  DEFAULT CHARSET=utf8; //done

CREATE TABLE `recipe_ratings`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `recipe _uid` varchar(80) NOT NULL default '',
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `owner_uid` varchar(80) NOT NULL default '',
   `rating` double(5,2) DEFAULT '0.00',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_recipe_ratings` (`recipe _uid`,`is_enabled`,`owner_uid`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 ; //done

CREATE TABLE `veg_egg_non_veg`
    (`id` int(11) NOT NULL AUTO_INCREMENT,
    `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
    `is_enabled` tinyint(1) NOT NULL default '0',
    `name` varchar(50) default '',
    `name_id` varchar(70) default '',
    PRIMARY KEY (`id`))
    ENGINE=InnoDB  DEFAULT CHARSET=utf8; //done

CREATE TABLE `recipe_category`
    (`id` int(11) NOT NULL AUTO_INCREMENT,
    `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
    `is_enabled` tinyint(1) NOT NULL default '0',
    `name` varchar(50) default '',
    `name_id` varchar(70) default '',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_recipe_category` (`name`,`is_enabled`))
    ENGINE=InnoDB  DEFAULT CHARSET=utf8; //done

CREATE TABLE `recipe_time_of_day`
    (`id` int(11) NOT NULL AUTO_INCREMENT,
    `recipe_uid` varchar(80) NOT NULL default '',
    `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
    `is_enabled` tinyint(1) NOT NULL default '0',
    `time_id_day_id` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `recipe_time_of_day_refs_id_d043b34a` FOREIGN KEY (`time_id_day_id`) REFERENCES `time_of_day` (`id`))
    ENGINE=InnoDB  DEFAULT CHARSET=utf8; //done

CREATE TABLE `time_of_day`
    (`id` int(11) NOT NULL AUTO_INCREMENT,
    `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
    `is_enabled` tinyint(1) NOT NULL default '0',
    `text` varchar(50) default '',
    `text_id` varchar(70) default '',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_recipe_category` (`text`,`is_enabled`))
    ENGINE=InnoDB  DEFAULT CHARSET=utf8; //done

CREATE TABLE `tag`
    (`id` int(11) NOT NULL AUTO_INCREMENT,
    `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
    `is_enabled` tinyint(1) NOT NULL default '0',
    `text` varchar(100) default '',
    `text_id` varchar(70) default '',
    `count_attachment` int(11) ,
    `approval_code_id` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `tag_refs_id_d043b34c` FOREIGN KEY (`approval_code_id`) REFERENCES `approval_code` (`id`))
    ENGINE=InnoDB  DEFAULT CHARSET=utf8; //done

CREATE TABLE `preparation_steps`
    (`id` int(11) NOT NULL AUTO_INCREMENT,
    `uid` varchar(80) NOT NULL default '',
    `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
    `is_enabled` tinyint(1) NOT NULL default '0',
    `text` longtext,
    `text_id` varchar(70) default '',
    `cooking_time_mins` double(9,2) DEFAULT '0.00',
    `preparation_time_mins` double(9,2) DEFAULT '0.00',
    `marination_time_mins` double(9,2) DEFAULT '0.00',
    `approval_code_id` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `preparation_steps_refs_id_d043b34c` FOREIGN KEY (`approval_code_id`) REFERENCES `approval_code` (`id`))
    ENGINE=InnoDB  DEFAULT CHARSET=utf8; //done

CREATE TABLE `recipe_preparation_steps`
    (`id` int(11) NOT NULL AUTO_INCREMENT,
    `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
    `is_enabled` tinyint(1) NOT NULL default '0',
    `recipe_uid` varchar(80) NOT NULL,
    `preparation_steps_uid` varchar(80) NOT NULL,
    `display_order` int(11) ,
    `approval_code_id` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `recipe_preparation_steps_refs_id_d043b34c` FOREIGN KEY (`approval_code_id`) REFERENCES `approval_code` (`id`))
    ENGINE=InnoDB  DEFAULT CHARSET=utf8; //done

CREATE TABLE `approvable_stages`
    (`id` int(11) NOT NULL AUTO_INCREMENT,
    `app_user_id` int(11) NOT NULL ,
    `content_type_id` int(11) NOT NULL,
    `object_id` varchar(80) NOT NULL default '',
    `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
    `approval_code_id` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `approvable_stages_refs_id_d043b34a` FOREIGN KEY (`approval_code_id`) REFERENCES `approval_code` (`id`),
    CONSTRAINT `approvable_stages_refs_id_d043b34b` FOREIGN KEY (`app_user_id`) REFERENCES `app_user` (`id`),
    CONSTRAINT `approvable_stages_refs_id_d043b34c` FOREIGN KEY (`content_type_id`) REFERENCES `mealok_content_type` (`id`))
     ENGINE=InnoDB  DEFAULT CHARSET=utf8;//done

CREATE TABLE `collection`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `uid` varchar(80) NOT NULL default '',
   `owner_uid` varchar(80) NOT NULL default '',
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `created_date` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `parent_id` int(11) default NULL,
   `is_cluster` tinyint(1) NOT NULL default '0',
   `name` longtext,
   `name_id` varchar(70) default '',
   `popular_name` longtext,
   `popular_name_id` varchar(70) default '',

   `approval_code_id` int(11) NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_review` (`uid`,`is_enabled`,`approval_code_id`),
   CONSTRAINT `collection_refs_id_d043b34a` FOREIGN KEY (`parent_id`) REFERENCES `collection` (`id`))
    ENGINE=InnoDB  DEFAULT CHARSET=utf8; // db created

CREATE TABLE `collection_image`
    (`id` int(11) NOT NULL AUTO_INCREMENT,
    `collection_uid` varchar(80) NOT NULL default '',
    `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
    `is_enabled` tinyint(1) NOT NULL default '0',
    `image_url` varchar(150) default '',
    `approval_code_id` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `collection_image_refs_id_d043b34b` FOREIGN KEY (`approval_code_id`) REFERENCES `approval_code` (`id`))
     ENGINE=InnoDB  DEFAULT CHARSET=utf8; // db created

CREATE TABLE `collection_entity`
    (`id` int(11) NOT NULL AUTO_INCREMENT,
    `collection_uid` varchar(80) NOT NULL default '',
    `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
    `is_enabled` tinyint(1) NOT NULL default '0',
    `entity_type_id` int(11) NOT NULL,
    `entity_uid` varchar(80) NOT NULL default '',
    PRIMARY KEY (`id`),
    CONSTRAINT `collection_entity_refs_id_d043b34b` FOREIGN KEY (`entity_type_id`) REFERENCES `entity_type` (`id`))
     ENGINE=InnoDB  DEFAULT CHARSET=utf8; //db created

CREATE TABLE `review`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `uid` varchar(80) NOT NULL default '',
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `created_date` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `entity_type_id` int(11) NOT NULL,
   `related_entity_uid` varchar(80) NOT NULL default '',
   `writer_uid` varchar(80) NOT NULL default '',
   `approval_code_id` int(11) NOT NULL,
   `text` longtext,
   `text_id` varchar(70) default '',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_review` (`uid`,`is_enabled`,`approval_code_id`),
   CONSTRAINT `review_refs_id_d043b34c` FOREIGN KEY (`approval_code_id`) REFERENCES `approval_code` (`id`),
   CONSTRAINT `review_refs_id_d043b34d` FOREIGN KEY (`entity_type_id`) REFERENCES `entity_type` (`id`))
    ENGINE=InnoDB  DEFAULT CHARSET=utf8; //db created

CREATE TABLE `comment_review`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `review_uid` varchar(80) NOT NULL default '',
   `mealk_ok_user_uid` varchar(80) NOT NULL default '',
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `created_date` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `approval_code_id` int(11) NOT NULL,
   `parent_id` int(11) default NULL,
   `text` longtext,
   `text_id` varchar(70) default '',
   PRIMARY KEY (`id`),
   CONSTRAINT `comment_review_refs_id_d043b34a` FOREIGN KEY (`parent_id`) REFERENCES `comment_review` (`id`)
    )ENGINE=InnoDB  DEFAULT CHARSET=utf8; //db created


CREATE TABLE `articles_blocks_types`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `name` varchar(50) NOT NUll,
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_articles_blocks_types` (`name`,`is_enabled`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 //db created

CREATE TABLE `articles_blocks`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `uid` varchar(80) NOT NULL default '',
   `article _uid` varchar(80) NOT NULL default '',
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `blocktype_id` int(11) NOT NULL,
   `text` longtext,
   `text_id` varchar(70) default '',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_articles_blocks` (`uid`,`article _uid`,`is_enabled`),
   CONSTRAINT `articles_blocks_refs_id_d043b34b` FOREIGN KEY (`blocktype_id`) REFERENCES `articles_blocks_types` (`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8 //db created

CREATE TABLE `articles`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `uid` varchar(80) NOT NULL default '',
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `created_date` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `owner_uid` varchar(80) NOT NULL default '',
   `avg_rating` int(8) NOT NULL default '0',
   `header` varchar(100) default '',
   `header_id` varchar(70) default '',
   `approval_code_id` int(11) NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_articles` (`uid`,`is_enabled`,`approval_code_id`),
   CONSTRAINT `articles_refs_id_d043b34b` FOREIGN KEY (`approval_code_id`) REFERENCES `approval_code` (`id`))
    ENGINE=InnoDB  DEFAULT CHARSET=utf8;//db created

CREATE TABLE `articles_ratings`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `article _uid` varchar(80) NOT NULL default '',
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `owner_uid` varchar(80) NOT NULL default '',
   `rating` int(8) NOT NULL default '0',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_articles_ratings` (`article _uid`,`is_enabled`,`owner_uid`)
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8; //db created

CREATE TABLE `approval_code`
(`id` int(11) NOT NULL AUTO_INCREMENT,
`created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
`updated_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
`is_enabled` tinyint(1) NOT NULL default '0',
`code` int(6) NOT NULL DEFAULT '0',
`name` varchar(50) default NULL,
`description` varchar(80) default NUll,
PRIMARY KEY (`id`),
UNIQUE KEY `uniq_1` (`code`,`is_enabled`)) ENGINE=InnoDB  DEFAULT CHARSET=utf8; //db created

CREATE TABLE `entity_type`
(`id` int(11) NOT NULL AUTO_INCREMENT,
`created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',

`is_enabled` tinyint(1) NOT NULL default '0',
`text` varchar(70) default NULL,
`text_id` varchar(80) default NULL,
`description` varchar(80) default NUll,
PRIMARY KEY (`id`)) ENGINE=InnoDB  DEFAULT CHARSET=utf8; // db created

CREATE TABLE `meal_ok_user_like`
(`id` int(11) NOT NULL AUTO_INCREMENT,
`created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
`is_enabled` tinyint(1) NOT NULL default '0',
`owner_uid` varchar(80) NOT NULL default '',
`object_uid` varchar(80) NOT NULL default '',
`type_entity_id` int(11) NOT NULL default '0',
PRIMARY KEY (`id`),
UNIQUE KEY `uniq_meal_ok_user_like` (`object_uid`,`owner_uid`,`is_enabled`),
CONSTRAINT `meal_ok_user_like_refs_id_d043b34a` FOREIGN KEY (`type_entity_id`) REFERENCES `entity_type` (`id`)) //db created
 ENGINE=InnoDB  DEFAULT CHARSET=utf8;// db created

 CREATE TABLE `meal_ok_user_follow`
 (`id` int(11) NOT NULL AUTO_INCREMENT,
 `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
 `is_enabled` tinyint(1) NOT NULL default '0',
 `owner_uid` varchar(80) NOT NULL default '',
 `object_uid` varchar(80) NOT NULL default '',
 `type_entity_id` int(11) NOT NULL default '0',
 PRIMARY KEY (`id`),
 UNIQUE KEY `uniq_meal_ok_user_follow` (`object_uid`,`owner_uid`,`is_enabled`),
 CONSTRAINT `meal_ok_user_follow_refs_id_d043b34a` FOREIGN KEY (`type_entity_id`) REFERENCES `entity_type` (`id`))
  ENGINE=InnoDB  DEFAULT CHARSET=utf8;// db created

 CREATE TABLE `tips`
   (`id` int(11) NOT NULL AUTO_INCREMENT,
   `uid` varchar(80) NOT NULL default '',
   `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `created_date` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
   `is_enabled` tinyint(1) NOT NULL default '0',
   `heading` varchar(70) default '',
   `heading_id` varchar(70) default '',
   `description` longtext,
   `description_id` varchar(70) default NULL,
   `alternate_text` longtext,
   `alternate_text_id` varchar(70) default '',
   `approval_code_id` int(11) NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_tips` (`uid`,`is_enabled`,`approval_code_id`),
   CONSTRAINT `tips_refs_id_d043b34b` FOREIGN KEY (`approval_code_id`) REFERENCES `approval_code` (`id`))
    ENGINE=InnoDB  DEFAULT CHARSET=utf8; // db created

 CREATE TABLE `tips_image`
    (`id` int(11) NOT NULL AUTO_INCREMENT,
    `tips_uid` varchar(80) NOT NULL default '',
    `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
    `is_enabled` tinyint(1) NOT NULL default '0',
    `image_url` varchar(150) default '',
    `approval_code_id` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `tips_image_refs_id_d043b34b` FOREIGN KEY (`approval_code_id`) REFERENCES `approval_code` (`id`))
     ENGINE=InnoDB  DEFAULT CHARSET=utf8; // db created

CREATE TABLE `entity_tag_relationship`
    (`id` int(11) NOT NULL AUTO_INCREMENT,
    `tag_uid` varchar(80) NOT NULL default '',
    `entity_object_uid` varchar(80) NOT NULL default '',
    `type_entity_id` int(11) NOT NULL default '0',
    `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
    `is_enabled` tinyint(1) NOT NULL default '0',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_entity_tag_relationship` (`tag_uid`,`entity_object_uid`,`is_enabled`),
    CONSTRAINT `entity_tag_relationship_refs_id_d043b34b` FOREIGN KEY (`type_entity_id`) REFERENCES `entity_type` (`id`))
     ENGINE=InnoDB  DEFAULT CHARSET=utf8; // db created