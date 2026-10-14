package bci;

import java.io.Serializable;

/**
 * Represents an observer's interest in receiving notifications about a work.
 * Associates an observer with a specific notification type for a work.
 */
public class Interest implements Serializable{

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    //                    Attributes

    private Observer _observer;
    private NotificationType _type;


    //                    Constructor

    /**
     * Creates a new interest registration.
     * 
     * @param observer the observer to be notified
     * @param type the type of notification (DISPONIBILIDADE or REQUISIÇÃO)
     */
    public Interest(Observer observer, NotificationType type){
        _observer = observer;
        _type = type;
    }


    //                    Getters

    /**
     * Gets the observer registered for this interest.
     * 
     * @return the observer
     */
    public Observer getObserver(){
        return _observer;
    }
    
    /**
     * Gets the notification type for this interest.
     * 
     * @return the notification type
     */
    public NotificationType getType(){
        return _type;
    }
}
