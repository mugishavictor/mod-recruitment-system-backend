INSERT INTO roles(name) VALUES ('SUPER_ADMIN'), ('HR'), ('APPLICANT');

-- BCrypt hashes
-- Admin@123
INSERT INTO users(full_name, email, password, role_id, is_active, created_at, updated_at)
VALUES (
  'System Admin',
  'admin@recruitment.com',
  '$2a$10$f/u3WjT96jfw1sqeOehypuaNbQGwCxIY25RASEE8eE1x/MkKZfJtS',
  1,
  true,
  NOW(),
  NOW()
);

-- Hr@123
INSERT INTO users(full_name, email, password, role_id, is_active, created_at, updated_at)
VALUES (
  'HR Officer',
  'hr@recruitment.com',
  '$2a$10$1QqwO3PKkp9WaRLoxlImZek5MfWeCNZsnX1nrH1CalxcwHgsWFzCe',
  2,
  true,
  NOW(),
  NOW()
);