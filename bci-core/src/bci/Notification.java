package bci;

import java.io.Serializable;

/**
 * Represents a notification sent to a user about a work.
 * Stores the work description at notification time for historical accuracy.
 */
public class Notification implements Serializable{

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    //                    Attributes

    private NotificationType _type;
    private String _workDescription;  // Stored at notification time, not a reference


    //                    Constructor

    /**
     * Creates a new notification.
     * Captures the work's string representation at notification time.
     * 
     * @param type the notification type (DISPONIBILIDADE or REQUISIÇÃO)
     * @param work the work this notification is about
     */
    public Notification(NotificationType type, Work work){
        _type = type;
        _workDescription = work.toString();  // Snapshot at notification time
    }


    //                    Getters

    /**
     * Gets the notification type.
     * 
     * @return the notification type
     */
    public NotificationType getType(){
        return _type;
    }
    
    /**
     * Gets the work description captured at notification time.
     * 
     * @return the work description
     */
    public String getWorkDescription(){
        return _workDescription;
    }


    //                    String Representation

    /**
     * Returns a formatted notification string.
     * Format: TYPE: work_description
     * 
     * @return formatted notification
     */
    @Override
    public String toString(){
        return _type + ": " + _workDescription;
    }
}
