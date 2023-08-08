# Portfolio-Book-API

A simple Book API with JWT authentication and email verification, built with Java Spring and PostgresQL.


## Features

- User authentication and authorization using JWT.
- User validation using mail verification.
- Built with Java Spring, a modern and powerfull Java framework.
- Written in Java, one of the most used and extended programming languages.
- Use of PostgreSQL as a database.


## Routes

| Route | Method | Description |
| ----- | ----- | ----- |
| /api/auth/login | POST | Authenticate user and generate JWT |
| /api/auth/logout | POST | Close user session |
| /api/auth/register | POST | Register new user |
| /api/auth/activateAccount | POST | Activate user account |
| /api/auth/refreshToken | PUT | Generate new Refresh token |
| /api/auth/requestPassword | PUT | Generate a request to change users password |
| /api/auth/recoverPassword | PUT | Change user passowrd |
| - | - | - |
| /api/books | POST | Create new book |
| /api/books/title/:bookTitle | GET | Retrieve one book by requested title |
| /api/auth/api/books/author/:author | GET | Retrieve all books written by an author |
| /api/auth/api/books/list | GET | Retrieve all books |
| /api/auth/api/books/update | PUT | Update book info |
| /api/books/delete/:author/:title | DELETE | Remove requested book by author and title |
| - | - | - |
| /api/users | GET | Retrieve user data |
| /api/users/remove | DELETE | Remove user account |
| - | - | - |


## To run this project locally

- First you will need to create a database with the name book_db. `CREATE DATABSE BOOK_DB;`.
- (Optional) Next, you will need to execute the file "book_api_script.sql". If you don't do this, the API will automatically generate the table schemas.
- Clone this repository using the command git clone https://github.com/pytervtr/Portfolio-Book-API.git .
- Then, in the file bookapicrud/target/classes/application.properties and change "spring.datasource.url" with your db url.
- In the same file, also update the username and password with the credentials used by your databse.
- If you wish to authenticate yourself, add a new key to the mail configuration.
- Run `mvn clean package` from your project directory.
- Execute the project using the command `java -jar target/bookapicrud-0.0.1-SNAPSHOT.jar`.
- Then API will be accessible at `localhost:8081`.

