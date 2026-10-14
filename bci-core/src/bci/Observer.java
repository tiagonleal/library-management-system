package bci;

/**
 * Observer interface for the notification system.
 * Entities interested in work state changes must implement this interface.
 * Currently implemented by User to receive work availability and borrowing notifications.
 */ 
public interface Observer {
    
    /**
     * Called when a notification is triggered for a work.
     * 
     * @param type the type of notification (DISPONIBILIDADE or REQUISIÇÃO)
     * @param work the work that triggered the notification
     */
    void update(NotificationType type, Object work);

    /**
     * Gets the unique identifier of this observer.
     * Used to prevent duplicate interest registrations.
     * 
     * @return the observer's ID
     */
    int getId();
}
