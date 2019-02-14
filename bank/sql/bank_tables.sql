/*

For testing

select * from users;
select * from users where moderator = 0 order by username;
select * from accounts;
*/
create table users (
    login_id number(10) primary key,
    username varchar2(255) not null unique,
    pass_word varchar2(255) not null,
    firstname varchar2(255) not null,
    lastname varchar2(255) not null,
    mod number(1) not null
);

create table accounts (
    account_id number(10) primary key,
    account_name varchar2(255) not null,
    owner varchar2(255) not null,
    balance number(10) not null,
    constraint acc_user
        foreign key (account_owner) references users (username)
);