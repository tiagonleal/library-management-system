package bci;

/**
 * Types of notifications that can be sent to observers.
 * Used in the observer pattern for work state changes.
 */
public enum NotificationType {

    //                    Enum Values

    /** Notification sent when a work is borrowed by someone */
    REQUISIÇÃO,
    
    /** Notification sent when a work becomes available after being unavailable */
    DISPONIBILIDADE;
}
