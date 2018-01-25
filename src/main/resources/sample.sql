drop table student
create table student(id int primary key, name varchar(20))
insert into student values(1, 'peter')
insert into student values(2,'jack')
insert into student values(3,' lucy')
create table token_t(token varchar(100) primary key, is_active varchar(1))