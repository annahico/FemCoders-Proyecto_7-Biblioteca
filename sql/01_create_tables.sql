-- ================================================
-- 01_create_tables.sql
-- Create all tables for the Library database
-- Run this AFTER creating the database library_db
-- ================================================

-- ================================================
-- TABLE 1: authors
-- ================================================
CREATE TABLE authors
(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(150) NOT NULL,
    last_name VARCHAR(150) NOT NULL,
);

-- ================================================
-- TABLE 2: books
-- isbn is UNIQUE because no two books share the same ISBN
-- description max 200 characters
-- ================================================
CREATE TABLE books
(
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    isbn VARCHAR(17) UNIQUE NOT NULL,
    description VARCHAR(200),
    publication_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ================================================
-- TABLE 3: genres
-- name is UNIQUE because each genre appears only once
-- ================================================
CREATE TABLE genres
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

-- ================================================
-- TABLE 4: books_authors
-- ON DELETE CASCADE: if a book or author is deleted the link is automatically removed
-- ================================================
CREATE TABLE books_authors
(
    book_id INTEGER REFERENCES books(id)   ON DELETE CASCADE,
    author_id INTEGER REFERENCES authors(id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, author_id)
);

-- ================================================
-- TABLE 5: books_genres
-- ON DELETE CASCADE: if a book or genre is deleted, the link is automatically removed
-- ================================================
CREATE TABLE books_genres
(
    book_id INTEGER REFERENCES books(id)  ON DELETE CASCADE,
    genre_id INTEGER REFERENCES genres(id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, genre_id)
);