package bci;

import bci.exceptions.*;
import java.io.*;
import java.util.List;

/**
 * Façade class for the library management system.
 * Provides a simplified interface to interact with library core functionality.
 * Manages serialization and tracks unsaved changes.
 */
public class LibraryManager{

  @java.io.Serial
  private static final long serialVersionUID = 202507171003L;

  //                    Attributes

  /** The core library object doing all the actual work. */
  private Library _library;
  
  /** Current filename associated with this library (null if none). */
  private String _filename;
  
  /** Tracks whether there are unsaved changes to the library state. */
  private boolean _dirty;


  //                    Constructor

  /**
   * Creates a new library manager with an empty library.
   * No file is associated initially, and state is clean.
   */
  public LibraryManager(){
      _library = new Library();
      _filename = null;
      _dirty = false;
  }


  //                    State Management

  /**
   * Indicates if there are unsaved changes since the last save/load/import.
   * 
   * @return true if there are unsaved changes, false otherwise
   */
  public boolean hasUnsavedChanges() {
      return _dirty;
  }


  //                    Serialization Methods

  /**
   * Saves the current library state to the associated file.
   * 
   * @throws MissingFileAssociationException if no file is associated
   * @throws IOException if an I/O error occurs during saving
   */
  public void save() throws MissingFileAssociationException, IOException {
      if (_filename == null) 
        throw new MissingFileAssociationException();
      saveAs(_filename);
  }

