/*
Name:
ID:
Course:
File: Customer.java
Purpose: Customer class to store customer name and purchased book name, quantity and price per book
Date:
*/


public class Customer
{
    // Variables to store customer name and book information
    private String customerName;
    private String bookName;
    private int quantity;
    private double price;

    //  Constructor to set customer namem book information during object creation
    public Customer(String customerName, String bookName, int quantity, double price) {
        this.customerName = customerName;
        this.bookName = bookName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and setters to get and set customer name and book name, purchased quantity and price per book in an ArrayList from outside of customer class
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

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
