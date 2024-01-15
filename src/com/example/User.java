package com.example;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class User {
    private int user_id;
    private String user_username, user_password, user_full_name, user_email;
    private LocalDate user_dob;
    private int numBooksBorrowed;
    private List<Book> borrowedBooks;

    public User(int user_id, String user_username, String user_password, String user_full_name, String user_email, String user_dob) {
        this.user_id = user_id;
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_full_name = user_full_name;
        this.user_email = user_email;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.user_dob = LocalDate.parse(user_dob, formatter);
        this.numBooksBorrowed = 0; // Initialize to 0 as the user has not borrowed any books yet
        this.borrowedBooks = new ArrayList<>(); // Initialize the list of borrowed books
    }

    // Getter and Setter for user_id
    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    // Getter and Setter for user_username
    public String getUserUsername() {
        return user_username;
    }

    public void setUserUsername(String user_username) {
        this.user_username = user_username;
    }

    // Getter and Setter for user_password
    public String getUserPassword() {
        return user_password;
    }

    public void setUserPassword(String user_password) {
        this.user_password = user_password;
    }

    // Getter and Setter for user_full_name
    public String getUserFullName() {
        return user_full_name;
    }

    public void setUserFullName(String user_full_name) {
        this.user_full_name = user_full_name;
    }

    // Getter and Setter for user_email
    public String getUserEmail() {
        return user_email;
    }

    public void setUserEmail(String user_email) {
        this.user_email = user_email;
    }

    // Getter and Setter for user_dob
    public LocalDate getUserDob() {
        return user_dob;
    }

    public void setUserDob(LocalDate user_dob) {
        this.user_dob = user_dob;
    }

    // Getter and Setter for numBooksBorrowed
    public int getNumBooksBorrowed() {
        return numBooksBorrowed;
    }

    public void setNumBooksBorrowed(int numBooksBorrowed) {
        this.numBooksBorrowed = numBooksBorrowed;
    }

    // Getter and Setter for borrowedBooks
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public void getUserDetails(){
        System.out.print("ID: "+this.getUserId() + " Username: " + this.getUserUsername() + " Full Name: " + this.getUserFullName() + " Email: "
                    + this.getUserEmail() +"  DOB: " + this.getUserDob() + " Number of Books Borrowed " + this.getNumBooksBorrowed() +  " [");
            for (Book book : this.getBorrowedBooks()) {
                System.out.print(book.getBookTitle() + ",");
            }
            System.out.println("]");
    }
}
