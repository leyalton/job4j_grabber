create database grabber;
create table Post (
    id      serial primary key,
    name    varchar(255),
    text    text,
    link    text unique,
    created date
)