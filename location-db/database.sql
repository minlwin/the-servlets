create database location_db;

create user 'location'@'localhost' identified by 'location';

grant all privileges on location_db.* to 'location'@'localhost';

use location_db

drop table if exists state;

create table state(
	id int primary key auto_increment,
	name varchar(40) unique not null,
	region varchar(20) not null,
	capital varchar(20) not null
);

drop table if exists district;

create table district(
	id int primary key auto_increment,
	name varchar(40) not null,
	state_id int not null,
	foreign key(state_id) references state(id)
);

drop table if exists township;

create table township(
	id int primary key auto_increment,
	name varchar(40) not null,
	district_id int not null,
	foreign key(district_id) references district(id)
);
