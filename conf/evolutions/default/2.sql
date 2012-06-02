# User schema
 
# --- !Ups
 
set ignorecase true;

create table user_ (
    id bigint(20) not null primary key,
    name varchar(255) not null unique,
    email varchar(255) not null unique,
    password varchar(255) not null
);

create sequence user_seq start with 100;
 
# --- !Downs
 
drop table user_;

drop sequence if exists user_seq;
