create database location_db;

create user 'location'@'localhost' identified by 'location';

grant all privileges on location_db.* to 'location'@'localhost';

use location_db

create table state(
	id int primary key auto_increment,
	name varchar(40) unique not null,
	region varchar(20) not null,
	capital varchar(20) not null
);

insert into state(name, region, capital) values ('Ayeyarwady Region', 'Lower', 'Pathein');
insert into state(name, region, capital) values ('Bago Region', 'Lower', 'Bago');
insert into state(name, region, capital) values ('Chin State', 'West', 'Hakha');
insert into state(name, region, capital) values ('Kachin State', 'North', 'Myitkyina');
