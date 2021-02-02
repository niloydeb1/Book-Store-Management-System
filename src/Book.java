/*
Name:
ID:
Course:
File: Book.java
Purpose: Book class to store book name, available quantity and price per book as an object
Date:
*/

public class Book
{
    // Variables to store book information
    private String bookName;
    private int quantity;
    private double price;

    // Constructor to set book information during object creation
    public Book(String bookName, int quantity, double price)
    {
        this.bookName = bookName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and setters to get and set book information like name, available quantity and price per book from outside of book class
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
