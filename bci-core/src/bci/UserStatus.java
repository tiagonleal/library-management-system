package bci;

/**
 * Possible statuses for a library user.
 * Status determines whether user can borrow works.
 */
public enum UserStatus {

    //                    Enum Values

    /** User is active and can borrow works */
    ACTIVE,

    /** User is suspended and cannot borrow works (due to fines or overdue returns) */
    SUSPENDED;
}
