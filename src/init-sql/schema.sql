-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS tea;

-- 选择数据库
USE tea;

-- auto-generated definition
create table users
(
    id       int auto_increment
        primary key,
    username text         not null,
    email    varchar(255) not null,
    password text         not null,
    role     int          not null,
    constraint email
        unique (email)
);

-- auto-generated definition
create table comments
(
    id       int auto_increment
        primary key,
    user_id  int                  null,
    paper_id int                  null comment '论文id，为0表示非直接评论',
    content  text                 not null,
    pid      int        default 0 null comment '父级评论di',
    deleted  tinyint(1) default 0 null,
    create_at datetime            not null,
    constraint comments_ibfk_1
        foreign key (user_id) references users (id)
);

create index paper_id
    on comments (paper_id);

create index user_id
    on comments (user_id);

-- auto-generated definition
create table friend_requests
(
    id           int auto_increment
        primary key,
    from_user_id int               not null comment '申请人ID',
    to_user_id   int               not null comment '接收人ID',
    status       tinyint default 0 not null comment '状态：0-待处理 1-已接受 2-已拒绝',
    message      varchar(255)      null comment '申请消息',
    create_time  datetime          null,
    constraint friend_requests_ibfk_1
        foreign key (from_user_id) references users (id),
    constraint friend_requests_ibfk_2
        foreign key (to_user_id) references users (id)
);

create index idx_from_user
    on friend_requests (from_user_id);

create index idx_to_user
    on friend_requests (to_user_id);

-- auto-generated definition
create table friends
(
    user_id   int               not null,
    friend_id int               not null,
    is_friend tinyint default 0 null comment '是否为好友 0-否 1-是',
    is_follow tinyint default 2 null comment '是否关注 2-否 3-是',
    primary key (user_id, friend_id),
    constraint friends_ibfk_1
        foreign key (user_id) references users (id),
    constraint friends_ibfk_2
        foreign key (friend_id) references users (id)
);

create index friend_id
    on friends (friend_id);

-- auto-generated definition
create table papers
(
    id              int auto_increment
        primary key,
    title           text                 not null,
    author          text                 not null,
    conference      text                 not null,
    file            varchar(255)         null,
    reporter_id     int                  null,
    paper_path      varchar(255)         null comment '论文路径',
    attachment_path varchar(255)         null comment '论文路径',
    deleted         tinyint(1) default 0 null,
    create_time     datetime             null,
    delete_time     datetime             null,
    file_size       varchar(64)          null,
    attach_author   varchar(255)         null,
    update_time     datetime             null,
    constraint papers_ibfk_1
        foreign key (reporter_id) references users (id)
);

create index reporter_id
    on papers (reporter_id);

-- auto-generated definition
create table reporter_apply
(
    id         int auto_increment
        primary key,
    user_id    int          not null,
    apply_note text         null,
    file_path  varchar(255) null,
    apply_time datetime     null,
    status     int          null comment '审核状态',
    constraint userId
        unique (user_id)
);

CREATE TABLE paper_status (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    paper_id INT NOT NULL COMMENT '论文ID',
    download_count INT DEFAULT 0 COMMENT '论文下载量',
    attachment_download_count INT DEFAULT 0 COMMENT '论文附件下载量',
    read_count INT DEFAULT 0 COMMENT '论文阅读量',
    UNIQUE KEY uniq_paper_id (paper_id)
) COMMENT '论文状态统计表';

CREATE TABLE paper_author (
  id INT PRIMARY KEY AUTO_INCREMENT,
  paper_id INT NULL,
  user_id INT NULL,
  CONSTRAINT fk_paper FOREIGN KEY (paper_id) REFERENCES papers(id),
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);
