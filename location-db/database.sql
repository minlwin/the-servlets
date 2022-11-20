create database location_db;

create user 'location'@'localhost' identified by 'location';

grant all privileges on location_db.* to 'location'@'localhost';

use location_db

create table state(
	id int primary key auto_increment,
	name varchar(20) unique not null,
	region varchar(20) not null,
	capital varchar(20) not null
);