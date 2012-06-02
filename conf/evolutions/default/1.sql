# Issue schema
 
# --- !Ups
 
set ignorecase true;

create table issue (
    number_ bigint(20) not null,
    title varchar(255) not null,
    description varchar(1024) not null,
    constraint pk_issue primary key (number_)
);

create sequence issue_seq start with 100;
 
# --- !Downs
 
drop table issue;

drop sequence if exists issue_seq;
