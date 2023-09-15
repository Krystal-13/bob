create table if not exists menu_name
(
    name varchar(255) not null
        primary key
);

create table if not exists  recipe
(
    id          bigint auto_increment
        primary key,
    cook_time   varchar(100) null,
    description varchar(255) null,
    image       varchar(255) null,
    ingredients text         null,
    name        varchar(100) null,
    recipe_link varchar(255) null,
    steps       text         null,
    user_id     bigint       null
);

create table if not exists  bookmark
(
    id         bigint auto_increment
        primary key,
    group_name varchar(100) null,
    memo       longtext     null,
    user_id    bigint       null,
    recipe_id  bigint       null,
    constraint FKd7wpd757bxutqdt02bove1r9h
        foreign key (recipe_id) references recipe (id)
);

create table if not exists  recipe_link
(
    id     bigint auto_increment
        primary key,
    link   varchar(255) null,
    name   varchar(255) null,
    source varchar(255) null,
    constraint UK_lejtialdwese995s7bln5tyg5
        unique (link)
);

create table if not exists  user
(
    id       bigint auto_increment
        primary key,
    email    varchar(255) not null,
    name     varchar(255) not null,
    password varchar(255) not null
);

create table if not exists  review
(
    id            bigint auto_increment
        primary key,
    image         varchar(255) null,
    recipe_id     bigint       null,
    registered_at datetime(6)  null,
    score         int          not null,
    text          longtext     null,
    user_id       bigint       null,
    constraint FK9dqw7ep5rnww1yuimutvg4nny
        foreign key (recipe_id) references recipe (id),
    constraint FKiyf57dy48lyiftdrf7y87rnxi
        foreign key (user_id) references user (id)
);
