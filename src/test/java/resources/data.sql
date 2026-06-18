DELETE FROM accounts;
DELETE FROM users;

INSERT INTO users (id, email, password, name)
VALUES (1, 'test@example.com', '$2a$10$abc...', 'Test User');

-- IMPORTANT: match Specmatic account IDs
INSERT INTO accounts (id, balance, user_id)
VALUES (106, 1000.0, 1);
