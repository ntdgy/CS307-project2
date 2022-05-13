drop_table = '''
drop table if exists center cascade;
drop table if exists enterprise cascade;
drop table if exists staff cascade;
drop table if exists model cascade;
drop table if exists warehousing cascade;
drop table if exists stockin cascade;
drop table if exists contract cascade;
drop table if exists contract_content cascade;
drop table if exists users cascade;
drop table if exists rechapter cascade;'''

create_table = '''
create table center
(
    id   serial primary key,
    name varchar(60) not null unique
);
create index center_name on center(name);

create table enterprise
(
    id               serial primary key,
    name             varchar not null unique ,
    supply_center varchar references center(name) on update cascade on delete cascade,         -- references supply_center(id),-- not null ,
    country          varchar(30) not null,
    city             varchar(20),
    industry         varchar(40)
);
create index enterprise_name on enterprise(name);

create table staff
(
    id            serial primary key,
    name          varchar(30) not null ,
    number        varchar(8) unique not null ,
    gender        varchar(6),
    age           int,
    mobile_number varchar(11) unique not null,
    supply_center varchar references center(name) on update cascade on delete cascade,
    stafftype          int          -- 0 is director, 1 is supply staff, 2 is contracts manager, 3 is salesman
);
create index staff_name on staff(number);

create table model
(
    id         serial primary key,
    number     varchar(20),
    model      varchar(60) not null unique,
    name       varchar not null ,
    unit_price int   not null
);
create index model_model on model(model);

create table warehousing(
    id serial primary key,
    model_name varchar references model(model) on update cascade on delete cascade,
    quantity int not null,
    center_name varchar references center(name)  on update cascade on delete cascade
);

create table stockin
(
    id             serial primary key,
    supply_center  varchar references center(name) on update cascade on delete cascade,
    product_model  varchar references model(model) on update cascade on delete cascade,
    supply_staff   varchar(8),
    date           date,
    purchase_price int,
    quantity       int
);

create table contract
(
    id               serial primary key,
    number           varchar(10) not null unique ,
    enterprise    varchar references enterprise(name) on update cascade on delete cascade,
    contract_date    date not null,
    contract_manager varchar(8),
    contract_type varchar(20)
);
create index contract_number on contract(number);

create table contract_content
(
    id                      serial primary key,
    contract_number            varchar(10) references contract(number) on update cascade on delete cascade, -- references contract (id) not null ,
    product_model_name       varchar references model(model) on update cascade on delete cascade, -- references product_model (id) not null,
    quantity                int, -- not null,
    estimated_delivery_date date,
    lodgement_date          date,
    salesman   varchar references staff(number) on update cascade on delete cascade -- references salesman (id) not null
);

create table users
(
    id         serial primary key,
    user_name  varchar(30) unique,
    passwd_md5 varchar,
    level      int -- 0: admin, 1: normal user  2: guest
);

create table rechapter
(
    id        serial primary key,
    chapter   varchar,
    isUsed    int,
    user_name varchar,
    used_date date
);

insert into users(user_name, passwd_md5, level) values('admin', '993a55c9801b146aa9d5c8cfd1ff24cb', 0);
insert into users(user_name, passwd_md5, level) values('user', '993a55c9801b146aa9d5c8cfd1ff24cb', 1);'''


insert_into_enterprise = '''insert into enterprise(id,name,country,city,supply_center,industry) values(%s,%s,%s,%s,
%s,%s); '''


insert_into_staff = '''insert into staff(id,name,age,gender,number,supply_center,mobile_number,stafftype) values (%s,
%s,%s,%s,%s,%s,%s,%s); '''
