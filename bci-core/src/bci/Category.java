package bci;

/**
 * Categories available for library works.
 * Each work must belong to exactly one category.
 */
public enum Category {

    //                    Enum Values

    /** Reference works (dictionaries, encyclopedias, documentaries) */
    REFERENCE,
    
    /** Fiction works (novels, fiction films) */
    FICTION,
    
    /** Science and technology works (technical books, educational content) */
    SCITECH;


    //                    Methods

    /**
     * Converts the category to its Portuguese representation for display.
     * 
     * @return the category name in Portuguese
     */
    public String toPortuguese(){
        switch(this){
            case REFERENCE:
                return "Referência";
            case FICTION:
                return "Ficção";
            case SCITECH:
                return "Técnica e Científica";
            default:
                return this.name();
        }
    }
}
