create database devices;

create table notebook(
                         id serial primary key,
                         name_brand varchar(255),
                         name_model varchar(255),
                         price int,
                         serial_num int
);

create table employees(
                          id serial primary key,
                          name varchar(255),
                          room int,
                          notebook_id int references notebook(id)
);

insert into notebook(name_brand, name_model, price, serial_num) values ('lenovo', '1', 150000, 80001);
insert into notebook(name_brand, name_model, price, serial_num) values ('samsung', 'max', 100000, 40002);
insert into notebook(name_brand, name_model, price, serial_num) values ('aser','xs', 75000, 50003);
insert into notebook(name_brand, name_model, price, serial_num) values ('aser', '54', 50000, 40004);
insert into notebook(name_brand, name_model, price, serial_num) values ('dell', 'xs45', 90000, 40005);
insert into employees(name, room, notebook_id) values('Boris',34, 1);
insert into employees(name, room, notebook_id) values('Ivan',325 ,2);
insert into employees(name, room, notebook_id) values('Kiril',34 , 3 );
insert into employees(name, room, notebook_id) values ('Marina',545, 4);
insert into employees(name, room, notebook_id) values ('Pers',354, 5);
insert into employees(name, room) values ('Alexander', 2);


-- вывод таблицы: сотрудник-ноутбук
select e.name as "Имя", n.name_brand as "Устройство",n.name_model as "Устройство", n.serial_num as "Серийный номер устройства" from employees as e join notebook as n on e.notebook_id = n.id;

-- вывод таблицы кабинет-ноубук(в каком кабинете находится ноут)
select e.room as "Кабинет", n.name_brand as "Устройство",n.name_model as "Устройство", n.serial_num as "Серийный номер устройства" from employees as e join notebook as n on e.notebook_id = n.id;

-- вывод таблицы с учетом товарно-материальных ценностей у каждого сотрудника
select e.name as "Имя", n.price as "Стоимость" from employees as e join notebook as n on e.notebook_id = n.id;