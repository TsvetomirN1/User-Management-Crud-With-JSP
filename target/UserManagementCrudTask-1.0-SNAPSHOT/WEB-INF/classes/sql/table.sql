create database user_management_crud;

use user_management_crud;

create table users (
                       id  int(3) NOT NULL AUTO_INCREMENT,
                       first_name varchar(120) NOT NULL,
                       last_name varchar(120) NOT NULL,
                       date_of_birth varchar(120) NOT NULL,
                       email varchar(120) NOT NULL,
                       phone_number varchar(120) NOT NULL,
                       PRIMARY KEY (id)
);


