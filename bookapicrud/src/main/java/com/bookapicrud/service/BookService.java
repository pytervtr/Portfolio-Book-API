package com.bookapicrud.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookapicrud.exception.types.APIRequestParameterException;
import com.bookapicrud.exception.types.APIResourceException;
import com.bookapicrud.model.DAO.Book;
import com.bookapicrud.model.DTO.BookDTO;
import com.bookapicrud.model.Mapper.BookMapper;
import com.bookapicrud.repository.BookRepository;

import jakarta.validation.Validator;

@Service
public class BookService implements BookServiceInterface {
    

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private Validator bookValidator;


    @Override
    public BookDTO saveBook(BookDTO book) throws APIRequestParameterException, APIResourceException{/*impl */

        if(!bookValidator.validate(book).isEmpty()){throw new APIRequestParameterException();}

        Book bookDao = BookMapper.bookDtoToDao(book);

        if(isBookPersisted(bookDao)){throw new  APIResourceException("Book resource already exist");}

        if(!bookValidator.validate(bookDao).isEmpty()){throw new APIRequestParameterException();}

        bookRepository.save(bookDao);

        return book;
    }



    @Override
    public List<BookDTO> retrieveAllBooks(){/*impl */

        List<Book> booksDao = bookRepository.findAll();
        List<BookDTO> booksDto = new ArrayList<>();

        for(Book currentBook : booksDao){
            booksDto.add(BookMapper.bookDaoToDto(currentBook));
        }
        return booksDto;
    }

    @Override
    public BookDTO retrieveBook(String title) throws APIResourceException{/*impl */

        Optional<Book> bookDao = bookRepository.retrieveBookByTitle(title);
        if(bookDao.isEmpty()){throw new APIResourceException("Book resource doesnt exist");}
        
        return BookMapper.bookDaoToDto(bookDao.get());
    }

    @Override
    public List<BookDTO> retrieveAuthorBooks(String author){/*impl */

        List<Book> booksDao = bookRepository.retrieveBooksByAuthor(author);
        if(booksDao.isEmpty()){throw new APIResourceException("Author requested doesnt exist");}
        List<BookDTO> booksDto = new ArrayList<>();

        for(Book currentBook : booksDao){
            booksDto.add(BookMapper.bookDaoToDto(currentBook));
        }
        return booksDto;
    }

    @Override
    public BookDTO updateBook(BookDTO updatedBook) throws  APIRequestParameterException, APIResourceException{/*impl */

        if(!bookValidator.validate(updatedBook).isEmpty()){throw new APIRequestParameterException();}

        Book checkBook = new Book(0, updatedBook.getTitle(), updatedBook.getAuthor(), 0, 0, null, null);
        if(!isBookPersisted(checkBook)){throw new  APIResourceException("Book resource doesnt exist");}
        Book bookDao = bookRepository.retrieveBook(updatedBook.getTitle(), updatedBook.getAuthor()).get(0);

        bookDao.setEditions(updatedBook.getEditions());
        bookDao.setGenre(updatedBook.getGenre());
        bookDao.setReleaseDate(updatedBook.getReleaseDate());
        bookDao.setSynopsis(updatedBook.getSynopsis());

        if(!bookValidator.validate(bookDao).isEmpty()){throw new APIRequestParameterException();}

        bookRepository.save(bookDao);

        return updatedBook;
    }


    @Override
    public Boolean removeBook(String author, String title){

        Book checkBook = new Book(0, title, author, 0, 0, null, null);
        if(!isBookPersisted(checkBook)){throw new APIResourceException("Book resource doesnt exist");}

        Book bookDao = bookRepository.retrieveBook(title, author).get(0);

        bookRepository.delete(bookDao);

        return true;

    }

    private Boolean isBookPersisted(Book bookDao){
        return bookRepository.retrieveBook( bookDao.getTitle(), bookDao.getAuthor()).size() == 1;
    }
     
}
