-- ENUM TYPES
CREATE TYPE USER_STATUS AS ENUM ('ACTIVE', 'INACTIVE', 'BANNED');
CREATE TYPE USER_ROLE AS ENUM ('ADMIN', 'USER', 'MODERATOR');
CREATE TYPE SKILL_LEVEL AS ENUM ('BEGINNER', 'INTERMEDIATE', 'ADVANCED', 'NATIVE');
CREATE TYPE VISIBILITY_TYPE AS ENUM ('WORLD', 'COUNTRY', 'CITY');
CREATE TYPE CHAT_STATUS AS ENUM ('ACTIVE', 'ARCHIVED', 'DELETED');
CREATE TYPE MESSAGE_TYPE AS ENUM ('PHOTO', 'LINK', 'TEXT');

-- TABLES
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role USER_ROLE NOT NULL,
    status USER_STATUS NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_details (
    user_id BIGINT PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE address (
    user_id BIGINT PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
    country VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    house_number VARCHAR(50) NOT NULL,
    zip_code VARCHAR(20) NOT NULL
);

CREATE TABLE skill (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    category VARCHAR(255)
);

CREATE TABLE skills_user (
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    skill_id BIGINT REFERENCES skill(id) ON DELETE CASCADE,
    level SKILL_LEVEL NOT NULL,
    experience NUMERIC CHECK (experience >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, skill_id)
);

CREATE TABLE announcement (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    skill_id BIGINT REFERENCES skill(id) ON DELETE SET NULL,
    visibility VISIBILITY_TYPE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE chat (
    id BIGSERIAL PRIMARY KEY,
    status CHAT_STATUS NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_chat (
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    chat_id BIGINT REFERENCES chat(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, chat_id)
);

CREATE TABLE message (
    id BIGSERIAL PRIMARY KEY,
    type MESSAGE_TYPE NOT NULL,
    chat_id BIGINT REFERENCES chat(id) ON DELETE CASCADE,
    sender_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    message_text TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE comment (
    id BIGSERIAL PRIMARY KEY,
    sender_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    owner_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    content VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rate (
    id BIGSERIAL PRIMARY KEY,
    value NUMERIC CHECK (value BETWEEN 1 AND 10),
    sender_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    owner_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
