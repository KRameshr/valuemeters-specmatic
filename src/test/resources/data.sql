DELETE FROM accounts;
DELETE FROM users;

INSERT INTO users (id, email, password, name)
VALUES (
1,
'test@example.com',
'$2a$10$6u3qyarJ2dPSGmpHryVIi.jLEh1f68LSERtIsZ8tS9KFFwQSBogla',
'Test User'
);

INSERT INTO accounts (id, balance, user_id)
VALUES
(1, 1000.0, 1),
(106, 1000.0, 1);