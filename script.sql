drop DATABASE IF EXISTS clock;
create database clock;
create table clock
(
    id              bigint(200) auto_increment
        primary key,
    begin_time      datetime      not null comment '开始时间',
    status          int           not null comment '0未打卡,1正在打卡',
    total_duration  int           not null,
    target_duration int           not null,
    create_time     datetime      null comment '创建时间',
    update_time     datetime      null comment '修改时间',
    del_flag        int default 0 null,
    check ((`status` = 1) or (`status` = 0))
)
    engine = InnoDB;

create table clock_history
(
    id          bigint(200)   not null,
    week        int           not null comment '周数',
    duration    int           not null comment '时长',
    is_standard int           not null comment '0未达标,1达标',
    create_time datetime      null comment '创建时间',
    update_time datetime      null comment '修改时间',
    del_flag    int default 0 null,
    check ((`is_standard` = 1) or (`is_standard` = 0))
)
    engine = InnoDB;

create table menu
(
    id          bigint(200)   not null
        primary key,
    perms       varchar(30)   null,
    create_time datetime      null comment '创建时间',
    update_time datetime      null comment '修改时间',
    del_flag    int default 0 null,
    des         char(20)      not null
)
    engine = InnoDB;

create table role
(
    id          bigint(200) auto_increment
        primary key,
    role_name   varchar(30)   not null comment '角色名',
    create_time datetime      null comment '创建时间',
    update_time datetime      null comment '修改时间',
    del_flag    int default 0 null
)
    engine = InnoDB;

create table role_menu
(
    role_id bigint(200) not null,
    menu_id bigint(200) not null,
    primary key (menu_id, role_id)
)
    engine = InnoDB;

create table user
(
    id          bigint(200) auto_increment
        primary key,
    username    varchar(30)                          not null comment '用户名',
    nickname    varchar(30)                          not null comment '昵称',
    password    varchar(100)                         not null comment '密码',
    avatar      varchar(300)                         not null comment '头像',
    grade       int                                  not null comment '年级',
    signature   varchar(100) default '....'          null comment '签名',
    create_time datetime                             null comment '创建时间',
    update_time datetime                             null comment '修改时间',
    del_flag    int          default 0               null
)
    engine = InnoDB;

create table user_role
(
    user_id bigint(200) not null,
    role_id bigint(200) not null,
    primary key (user_id, role_id)
)
    engine = InnoDB;


