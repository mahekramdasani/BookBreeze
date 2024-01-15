package com.example;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Book implements Comparable<Book> {
    private int book_isbn;
    private String book_title, book_author, book_genre, book_content;
    private float book_fine;
    private boolean isAvailable;
    private int issuedToUserId;
    private LocalDate issueDate;
    private LocalDate returnDate;
    private float rating;

    public Book(int book_isbn, String book_title, String book_author,String book_genre, String book_content, float book_fine,float rating) {
        this.book_isbn = book_isbn;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_genre = book_genre;
        this.book_content = book_content;
        this.book_fine = book_fine;
        this.isAvailable = true;
        this.issuedToUserId = -1;
        this.issueDate = null;
        this.returnDate = null;
        this.rating = rating; // Initialize to -1 indicating no rating given yet
    }
    public Book(int book_isbn, String book_title, String book_author,String book_genre, String book_content, int issuedToUserId,String issueDate,float book_fine,float rating) {
        this.book_isbn = book_isbn;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_genre = book_genre;
        this.book_content = book_content;
        this.book_fine = book_fine;
        this.isAvailable = false;
        this.issuedToUserId = issuedToUserId;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        this.issueDate = LocalDate.parse(issueDate,formatter);
        this.returnDate = this.issueDate.plusDays(10);
        this.rating = rating;
    }
    public Book(Book other) {
        this.book_isbn = other.book_isbn;
        this.book_title = other.book_title;
        this.book_author = other.book_author;
        this.book_genre = other.book_genre;
        this.book_content = other.book_content;
        this.book_fine = other.book_fine;
        this.isAvailable = other.isAvailable;
        this.issuedToUserId = other.issuedToUserId;
        this.issueDate = (other.issueDate != null) ? LocalDate.ofEpochDay(other.issueDate.toEpochDay()) : null;
        this.returnDate = (other.returnDate != null) ? LocalDate.ofEpochDay(other.returnDate.toEpochDay()) : null;
        this.rating = other.rating;
    }

    @Override
    public int compareTo(Book otherBook) {
        // Default comparison based on title
        return this.book_title.compareTo(otherBook.book_title);
    }

    // Custom comparison method based on user input
    public int compareByCriterion(int criterion, Book otherBook) {
        switch (criterion) {
            case 1:
                return this.book_title.compareTo(otherBook.book_title);
            case 2:
                return this.book_author.compareTo(otherBook.book_author);
            case 3:
                return this.book_genre.compareTo(otherBook.book_genre);
            // Add more cases for additional criteria
            default:
                return 0; // Default to title comparison
        }
    }

    // Getter and Setter for isAvailable
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    // Getter and Setter for issuedToUserId
    public int getIssuedToUserId() {
        return issuedToUserId;
    }

    public void setIssuedToUserId(int issuedToUserId) {
        this.issuedToUserId = issuedToUserId;
    }

    // Getter and Setter for issueDate
    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    // Getter and Setter for returnDate
    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    // Getter and Setter for rating
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    // Getter and Setter for book_isbn
    public int getBookIsbn() {
        return book_isbn;
    }

    public void setBookIsbn(int book_isbn) {
        this.book_isbn = book_isbn;
    }

    // Getter and Setter for book_title
    public String getBookTitle() {
        return book_title;
    }

    public void setBookTitle(String book_title) {
        this.book_title = book_title;
    }

    // Getter and Setter for book_author
    public String getBookAuthor() {
        return book_author;
    }

    public void setBookAuthor(String book_author) {
        this.book_author = book_author;
    }

    // Getter and Setter for
    // Getter and Setter for book_genre
    public String getBookGenre() {
        return book_genre;
    }

    public void setBookGenre(String book_genre) {
        this.book_genre = book_genre;
    }

    // Getter and Setter for book_content
    public String getBookContent() {
        return book_content;
    }

    public void setBookContent(String book_content) {
        this.book_content = book_content;
    }


    // Getter and Setter for book_fine
    public float getBookFine() {
        return book_fine;
    }

    public void setBookPrice(float book_fine) {
        this.book_fine = book_fine;
    }

    public void getBookDetails(){
        if(isAvailable){
            System.out.println("ISBN: "+this.book_isbn+" Title: "+this.book_title+" Author: "+this.book_author+" Genre: "+this.book_genre+" Content: "+this.book_content+" Rating: "+this.rating+" Book Available: yes Fine: "+this.book_fine);

        }else{
            System.out.println("ISBN: "+this.book_isbn+" Title: "+this.book_title+" Author: "+this.book_author+" Genre: "+this.book_genre+" Content: "+this.book_content+" Rating: "+this.rating+" Book Available: no Issued to UserId: "+this.issuedToUserId+" Issued Date: "+this.issueDate.toString()+" Return Date: "+this.returnDate.toString()+" Fine: "+this.book_fine);
        }
    }
    public Book deepCopy() {
        return new Book(this);
    }

}
