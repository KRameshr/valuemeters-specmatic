DELETE FROM accounts;
DELETE FROM users;

ALTER TABLE users ALTER COLUMN id RESTART WITH 2;
ALTER TABLE accounts ALTER COLUMN id RESTART WITH 2;

INSERT INTO users (id, name, email, password, role)
VALUES (
    1,
    'Test User',
    'test@example.com',
    '$2a$10$i4S5ksAsnY2/XdCxIhO8O.RpqNBhAOq8Ri/i1M/kcJAYpYOhR5mO.',
    'USER'
);

INSERT INTO accounts (id, account_number, balance, user_id)
VALUES (
    1,
    'ACC000001',
    1000.0,
    1
);
