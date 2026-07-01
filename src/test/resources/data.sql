DELETE FROM budgets;
DELETE FROM accounts;
DELETE FROM users;

ALTER TABLE users ALTER COLUMN id RESTART WITH 3;
ALTER TABLE accounts ALTER COLUMN id RESTART WITH 3;
ALTER TABLE budgets ALTER COLUMN id RESTART WITH 2;

INSERT INTO users (id, name, email, password, role)
VALUES (
    1,
    'Test User',
    'test@example.com',
    '$2a$10$i4S5ksAsnY2/XdCxIhO8O.RpqNBhAOq8Ri/i1M/kcJAYpYOhR5mO.',
    'USER'
);

INSERT INTO users (id, name, email, password, role)
VALUES (
    2,
    'Second User',
    'second@example.com',
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

INSERT INTO accounts (id, account_number, balance, user_id)
VALUES (
    2,
    'ACC000002',
    1000.0,
    2
);

INSERT INTO budgets (id, daily_limit, weekly_limit, monthly_limit, account_id)
VALUES (
    1,
    100.0,
    500.0,
    2000.0,
    1
);
