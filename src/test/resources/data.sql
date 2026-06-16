-- Test user (password: Test@1234)
INSERT IGNORE INTO users (id, name, email, password, role) VALUES 
(999, 'Test User', 'test@example.com', '$2a$10$WqFsX6BYDOx8mSWCP8S/l.7XON1HNU9ZpdNZEUdLLFilABWASNQqK', 'USER');

-- Test account for user 999
INSERT IGNORE INTO accounts (id, account_number, balance, user_id) VALUES 
(999, 'ACC999TEST1', 10000.00, 999);