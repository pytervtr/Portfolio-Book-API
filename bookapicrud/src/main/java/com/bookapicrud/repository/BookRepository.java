package com.bookapicrud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookapicrud.model.DAO.Book;


@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
    @Query(value = "SELECT * FROM books WHERE books.title=?1 AND books.author=?2", nativeQuery = true)
    List<Book> retrieveBook(String bookName, String author);

    @Query(value = "SELECT * FROM books WHERE books.title=?1", nativeQuery = true)
    Optional<Book> retrieveBookByTitle(String bookName);

    @Query(value = "SELECT * FROM books WHERE books.author=?1", nativeQuery = true)
    List<Book> retrieveBooksByAuthor(String author);
}
