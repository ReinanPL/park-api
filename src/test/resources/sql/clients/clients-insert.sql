insert into users (id, username, password, role) values (101, 'ana@gmail.com', '$2a$12$uC/huPf8V16gYLnzFkz6ouALKyeDiRME7QPMXVs3MaN1Bt8M6C2Ji', 'ROLE_ADMIN');
insert into users (id, username, password, role) values (102, 'bia@gmail.com', '$2a$12$uC/huPf8V16gYLnzFkz6ouALKyeDiRME7QPMXVs3MaN1Bt8M6C2Ji', 'ROLE_CLIENT');
insert into users (id, username, password, role) values (103, 'bob@gmail.com', '$2a$12$uC/huPf8V16gYLnzFkz6ouALKyeDiRME7QPMXVs3MaN1Bt8M6C2Ji', 'ROLE_CLIENT');
insert into users (id, username, password, role) values (104, 'levi@gmail.com', '$2a$12$uC/huPf8V16gYLnzFkz6ouALKyeDiRME7QPMXVs3MaN1Bt8M6C2Ji', 'ROLE_CLIENT');
insert into users (id, username, password, role) values (105, 'zac@gmail.com', '$2a$12$uC/huPf8V16gYLnzFkz6ouALKyeDiRME7QPMXVs3MaN1Bt8M6C2Ji', 'ROLE_CLIENT');

insert into clients(id, name, cpf, user_id) values (12, 'Bia Rosa', '44019429065', 102);
insert into clients(id, name, cpf, user_id) values (13, 'Levi John', '96766275006', 104);
insert into clients(id, name, cpf, user_id) values (14, 'Zac Kol', '15930856052', 105);
