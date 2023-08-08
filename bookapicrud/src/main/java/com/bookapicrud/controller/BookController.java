package com.bookapicrud.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookapicrud.model.DTO.BookDTO;
import com.bookapicrud.service.BookServiceInterface;


@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookServiceInterface bookService;
    
    @GetMapping("/test")
    public ResponseEntity<Map<String,String>> test(){
        return new ResponseEntity<Map<String,String>>
            (Map.of(
                "status","connection working", 
                "message", "hello world book"),
            HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Map<String,Object>> createBook(@RequestBody BookDTO book){

        bookService.saveBook(book);

        return new ResponseEntity<Map<String,Object>>
            (Map.of(
                "status","request completed", 
                "message", book),
            HttpStatus.CREATED);
    }

    @GetMapping("/title/{bookTitle}")
    public ResponseEntity<Map<String,Object>> retrieveBook(@PathVariable String bookTitle){
        
        BookDTO book = bookService.retrieveBook(bookTitle);

        return new ResponseEntity<Map<String,Object>>
            (Map.of(
                "status","request completed", 
                "message", book),
            HttpStatus.OK);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<Map<String,Object>> retrieveAuthorBooks(@PathVariable("author") String bookAuthor){
        
        List<BookDTO> bookList = bookService.retrieveAuthorBooks(bookAuthor);

        return new ResponseEntity<Map<String,Object>>
            (Map.of(
                "status","request completed", 
                "message", bookList),
            HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String,Object>> retrieveAllBooks(){
        
        List<BookDTO> allBooks = bookService.retrieveAllBooks();

        return new ResponseEntity<Map<String,Object>>
            (Map.of(
                "status","request completed", 
                "message", allBooks),
            HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String,Object>> updateBook(@RequestBody BookDTO book){
        
        bookService.updateBook(book);

        return new ResponseEntity<Map<String,Object>>
            (Map.of(
                "status","request completed", 
                "message", book),
            HttpStatus.OK);
    }

    @DeleteMapping("/delete/{author}/{title}")
    public ResponseEntity<Map<String,Object>> removeBook(@PathVariable("author") String author, @PathVariable("title") String title){
        
        bookService.removeBook(author, title);

        return new ResponseEntity<Map<String,Object>>
            (Map.of(
                "status","request completed", 
                "message", "book remove correctly"),
            HttpStatus.OK);
    }
}
