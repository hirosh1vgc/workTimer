drop database worktimer;
create database worktimer default character set utf8;

use worktimer;

create table User(
user_id int primary key auto_increment,
user_name varchar(10) not null,
login_id varchar(10) not null,
password varchar(10) not null,
userlv_id int not null,
enrollmentlv_id int default 1
)ENGINE=InnoDB default CHARSET=utf8;

create table Userlv(
id int,
lv char(3)
)ENGINE=InnoDB default CHARSET=utf8;

create table Enrollmentlv(
id int,
lv char(3)
)ENGINE=InnoDB default CHARSET=utf8;

create table Workstart(
work_id int primary key auto_increment,
user_id int not null,
work_day date,
work_start char(8)
)ENGINE=InnoDB default CHARSET=utf8;

create table Workstop(
id int,
stop_time varchar(8),
work_length varchar(8),
work_over varchar(8),
work_detail varchar(50)
)ENGINE=InnoDB default CHARSET=utf8;

create table Workdefault(
wakeup_time varchar(8),
sleep_time varchar(8),
rest_time int
)ENGINE=InnoDB default CHARSET=utf8;

insert into User (user_name, login_id, password, userlv_id) values ('ユーザー1号','user','user', 1),('管理者1号','kanri','kanri', 2),('あ','a','a', 1),('い','i','i', 1),('う','u','u', 2);
insert into Userlv (id, lv) values (1, '利用者'),(2, '管理者');
insert into Enrollmentlv (id, lv) values (1, '在籍中'),(2, '退社済');
insert into Workstart (user_id, work_day, work_start)
values (1,'2022-04-01','09:00:00'),(2,'2022-05-01','10:00:00'),(4,'2022-05-01','08:45:00'),(1,'2022-05-01','09:00:00'),(1,'2022-05-02','09:00:00'),(2,'2022-05-02','09:00:00'),(2,'2022-05-06','09:00:00');

insert into Workstop(id, stop_time, work_length, work_over, work_detail)
values (1, '18:00:00','8時間0分','0分', 'ユーザー1勤務終了'),(2,'20:00:00','9時間0分','60分', 'ユーザー2勤務終了'),(4,'17:30:00','7時間30分','0分','ユーザー1勤務終了'),(5,'19:00:00','9時間0分','60分','ユーザー1勤務終了'),(6,'20:00:00','10時間0分','120分', 'ユーザー2勤務終了');
insert into Workdefault(wakeup_time, sleep_time, rest_time) values ('09:00:00', '18:00:00', 60);

create view v_user as
select user_id, user_name, login_id, password, Userlv.lv as user_lv, Enrollmentlv.lv as enrollment_lv from user
join Userlv
on user.userlv_id = userlv.id
join Enrollmentlv
on user.enrollmentlv_id = enrollmentlv.id;

create view v_timer as
select work_day, work_id, Workstart.user_id, User.login_id as login_id, work_start, Workstop.stop_time as work_stop, Workstop.work_length as work_length, Workstop.work_over as work_over, Workstop.work_detail as work_detail from Workstart
join User
on Workstart.user_id = User.user_id
join Workstop
on Workstart.work_id = Workstop.id
order by work_day;
