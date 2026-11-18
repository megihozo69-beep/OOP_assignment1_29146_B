package com.auca.librarybook;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;

public class LibraryBook implements Identifiable, Registrable {

    private String bookId;
    private String isbn;
    private String title;
    private String author;
    private int totalCopies;
    private int availableCopies;

    public LibraryBook(String bookId, String isbn, String title,
                       String author, int totalCopies, int availableCopies) {

        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    @Override
    public String getId() {
        return bookId;
    }

    @Override
    public String getDisplayName() {
        return title;
    }

    @Override
    public boolean isActive() {
        return availableCopies > 0;
    }

    @Override
    public void validate() throws ValidationException {

        if (bookId == null || bookId.trim().isEmpty()) {
            throw new ValidationException("Book ID cannot be empty");
        }

        if (isbn == null || isbn.trim().isEmpty()) {
            throw new ValidationException("ISBN cannot be empty");
        }

        if (title == null || title.trim().length() < 2
                || title.trim().length() > 60) {
            throw new ValidationException("Book title must be between 2 and 60 characters");
        }

        if (author == null || author.trim().isEmpty()) {
            throw new ValidationException("Author cannot be empty");
        }

        if (totalCopies < 0) {
            throw new ValidationException("Total copies cannot be negative");
        }

        if (availableCopies < 0 || availableCopies > totalCopies) {
            throw new ValidationException("Available copies must be between zero and total copies");
        }
    }

    @Override
    public void printDetails() {
        System.out.println(this);
    }

    @Override
    public String getRegistrationSummary() {
        return "Book ID: " + bookId
                + ", Title: " + title
                + ", Available Copies: " + availableCopies;
    }

    @Override
    public String toString() {
        return "Book ID: " + bookId
                + "\nISBN: " + isbn
                + "\nTitle: " + title
                + "\nAuthor: " + author
                + "\nTotal Copies: " + totalCopies
                + "\nAvailable Copies: " + availableCopies;
    }
}