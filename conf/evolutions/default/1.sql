# Issue schema
 
# --- !Ups
 
set ignorecase true;

create table user_ (
    id bigint(20) not null primary key,
    name varchar(255) not null unique,
    email varchar(255) not null unique,
    password varchar(255) not null
);

create sequence user_seq start with 100;

create table issue (
    number_ bigint(20) not null primary key,
    user_id bigint(20) not null,
    title varchar(255) not null,
    description varchar(1024) not null,
    foreign key (user_id) references user_ (id)
);

create sequence issue_seq start with 100;
 
# --- !Downs
 
drop table issue;

drop sequence if exists issue_seq;
 
drop table user_;

drop sequence if exists user_seq;
