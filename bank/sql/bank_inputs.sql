insert into users (login_id, username, password, firstname, lastname, mod) values (0, 'person', 'yes', 'TestGuy', 'Test Guy Jr.', 0);
insert into users (login_id, username, password, firstname, lastname, mod) values (1, 'nosybastard', 'johnsnow', 'Randy', 'Newcommer', 0);
insert into users (login_id, username, password, firstname, lastname, mod) values (2, 'greybeard', 'arengar', 'Stan', 'Smith',  0);
insert into users (login_id, username, password, firstname, lastname, mod) values (3, 'IseeIdo15', 'DoneisDone', 'Rose', 'Gonzalez', 0);
insert into users (login_id, username, password, firstname, lastname, mod) values (4, 'MichaelBay5', 'Explosion', 'Michael', 'Bayer', 1);
insert into users (login_id, username, password, firstname, lastname, mod) values (5, 'Example', 'Account', 'Dark', 'Souls', 0);
insert into users (login_id, username, password, firstname, lastname, mod) values (6, 'ModEx', 'Account', 'Mircosoft', 'Apple', 1);

insert into accounts values (0, 'Checkings', 'ichinisan', 0);
insert into accounts values (1, 'Savings', 'ichinisan', 150.76);
insert into accounts values (2, 'Checkings', 'IseeIdo15', 35);
insert into accounts values (3, 'Gold', 'Example', 50);
insert into accounts values (4, 'Guns', 'Example', 12);
insert into accounts values (5, 'Fudge', 'Example', 3000);

update accounts set balance = -38 + (select balance from accounts where account_id = 0) where account_id = 0;
update accounts set balance = 10 + (select balance from accounts where account_id = 0) where account_id = 0;
select balance from accounts where account_id = 0;
delete from accounts where account_id = 22 and account_owner = 'Example';
select * from users where username = 'ichinisan' and password = 'junichi' and moderator = 0;

grant debug any procedure to master;
grant debug connect session to master;
select * from user_sys_privs;
commit;
rollback;