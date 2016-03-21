create table PERSON (id int not null auto_increment, first_name varchar(255), middle_name varchar(255), last_name varchar(255), gender int, birthdate date, address int not null, employed bit, gwa float, role int, primary key (id) );

create table ADDRESS (id int not null auto_increment, street varchar(255), house_no int, barangay varchar(255), subdivision varchar(255), city varchar(255), zip_code varchar(255), primary key (id));

create table CONTACT (id int not null auto_increment, type int, value varchar(255), person_id int not null, primary key (id));

insert into ADDRESS (street, house_no, barangay, subdivision, city, zip_code) values('qwe',123,'qwe','qwe','qwe','qwe');

insert into PERSON (first_name,middle_name,last_name,gender,birthdate,address,employed,gwa,role) values('asd','asd','asd',0,'1212-12-12',1,1,2.1,0);

insert into CONTACT (type,value,person_id) values(0,'123123',1);
insert into CONTACT (type,value,person_id) values(1,'45454545',1);
