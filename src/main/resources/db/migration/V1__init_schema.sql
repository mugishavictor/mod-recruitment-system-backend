CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(150) NOT NULL,
    email VARCHAR(120) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_id BIGINT NOT NULL REFERENCES roles(id),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE applicants (
    id BIGSERIAL PRIMARY KEY,
    nid VARCHAR(16) UNIQUE NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone VARCHAR(30) NOT NULL,
    email VARCHAR(120) NOT NULL,
    date_of_birth DATE,
    address VARCHAR(255),
    grade VARCHAR(50),
    school_option VARCHAR(100),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE cv_files (
    id BIGSERIAL PRIMARY KEY,
    original_name VARCHAR(255),
    stored_name VARCHAR(255),
    content_type VARCHAR(120),
    file_path VARCHAR(255),
    file_size BIGINT,
    uploaded_at TIMESTAMP
);

CREATE TABLE applications (
    id BIGSERIAL PRIMARY KEY,
    applicant_id BIGINT NOT NULL REFERENCES applicants(id),
    reference_code VARCHAR(30) UNIQUE NOT NULL,
    status VARCHAR(30) NOT NULL,
    cv_file_id BIGINT REFERENCES cv_files(id),
    submitted_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE application_reviews (
    id BIGSERIAL PRIMARY KEY,
    application_id BIGINT NOT NULL REFERENCES applications(id),
    reviewed_by BIGINT NOT NULL REFERENCES users(id),
    decision VARCHAR(20) NOT NULL,
    reason TEXT,
    reviewed_at TIMESTAMP
);