package bci;

/**
 * Represents a DVD in the library system.
 * DVDs have a director and an IGAC registration number.
 */
public class DVD extends Work {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    //                    Attributes
    
    private Creator _director;
    private String _igacNumber;
    private static final String TYPE = "DVD";


    //                    Constructor

    /**
     * Creates a new DVD with the specified attributes.
     * 
     * @param id Unique DVD ID (automatic)
     * @param title DVD's title
     * @param price DVD's price in EUR
     * @param category DVD's category
     * @param copies DVD's total copies
     * @param director DVD's director
     * @param igacNumber DVD's IGAC registration number
     */
    public DVD(int id, String title, int price, Category category, int copies, Creator director, String igacNumber){
        super(id, title, price, category, copies);
        _director = director;
        _igacNumber = igacNumber;
    }


    //                    Getters

    /**
     * Gets the IGAC registration number of this DVD.
     * 
     * @return the DVD's IGAC number
     */
    private String getIgacNumber() {
        return _igacNumber;
    }
    
    /**
     * Gets the director of this DVD.
     * 
     * @return the DVD's director
     */
    public Creator getDirector() {
        return _director;
    }


    //                    String Representation

    /**
     * Returns a string representation of this DVD.
     * Format: ID - AC de TC - DVD - TITLE - PRICE - CATEGORY - DIRECTOR - IGAC
     * where AC is Available Copies and TC is Total Copies
     * 
     * @return formatted DVD information
     */
    @Override
    public String toString(){
        return getId() + " - " + getAvailableCopies() + " de " + getTotalCopies() + " - " + TYPE + " - " + 
               getTitle() + " - " + getPrice() + " - " + getCategory().toPortuguese() + " - " + 
               _director.getName() + " - " + getIgacNumber();
    }
}
