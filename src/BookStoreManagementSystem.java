/*
Name:
ID:
Course:
File: BookStoreManagementSystem.java
Purpose: Project -- Book Store Management System application
Date:
*/

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;

public class BookStoreManagementSystem extends JFrame implements ActionListener
{
    private ArrayList<Book> book = new ArrayList<Book>(); // ArrayList to store all available book information
    private ArrayList<Customer> customer = new ArrayList<Customer>(); // ArrayList to store all customer name and purchased book information
    private ArrayList<Book> bookCart = new ArrayList<Book>(); // ArrayList to store book added to the cart by customer
    private String customerName; // Store customer name. Needed for verification during purchase after book added to cart

    private JTextField bookNameField;
    private JTextField priceField;
    private JButton addBookButton;
    private JTextField customerNameField;
    private JButton addToCartButton;
    private JButton purchaseButton;
    private JTextArea displayTextArea;
    private JTextField quantityField;
    private JLabel headingLabel1;
    private JLabel bookNameLabel;
    private JLabel hiddenLabel;
    private JButton displayAllBooksButton;
    private JButton exitButton;
    private JLabel headingLabel2;
    private JLabel priceLabel;
    private JLabel headingLabel3;
    private JLabel customerNameLabel;
    private JPanel rootPanel;
    private JLabel quantityLabel;
    private JButton displayRegisterButton;

