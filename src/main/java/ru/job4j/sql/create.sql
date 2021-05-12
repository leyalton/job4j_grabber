-- скрипт создает таблицы для хранения структуры системы заявок.

create table rule
(
    rule_id   serial primary key,
    rule_name varchar(255) not null
);

create table role
(
    role_id   serial primary key,
    rule_name varchar(255) not null
);

create table role_rule
(
    role_rule_id serial primary key,
    rule         int references rule (rule_id),
    role         int references role (role_id)
);

create table users
(
    users_id   serial primary key,
    users_name varchar(255),
    role       int references role (role_id)
);

create table category
(
    category_id   serial primary key,
    category_name varchar(255) not null
);

create table state
(
    state_id   serial primary key,
    state_name varchar(255) not null
);

create table attach
(
    attach_id   serial primary key,
    attach_name varchar(255) not null
);

create table item
(
    item_id   serial primary key,
    item_name varchar(255) not null,
    author    int references users (users_id),
    category  int references category (category_id),
    state     int references state (state_id),
    file      int references attach (attach_id)
);

create table comments
(
    id      serial primary key,
    comment varchar(255),
    author  int references users (users_id),
    item    int references item (item_id)
);