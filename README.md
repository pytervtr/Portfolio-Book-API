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


