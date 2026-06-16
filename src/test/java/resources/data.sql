-- Clean up existing data first if necessary
DELETE FROM accounts;
DELETE FROM users;

-- Seed User
INSERT INTO users (id, email, password, name) 
VALUES (1, 'test@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'Test User');

-- Seed a few "Safe" accounts
INSERT INTO accounts (id, balance, user_id) VALUES (100, 1000.0, 1);
INSERT INTO accounts (id, balance, user_id) VALUES (200, 1000.0, 1);