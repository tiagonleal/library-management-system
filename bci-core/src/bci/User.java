package bci;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import bci.exceptions.UserIsActiveException;

/**
 * Represents a library user with borrowing capabilities and fine management.
 * User can have different statuses and behaviours based on their borrowing history.
 */
public class User implements Serializable, Observer {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    //                    Attributes
    
    private String _name;
    private String _email;
    private int _id;
    private UserStatus _status;
    private UserBehaviour _behaviour;
    private int _fine;
    private List<Request> _borrowedWorks;
    private List<Notification> _notifications;
    private List<Boolean> _onTimeHistory;  // Track last 10 returns (true=on time, false=late)


    //                    Constructor

    /**
     * Creates a new user with the specified attributes.
     * New users start with ACTIVE status, NORMAL behaviour, no fines, and no borrowed works.
     * 
     * @param name Name of the user
     * @param email Email of the user
     * @param id Unique user id (automatic)
     */
    public User(String name, String email, int id){
        _name = name;
        _email = email;
        _id = id;
        _status = UserStatus.ACTIVE;
        _behaviour = UserBehaviour.NORMAL;
        _fine = 0;
        _borrowedWorks = new ArrayList<>();
        _notifications = new ArrayList<>();
        _onTimeHistory = new ArrayList<>();
    }


    //                    Getters

    /**
     * Gets the user's name.
     * 
     * @return the user's name
     */
    public String getName(){
        return _name;
    }

    /**
     * Gets the user's email address.
     * 
     * @return the user's email
     */
    public String getEmail(){
        return _email;
    }

    /**
     * Gets the user's unique identifier.
     * 
     * @return the user's ID
     */
    public int getId(){
        return _id;
    }

    /**
     * Gets the user's current status.
     * 
     * @return the user's status (ACTIVE or SUSPENDED)
     */
    public UserStatus getStatus(){
        return _status;
    }

    /**
     * Gets the user's behaviour classification.
     * 
     * @return the user's behaviour (NORMAL, CUMPRIDOR, or FALTOSO)
     */
    public UserBehaviour getBehaviour(){
        return _behaviour;
    }

    /**
     * Gets the total amount of fines owed by the user.
     * 
     * @return the fine amount in EUR
     */
    public int getFine(){
        return _fine;
    }

    /**
     * Gets the list of works currently borrowed by the user.
     * Returns a defensive copy to prevent external modification.
     * 
     * @return defensive copy of the list of active borrow requests
     */
    public List<Request> getBorrowedWorks(){
        return new ArrayList<>(_borrowedWorks);
    }


    //                    Borrowing Status Queries

    /**
     * Checks if the user currently has a specific work borrowed.
     * Only considers active (non-returned) requests.
     * 
     * @param work the work to check
     * @return true if user has an active borrow of this work, false otherwise
     */
    public boolean hasWorkBorrowed(Work work){
        for (Request request : _borrowedWorks){
            if (request.getWork().getId() == work.getId() && !request.isReturned()){
                return true;
            }
        }
        return false;
    }

    /**
     * Counts the number of works currently borrowed by the user.
     * Only counts non-returned requests.
     * 
     * @return number of active borrows
     */
    public int getActiveBorrowCount(){
        int count = 0;
        for (Request request : _borrowedWorks){
            if (!request.isReturned()){
                count++;
            }
        }
        return count;
    }

    /**
     * Gets the maximum number of works this user can borrow simultaneously.
     * Depends on user behaviour: CUMPRIDOR=5, NORMAL=3, FALTOSO=1
     * 
     * @return maximum number of concurrent borrows allowed
     */
    public int getMaxBorrowedWorks(){
        switch(_behaviour){
            case CUMPRIDOR:
                return 5;
            case FALTOSO:
                return 1;
            default:
            case NORMAL:
                return 3;
        }
    }

    /**
     * Gets the active request for a specific work, if it exists.
     * 
     * @param work the work to search for
     * @return the active request for this work, or null if not found
     */
    public Request getActiveRequest(Work work){
        for (Request request : _borrowedWorks){
            if (request.getWork().getId() == work.getId() && !request.isReturned()){
                return request;
            }
        }
        return null;
    }


    //                    Notification Management

    /**
     * Gets all pending notifications and clears the notification list.
     * This is a one-time retrieval - notifications are removed after reading.
     * 
     * @return list of pending notifications
     */
    public List<Notification> getAndClearNotifications(){
        List<Notification> notifications = new ArrayList<>(_notifications);
        _notifications.clear();
        return notifications;
    }

    /**
     * Checks if the user has any pending notifications.
     * 
     * @return true if there are unread notifications, false otherwise
     */
    public boolean hasNotifications(){
        return !_notifications.isEmpty();
    }

    /**
     * Observer pattern update method.
     * Called when a work this user is interested in changes state.
     * 
     * @param type the type of notification
     * @param work the work that changed state
     */
    public void update(NotificationType type, Object work){
        Work w = (Work) work;
        Notification notification = new Notification(type, w);
        _notifications.add(notification);
    }


    //                    Fine Management