    public BookStoreManagementSystem () {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	  // allow the code to close the program
        setBounds(0, 0, 1000, 1500);			  // Define position and size of app
        setTitle("Book Store Management System");		          // Set the title of the app
        setExtendedState(JFrame.MAXIMIZED_BOTH);                  // Make the application maximized screened
        setResizable(false);                                      // Make the application not resizable

        add(rootPanel); // add panel to JFrame

        addBookButton.addActionListener(this);        // add the action listener to the buttons
        addToCartButton.addActionListener(this);
        displayAllBooksButton.addActionListener(this);
        displayRegisterButton.addActionListener(this);
        purchaseButton.addActionListener(this);
        exitButton.addActionListener(this);

        // when the user pushes the system close (X top right corner)
        addWindowListener( // override window closing method
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        exit();                // Attempt to exit application
                    }
                }
        );

    }

    @Override
    public void actionPerformed(ActionEvent e)
    { // process the clicks on all of the buttons
        String command = e.getActionCommand();
        if (command.compareTo("Add to cart") == 0)
            addToCart();
        else if (command.compareTo("Add Book") == 0)
            addBooks();
        else if (command.compareTo("Purchase") == 0)
            purchase();
        else if (command.compareTo("Display All Books") == 0)
            displayAll();
        else if (command.compareTo("Display Register") == 0)
            displayRegister();
        else if (command.compareTo("Exit") == 0)
            exit();
    }

    // This method takes book name, quantity and price as input and store it in an object of Book class
    // Display added book to the store in TextArea
    private void addBooks()
    {

        // Check if book name is entered or not. If not then end the execution of method
        if(!checkBookName())
        {
            clearCart(); // Book cart ArrayList is cleared
            return;
        }

        // Else book name is retrieved from name field
        String bookName = bookNameField.getText();      // Store Book name

        // Check if book quantity is valid or not. If not then end the execution of method
        if(!checkBookQuantity())
        {
            clearCart(); // Book cart is cleared
            return;
        }
        clearCart(); // Book cart is cleared

        int quantity = Integer.parseInt(quantityField.getText());   // Store available book quantity

        // If invalid price or no price is entered, exception is caught and error dialogue with appropriate message is displayed
        // Focus is set in the price field
        // Else price per book is retrieved from price field and converted to double
        double price = 0.0;    // Store price per book
        try
        {
            price = Double.parseDouble(priceField.getText());
        }
        catch(Exception e)
        {
            String errorMessage = "Invalid price entered";
            showError(errorMessage);
            priceField.setText("");
            customerNameField.setText("");
            priceField.requestFocus();
            clearCart(); // Book cart is cleared
            return;
        }

        // Store book information in book class object
        Book b = new Book(bookName, quantity, price);

        // Store book object to book ArrayList
        book.add(b);
        displayAvailableBookHeading(); // Display book information header of Text Area

        // Get the book index from book ArrayList
        int index = book.indexOf(b);

        // Display book information of given index and success message in Text Area
        displayAvailableBookData(index);
        appendLine();
        displayTextArea.append("Book added successfully\n");

        // All the input field of GUI is restored and focus is set in the book name field
        // Book cart is cleared
        reset();
        clearCart();
    }

    // This method takes book name, purchase quantity and customer name, and add this to book cart ArrayList and display book information
    private void addToCart()
    {
        // If customer name is empty error dialogue is displayed with appropriate message
        // Price field and customer field is cleared
        // Focus is set on customer name field
        if (customerNameField.getText().compareTo("") == 0)
        {
            String errorMessage = "You must enter a customer name";
            showError(errorMessage);
            customerNameField.setText("");
            priceField.setText("");
            customerNameField.requestFocus();
            return;
        }

        // If book cart is not empty but customer name is changed it will clear the book cart
        if(!bookCart.isEmpty() && !customerNameField.getText().equals(customerName))
        {
            bookCart.clear();
        }
        // Else Customer name is retrieved from name field
        customerName = customerNameField.getText();

        // Check if book name is entered or not. If not then end the execution of method
        if(!checkBookName())
        {
            return;
        }

        // Else book name is retrieved from name field
        String bookName = bookNameField.getText();      // Store Book name

        // Check if book quantity is valid or not. If not then end the execution of method
        if(!checkBookQuantity())
        {
            return;
        }

        int quantity = Integer.parseInt(quantityField.getText());   // Store available book quantity

        // If quantity is zero, error dialogue is displayed with appropriate message
        // Price field and quantity field is cleared and focus is set on quantity field
        if(quantity == 0)
        {
            String errorMessage = "Invalid quantity entered";
            showError(errorMessage);
            priceField.setText("");
            quantityField.setText("");
            quantityField.requestFocus();
            return;
        }

        // Check if the book is available in the book ArrayList
        boolean bookFound = false; // If book is found, make this true

        for(int i = 0; i < book.size(); i++)
        {
            // Check if entered book is available in book ArrayList
            if(book.get(i).getBookName().equalsIgnoreCase(bookName))
            {
                bookFound = true;
                // Check asking book quantity is smaller or equal to available book quantity
                if(book.get(i).getQuantity() >= quantity)
                {
                    bookCart.add(new Book(book.get(i).getBookName(), quantity, book.get(i).getPrice()));  // Add book object to bookCart ArrayList
                }
                // Else error message is shown with appropriate dialogue
                // Price field and quantity field is cleared and focus is set on quantity field
                else
                {
                    String errorMessage = "Maximum available book quantity is: "+book.get(i).getQuantity();
                    showError(errorMessage);
                    priceField.setText("");
                    quantityField.setText("");
                    quantityField.requestFocus();
                    return;
                }
            }
        }
        // If book is unavailable error message is shown with appropriate dialogue
        // Book name field, price field and quantity field is cleared and focus is set on book name field
        if(!bookFound)
        {
            String errorMessage = bookName + " is not available";
            showError(errorMessage);
            reset();
            customerNameField.setText(customerName);
            return;
        }

        // Display added to cart book information and success message in the TextArea
        displayCustomerHeading(customerName);
        displayCartBookData(bookCart.size()-1);
        appendLine();
        displayTextArea.append("Added to cart successfully\n");
        reset();  // All input field is restored
        customerNameField.setText(customerName);

    }

    // This method purchase all the books added to the book cart ArrayList by adding customer name and book information in customer ArrayList
    // Displays all book purchased by customer in TextArea
    private void purchase()
    {
        // If bookCart is empty, error dialogue is displayed with appropriate message
        if(bookCart.isEmpty())
        {
            String errorMessage = "Cart is empty";
            showError(errorMessage);
            reset();    // All Gui input field is restored and focus is set on book name
            return;
        }

        // If customer name does not matches with previous one, error dialogue is displayed with appropriate message
        if(!customerNameField.getText().equals(customerName))
        {
            String errorMessage = "Customer name is invalid";
            showError(errorMessage);
            reset();    // All GUI input field is restored and focus is set on book name
            return;
        }

        for(int i = 0; i < bookCart.size(); i++)
        {
            for(int j = 0; j < book.size(); j++)
            {
                // Check if entered book is available in book ArrayList
                // Book name search is case insensitive
                if(book.get(j).getBookName().equalsIgnoreCase(bookCart.get(i).getBookName()))
                {
                    // If available book quantity is more then entered quantity, available quantity is subtracted from entered quantity
                    if(book.get(j).getQuantity() > bookCart.get(i).getQuantity())
                    {
                        book.get(j).setQuantity((book.get(j).getQuantity() - bookCart.get(i).getQuantity()));
                    }

                    // If available book quantity is equal to entered quantity, book is removed from book ArrayList
                    else if(book.get(j).getQuantity() == bookCart.get(i).getQuantity())
                    {
                        book.remove(j);
                    }
                }
            }
        }

        // Display customer name and purchased book heading and information
        displayCustomerHeading(customerName);
        displayCustomerRegister();

        // Customer name and book information is added to the customer ArrayList
        for(int i = 0; i < bookCart.size(); i++)
        {
            Customer temp = new Customer(customerName, bookCart.get(i).getBookName(), bookCart.get(i).getQuantity(), bookCart.get(i).getPrice());
            customer.add(temp);
        }
        // Clear cart and reset all input field and focus is set on book name
        clearCart();
        reset();
    }

    // This method display all the available boon information in the TextArea
    private void displayAll()
    {
        // If no book is added, error message is displayed with appropriate dialogue
        if(book.size()==0)
        {
            String errorMessage = "No book available";
            showError(errorMessage);
            reset();    // All GUI input field is restored and focus is set on book name
            return;
        }

        // Display available book header and all book information in the TextArea
        displayAvailableBookHeading();
        for(int i = 0; i < book.size(); i++)
        {
            displayAvailableBookData(i);
        }
        appendLine();

        // Clear cart and reset all input field and focus is set on book name
        reset();
        clearCart();
    }

    private void displayRegister()
    {
        // Clear cart, TextArea and reset all input field and focus is set on book name
        reset();
        clearCart();
        clearTextArea();

        // If no book it entered, error dialogue is displayed with appropriate message
        if(customer.isEmpty())
        {
            String errorMessage = "Register is empty";
            showError(errorMessage);
            reset();    // All Gui input field is restored and focus is set on book name
            return;
        }

        displayPurchasedBookHeading();      // Display purchased book heading in the TextArea

        // Add all book information brought by customers in book cart ArrayList
        for(int i = 0; i < customer.size(); i++)
        {
            Book temp = new Book(customer.get(i).getBookName(), customer.get(i).getQuantity(), customer.get(i).getPrice());
            bookCart.add(temp);
        }

        // Display all the books purchased by customers in the TextArea
        displayCustomerRegister();
    }

    // If book name is empty error dialogue is displayed with appropriate message and return false.
    // Else return true.
    private boolean checkBookName()
    {
        if(bookNameField.getText().compareTo("") == 0)
        {
            String errorMessage = "You must enter a book name";
            showError(errorMessage);
            reset(); // All the input field of GUI is restored and focus is set in the book name field
            return false;
        }
        return true;
    }

    // If invalid quantity or no quantity is entered, exception is caught and error dialogue with appropriate message is displayed and return false.
    // Focus is set in the quantity field
    // Price field, quantity field and customer field is cleared
    // Else quantity is retrieved from quantity field and converted to integer and return true
    private boolean checkBookQuantity()
    {
        int q = 0;
        try
        {
            q = Integer.parseInt(quantityField.getText());   // Store available book quantity
            return true;
        }
        catch(Exception e)
        {
            String errorMessage = "Invalid quantity entered";
            showError(errorMessage);
            quantityField.setText("");
            priceField.setText("");
            customerNameField.setText("");
            quantityField.requestFocus();
            return false;
        }

    }

    // This method display header for showing available books in appropriate format in the Text Area
    // Append a separator line
    private void displayAvailableBookHeading()
    {
        displayTextArea.setText(String.format("%-70s%-35s%-35s\n", "Book Name", "Available quantity", "Price per book"));
        appendLine();
    }

    // This method display header for showing added to cart / purchased books in appropriate format in the Text Area
    // Append a separator line
    private void displayPurchasedBookHeading()
    {
        displayTextArea.append(String.format("%-70s%-35s%-35s%s\n", "Book Name", "Purchased quantity", "Price per book", "Total charge"));
        appendLine();
    }

    // This method display customer name in the Text Area
    // Append a separator line
    private void displayCustomerHeading(String name)
    {
        displayTextArea.setText("Customer Name: " + name + "\n");
        appendLine();
        displayPurchasedBookHeading();
    }

    // Gets the book name, available quantity and price from the given book index and display in the text area with appropriate format
    private void displayAvailableBookData(int index)
    {
        displayTextArea.append(String.format("%-70s%-35s%-35s\n", book.get(index).getBookName(), book.get(index).getQuantity(), "$"+book.get(index).getPrice()));
    }

    // Gets the book name, available quantity and price from the given bookCart index and display in the text area with appropriate format
    private void displayCartBookData(int index)
    {
        displayTextArea.append(String.format("%-70s%-35s%-35s%s\n", bookCart.get(index).getBookName(), bookCart.get(index).getQuantity(), "$" + bookCart.get(index).getPrice(), "$" + String.format("%.2f",(bookCart.get(index).getPrice() * bookCart.get(index).getQuantity()))));
    }

    // This method display purchased book information
    private void displayCustomerRegister()
    {
        double total_price = 0.0;  //Total cost of all books
        int total_quantity = 0;    //Total number of purchased books

        // Display all book purchased by customer
        for(int i = 0; i < bookCart.size(); i++)
        {
            displayCartBookData(i);
            total_price += (bookCart.get(i).getPrice() * bookCart.get(i).getQuantity());
            total_quantity += bookCart.get(i).getQuantity();
        }

        appendLine();

        // Display Total Cost and Average cost per book on purchased books
        displayTextArea.append("Total Cost: "+String.format("%.2f",total_price) + "\n");
        displayTextArea.append("Average cost per book: "+String.format("%.2f",(total_price / total_quantity)) + "\n");
    }

    // This method separate line of information
    private void appendLine()
    {
        displayTextArea.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
    }

    // All the input field of GUI is restored and focus is set in the Book name field
    private void reset()
    {
        bookNameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        customerNameField.setText("");
        bookNameField.requestFocus();
    }

    // Clear bookCart ArrayList and customer name
    private void clearCart()
    {
        bookCart.clear();
        customerName = "";
    }

    // Clear Text Area
    private void clearTextArea()
    {
        displayTextArea.setText("");
    }

    // Take message in the parameter and show it in the error dialogue
    private  void showError(String errorMessage)
    {
        JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Book Store Management System",
                JOptionPane.ERROR_MESSAGE);
    }

    // Exit the application by showing a thank you message in the dialogue box
    private void exit()
    {

        // TODO -- display exit message here
        JOptionPane.showMessageDialog(new JFrame(), "Thank you for using Book Store Management System", "Book Store Management System",
                JOptionPane.PLAIN_MESSAGE);
        System.exit(0);
    } // exit

    public static void main(String args [])
    {
        BookStoreManagementSystem f = new BookStoreManagementSystem();	// Create instance of class
        f.setVisible(true);			                                    // Make the application visible
    }
}
