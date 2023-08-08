DROP TABLE books;
DROP TABLE tokens;
DROP TABLE users;

CREATE TABLE books(
	book_id SERIAL PRIMARY KEY,
	title VARCHAR(255) NOT NULL,
	author VARCHAR(100),
	editions INTEGER,
	release_date INTEGER,
	genre VARCHAR(50),
	synopsis VARCHAR(1000)

);

CREATE TABLE users(
	user_id SERIAL PRIMARY KEY,
	user_name VARCHAR(100) NOT NULL UNIQUE,
	email VARCHAR(100) NOT NULL UNIQUE,
	password VARCHAR(255),
	activated BOOLEAN,
	join_date TIMESTAMP
);

CREATE TABLE tokens(
	token_id SERIAL PRIMARY KEY,
    user_id INTEGER,
    expiration_date DATE,

	FOREIGN KEY (user_id) REFERENCES USERS(user_id)
);



INSERT INTO users(user_id , user_name, email, password, activated, join_date) VALUES (1, 'user1', 'user1@example.com', '$2a$10$0cWaVVrcyDRtczWueYXAQ.SQV/mTjmxar9I7H50yg7X4bqz.qqUkS', true, '2023-08-01');
INSERT INTO users(user_id , user_name, email, password, activated, join_date) VALUES (2, 'user2', 'user2@example.com', '$2a$10$W5UT0VV0r.cfW0BamgAW4.i4QrwalaUUE3ODt8AUQINeasbvVY/Xu', true, '2023-08-01');
INSERT INTO users(user_id , user_name, email, password, activated, join_date) VALUES (3, 'user3', 'user3@example.com', '$2a$10$Gdiu6oQcM4JEQxZM4lHF8uFUOE9AFXOr6HpkppBO7TiEzSGQbWV4.', true, '2023-08-01');
INSERT INTO users(user_id , user_name, email, password, activated, join_date) VALUES (4, 'user4', 'user4@example.com', '$2a$10$jEyQzsfT7.SyIGwG9MKx4uXFalgC5L6aS7hkvV0hTbTntu2Njf4/C', true, '2023-08-01');
INSERT INTO users(user_id , user_name, email, password, activated, join_date) VALUES (5, 'user5', 'user5@example.com', '$2a$10$YpkNF6B3jhq.XxFaGg83XeuHTWeUxTLoWg.9CFD19t0PuE5kdowA2', false, '2023-08-01');
INSERT INTO users(user_id , user_name, email, password, activated, join_date) VALUES (6, 'user6', 'user6@example.com', '$2a$10$OrTJ/x4tm7SmSVYeqFXEjueykTQvNieGZRPIE7jtbopLv6qTswl2W', true, '2023-08-01');
INSERT INTO users(user_id , user_name, email, password, activated, join_date) VALUES (7, 'user7', 'user7@example.com', '$2a$10$JkV7bA9JL0.T7CmdRWtFsebqY44pRd64AM8/taD7b.bNe0CPnB4Te', false, '2023-08-01');






INSERT INTO books (book_id, title, author, editions, release_date, genre, synopsis) VALUES (1, 'The Great Gatsby', 'F. Scott Fitzgerald', 5, 1925, 'Classic Fiction', 'A story of Jay Gatsby and his pursuit of the American Dream.');
INSERT INTO books (book_id, title, author, editions, release_date, genre, synopsis) VALUES (2, 'Harry Potter and the Sorcerers Stone', 'J.K. Rowling', 10, 1997, 'Fantasy', 'The first book in the magical Harry Potter series.');
INSERT INTO books (book_id, title, author, editions, release_date, genre, synopsis) VALUES (3, 'To Kill a Mockingbird', 'Harper Lee', 3, 1960, 'Classic Fiction', 'A novel that addresses issues of racial injustice and moral growth.');
INSERT INTO books (book_id, title, author, editions, release_date, genre, synopsis) VALUES (4, '1984', 'George Orwell', 8, 1949, 'Dystopian Fiction', 'A chilling portrayal of a totalitarian society and government surveillance.');
INSERT INTO books (book_id, title, author, editions, release_date, genre, synopsis) VALUES (5, 'Pride and Prejudice', 'Jane Austen', 6, 1813, 'Romance', 'A romantic novel revolving around Elizabeth Bennet and Mr. Darcy.');
INSERT INTO books (book_id, title, author, editions, release_date, genre, synopsis) VALUES (6, 'The Catcher in the Rye', 'J.D. Salinger', 4, 1951, 'Coming-of-age Fiction', 'A story of teenage rebellion and alienation.');
INSERT INTO books (book_id, title, author, editions, release_date, genre, synopsis) VALUES (7, 'The Lord of the Rings', 'J.R.R. Tolkien', 12, 1954, 'Fantasy', 'An epic fantasy adventure in Middle-earth.');
INSERT INTO books (book_id, title, author, editions, release_date, genre, synopsis) VALUES (8, 'Brave New World', 'Aldous Huxley', 6, 1932, 'Dystopian Fiction', 'A vision of a future society where pleasure and conformity dominate.');
INSERT INTO books (book_id, title, author, editions, release_date, genre, synopsis) VALUES (9, 'The Hobbit', 'J.R.R. Tolkien', 8, 1937, 'Fantasy', 'The prelude to The Lord of the Rings following Bilbo Baggins journey.');
INSERT INTO books (book_id, title, author, editions, release_date, genre, synopsis) VALUES (10, 'A Game of Thrones', 'George R.R. Martin', 10, 1996, 'Fantasy', 'The first book in the epic fantasy series A Song of Ice and Fire that inspired the TV show Game of Thrones.');

