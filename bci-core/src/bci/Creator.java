package bci;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a creator (author/director) of works in the library.
 * A creator can have multiple works associated with them.
 * Creators are automatically removed from the system when they have no works.
 */
public class Creator implements Serializable {
    
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    //                    Attributes

    private String _name;
    private List<Work> _works;


    //                    Constructor

    /**
     * Creates a new creator with the specified name.
     * Initially, the creator has no associated works.
     * 
     * @param name Creator's unique identifier (name)
     */
    public Creator(String name){
        _name = name;
        _works = new ArrayList<>();
    }


    //                    Getters

    /**
     * Gets the name of this creator.
     * 
     * @return the creator's name
     */
    public String getName(){
        return _name;
    }
    
    /**
     * Gets all works created by this creator.
     * Returns a defensive copy to prevent external modification.
     * 
     * @return defensive copy of the list of works
     */
    public List<Work> getWorks(){
        return new ArrayList<>(_works);
    }

    
    //                    Work Management

    /**
     * Adds a work to this creator's list of works.
     * 
     * @param work the work to add
     */
    public void addWork(Work work){
        _works.add(work);
    }
    
    /**
     * Removes a work from this creator's list of works.
     * If the creator has no more works after removal, they should be removed from the system.
     * 
     * @param work the work to remove
     */
    public void removeWork(Work work){
        _works.remove(work);
    }
}