  /**
   * Saves the library to a specified file and associates it with this manager.
   * Marks the state as clean (no unsaved changes).
   * 
   * @param filename the name of the file to save to
   * @throws IOException if an I/O error occurs during saving
   */
  public void saveAs(String filename) throws IOException {
      try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))){
        out.writeObject(_library);
        _filename = filename;
        _dirty = false;
      }
  }

  /**
   * Loads a previously saved library state from a file.
   * Replaces the current library with the loaded one.
   * Associates the file for future saves.
   * 
   * @param filename the name of the file to load from
   * @throws UnavailableFileException if file cannot be read or deserialized
   */
  public void load(String filename) throws UnavailableFileException {
      try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))){
        _library = (Library) in.readObject();
        _filename = filename;
        _dirty = false;
      } catch (IOException | ClassNotFoundException e) {
        throw new UnavailableFileException(filename);
      }
  }

  /**
   * Imports data from a text file and initializes the library.
   * The import file should contain user and work definitions in specific format.
   * Marks state as clean after successful import.
   *
   * @param filename name of the text input file
   * @throws ImportFileException if an error occurs during file processing
   */
  public void importFile(String filename) throws ImportFileException {
    try {
        _library.importFile(filename);
        _dirty = false;
    } catch (IOException | UnrecognizedEntryException e) {
      throw new ImportFileException(filename, e);
    }
  }


  //                    Date Management

  /**
   * Gets the current date in the library system.
   * 
   * @return the current date as an integer
   */
  public int displayDate() {
    return _library.getCurrentDate();
  }

  /**
   * Advances the current date by a specified number of days.
   * Automatically updates fines and user statuses for all users.
   * 
   * @param days number of days to advance (must be positive)
   */
  public void advanceDate(int days) {
      _library.advanceDate(days);
      _dirty = true;
  }


  //                    User Management Methods

  /**
   * Registers a new user in the library system.
   * Automatically assigns a unique ID to the user.
   * 
   * @param name the user's name (cannot be empty)
   * @param email the user's email address (cannot be empty)
   * @return the ID assigned to the newly registered user
   * @throws UserRegistrationFailedException if registration fails due to invalid data
   */
  public int registerUser(String name, String email) throws UserRegistrationFailedException {
      _library.registerUser(name, email);
      _dirty = true;
      return _library.getUserIdCounter() - 1;
  }

  /**
   * Retrieves detailed information about a specific user.
   * 
   * @param id the user's ID
   * @return string representation of the user
   * @throws NoSuchEntityException if the user does not exist
   */
  public String showUser(int id) throws NoSuchEntityException {
      return _library.showUser(id);
  }

  /**
   * Retrieves information about all users in the library.
   * Users are sorted by name (case-insensitive), then by ID.
   * 
   * @return string representation of all users, one per line
   */
  public String showUsers(){
      return _library.showUsers();
  }

  /**
   * Checks if a user has any outstanding fines.
   * 
   * @param userId the user's ID
   * @return true if user has fines, false otherwise
   * @throws NoSuchEntityException if user does not exist
   */
  public boolean userHasFines(int userId) throws NoSuchEntityException{
    return _library.userHasFines(userId);
  }

  /**
   * Gets the total fine amount for a user.
   * 
   * @param userId the user's ID
   * @return fine amount in EUR
   * @throws NoSuchEntityException if user does not exist
   */
  public int getUserFines(int userId) throws NoSuchEntityException{
    return _library.getUserFines(userId);
  }

  /**
   * Pays all outstanding fines for a user.
   * Updates user status to ACTIVE if no overdue works remain.
   * 
   * @param userId the user's ID
   * @throws NoSuchEntityException if user does not exist
   * @throws UserIsActiveException if user has no fines to pay
   */
  public void payFine(int userId) throws NoSuchEntityException, UserIsActiveException{
    try{
      _library.payFine(userId);
      _dirty = true;
    } catch (bci.exceptions.UserIsActiveException e) {
      throw new UserIsActiveException(userId);
    }
  }


  //                    Work Management Methods

  /**
   * Retrieves detailed information about a specific work.
   * 
   * @param id the work's ID
   * @return string representation of the work
   * @throws NoSuchEntityException if the work does not exist
   */
  public String showWork(int id) throws NoSuchEntityException{
      return _library.displayWork(id);
  }

  /**
   * Retrieves information about all works in the library.
   * 
   * @return string representation of all works, one per line
   */
  public String showWorks(){
      return _library.displayWorks();
  }

  /**
   * Retrieves all works created by a specific creator.
   * Works are sorted alphabetically by title (case-insensitive).
   * 
   * @param id the creator's name/identifier
   * @return string representation of the creator's works, one per line
   * @throws NoSuchEntityException if the creator does not exist
   */
  public String showCreatorWorks(String id) throws NoSuchEntityException{
      return _library.displayWorksByCreator(id);
  }

  /**
   * Changes the inventory of a work by adding or removing copies.
   * If inventory reaches zero, the work is removed from the system.
   * 
   * @param workId the work's ID
   * @param numberToChange amount to add (positive) or remove (negative)
   * @return true if successful, false if would result in negative inventory
   * @throws NoSuchEntityException if work does not exist
   */
  public boolean changeWorkInventory(int workId, int numberToChange) throws NoSuchEntityException{
    boolean success = _library.changeWorkInventory(workId, numberToChange);
    _dirty = true;
    return success;
  }


  //                    Search Methods

  /**
   * Searches for works containing the specified term.
   * Searches in title, authors (for books), and director (for DVDs).
   * Search is case-insensitive.
   * 
   * @param term the search term
   * @return multi-line string with matching works sorted by ID
   */
  public String searchWorks(String term){
    return _library.searchWorks(term);
  }


  //                    Request Management Methods

  /**
   * Processes a work borrowing request.
   * Validates all borrowing rules before allowing the request.
   * 
   * @param userId the user's ID
   * @param workId the work's ID
   * @return the due date for returning the work
   * @throws NoSuchEntityException if user or work does not exist
   * @throws BorrowingFailedException if any borrowing rule is violated
   */
  public int requestWork(int userId, int workId) throws NoSuchEntityException, BorrowingFailedException {
      int dueDate = _library.requestWork(userId, workId);
      _dirty = true;
      return dueDate;
  }

  /**
   * Processes a work return by a user.
   * Updates work availability, user behaviour, fines, and status.
   * 
   * @param userId the user's ID
   * @param workId the work's ID
   * @throws NoSuchEntityException if user or work does not exist
   * @throws ReturnFailedException if user doesn't have this work borrowed
   */
  public void returnWork(int userId, int workId) throws NoSuchEntityException, ReturnFailedException{
        _library.returnWork(userId, workId);
        _dirty = true;
  }


  //                    Interest/Notification Management

  /**
   * Registers a user's interest in being notified when a work becomes available.
   * 
   * @param userId the user's ID
   * @param workId the work's ID
   * @throws NoSuchEntityException if user or work does not exist
   */
  public void registerAvailabilityInterest(int userId, int workId) throws NoSuchEntityException{
    _library.registerInterest(userId, workId, NotificationType.DISPONIBILIDADE);
    _dirty = true;
  }

  /**
   * Registers a user's interest in being notified when a work is borrowed.
   * 
   * @param userId the user's ID
   * @param workId the work's ID
   * @throws NoSuchEntityException if user or work does not exist
   */
  public void registerBorrowingInterest(int userId, int workId) throws NoSuchEntityException{
    _library.registerInterest(userId, workId, NotificationType.REQUISIÇÃO);
    _dirty = true;
  }

  /**
   * Gets all pending notifications for a user and formats them as a string.
   * Notifications are cleared after retrieval (one-time display).
   * 
   * @param userId the user's ID
   * @return multi-line string with notifications, or empty string if none
   * @throws NoSuchEntityException if user does not exist
   */
  public String showUserNotifications(int userId) throws NoSuchEntityException{
    List<Notification> notifications = _library.getUserNotifications(userId);

    if (notifications.isEmpty())
      return "";
      
    StringBuilder notificationsString = new StringBuilder();
    for (Notification notification : notifications){
      notificationsString.append(notification.toString()).append("\n");
    }

    // Remove trailing newline
    if (notificationsString.length() > 0)
      notificationsString.setLength(notificationsString.length() - 1);
      
    return notificationsString.toString();
  }
}