    /**
     * Checks if the user has any works that are currently overdue.
     * 
     * @param currentDate current system date
     * @return true if user has overdue works, false otherwise
     */
    public boolean hasOverdueWorks(int currentDate){
        for (Request request : _borrowedWorks){
            if (request.isOverdue(currentDate)){
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates the total fine including both paid and unpaid fines.
     * Includes fines from currently overdue works.
     * 
     * @param currentDate current system date
     * @return total fine amount in EUR
     */
    public int calculateTotalFine(int currentDate){
        int total = _fine;
        
        // Add fines from currently overdue works
        for (Request request : _borrowedWorks){
            if (!request.isReturned() && request.isOverdue(currentDate)){
                total += request.calculateFine(currentDate);
            }
        }
        return total;
    }

    /**
     * Updates the user's fine based on returned works.
     * Only counts fines from already-returned works that haven't been paid.
     * 
     * @param currentDate current system date
     */
    public void updateOverdueFine(int currentDate){
        _fine = 0;
        for (Request request : _borrowedWorks){
            if (request.hasGeneratedFine()){
                _fine += request.getGeneratedFine();
            }
        }
    }

    /**
     * Adds a fine amount to the user's total fine.
     * 
     * @param fine the fine amount to add
     */
    public void addFine(int fine){
        _fine += fine;
    }

    /**
     * Pays all outstanding fines for this user.
     * Marks all request fines as paid and resets the fine amount to zero.
     * 
     * @throws UserIsActiveException if user has no fines to pay
     */
    public void payFine() throws UserIsActiveException{
        if (_fine == 0)
            throw new UserIsActiveException(_id);
            
        _fine = 0;
        
        // Mark all requests' fines as paid
        for (Request request : _borrowedWorks){
            if (request.hasGeneratedFine()){
                request.markFinePaid();
            }
        }
    }


    //                    Status Management

    /**
     * Updates the user's status based on current state.
     * User is SUSPENDED if they have unpaid fines or overdue works.
     * Otherwise, user is ACTIVE.
     * 
     * @param currentDate current system date
     */
    public void updateStatus(int currentDate){
        if (_fine > 0 || hasOverdueWorks(currentDate))
            _status = UserStatus.SUSPENDED;
        else 
            _status = UserStatus.ACTIVE;
    }


    //                    Request Management

    /**
     * Adds a new borrow request to the user's history.
     * 
     * @param request the request to add
     */
    public void addRequest(Request request){
        _borrowedWorks.add(request);
    }

    /**
     * Records a work return and updates the user's behaviour based on punctuality.
     * Maintains a history of the last 10 returns for behaviour classification.
     * 
     * @param onTime true if returned on time, false if late
     */
    public void recordReturn(boolean onTime){
        _onTimeHistory.add(onTime);
        
        // Keep only last 10 returns in history
        if (_onTimeHistory.size() > 10)
            _onTimeHistory.remove(0);
            
        recomputeBehaviour();
    }


    //                    Behaviour Classification 

    /**
     * Recomputes the user's behaviour classification based on return history.
     * 
     * Rules:
     * - CUMPRIDOR: Last 5 returns all on time
     * - FALTOSO: Last 3 returns all late
     * - NORMAL: FALTOSO with 3 consecutive on-time returns, or CUMPRIDOR who lost streak
     */
    private void recomputeBehaviour(){
        // Check if user qualifies for CUMPRIDOR status (5 on-time returns)
        if (lastNReturns(true, 5)){
            _behaviour = UserBehaviour.CUMPRIDOR;
            return;
        }

        // Check if user qualifies for FALTOSO status (3 late returns)
        if (lastNReturns(false, 3)){
            _behaviour = UserBehaviour.FALTOSO;
            return;
        }
        
        // FALTOSO users become NORMAL after 3 consecutive on-time returns
        if (_behaviour == UserBehaviour.FALTOSO && lastNReturns(true, 3)){
            _behaviour = UserBehaviour.NORMAL;
            return;
        }

        // CUMPRIDOR users become NORMAL if they lose the 5 consecutive on-time streak
        if (_behaviour == UserBehaviour.CUMPRIDOR){
            _behaviour = UserBehaviour.NORMAL;
            return;
        }

        // All other cases: remain NORMAL (default behaviour)
    }

    /**
     * Checks if the last N returns match a specific pattern (all on-time or all late).
     * 
     * @param onTime the pattern to check (true for on-time, false for late)
     * @param n the number of returns to check
     * @return true if last N returns match the pattern, false otherwise
     */
    private boolean lastNReturns(boolean onTime, int n){
        // Not enough history
        if (_onTimeHistory.size() < n)
            return false;
            
        // Check last N returns
        for (int i = _onTimeHistory.size() - n; i < _onTimeHistory.size(); i++){
            if (_onTimeHistory.get(i) != onTime)
                return false;
        }
        return true;
    }


    //                    String Representation

    /**
     * Returns a string representation of the user.
     * Format: id - name - email - behaviour - status [- EUR fine]
     * 
     * @return formatted user information
     */
    @Override   
    public String toString(){
        if (_status == UserStatus.ACTIVE){
            return _id + " - " + _name + " - " + _email + " - " + _behaviour + " - ACTIVO";
        } else {
            return _id + " - " + _name + " - " + _email + " - " + _behaviour + " - SUSPENSO - EUR " + _fine;
        }
    }
}
