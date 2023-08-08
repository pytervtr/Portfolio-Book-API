package com.bookapicrud.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookDTO {

    @NotNull(message = "Every book must have one title")
    @NotBlank(message = "Title must not be blank")
    @JsonProperty("title")
    private String title;

    @NotNull(message = "Every book must have one author")
    @NotBlank(message = "Author must not be blank")
    @JsonProperty("author")
    private String author;

    @NotNull(message = "Every book must have one release date")
    @Digits(integer = 5, fraction = 0,message = "Release date attribute must be a number")    
    @JsonProperty("releaseDate")
    private int releaseDate;

    @NotNull(message = "Every book must have one edition number")
    @Min(value = 1, message = "Every book has a minimum of one edition")
    @Digits(integer = 5, fraction = 0,message = "Edition attribute must be a number")    
    @JsonProperty("editions")
    private int editions;

    @NotNull(message = "Every book must have one genre")
    @NotBlank(message = "Genre must not be blank")
    @JsonProperty("genre")
    private String genre;

    @NotNull(message = "Every book must have one synopsis")
    @NotBlank(message = "Sysnopsis must not be blank") 
    @JsonProperty("synopsis")
    private String synopsis;

    public BookDTO() {
    }

    public BookDTO(String title, String author, int releaseDate, int editions, String genre, String synopsis) {
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.editions = editions;
        this.genre = genre;
        this.synopsis = synopsis;
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
        return "BookDTO{"+
                "title = "+this.title+"\n"+
                "author = "+this.author+"\n"+
                "releaseDate = "+this.releaseDate+"\n"+
                "editions = "+this.editions+"\n"+
                "genre = "+this.genre+"\n"+
                "synopsis = "+this.synopsis+"\n"+
            "}";
    }

}
