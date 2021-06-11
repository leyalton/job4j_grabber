create database shop;

create table type(
    id   serial primary key,
    name varchar(255)
);

create table product(
    id           serial primary key,
    name         varchar(255),
    expired_date date,
    price        int,
    type_id      int references type (id)
);


insert into type(name) values ('сыр');
insert into type(name) values ('мороженое');
insert into type(name) values ('молоко');

insert into product(name, expired_date, price, type_id) values ('тофу', '2022-01-01', 100, 1);
insert into product(name, expired_date, price, type_id) values ('филадельфия', '2021-01-01', 150, 1);
insert into product(name, expired_date, price, type_id) values ('аперифрэ', '2020-01-01', 200, 1);

insert into product(name, expired_date, price, type_id) values ('мороженое 48 коп', '2022-01-01', 100, 2);
insert into product(name, expired_date, price, type_id) values ('айс', '2021-01-01', 150, 2);
insert into product(name, expired_date, price, type_id) values ('лед', '2020-01-01', 200, 2);

insert into product(name, expired_date, price, type_id) values ('молоко 3.2', '2022-01-01', 100, 3);
insert into product(name, expired_date, price, type_id) values ('простоквашино', '2021-01-01', 150, 3);
insert into product(name, expired_date, price, type_id) values ('процент', '2020-01-01', 200, 3);


--получение всех продуктов с типом "СЫР"
select t.name, p.name from product as p join type t on p.type_id = t.id
group by t.name, p.name
having t.name = 'сыр';

--продукты, у кого в имени есть слово "мороженое"
select * from product where name like 'мороженое%';

--все продукты, срок годности которых уже истек
select p.name, p.expired_date from product as p
group by p.expired_date, p.name
having p.expired_date < now();

-- название самого дорогого продукта
select name from product
group by price,name
having price = (select max(price) from product);

--количество продуктов каждого типа
select t.name, count(p.name) from product as p join type t on p.type_id = t.id
group by t.name;

--получение всех продуктов с типом "СЫР" и "МОЛОКО"
select t.name, p.name from product as p join type t on p.type_id = t.id
group by t.name, p.name
having t.name = 'сыр' or t.name = 'молоко';

--продукты, которых осталось меньше 10 штук
select t.name from product as p join type t on p.type_id = t.id
group by t.name
having count(p.name) <= 10;

--все продукты и их тип
select p.name, t.name from product as p join type t on p.type_id = t.id;