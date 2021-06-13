create table devices(
    id    serial primary key,
    name  varchar(255),
    price float
);

create table people(
    id   serial primary key,
    name varchar(255)
);

create table devices_people(
    id        serial primary key,
    device_id int references devices (id),
    people_id int references people (id)
);


insert into devices(name, price) values ('samsung', 1000);
insert into devices(name, price) values ('samsung', 2000);
insert into devices(name, price) values ('samsung', 3000);
insert into devices(name, price) values ('samsung', 1000);
insert into devices(name, price) values ('lenovo', 2000);
insert into devices(name, price) values ('asus', 3000);
insert into devices(name, price) values ('asus', 40000);

insert into people(name) values ('name_1');
insert into people(name) values ('name_2');
insert into people(name) values ('name_3');

insert into devices_people(device_id, people_id) values (1, 1);
insert into devices_people(device_id, people_id) values (2, 1);
insert into devices_people(device_id, people_id) values (3, 1);
insert into devices_people(device_id, people_id) values (7, 1);

insert into devices_people(device_id, people_id) values (1, 2);
insert into devices_people(device_id, people_id) values (2, 2);
insert into devices_people(device_id, people_id) values (3, 2);

insert into devices_people(device_id, people_id) values (1, 3);
insert into devices_people(device_id, people_id) values (2, 3);
insert into devices_people(device_id, people_id) values (3, 3);

select d.name, avg(d.price) from devices as d group by d.name;

select p.name, avg(d.price)
from devices_people as dp
         join devices d on dp.device_id = d.id
         join people p on dp.people_id = p.id
group by p.name
having avg(d.price) > 5000;