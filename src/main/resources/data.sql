INSERT INTO users (id, username, password, role)
VALUES
(1, 'alice@example.com', '$2a$12$uC/huPf8V16gYLnzFkz6ouALKyeDiRME7QPMXVs3MaN1Bt8M6C2Ji', 'ROLE_ADMIN'),
(2, 'bob@example.com', '$2a$12$uC/huPf8V16gYLnzFkz6ouALKyeDiRME7QPMXVs3MaN1Bt8M6C2Ji', 'ROLE_CLIENT'),
(3, 'charlie@example.com', '$2a$12$uC/huPf8V16gYLnzFkz6ouALKyeDiRME7QPMXVs3MaN1Bt8M6C2Ji', 'ROLE_CLIENT'),
(4, 'david@example.com', '$2a$12$uC/huPf8V16gYLnzFkz6ouALKyeDiRME7QPMXVs3MaN1Bt8M6C2Ji', 'ROLE_CLIENT'),
(5, 'eva@example.com', '$2a$12$uC/huPf8V16gYLnzFkz6ouALKyeDiRME7QPMXVs3MaN1Bt8M6C2Ji', 'ROLE_CLIENT'),
(6, 'frank@example.com', '$2a$12$uC/huPf8V16gYLnzFkz6ouALKyeDiRME7QPMXVs3MaN1Bt8M6C2Ji', 'ROLE_CLIENT');

INSERT INTO clients(id, name, cpf, user_id)
VALUES
(1, 'Alice Silva', '21802504010', 1),
(2, 'Bob Santos', '44849813003', 2),
(3, 'Charlie Oliveira', '35967515003', 3),
(4, 'David Pereira', '24746957029', 4),
(5, 'Eva Lima', '74126661000', 5),
(6, 'Frank Costa', '60751322016', 6);
