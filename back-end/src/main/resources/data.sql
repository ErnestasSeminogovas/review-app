INSERT INTO roles (name, created_at, updated_at)
SELECT 'ROLE_USER', current_timestamp, current_timestamp WHERE 'ROLE_USER' NOT IN (SELECT name FROM roles);
INSERT INTO roles (name, created_at, updated_at)
SELECT 'ROLE_ADMIN', current_timestamp, current_timestamp WHERE 'ROLE_ADMIN' NOT IN (SELECT name FROM roles);