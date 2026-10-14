package bci;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a book in the library system.
 * Books have one or more authors and an ISBN.
 */
public class Book extends Work {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    //                    Attributes
    
    private List<Creator> _authors;
    private String _isbn;
    private static final String TYPE = "Livro";


    //                    Constructor

    /**
     * Creates a new book with the specified attributes.
     * 
     * @param id Unique book ID (automatic)
     * @param title The book's title
     * @param price The book's price in EUR
     * @param category The book's category
     * @param copies Number of total book copies
     * @param isbn Book's ISBN number (10 or 13 digits)
     * @param authors List of book authors (at least one)
     */
    public Book(int id, String title, int price, Category category, int copies, String isbn, List<Creator> authors){
        super(id, title, price, category, copies);
        _authors = authors;
        _isbn = isbn;
    }

    
    //                    Getters

    /**
     * Gets the ISBN of this book.
     * 
     * @return the book's ISBN
     */
    private String getIsbn() {  
        return _isbn;
    }
    
    /**
     * Gets the authors of this book.
     * Returns a defensive copy to prevent external modification.
     * 
     * @return defensive copy of the book's authors
     */
    public List<Creator> getAuthors() {
        return new ArrayList<>(_authors);
    }


    //                    Helper Methods

    /**
     * Converts the authors list to a formatted string.
     * Authors are separated by "; " for display purposes.
     * 
     * @return formatted authors string (e.g., "Author1; Author2; Author3")
     */
    private String authorsToString() {
        List<String> names = new ArrayList<>();
        for (Creator author : _authors) {
            names.add(author.getName());
        }
        return String.join("; ", names);
    }
    

    //                    String Representation

    /**
     * Returns a string representation of this book.
     * Format: ID - AC de TC - Livro - TITLE - PRICE - CATEGORY - AUTHORS - ISBN
     * where AC is Available Copies and TC is Total Copies
     * 
     * @return formatted book information
     */
    @Override
    public String toString(){
        return getId() + " - " + getAvailableCopies() + " de " + getTotalCopies() + " - " + TYPE + " - " + 
               getTitle() + " - " + getPrice() + " - " + getCategory().toPortuguese() + " - " + 
               authorsToString() + " - " + getIsbn();
    }
}
