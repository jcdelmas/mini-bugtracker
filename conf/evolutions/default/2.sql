# Sample issues
 
# --- !Ups
 
insert into issue values (issue_seq.nextval, 'Bug 1', 'Bug 1 description');
insert into issue values (issue_seq.nextval, 'Bug 2', 'Bug 2 description');
 
# --- !Downs

delete from issue; 
