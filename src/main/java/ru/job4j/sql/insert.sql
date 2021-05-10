-- скрипт заполняет начальные данные для системы заявок.

insert into role(id, name) values (1, 'role 1');
insert into role(id, name) values (2, 'role 2');
insert into role(id, name) values (3, 'role 3');
insert into users(id, name, role) values (1, 'user 1', 1);
insert into users(id, name, role) values (2, 'user 2', 2);
insert into users(id, name, role) values (3, 'user 3', 3);