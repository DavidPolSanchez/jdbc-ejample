create table directions
(
    id int auto_increment,
    street varchar(200) not null,
    postal_code varchar(5) null,
    province varchar(200) null,
    country varchar(100) null,
    constraint directions_pk
        primary key (id)
);


create table users
(
    id int auto_increment,
    first_name varchar(50) not null,
    last_name varchar(50) null,
    email varchar(100) null,
    age int null,
    phone varchar(9) null,
    id_direction int null,
    constraint users_pk
        primary key (id),
    constraint users_directions_id_fk
        foreign key (id_direction) references directions (id)
);

create table tasks
(
    id int auto_increment,
    title varchar(255) not null,
    description varchar(300) null,
    id_user int null,
    constraint tasks_pk
        primary key (id),
    constraint tasks_users_id_fk
        foreign key (id_user) references users (id)
);


