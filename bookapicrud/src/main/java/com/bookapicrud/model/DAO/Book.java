package com.bookapicrud.model.DAO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "books", uniqueConstraints = @UniqueConstraint(columnNames = {"title","author"}))
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    @SequenceGenerator(name = "book_generator", sequenceName = "book_id_generator",allocationSize=10, initialValue = 11)
    @Column(name="book_id", updatable = false, nullable = false)
    private long id;

    @Column(name = "title", nullable = false)
    @NotNull(message = "Every book must have one title")
    @NotBlank(message = "Title must not be blank")
    private String title;

    @Column(name = "author", nullable = false)
    @NotNull(message = "Every book must have one author")
    @NotBlank(message = "Author must not be blank")
    private String author;

    @Column(name = "releaseDate", nullable = false)
    @NotNull(message = "Every book must have one release date")
    @Digits(integer = 5, fraction = 0,message = "Release date attribute must be a number")
    private int releaseDate;

    @Column(name = "editions", nullable = false)
    @NotNull(message = "Every book must have one edition number")
    @Min(value = 1, message = "Every book has a minimum of one edition")
    @Digits(integer = 5, fraction = 0,message = "Edition attribute must be a number")
    private int editions;

    @Column(name = "genre", nullable = false)
    @NotNull(message = "Every book must have one genre")
    @NotBlank(message = "Genre must not be blank")
    private String genre;

    @Column(name = "synopsis", nullable = false)
    @NotNull(message = "Every book must have one synopsis")
    @NotBlank(message = "Sysnopsis must not be blank") 
    private String synopsis;


    public Book (){}


    public Book(long id, String title, String author, int releaseDate, int editions, String genre,
            String synopsis) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.editions = editions;
        this.genre = genre;
        this.synopsis = synopsis;
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getAuthor() {
        return author;
    }


    public void setAuthor(String author) {
        this.author = author;
    }


    public int getReleaseDate() {
        return releaseDate;
    }


    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }


    public int getEditions() {
        return editions;
    }


    public void setEditions(int editions) {
        this.editions = editions;
    }


    public String getGenre() {
        return genre;
    }


    public void setGenre(String genre) {
        this.genre = genre;
    }


    public String getSynopsis() {
        return synopsis;
    }


    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    @Override
    public String toString(){
        return "Book{"+
                "id = "+this.id+"\n"+
                "title = "+this.title+"\n"+
                "author = "+this.author+"\n"+
                "releaseDate = "+this.releaseDate+"\n"+
                "editions = "+this.editions+"\n"+
                "genre = "+this.genre+"\n"+
                "synopsis = "+this.synopsis+"\n"+
            "}";
    }

}
