--liquibase formatted sql

--changeset Volkov:create_table_users
create table users(
    id serial primary key,
    username varchar(50),
    first_name varchar(50),
    last_name varchar(50),
    password varchar(255),
    phone varchar(50),
    role varchar(50),
    image text,
    email varchar(255)
);

--changeset Volkov:create_table_ads
create table ads(
    pk serial primary key,
    image varchar(255),
    price integer,
    title text,
    description text,
    author_id int,
    foreign key (author_id) references users (id)
);

--changeset Volkov:create_table_comments
create table comments(
    pk serial primary key,
    created_at date,
    text text,
    author_id int,
    ad_id int,
    foreign key (author_id) references users (id),
    foreign key (ad_id) references ads (pk)
);