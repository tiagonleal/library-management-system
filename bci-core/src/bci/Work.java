package bci;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Abstract class representing a work in the library.
 * A work can be a book, a DVD, or another type to be implemented.
 */
public abstract class Work implements Serializable{

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    //                    Attributes
    
    private int _id;   
    private String _title;
    private int _price;
    private Category _category;
    private int _totalCopies;
    private int _availableCopies;
    private List<Interest> _interests;

    //                    Constructor

    /**
     * Constructor for work
     * 
     * @param id    id - Unique identifier for the Work
     * @param title title - The title of the work
     * @param price price - the price of the work
     * @param category category - the category of the work
     * @param copies copies - the number of total copies
     */
    public Work(int id, String title, int price, Category category, int copies){
        _id = id;
        _title = title;
        _price = price;
        _category = category;
        _totalCopies = copies;
        _availableCopies = copies;
        _interests = new ArrayList<>();
    }

    
    //                    Getters

    /**
     * Gets the unique identifier of this work.
     * 
     * @return the work's ID
     */
    public int getId(){
        return _id;
    }

    /**
     * Gets the title of this work.
     * 
     * @return the work's title
     */
    public String getTitle(){
        return _title;
    }

    /**
     * Gets the price of this work.
     * 
     * @return the work's price in EUR
     */
    public int getPrice(){
        return _price;
    }

    /**
     * Gets the category of this work.
     * 
     * @return the work's category
     */
    public Category getCategory(){
        return _category;
    }

    /**
     * Gets the total number of copies of this work in the library.
     * 
     * @return the total number of copies
     */
    public int getTotalCopies(){
        return _totalCopies;
    }

    /**
     * Gets the number of copies currently available for borrowing.
     * 
     * @return the number of available copies
     */
    public int getAvailableCopies(){
        return _availableCopies;
    }


    //                    Inventory Management

    /**
     * Changes the total and available copies of this work.
     * Notifies observers if the work becomes available after being unavailable.
     * 
     * @param numberToChange the number to add (positive) or subtract (negative)
     * @return true if successful, false if operation would result in negative copies
     */
    public boolean changeTotalCopies(int numberToChange){
        // Validate that operation won't result in negative copies
        if (_availableCopies + numberToChange < 0 || _totalCopies + numberToChange < 0)
            return false;
            
        // Track if work was unavailable before the change
        boolean wasUnavailable = _availableCopies == 0;
        
        // Update both total and available copies
        _totalCopies += numberToChange;
        _availableCopies += numberToChange;
        
        // Notify interested users if work became available
        if (wasUnavailable && _availableCopies > 0)
            notifyAvailability();
            
        return true;
    }

    /**
     * Decrements the number of available copies when a work is borrowed.
     */
    public void decrementAvailableCopies(){
        if (_availableCopies > 0)
            _availableCopies--;
    }

    /**
     * Increments the number of available copies when a work is returned.
     * Notifies observers if work was unavailable before.
     */
    public void incrementAvailableCopies(){
        if (_availableCopies < _totalCopies){
            boolean wasUnavailable = _availableCopies == 0;
            _availableCopies++;
            
            // Notify if this was the first available copy
            if (wasUnavailable)
                notifyAvailability();
        }
    }


    //                    Availability Checking

    /**
     * Checks if this work is available for borrowing.
     * A work is available if it has at least one copy not currently borrowed.
     * 
     * @return true if the work has available copies, false otherwise
     */
    public boolean isAvailable(){
        return _availableCopies > 0;
    }


    //                    Observer Pattern - Interest Management

    /**
     * Registers an observer's interest in this work for a specific notification type.
     * Prevents duplicate registrations.
     * 
     * @param observer the observer to register
     * @param type the type of notification (BORROWING or AVAILABILITY)
     */
    public void addInterest(Observer observer, NotificationType type){
        // Check if observer is already registered for this type
        for (Interest existingInterest : _interests){
            if (existingInterest.getObserver().getId() == observer.getId() && existingInterest.getType() == type)
                return; // Already registered, skip
        }
        _interests.add(new Interest(observer, type));
    }

    /**
     * Removes an observer's interest in this work for a specific notification type.
     * 
     * @param observer the observer to unregister
     * @param type the type of notification to stop receiving
     */
    public void removeInterest(Observer observer, NotificationType type){
        _interests.removeIf(i -> i.getObserver().getId() == observer.getId() && i.getType() == type);
    }

    /**
     * Notifies all observers interested in borrowing events.
     * Called when this work is borrowed by a user.
     */
    public void notifyBorrowing(){
        for (Interest interest : _interests){
            if (interest.getType() == NotificationType.REQUISIÇÃO){
                interest.getObserver().update(NotificationType.REQUISIÇÃO, this);
            }
        }
    }

    /**
     * Notifies all observers interested in availability events.
     * Called when this work becomes available after being unavailable.
     * After notification, removes the availability interest (one-time notification).
     */
    public void notifyAvailability(){
        if (_availableCopies > 0){
            // Collect all availability interests
            List<Interest> availabilityInterests = new ArrayList<>();
            for (Interest interest : _interests){
                if (interest.getType() == NotificationType.DISPONIBILIDADE){
                    availabilityInterests.add(interest);
                }
            }

            // Notify all interested observers
            for (Interest interest : availabilityInterests){
                interest.getObserver().update(NotificationType.DISPONIBILIDADE, this);
            }
        }
    }


    //                    Abstract Methods

    /**
     * Returns a string representation of this work.
     * The format depends on the specific type of work (Book, DVD, etc.).
     * 
     * @return string representation of the work
     */
    @Override
    public abstract String toString();
}
