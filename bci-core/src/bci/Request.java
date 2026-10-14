package bci;

import java.io.Serializable;

/**
 * Represents a borrowing request for a work by a user.
 * Tracks the borrowing period, return status, and associated fines.
 */
public class Request implements Serializable{

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    //                    Attributes
    
    private Work _work;
    private int _borrowDate;
    private int _dueDate;
    private boolean _returned;
    private int _returnDate;  // -1 if not yet returned
    private boolean _finePaid;


    //                    Constructor

    /**
     * Creates a new borrowing request.
     * Automatically calculates the due date based on work type and user behaviour.
     * 
     * @param work the work being borrowed
     * @param borrowDate the date when the work is borrowed
     * @param user the user borrowing the work
     */
    public Request(Work work, int borrowDate, User user){
        _work = work;
        _borrowDate = borrowDate;
        _dueDate = borrowDate + calculateBorrowingPeriod(user);
        _returned = false;
        _returnDate = -1;  // -1 indicates not yet returned
        _finePaid = false;
    }


    //                    Getters

    /**
     * Gets the work associated with this request.
     * 
     * @return the borrowed work
     */
    public Work getWork(){
        return _work;
    }

    /**
     * Gets the date when the work was borrowed.
     * 
     * @return the borrow date
     */
    public int getBorrowDate(){
        return _borrowDate;
    }

    /**
     * Gets the due date for returning the work.
     * 
     * @return the due date
     */
    public int getDueDate(){
        return _dueDate;
    }

    /**
     * Checks if the work has been returned.
     * 
     * @return true if returned, false otherwise
     */
    public boolean isReturned(){
        return _returned;
    }

    /**
     * Gets the date when the work was returned.
     * 
     * @return the return date, or -1 if not yet returned
     */
    public int getReturnDate(){
        return _returnDate;
    }


    //                    Borrowing Period Calculation

    /**
     * Calculates the borrowing period in days based on work availability and user behaviour.
     * 
     * Rules:
     * - 1 copy: CUMPRIDOR=8 days, NORMAL=3 days, FALTOSO=2 days
     * - 2-5 copies: CUMPRIDOR=15 days, NORMAL=8 days, FALTOSO=2 days
     * - 6+ copies: CUMPRIDOR=30 days, NORMAL=15 days, FALTOSO=2 days
     * 
     * @param user the user borrowing the work
     * @return the number of days allowed for borrowing
     */
    private int calculateBorrowingPeriod(User user){
        int totalCopies = _work.getTotalCopies();
        UserBehaviour behaviour = user.getBehaviour();

        int days = 0;

        // Single copy works have shorter periods
        if(totalCopies == 1){
            switch(behaviour){
                case CUMPRIDOR:
                    days = 8;
                    break;
                case FALTOSO:
                    days = 2;
                    break;
                case NORMAL:
                    days = 3;
                    break;
            }
        } 
        // Works with 2-5 copies have medium periods
        else if (totalCopies <= 5){
            switch(behaviour){
                case CUMPRIDOR:
                    days = 15;
                    break;
                case FALTOSO:
                    days = 2;
                    break;
                case NORMAL:
                    days = 8;
                    break;
            }
        } 
        // Works with 6+ copies have longer periods
        else {
            switch(behaviour){
                case CUMPRIDOR:
                    days = 30;
                    break;
                case FALTOSO:
                    days = 2;
                    break;
                case NORMAL:
                    days = 15;
                    break;
            }
        }
        return days;
    }


    //                    Return Management

    /**
     * Marks this request as returned on the specified date.
     * 
     * @param returnDate the date when the work was returned
     */
    public void markAsReturned(int returnDate){
        _returned = true;
        _returnDate = returnDate;
    }

    /**
     * Marks the fine for this request as paid.
     */
    public void markFinePaid(){
        _finePaid = true;
    }


    //                    Status Checking

    /**
     * Checks if this request is currently overdue.
     * A request is overdue if it's not returned and current date exceeds due date.
     * 
     * @param currentDate the current system date
     * @return true if overdue and not returned, false otherwise
     */
    public boolean isOverdue(int currentDate){
        if (_returned) return false;
        return currentDate > _dueDate;
    }

    /**
     * Checks if the work was returned on time.
     * 
     * @param currentDate the current system date (not used if already returned)
     * @return true if returned by due date, false otherwise
     */
    public boolean wasReturnedOnTime(int currentDate){
        if (!_returned) return false;
        return _returnDate <= _dueDate;
    }

    /**
     * Checks if this request has generated an unpaid fine.
     * 
     * @return true if returned late and fine not paid, false otherwise
     */
    public boolean hasGeneratedFine(){
        return _returned && _returnDate > _dueDate && !_finePaid;
    }


    //                    Fine Calculation

    /**
     * Calculates the number of days this request is/was overdue.
     * 
     * @param currentDate the current system date
     * @return number of days overdue (0 if not overdue)
     */
    public int getDaysOverdue(int currentDate){
        if (_returned)
            return Math.max(0, _returnDate - _dueDate);
        return Math.max(0, currentDate - _dueDate);
    }

    /**
     * Calculates the fine amount for this request.
     * Fine is €5 per day overdue.
     * 
     * @param currentDate the current system date
     * @return fine amount in EUR
     */
    public int calculateFine(int currentDate){
        return getDaysOverdue(currentDate) * 5;
    }

    /**
     * Gets the fine amount generated by this request if returned late.
     * Returns 0 if not returned, returned on time, or fine already paid.
     * 
     * @return the generated fine amount in EUR
     */
    public int getGeneratedFine(){
        if (!_returned || _finePaid)
            return 0;
        return Math.max(0, _returnDate - _dueDate) * 5;
    }
}
