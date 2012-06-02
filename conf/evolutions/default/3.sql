#  Sample users
 
# --- !Ups
 
set ignorecase true;

insert into user_ values (user_seq.nextval, 'jcdelmas', 'jc.delmas@gmail.com', 'password');
 
# --- !Downs

delete from user_; 
