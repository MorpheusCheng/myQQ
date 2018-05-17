--创建山寨QQ数据库
create database QQdb;

--使用该数据库
use QQdb;

--创建用户表(PS:目前只需要账号和密码，实现登录功能)
create table QQUser
(
	QQUserId char(20) primary key,	--账号
	QQPassword char(16) not null,	--密码
);

--插入测试用例
insert into QQUser values('1','123456');
insert into QQUser values('2','123456');
insert into QQUser values('3','123456');
insert into QQUser values('4','123');
insert into QQUser values('5','123');
insert into QQUser values('6','123456');

--查询用户表
select * from QQUser;