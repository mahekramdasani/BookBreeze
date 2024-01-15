package com.example;

import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Library {
    private List<Book> books;
    private List<Author> authors;
    private List<User> users;
    private User currentUser;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Library l = new Library();
            l.users = generateUsers(l);
            l.books = generateBooks(l);
            l.authors = generateAuthors(l);
            System.out.println("Welcome to Book Breeze Library!");
            System.out.println("Firstly signup/login to continue");
            System.out.println("1.Sign Up(New User)\n2.Login\nAny Key to exit");
            String num = sc.nextLine();
            if (num.equals("1")) {
                signUp(l);
            } else if (num.equals("2")) {
                login(l);
            } else {
                System.out.println("Thank You for Coming!Visit Again");
                sc.close();
                System.exit(0);
            }
            // l.currentUser.getUserId()==1
            while (true) {
                if (l.currentUser.getUserId() == 1) {
                    System.out.println(
                            "\n\nHello there admin,What do you want to do?\n1.User Information\n2.Book Information\n3.Author Information\nAny Key to exit");
                    char key = sc.next().charAt(0);
                    if (key == '1') {
                        userInfo(l);
                    } else if (key == '2') {
                        bookInfo(l);
                    } else if (key == '3') {
                        authorInfo(l);
                    } else {
                        System.out.println("Have a great day!Byeee...");
                        sc.close();
                        System.exit(0);
                    }
                } else {
                    System.out.println("\n\nHello there " + l.currentUser.getUserUsername()
                            + " ,What do you want to do?\n1.Book Information\n2.Author Information\nAny Key to exit");
                    char key = sc.next().charAt(0);
                    if (key == '1') {
                        bookInfo(l);
                    } else if (key == '2') {
                        authorInfo(l);
                    } else {
                        System.out.println("Have a great day!Byeee...");
                        sc.close();
                        System.exit(0);
                    }
                }
            }
        } catch (Exception e) {
            printError("main",e);
        }
    }
    private static void printError(String functionName, Exception e) {
        System.out.println("An error occurred in " + functionName + ": " + e.getMessage());
        e.printStackTrace();
    }
    private static void userInfo(Library l) {
        try {
            while (true) {
                System.out.println("Enter\n1.Get all User Names\n2.Search particular User\n3.Add User\n4.Delete User\nAny key to go Back");
                char key = sc.next().charAt(0);
                if (key == '1') {
                    getAllUsers(l);
                } else if (key == '2') {
                    searchUser(l);
                } else if (key == '3') {
                    addUser(l);
                } else if (key == '4') {
                    deleteUser(l);
                } else {
                    System.out.println("Going back...");
                    break;
                }
            }
        } catch (Exception e) {
            printError("userInfo", e);
        }
    }
    private static void getAllUsers(Library l) {
        try {
            for (User user : l.users) {
                user.getUserDetails();
            }
        } catch (Exception e) {
            printError("getAllUsers", e);
        }
    }
    private static void deleteUser(Library l) {
        try {
            getAllUsers(l);
            System.out.println("Enter Username:");
            sc.nextLine();
            String username = sc.nextLine();
            if (username.equals("admin")) {
                System.out.println("Cannot delete you admin!");
                System.exit(0);
            }
            User user = findUserByName(username, l);
            if (user == null) {
                System.out.println("Invalid Username");
                System.out.println("Going back...");
            } else {
                if (user.getNumBooksBorrowed() != 0) {
                    System.out.println("We cannot delete user. User has borrowed books!");
                    System.out.println("Going back...");
                } else {
                    System.out.println("Do you want to delete user " + user.getUserUsername() + " ?1.Yes\t2.No");
                    int key = sc.nextInt();
                    if (key == 1) {
                        System.out.println("Okay deleting the User....");
                        l.users.remove(user);
                        System.out.println("User" + user.getUserUsername() + " Deleted!");
                    } else {
                        System.out.println("Okay...Going Back...");
                    }
                }
            }
        } catch (Exception e) {
            printError("deleteUser", e);
        }
    }
    private static void addUser(Library l) {
        try {
            System.out.println("Enter Username:(String)");
            sc.nextLine();
            String username = sc.nextLine();
            for (User user : l.users) {
                if (username.equals(user.getUserUsername())) {
                    System.out.println("You cannot add user! User already exists");
                    return;
                }
            }
            System.out.println("Enter Password:(String)");
            String password = sc.nextLine();
            System.out.println("Enter Full Name:(String)");
            String fullName = sc.nextLine();
            System.out.println("Enter Email:(String)");
            String email = sc.nextLine();
            System.out.println("Enter DOB(dd/MM/yyyy)");
            String dob = sc.nextLine();
            l.users.add(new User(l.users.get(l.users.size() - 1).getUserId() + 1, username, password, fullName, email, dob));
        } catch (Exception e) {
            printError("addUser", e);
        }
    }
    private static void searchUser(Library l) {
        try {
            while (true) {
                System.out.println("Enter\n1.Search User by username\n2.Search User by email\n\nAny Key to exit");

                char key = sc.next().charAt(0);
                if (key == '1') {
                    searchByUsername(l);
                } else if (key == '2') {
                    searchByEmail(l);
                } else {
                    System.out.println("Going back...");
                    break;
                }
            }
        } catch (Exception e) {
            printError("searchUser", e);
        }
    }
    private static void searchByUsername(Library l) {
        try {
            System.out.println("Enter username");
            sc.nextLine();
            String username = sc.nextLine();
            for (User user : l.users) {
                if (KMPSearch(username, user.getUserUsername())) {
                    user.getUserDetails();
                }
            }
        } catch (Exception e) {
            printError("searchByUsername", e);
        }
    }

    private static void searchByEmail(Library l) {
        try {
            System.out.println("Enter Email");
            sc.nextLine();
            String useremail = sc.nextLine();
            for (User user : l.users) {
                if (KMPSearch(useremail, user.getUserEmail())) {
                    user.getUserDetails();
                }
            }
        } catch (Exception e) {
            printError("searchByEmail", e);
        }
    }

    private static void bookInfo(Library l) {
        try {
            if (l.currentUser.getUserId() == 1) {
                while (true) {
                    System.out.println("Enter\n1.Get all books\n2.Search books\n3.Sort books and display!\n4.Add a book\n5.Delete a book\nAny key to exit");
                    char key = sc.next().charAt(0);
                    if (key == '1') {
                        getAllBooks(l);
                    } else if (key == '2') {
                        searchBook(l);
                    } else if (key == '3') {
                        sortBook(l);
                    } else if (key == '4') {
                        addBook(l);
                    } else if (key == '5') {
                        deleteBook(l);
                    } else {
                        System.out.println("Going Back...");
                        break;
                    }
                }
            } else {
                while (true) {
                    System.out.println("Enter\n1.Get all books\n2.Search books\n3.Sort books and display!\n4.Issue a book\n5.Return a book\nAny key to exit");
                    char key = sc.next().charAt(0);
                    if (key == '1') {
                        getAllBooks(l);
                    } else if (key == '2') {
                        searchBook(l);
                    } else if (key == '3') {
                        sortBook(l);
                    } else if (key == '4') {
                        issueBook(l);
                    } else if (key == '5') {
                        returnBook(l);
                    } else {
                        System.out.println("Going Back...");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            printError("bookInfo", e);
        }
    }

    private static void issueBook(Library l) {
        try {
            if (l.currentUser.getNumBooksBorrowed() == 5) {
                limitReached(l);
            } else {
                getAllBooks(l);
                System.out.println("Which book you want to issue? Enter Book ISBN");
                int id = sc.nextInt();
                Book book = findBookById(id, l);
                if (book == null) {
                    System.out.println("Invalid ISBN! Going Back...");
                } else {
                    if (book.isAvailable()) {
                        book.setAvailable(false);
                        book.setIssuedToUserId(l.currentUser.getUserId());
                        book.setIssueDate(LocalDate.now());
                        book.setReturnDate(LocalDate.now().plusDays(10));
                        System.out.println("Book: " + book.getBookTitle() + " Issued to User " + l.currentUser.getUserUsername() + " Issue Date " + LocalDate.now() + " Return Date: " + LocalDate.now().plusDays(10));
                        l.currentUser.setNumBooksBorrowed(l.currentUser.getNumBooksBorrowed() + 1);
                        List<Book> bookss = l.currentUser.getBorrowedBooks();
                        bookss.add(book);
                        l.currentUser.setBorrowedBooks(bookss);
                    } else {
                        System.out.println("Ohh... The book is unavailable! It will be available on: " + book.getReturnDate());
                        System.out.println("Going Back...");
                    }
                }
            }
        } catch (Exception e) {
            printError("issueBook", e);
        }
    }

    private static void limitReached(Library l) {
        try {
            System.out.println("You can issue 5 books only!");
            System.out.println("Do you want to return any book? 1.Yes\t2.No");
            int key = sc.nextInt();
            if (key == 1) {
                returnBook(l);
            } else {
                System.out.println("Okay Thank you! You cannot issue more books");
            }
        } catch (Exception e) {
            printError("limitReached", e);
        }
    }

    private static void returnBook(Library l) {
        try {
            if (l.currentUser.getNumBooksBorrowed() == 0) {
                System.out.println("You have no books borrowed!");
            } else {
                System.out.println("You issued:");
                for (Book book : l.currentUser.getBorrowedBooks()) {
                    book.getBookDetails();
                }
                System.out.println("Now which book do you want to return? Give me Book ISBN");
                int id = sc.nextInt();
                Book book = findBookById(id, l);

                if (book != null && l.currentUser.getBorrowedBooks().contains(book)) {
                    System.out.println("Okay checking the return date... Just a sec..");

                    if (book.getReturnDate().isAfter(LocalDate.now()) || book.getReturnDate().isEqual(LocalDate.now())) {
                        System.out.println("Okay you are returning before: " + book.getReturnDate() + ". No fine applied!");
                        System.out.println("Returning the book... Just a sec...");
                    } else {
                        System.out.println("Ohhh! You passed the return date! You have to pay a fine of " + book.getBookFine());
                        System.out.println("Okay thank you! Returning the book... Just a sec...");
                    }

                    book.setAvailable(true);
                    book.setIssuedToUserId(-1);
                    book.setIssueDate(null);
                    book.setReturnDate(null);
                    l.currentUser.setNumBooksBorrowed(l.currentUser.getNumBooksBorrowed()-1);
                    List<Book> books = l.currentUser.getBorrowedBooks();
                    books.remove(book);
                    l.currentUser.setBorrowedBooks(books);
                    System.out.println("Book Returned! Thank you");
                } else {
                    System.out.println("Ohhhh I guess you entered a wrong ISBN or maybe the book does not exist! You didn't borrow");
                    System.out.println("Okay Going Back...");
                }
            }
        } catch (Exception e) {
            printError("returnBook", e);
        }
    }


    private static void deleteBook(Library l) {
        try {
            getAllBooks(l);
            System.out.println("Enter book ISBN of which you want to delete");
            int id = sc.nextInt();
            Book book = findBookById(id, l);
            if (book == null) {
                System.out.println("Book does not exist! Going back...");
            } else {
                System.out.println("Do you really want to delete it? 1.Yes\t2.No");
                int key = sc.nextInt();
                if (key == 1) {
                    l.books.remove(book);
                    System.out.println("Book Deleted Successfully!");
                } else {
                    System.out.println("Okay Going back...");
                }
            }
        } catch (Exception e) {
            printError("deleteBook", e);
        }
    }
    private static void addBook(Library l) {
        try {
            System.out.println("Enter Book Title:(String)");
            sc.nextLine();
            String title = sc.nextLine();
            System.out.println("Enter Book Author:(String)");
            String author = sc.nextLine();
            System.out.println("Enter Genre:(String)");
            String genre = sc.nextLine();
            System.out.println("Enter Content(String)");
            String content = sc.nextLine();
            System.out.println("Enter Fine:(float)");
            float fine = sc.nextFloat();
            System.out.println("Enter Rating:(float)");
            float rating = sc.nextFloat();
            l.books.add(new Book(l.books.get(l.books.size()-1).getBookIsbn()+1, title, author, genre, content, fine, rating));
        } catch (Exception e) {
            printError("addBook", e);
        }
    }

    private static void sortBook(Library l) {
        try {
            while (true) {
                System.out.println("Enter\n1.Sort by title(ASC)\n2.Sort by author(ASC)\n3.Sort by genre(ASC)\n4.Sort by rating(DESC)\nAny key to exit");
                char key = sc.next().charAt(0);
                if (key == '1') {
                    sortByTitle(l);
                } else if (key == '2') {
                    sortByAuthor(l);
                } else if (key == '3') {
                    sortByGenre(l);
                } else if (key == '4') {
                    sortByRating(l);
                } else {
                    System.out.println("Going Back...");
                    break;
                }
            }
        } catch (Exception e) {
            printError("sortBook", e);
        }
    }

    private static void sortByRating(Library l) {
        try {
            List<Book> books = deepCopy(l.books);
            quickSort(books, 4, 0, books.size() - 1);
            for (Book book : books) {
                book.getBookDetails();
            }
        } catch (Exception e) {
            printError("sortByRating", e);
        }
    }
    private static void sortByGenre(Library l) {
        try {
            List<Book> books = deepCopy(l.books);
            quickSort(books, 3, 0, books.size() - 1);
            for (Book book : books) {
                book.getBookDetails();
            }
        } catch (Exception e) {
            printError("sortByGenre", e);
        }
    }

    private static void sortByAuthor(Library l) {
        try {
            List<Book> books = deepCopy(l.books);
            quickSort(books, 2, 0, books.size() - 1);
            for (Book book : books) {
                book.getBookDetails();
            }
        } catch (Exception e) {
            printError("sortByAuthor", e);
        }
    }

    private static void sortByTitle(Library l) {
        try {
            List<Book> books = deepCopy(l.books);
            quickSort(books, 1, 0, books.size() - 1);
            for (Book book : books) {
                book.getBookDetails();
            }
        } catch (Exception e) {
            printError("sortByTitle", e);
        }
    }

    private static List<Book> deepCopy(List<Book> originalList) {
        try {
            return originalList.stream()
                    .map(Book::deepCopy) // Assuming you have a deepCopy method in your Book class
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (Exception e) {
            printError("deepCopy", e);
            return new ArrayList<>(); // Return an empty list in case of an error
        }
    }

static void swap(List<Book> books, int i, int j) {
    List<Book> mutableList = new ArrayList<>(List.copyOf(books));
    Book temp = mutableList.get(i);
    mutableList.set(i, mutableList.get(j));
    mutableList.set(j, temp);
    books.clear();
    books.addAll(mutableList);
}

    static int partition(List<Book> books, int criteria, int low, int high) {
        Book pivot = books.get(high);
        int i = low - 1;

        for (int j = low; j <= high - 1; j++) {
            int comparisonResult = 0;

            switch (criteria) {
                case 1:
                    comparisonResult = books.get(j).getBookTitle().compareTo(pivot.getBookTitle());
                    break;
                case 2:
                    comparisonResult = books.get(j).getBookAuthor().compareTo(pivot.getBookAuthor());
                    break;
                case 3:
                    comparisonResult = books.get(j).getBookGenre().compareTo(pivot.getBookGenre());
                    break;
                default:
                    comparisonResult = Float.compare(pivot.getRating(),books.get(j).getRating());
            }

            if (comparisonResult < 0) {
                i++;
                swap(books, i, j);
            }
        }

        swap(books, i + 1, high);
        return i + 1;
    }

    static void quickSort(List<Book> books, int criteria, int low, int high) {
        if (low < high) {
            int partitionIndex = partition(books, criteria, low, high);
            quickSort(books, criteria, low, partitionIndex - 1);
            quickSort(books, criteria, partitionIndex + 1, high);
        }
    }
    private static void searchBook(Library l) {
        try {
            while (true) {
                System.out.println("Enter\n1.Get book by title\n2.Get book by author\n3.Get book by genre\n4.Get book by rating\nAny key to exit");
                char key = sc.next().charAt(0);
                if (key == '1') {
                    searchBookByTitle(l);
                } else if (key == '2') {
                    searchBookByAuthor(l);
                } else if (key == '3') {
                    searchBookByGenre(l);
                } else if (key == '4') {
                    searchBookByRating(l);
                } else {
                    System.out.println("Going back...");
                    break;
                }
            }
        } catch (Exception e) {
            printError("searchBook", e);
        }
    }

    private static void searchBookByRating(Library l) {
        try {
            System.out.println("Enter Rating in range(1-10) and in ints:");
            int rating = sc.nextInt();
            for (Book book : l.books) {
                if (book.getRating() >= rating) {
                    book.getBookDetails();
                }
            }
        } catch (Exception e) {
            printError("searchBookByRating", e);
        }
    }

    private static void searchBookByGenre(Library l) {
        try {
            System.out.println("Enter book genre:");
            sc.nextLine();
            String genre = sc.nextLine();
            for (Book book : l.books) {
                if (KMPSearch(genre.toLowerCase(), book.getBookGenre().toLowerCase())) {
                    book.getBookDetails();
                }
            }
        } catch (Exception e) {
            printError("searchBookByGenre", e);
        }
    }

    private static void searchBookByAuthor(Library l) {
        try {
            System.out.println("Enter Book Author");
            sc.nextLine();
            String author = sc.nextLine();
            for (Book book : l.books) {
                if (KMPSearch(author.toLowerCase(), book.getBookAuthor().toLowerCase())) {
                    book.getBookDetails();
                }
            }
        } catch (Exception e) {
            printError("searchBookByAuthor", e);
        }
    }

    private static void searchBookByTitle(Library l) {
        try {
            System.out.println("Enter Book Title:");
            sc.nextLine();
            String title = sc.nextLine();
            for (Book book : l.books) {
                if (KMPSearch(title.toLowerCase(), book.getBookTitle().toLowerCase())) {
                    book.getBookDetails();
                }
            }
        } catch (Exception e) {
            printError("searchBookByTitle", e);
        }
    }

    private static void getAllBooks(Library l) {
        for (Book book : l.books) {
            book.getBookDetails();
        }
    }

    private static void authorInfo(Library l) {
        try {
            while (true) {
                System.out.println("Enter\n1.Get all Authors\n2.Search Author\nAny key to exit");
                char key = sc.next().charAt(0);
                if (key == '1') {
                    getAllAuthors(l);
                } else if (key == '2') {
                    searchAuthor(l);
                } else {
                    System.out.println("Going Back...");
                    break;
                }
            }
        } catch (Exception e) {
            printError("authorInfo", e);
        }
    }

    private static void searchAuthor(Library l) {
        try {
            while (true) {
                System.out.println("Enter\n1.Get Author by Name\n2.Get Author by Nationality\n3.Get Author by Genre\n4.Get Author by awards\nAny key to exit");
                char key = sc.next().charAt(0);
                if (key == '1') {
                    searchAuthorByName(l);
                } else if (key == '2') {
                    searchAuthorByNation(l);
                } else if (key == '3') {
                    searchAuthorByGenre(l);
                } else if (key == '4') {
                    searchAuthorByAwards(l);
                } else {
                    System.out.println("Going back...");
                    break;
                }
            }
        } catch (Exception e) {
            printError("searchAuthor", e);
        }
    }

    private static void searchAuthorByAwards(Library l) {
        try {
            System.out.println("Enter Award of Author: ");
            sc.nextLine();
            String award = sc.nextLine();
            for (Author author : l.authors) {
                if (KMPSearch(award, author.getAwards().toLowerCase())) {
                    author.getAuthorDetails();
                }
            }
        } catch (Exception e) {
            printError("searchAuthorByAwards", e);
        }
    }

    private static void searchAuthorByGenre(Library l) {
        try {
            System.out.println("Enter Genre of Author: ");
            sc.nextLine();
            String genre = sc.nextLine();
            for (Author author : l.authors) {
                if (KMPSearch(genre, author.getAuthorGenre().toLowerCase())) {
                    author.getAuthorDetails();
                }
            }
        } catch (Exception e) {
            printError("searchAuthorByGenre", e);
        }
    }

    private static void searchAuthorByNation(Library l) {
        try {
            System.out.println("Enter Nationality of Author: ");
            sc.nextLine();
            String nation = sc.nextLine();
            for (Author author : l.authors) {
                if (KMPSearch(nation, author.getAuthorNationality().toLowerCase())) {
                    author.getAuthorDetails();
                }
            }
        } catch (Exception e) {
            printError("searchAuthorByNation", e);
        }
    }

    private static void searchAuthorByName(Library l) {
        try {
            System.out.println("Enter Name of Author: ");
            sc.nextLine();
            String name = sc.nextLine();
            for (Author author : l.authors) {
                if (KMPSearch(name, author.getAuthorName().toLowerCase())) {
                    author.getAuthorDetails();
                }
            }
        } catch (Exception e) {
            printError("searchAuthorByName", e);
        }
    }

    private static void getAllAuthors(Library l) {
        try {
            for (Author author : l.authors) {
                author.getAuthorDetails();
            }
        } catch (Exception e) {
            printError("getAllAuthors", e);
        }
    }

    public static void signUp(Library l) {
        try {
            System.out.println("Enter Username");
            String username = sc.nextLine();

            // Check if the username already exists
            for (User user : l.users) {
                if (username.equals(user.getUserUsername())) {
                    System.out.println("You cannot signup user! Username already exists");
                    return;
                }
            }

            System.out.println("Enter Password");
            String password = sc.nextLine();

            System.out.println("Confirm Password");
            String password1 = sc.nextLine();

            if (password.equals(password1)) {
                int id = l.users.get(l.users.size() - 1).getUserId() + 1;

                System.out.println("Enter Fullname");
                String full_name = sc.nextLine();

                System.out.println("Enter Email");
                String email = sc.nextLine();

                System.out.println("Enter dob(dd/mm/yyyy)");
                String dob = sc.nextLine();

                User user = new User(id, username, password1, full_name, email, dob);
                l.users.add(user);
                l.currentUser = user;
                System.out.println("User:" + l.currentUser.getUserFullName() + " signed in Successfully!");
            } else {
                System.out.println("Invalid Passwords");
                sc.close();
                System.exit(0);
            }
        } catch (Exception e) {
            printError("signUp", e);
        }
    }

    public static void login(Library l) {
        try {
            System.out.println("Enter username");
            String username = sc.nextLine();

            System.out.println("Enter Password");
            String password = sc.nextLine();

            User user = findUserByName(username, l);
            if (user == null) {
                System.out.println("Invalid user credentials");
                sc.close();
                System.exit(0);
            } else {
                String password1 = user.getUserPassword();
                if (password.equals(password1)) {
                    l.currentUser = user;
                    System.out.println("User:" + l.currentUser.getUserFullName() + " Logged in Successfully");
                } else {
                    System.out.println("Invalid password");
                    sc.close();
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            printError("login", e);
        }
    }

    public static User findUserByName(String userName, Library l) {
        try {
            for (User user : l.users) {
                if (user.getUserUsername().equalsIgnoreCase(userName)) {
                    return user;
                }
            }
            return null; // User not found
        } catch (Exception e) {
            printError("findUserByName", e);
            return null;
        }
    }

    private static Book findBookById(int id, Library l) {
        try {
            for (Book book : l.books) {
                if (book.getBookIsbn() == id) {
                    return book;
                }
            }
            return null;
        } catch (Exception e) {
            printError("findBookById", e);
            return null;
        }
    }

    //KMP For Pattern Matching //in search

    private static boolean KMPSearch(String pat, String txt) {
        try {
            int M = pat.length();
            int N = txt.length();

            // create lps[] that will hold the longest
            // prefix suffix values for the pattern
            int[] lps = new int[M];
            int j = 0; // index for pat[]

            // Preprocess the pattern (calculate lps[]
            // array)
            computeLPSArray(pat, M, lps);

            int i = 0; // index for txt[]
            while ((N - i) >= (M - j)) {
                if (pat.charAt(j) == txt.charAt(i)) {
                    j++;
                    i++;
                }
                if (j == M) {
                    return true;
                } else if (i < N && pat.charAt(j) != txt.charAt(i)) {
                    // mismatch after j matches
                    // Do not match lps[0..lps[j-1]] characters,
                    // they will match anyway
                    if (j != 0)
                        j = lps[j - 1];
                    else
                        i = i + 1;
                }
            }
            return false;
        } catch (Exception e) {
            printError("KMPSearch", e);
            return false;
        }
    }
    private static void computeLPSArray(String pat, int M, int[] lps) {
        try {
            // length of the previous longest prefix suffix
            int len = 0;
            int i = 1;
            lps[0] = 0; // lps[0] is always 0

            // the loop calculates lps[i] for i = 1 to M-1
            while (i < M) {
                if (pat.charAt(i) == pat.charAt(len)) {
                    len++;
                    lps[i] = len;
                    i++;
                } else {
                    if (len != 0) {
                        len = lps[len - 1];
                    } else {
                        lps[i] = len;
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            printError("computeLPSArray", e);
        }
    }

    public static List<Author> generateAuthors(Library l) {
        List<Author> authors = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            JSONArray authorsData = (JSONArray) parser.parse(new FileReader("C:\\Users\\Lenovo\\DAAGroup\\src\\com\\example\\authordata.json"));
            for (Object o : authorsData) {
                JSONObject authorData = (JSONObject) o;
                String authorName = (String) authorData.get("author_name");
                String biography = (String) authorData.get("bio");
                String nationality = (String) authorData.get("nationality");
                String birthDate = (String) authorData.get("dob");
                String genre = (String) authorData.get("genre");
                String awards = (String) authorData.get("awards");

                authors.add(new Author(authorName, biography, nationality, birthDate, genre, awards));
            }
        } catch (Exception e) {
            printError("generateAuthors", e);
        }
        return authors;
    }

    public static List<Book> generateBooks(Library l) {
        List<Book> books = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            JSONArray booksData = (JSONArray) parser.parse(new FileReader("C:\\Users\\Lenovo\\DAAGroup\\src\\com\\example\\bookdata.json"));

            for (Object o : booksData) {
                JSONObject bookData = (JSONObject) o;
                int id = Integer.parseInt((String) bookData.get("id"));
                String title = (String) bookData.get("title");
                String author = (String) bookData.get("author");
                String genre = (String) bookData.get("genre");
                String content = (String) bookData.get("content");
                float fine = Float.parseFloat((String) bookData.get("fine"));
                float rating = Float.parseFloat(bookData.get("rating").toString());
                boolean isAvailable = (boolean) bookData.get("isAvailable");

                if (!isAvailable) {
                    int issuedToUserId = Integer.parseInt((String) bookData.get("issuedToUserId"));
                    String issueDate = (String) bookData.get("issueDate");
                    Book book = new Book(id, title, author, genre, content,issuedToUserId,issueDate, fine, rating);
                    books.add(book);
                    User user = l.users.get(issuedToUserId-1);

                        List<Book> bookss =  user.getBorrowedBooks();
                        bookss.add(book);
                        user.setBorrowedBooks(bookss);
                        user.setNumBooksBorrowed(user.getNumBooksBorrowed()+1);


                }
                else{
                    books.add(new Book(id, title, author, genre, content, fine, rating));
                }
            }
        } catch (Exception e) {
            System.out.println("Error parsing book data: "+e.getCause());
        }

        return books;
    }



    public static List<User> generateUsers(Library l) {
        List<User> users = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            JSONArray usersData = (JSONArray) parser.parse(new FileReader("C:\\Users\\Lenovo\\DAAGroup\\src\\com\\example\\userdata.json"));
            for (Object o : usersData) {
                JSONObject userData = (JSONObject) o;
                int userId = Integer.parseInt((String) userData.get("id"));
                String username = (String) userData.get("username");
                String password = (String) userData.get("password");
                String fullName = (String) userData.get("fullName");
                String email = (String) userData.get("email");
                String dob = (String) userData.get("birthDate");

                users.add(new User(userId, username, password, fullName, email, dob));
            }
        } catch (Exception e) {
            System.out.println("Error parsing user data");
        }
        return users;
    }

}
