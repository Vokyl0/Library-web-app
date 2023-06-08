CREATE TABLE books
(
    book_id int PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    genre VARCHAR(255),
    content TEXT,
    price NUMERIC(7,2),
    release_year int
)