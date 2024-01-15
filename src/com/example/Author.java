package com.example;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Author {
    private String author_name, author_bio, author_nationality, author_genre,awards;
    private LocalDate author_dob;
    // private List<String> awards;

    public Author(String author_name, String author_bio, String author_nationality, String author_dob, String author_genre, String awards) {
        this.author_name = author_name;
        this.author_bio = author_bio;
        this.author_nationality = author_nationality;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.author_dob = LocalDate.parse(author_dob,formatter);
        this.author_genre = author_genre;
        this.awards = awards;
    }

    // Getter and Setter for author_name
    public String getAuthorName() {
        return author_name;
    }

    public void setAuthorName(String author_name) {
        this.author_name = author_name;
    }

    // Getter and Setter for author_bio
    public String getAuthorBio() {
        return author_bio;
    }

    public void setAuthorBio(String author_bio) {
        this.author_bio = author_bio;
    }

    // Getter and Setter for author_nationality
    public String getAuthorNationality() {
        return author_nationality;
    }

    public void setAuthorNationality(String author_nationality) {
        this.author_nationality = author_nationality;
    }

    // Getter and Setter for author_dob
    public LocalDate getAuthorDob() {
        return author_dob;
    }

    public void setAuthorDob(LocalDate author_dob) {
        this.author_dob = author_dob;
    }

    // Getter and Setter for author_genre
    public String getAuthorGenre() {
        return author_genre;
    }

    public void setAuthorGenre(String author_genre) {
        this.author_genre = author_genre;
    }

    // Getter and Setter for awards
    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }
    public void getAuthorDetails(){
        System.out.println("Name: "+this.author_name+" Bio: "+this.author_bio+" Nationality: "+this.author_nationality+" Genre: "+this.author_genre+" DOB: "+this.author_dob+" Awards: "+this.awards);
    }
}
