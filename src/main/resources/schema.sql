create table if not exists `security_practice`.`member` (
    member_id bigint not null auto_increment,
    login_id varchar(45) not null unique,
    nick_name varchar(60) not null unique,
    password TEXT not null,
    primary key (member_id)
);
