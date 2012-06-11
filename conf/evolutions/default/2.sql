#  Sample users
 
# --- !Ups
 
set ignorecase true;

insert into user_ values (user_seq.nextval, 'jcdelmas', 'jc.delmas@gmail.com', 'password');
insert into user_ values (user_seq.nextval, 'Test User', 'test.user@yopmail.com', 'password');
# --- !Downs

delete from user_; 
