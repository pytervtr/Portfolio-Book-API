package com.bookapicrud.model.Mapper;

import com.bookapicrud.model.DAO.Book;
import com.bookapicrud.model.DTO.BookDTO;

public class BookMapper {
    
    public static BookDTO bookDaoToDto(Book bookDao){
        if(bookDao == null){return null;}
        
        BookDTO bookDto = new BookDTO();
        bookDto.setTitle(bookDao.getTitle());
        bookDto.setAuthor(bookDao.getAuthor());
        bookDto.setReleaseDate(bookDao.getReleaseDate());
        bookDto.setEditions(bookDao.getEditions());
        bookDto.setGenre(bookDao.getGenre());
        bookDto.setSynopsis(bookDao.getSynopsis());

        return bookDto;
    }

    public static Book bookDtoToDao(BookDTO bookDto){
        
        if(bookDto == null){return null;}
        
        Book bookDao = new Book();
        bookDao.setTitle(bookDto.getTitle());
        bookDao.setAuthor(bookDto.getAuthor());
        bookDao.setReleaseDate(bookDto.getReleaseDate());
        bookDao.setEditions(bookDto.getEditions());
        bookDao.setGenre(bookDto.getGenre());
        bookDao.setSynopsis(bookDto.getSynopsis());

        return bookDao;
    }
}
