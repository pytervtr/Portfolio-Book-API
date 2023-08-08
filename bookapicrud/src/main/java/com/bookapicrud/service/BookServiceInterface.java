package com.bookapicrud.service;

import java.util.List;

import com.bookapicrud.model.DTO.BookDTO;

public interface BookServiceInterface {
    List<BookDTO> retrieveAllBooks();

    BookDTO saveBook(BookDTO book); 
    
    BookDTO retrieveBook(String title);

    List<BookDTO> retrieveAuthorBooks(String author);

    BookDTO updateBook(BookDTO updatedBook);

    Boolean removeBook(String author, String title);

}
