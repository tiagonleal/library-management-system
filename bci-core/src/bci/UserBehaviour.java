package bci;

/**
 * Possible behaviour classifications for library users.
 * Based on their borrowing history and punctuality.
 * Affects borrowing limits and periods.
 */
public enum UserBehaviour {

    //                    Enum Values

    /** User who frequently has late returns (last 3 returns late) */
    FALTOSO,

    /** User with average borrowing behavior (default) */
    NORMAL,

    /** User who consistently returns works on time (last 5 returns on time) */
    CUMPRIDOR;
}
