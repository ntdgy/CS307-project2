package ntdgy.cs307project2;

public class sql {
    final String sql = "drop table if exists center cascade;\n" +
            "drop table if exists enterprise cascade;\n" +
            "drop table if exists staff cascade;\n" +
            "drop table if exists model cascade;\n" +
            "drop table if exists warehousing cascade;\n" +
            "drop table if exists stockin cascade;\n" +
            "drop table if exists contract cascade;\n" +
            "drop table if exists contract_content cascade;\n" +
            "drop table if exists users cascade;\n" +
            "drop table if exists rechapter cascade;\n" +
            "\n" +
            "create table center\n" +
            "(\n" +
            "    id   serial primary key,\n" +
            "    name varchar(60) not null unique\n" +
            ");\n" +
            "create index center_name on center(name);\n" +
            "\n" +
            "create table enterprise\n" +
            "(\n" +
            "    id               serial primary key,\n" +
            "    name             varchar(60) not null unique ,\n" +
            "    supply_center varchar references center(name) on update cascade on delete cascade,         -- references supply_center(id),-- not null ,\n" +
            "    country          varchar(30) not null,\n" +
            "    city             varchar(20),\n" +
            "    industry         varchar(40)\n" +
            ");\n" +
            "create index enterprise_name on enterprise(name);\n" +
            "\n" +
            "create table staff\n" +
            "(\n" +
            "    id            serial primary key,\n" +
            "    name          varchar(30) not null unique ,\n" +
            "    number        varchar(8) unique not null ,\n" +
            "    gender        varchar(6),\n" +
            "    age           int,\n" +
            "    mobile_number varchar(11) unique not null,\n" +
            "    supply_center varchar references center(name) on update cascade on delete cascade,\n" +
            "    type          int          -- 0 is director, 1 is supply staff, 2 is contracts manager, 3 is salesman\n" +
            ");\n" +
            "create index staff_name on staff(name);\n" +
            "\n" +
            "create table model\n" +
            "(\n" +
            "    id         serial primary key,\n" +
            "    number     varchar(20),\n" +
            "    model      varchar(60) unique not null ,\n" +
            "    name       varchar(60) not null unique ,\n" +
            "    unit_price int   not null\n" +
            ");\n" +
            "create index model_model on model(model);\n" +
            "\n" +
            "create table warehousing(\n" +
            "    id serial primary key,\n" +
            "    model_name varchar references model(name) on update cascade on delete cascade,\n" +
            "    quantity int not null,\n" +
            "    center_name varchar references center(name)  on update cascade on delete cascade\n" +
            ");\n" +
            "\n" +
            "create table stockin\n" +
            "(\n" +
            "    id             serial primary key,\n" +
            "    supply_center  varchar references center(name) on update cascade on delete cascade,\n" +
            "    product_model  varchar references model(model) on update cascade on delete cascade,\n" +
            "    supply_staff   varchar(8),\n" +
            "    date           date,\n" +
            "    purchase_price int,\n" +
            "    quantity       int\n" +
            ");\n" +
            "\n" +
            "create table contract\n" +
            "(\n" +
            "    id               serial primary key,\n" +
            "    number           varchar(10) not null unique ,\n" +
            "    enterprise    varchar references enterprise(name) on update cascade on delete cascade,\n" +
            "    contract_date    date not null,\n" +
            "    contract_manager varchar(8)\n" +
            ");\n" +
            "create index contract_number on contract(number);\n" +
            "\n" +
            "create table contract_content\n" +
            "(\n" +
            "    id                      serial primary key,\n" +
            "    contract_number            varchar(10) references contract(number) on update cascade on delete cascade, -- references contract (id) not null ,\n" +
            "    product_model_name       varchar references model(name) on update cascade on delete cascade, -- references product_model (id) not null,\n" +
            "    quantity                int, -- not null,\n" +
            "    estimated_delivery_date date,\n" +
            "    lodgement_date          date,\n" +
            "    salesman   varchar references staff(name) on update cascade on delete cascade -- references salesman (id) not null\n" +
            ");\n" +
            "\n" +
            "create table users\n" +
            "(\n" +
            "    id         serial primary key,\n" +
            "    user_name  varchar(30) unique,\n" +
            "    passwd_md5 varchar,\n" +
            "    level      int -- 0: admin, 1: normal user  2: guest\n" +
            ");\n" +
            "\n" +
            "create table rechapter\n" +
            "(\n" +
            "    id        serial primary key,\n" +
            "    chapter   varchar,\n" +
            "    isUsed    int,\n" +
            "    user_name varchar,\n" +
            "    used_date date\n" +
            ");\n" +
            "\n" +
            "insert into users(user_name, passwd_md5, level) values('admin', '993a55c9801b146aa9d5c8cfd1ff24cb', 0);\n" +
            "insert into users(user_name, passwd_md5, level) values('user', '993a55c9801b146aa9d5c8cfd1ff24cb', 1);\n";
}
