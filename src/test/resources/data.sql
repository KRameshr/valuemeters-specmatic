DELETE FROM accounts;
DELETE FROM users;

INSERT INTO users (id, email, password, name)
VALUES (
1,
'test@example.com',
'$2a$10$7EqJtq98hPqEX7fNZaFWoOHi6xM7QJ7N5K56ZtPcNI0YvrFica8F2',
'Test User'
);

INSERT INTO accounts (id, balance, user_id)
VALUES
(1, 1000.0, 1),
(106, 1000.0, 1);