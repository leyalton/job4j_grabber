-- скрипт создает таблицы для хранения структуры системы заявок.

create table rule
(
    id   serial primary key,
    name varchar(255) not null
);

create table role
(
    id   serial primary key,
    name varchar(255) not null
);

create table role_rule
(
    id      serial primary key,
    rule_id int references rule (id),
    role_id int references role (id)
);

create table users
(
    id   serial primary key,
    name varchar(255),
    role int references role (id)
);

create table category
(
    id   serial primary key,
    name varchar(255) not null
);

create table state
(
    id   serial primary key,
    name varchar(255) not null
);

create table attach
(
    id   serial primary key,
    name varchar(255) not null
);

create table item
(
    id       serial primary key,
    name     varchar(255) not null,
    author   int references users (id),
    category int references category (id),
    state    int references state (id),
    file     int references attach (id)
);

create table item_comments
(
    id serial primary key,
    comment    varchar(255),
    author     int references users (id),
    item       int references item (id)
);