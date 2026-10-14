package bci;

import bci.exceptions.*;
import java.io.IOException;
import java.io.Serializable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;


/**
 * Core class that represents the library system as a whole.
 * Manages users, works, creators, and all library operations.
 */
class Library implements Serializable {

  @java.io.Serial
  private static final long serialVersionUID = 202507171003L;

  //                    Attributes

  private Map<String, Creator> _creators;
  private Map<Integer, User> _users;
  private Map<Integer, Work> _works;

  private int _workIdCounter;
  private int _userIdCounter;
  private int _currentDate;


  //                    Constructor

  /**
   * Creates a new library instance.
   * Initializes all collections and counters.
   * Starting date is day 1.
   */
  public Library(){
    _creators = new HashMap<>();
    _users = new HashMap<>();
    _works = new HashMap<>();
    _workIdCounter = 1;
    _userIdCounter = 1;
    _currentDate = 1;
  }


  //                    Getters

  /**
   * Gets the current work ID counter.
   * 
   * @return the next work ID to be assigned
   */
  private int getWorkIdCounter(){
    return _workIdCounter;
  }

  /**
   * Gets the current user ID counter.
   * 
   * @return the next user ID to be assigned
   */
  public int getUserIdCounter(){
    return _userIdCounter;
  }

  /**
   * Gets the current system date.
   * 
   * @return current date as an integer
   */
  public int getCurrentDate() {
    return _currentDate;
  }


  //                    ID Counter Incrementation

  /**
   * Increments the work ID counter by one.
   */
  private void incrementWorkIdCounter(){
    _workIdCounter++;
  }

  /**
   * Increments the user ID counter by one.
   */
  private void incrementUserIdCounter(){
    _userIdCounter++;
  }


  //                    Entity Existence Checkers

  /**
   * Checks if a user exists in the system.
   * 
   * @param id the user's ID
   * @return true if user exists, false otherwise
   */
  public boolean hasUser(int id){
    return _users.containsKey(id);
  }

  /**
   * Checks if a work exists in the system.
   * 
   * @param id the work's ID
   * @return true if work exists, false otherwise
   */
  public boolean hasWork(int id){
    return _works.containsKey(id);
  }

  /**
   * Checks if a creator exists in the system.
   * 
   * @param name the creator's name (unique identifier)
   * @return true if creator exists, false otherwise
   */
  public boolean hasCreator(String name){
    return _creators.containsKey(name);
  }

  /**
   * Gets an existing creator or creates a new one if not found.
   * 
   * @param name the creator's name
   * @return the creator (existing or newly created)
   */
  private Creator getOrCreateCreator(String name){
    if(hasCreator(name)){
        return _creators.get(name);
    }
    Creator creator = new Creator(name);
    _creators.put(name, creator);
    return creator;
  }


  //                    Entity Addition Methods

  /**
   * Adds a work to the library's collection.
   * 
   * @param work the work to add
   */
  private void addWork(Work work){
    _works.put(work.getId(), work);
  }
  
  /**
   * Adds a user to the library's collection.
   * 
   * @param user the user to add
   */
  private void addUser(User user){
    _users.put(user.getId(), user);
  }


  //                    File Import Methods

  /**
   * Imports data from a text file and populates the library.
   * Processes USER, BOOK, and DVD entries line by line.
   * 
   * @param filename the path to the import file
   * @throws UnrecognizedEntryException if a line has invalid format
   * @throws IOException if file reading fails
   */
  public void importFile(String filename) throws UnrecognizedEntryException, IOException{
    try(BufferedReader in = new BufferedReader(new FileReader(filename, StandardCharsets.UTF_8))) {
      String line;
      
      // Process each line from the file
      while ((line = in.readLine()) != null) {
        processLine(line);  
      }
    } // BufferedReader closes automatically
  }

  /**
   * Processes a single line from the import file.
   * Delegates to specific processors based on entry type (USER/BOOK/DVD).
   * 
   * @param line the line to process
   * @throws UnrecognizedEntryException if line format is invalid
   */
  private void processLine(String line) throws UnrecognizedEntryException{
    String[] words = line.split(":");
    String type = words[0];

    // Delegate to appropriate processor based on type
    switch (type) {
      case "BOOK":
        processBook(words, line);
        break;
      case "DVD":
        processDVD(words, line);
        break;
      case "USER":
        processUser(words, line);
        break;
      default:
        throw new UnrecognizedEntryException(line);
    }
  }

  /**
   * Processes a USER entry from import file.
   * Format: USER:name:email
   * 
   * @param words the split line components
   * @param line the original line (for error reporting)
   * @throws UnrecognizedEntryException if format is invalid
   */
  private void processUser(String[] words, String line) throws UnrecognizedEntryException{
    try{
      String name = words[1].trim();
      String email = words[2].trim();

      User user = new User(name, email, _userIdCounter);
      incrementUserIdCounter();
      addUser(user);

    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
      throw new UnrecognizedEntryException(line, e);
    }
  }

  /**
   * Processes a BOOK entry from import file.
   * Format: BOOK:title:author(s):price:category:ISBN:copies
   * Authors can be comma-separated for multiple authors.
   * 
   * @param words the split line components
   * @param line the original line (for error reporting)
   * @throws UnrecognizedEntryException if format is invalid
   */
  private void processBook(String[] words, String line) throws UnrecognizedEntryException{
    try{
      String title = words[1].trim();
      String authors = words[2];
      List<Creator> authorsList = processAuthors(authors);
      int price = Integer.parseInt(words[3].trim());
      Category category = processCategory(words[4].trim());
      String isbn = words[5].trim();
      int totalCopies = Integer.parseInt(words[6].trim());

      Book book = new Book(getWorkIdCounter(), title, price, category, totalCopies, isbn, authorsList);
      incrementWorkIdCounter();
      addWork(book);

      // Link book to all its authors
      for (Creator author : authorsList) {
        author.addWork(book);
      }
      
    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
      throw new UnrecognizedEntryException(line, e);
    }
  }

  /**
   * Processes a DVD entry from import file.
   * Format: DVD:title:director:price:category:IGACnumber:copies
   * 
   * @param words the split line components
   * @param line the original line (for error reporting)
   * @throws UnrecognizedEntryException if format is invalid
   */
  private void processDVD(String[] words, String line) throws UnrecognizedEntryException{
    try{
      String title = words[1].trim();
      String directorName = words[2].trim();
      int price = Integer.parseInt(words[3].trim());
      Category category = processCategory(words[4].trim());
      String igacNumber = words[5].trim();
      int totalCopies = Integer.parseInt(words[6].trim());
      
      Creator creator = getOrCreateCreator(directorName);
      DVD dvd = new DVD(getWorkIdCounter(), title, price, category, totalCopies, creator, igacNumber);
      incrementWorkIdCounter();

      creator.addWork(dvd);
      addWork(dvd);
      
    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
      throw new UnrecognizedEntryException(line, e);
    }
  }

  /**
   * Processes a comma-separated list of author names.
   * Creates or retrieves creators for each author.
   * 
   * @param authorsString comma-separated author names
   * @return list of Creator objects
   */
  private List<Creator> processAuthors(String authorsString) {
    String[] authorsArray = authorsString.split(",");
    List<Creator> authorsList = new ArrayList<>();

    for (String author : authorsArray) {
      String authorTrim = author.trim();
      Creator creator = getOrCreateCreator(authorTrim);
      authorsList.add(creator);
    }

    return authorsList;
  }

  /**
   * Converts a category string to the corresponding enum value.
   * 
   * @param category the category string (FICTION, REFERENCE, SCITECH)
   * @return the corresponding Category enum
   * @throws UnrecognizedEntryException if category is not recognized
   */
  private Category processCategory(String category) throws UnrecognizedEntryException{
    switch (category) {
      case "FICTION":
        return Category.FICTION;
      case "REFERENCE":
        return Category.REFERENCE;
      case "SCITECH":
        return Category.SCITECH;
      default:
        throw new UnrecognizedEntryException(category);
    }
  }


  //                    Date Management

  /**
   * Advances the system date and updates all user states.
   * Automatically updates fines and suspensions based on new date.
   * 
   * @param days number of days to advance (must be positive)
   */
  public void advanceDate(int days) {
    _currentDate += days;
    
    // Update all users' fines and status based on new date
    for (User user : _users.values()){
      user.updateOverdueFine(_currentDate);
      user.updateStatus(_currentDate);
    }
  }


  //                    User Management Methods

  /**
   * Gets all users sorted by name (case-insensitive), then by ID.
   * 
   * @return sorted list of all users
   */
  private List<User> getAllUsersSorted(){
    List<User> userList = new ArrayList<>(_users.values());

    // Sort by name (case-insensitive), then by ID
    userList.sort((u1, u2) -> {
      int nameCompare = u1.getName().compareToIgnoreCase(u2.getName());
      if (nameCompare != 0)
        return nameCompare;
      return Integer.compare(u1.getId(), u2.getId());
    });
    return userList;  
  }

  /**
   * Registers a new user in the library system.
   * Automatically assigns a unique ID to the user.
   * 
   * @param name the user's name (cannot be null or empty)
   * @param email the user's email (cannot be null or empty)
   * @throws UserRegistrationFailedException if name or email is invalid
   */
  public void registerUser(String name, String email) throws UserRegistrationFailedException {
    if (name == null || name.isEmpty() || email == null || email.isEmpty()) 
      throw new UserRegistrationFailedException(name);

    addUser(new User(name, email, _userIdCounter));
    incrementUserIdCounter();
  }

  /**
   * Gets a string representation of a specific user.
   * 
   * @param id the user's ID
   * @return formatted user information
   * @throws NoSuchEntityException if user does not exist
   */
  public String showUser(int id) throws NoSuchEntityException {
    if (!hasUser(id))
      throw new NoSuchEntityException("utente", String.valueOf(id));
    
    User user = _users.get(id);
    return user.toString();
  }

  /**
   * Gets a string representation of all users.
   * Users are sorted by name (case-insensitive), then by ID.
   * 
   * @return multi-line string with all users, one per line
   */
  public String showUsers(){
    List<User> users = getAllUsersSorted();

    StringBuilder list = new StringBuilder();
    for (User user: users){
      if (list.length() > 0)
        list.append("\n");
      list.append(user.toString());
    }

    return list.toString();
  }

  /**
   * Checks if a user has any outstanding fines.
   * 
   * @param userId the user's ID
   * @return true if user has fines, false otherwise
   * @throws NoSuchEntityException if user does not exist
   */
  public boolean userHasFines(int userId) throws NoSuchEntityException{
    if (!hasUser(userId))
      throw new NoSuchEntityException("utente", String.valueOf(userId));
    User user = _users.get(userId);
    return user.getFine() > 0;
  }

  /**
   * Gets the total fine amount for a user.
   * 
   * @param userId the user's ID
   * @return fine amount in EUR
   * @throws NoSuchEntityException if user does not exist
   */
  public int getUserFines(int userId) throws NoSuchEntityException{
    if (!hasUser(userId))
      throw new NoSuchEntityException("utente", String.valueOf(userId));
    User user = _users.get(userId);
    return user.getFine();
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
    if (!hasUser(userId))
      throw new NoSuchEntityException("utente", String.valueOf(userId));

    User user = _users.get(userId);
    user.payFine();
    user.updateStatus(_currentDate);
  }


  //                    Work Management Methods

  /**
   * Gets a string representation of a specific work.
   * 
   * @param id the work's ID
   * @return formatted work information
   * @throws NoSuchEntityException if work does not exist
   */
  public String displayWork(int id) throws NoSuchEntityException{
    if(!hasWork(id))
      throw new NoSuchEntityException("obra", String.valueOf(id));

    Work work = _works.get(id);
    return work.toString();
  }

  /**
   * Gets a string representation of all works in the library.
   * 
   * @return multi-line string with all works, one per line
   */
  public String displayWorks(){
    StringBuilder works = new StringBuilder();

    for (Work work : _works.values()){
      if (works.length() > 0)
        works.append("\n");
      works.append(work.toString());
    }

    return works.toString();
  }

  /**
   * Gets all works by a specific creator, sorted by title.
   * 
   * @param creatorName the creator's name
   * @return multi-line string with creator's works, one per line
   * @throws NoSuchEntityException if creator does not exist
   */
  public String displayWorksByCreator(String creatorName) throws NoSuchEntityException{
    if (!hasCreator(creatorName))
      throw new NoSuchEntityException("criador", creatorName);

    Creator creator = _creators.get(creatorName);
    List<Work> creatorWorks = creator.getWorks();
    
    // Sort by title (case-insensitive)
    creatorWorks.sort((w1, w2) -> w1.getTitle().compareToIgnoreCase(w2.getTitle()));

    StringBuilder result = new StringBuilder();
    for (Work work : creatorWorks){
      if (result.length() > 0)
        result.append("\n");
      result.append(work.toString());
    }

    return result.toString();
  }

  /**
   * Changes the inventory (total and available copies) of a work.
   * If inventory reaches zero, removes the work and orphaned creators.
   * 
   * @param workId the work's ID
   * @param numberToChange amount to add (positive) or subtract (negative)
   * @return true if successful, false if would result in negative inventory
   * @throws NoSuchEntityException if work does not exist
   */
  public boolean changeWorkInventory(int workId, int numberToChange) throws NoSuchEntityException{
    if (!hasWork(workId))
      throw new NoSuchEntityException("obra", String.valueOf(workId));
      
    Work work = _works.get(workId);
    boolean success = work.changeTotalCopies(numberToChange);

    // If work has no more copies, remove it and clean up orphaned creators
    if (work.getTotalCopies() == 0) {
      removeWorkAndOrphanedCreators(work);
      _works.remove(workId);
    }
    
    return success;
  }


  //                    Request Management Methods

  /**
   * Processes a work borrowing request.
   * Validates all borrowing rules before allowing the request.
   * 
   * Rules checked (in order):
   * 1. User doesn't already have this work
   * 2. User is not suspended
   * 3. Work has available copies
   * 4. User hasn't exceeded borrow limit
   * 5. Work is not a reference work
   * 6. Work price <= 25 EUR (except for CUMPRIDOR users)
   * 
   * @param userId the user's ID
   * @param workId the work's ID
   * @return the due date for returning the work
   * @throws NoSuchEntityException if user or work does not exist
   * @throws BorrowingFailedException if any borrowing rule is violated
   */
  public int requestWork(int userId, int workId) throws NoSuchEntityException, BorrowingFailedException {
    if (!hasUser(userId))
      throw new NoSuchEntityException("utente", String.valueOf(userId));
    if (!hasWork(workId))
      throw new NoSuchEntityException("obra", String.valueOf(workId));

    User user = _users.get(userId);
    Work work = _works.get(workId);

    // Rule 1: Cannot borrow the same work twice
    if (user.hasWorkBorrowed(work))
      throw new BorrowingFailedException(userId, workId, 1);

    // Rule 2: Cannot borrow if suspended
    if (user.getStatus() == UserStatus.SUSPENDED)
      throw new BorrowingFailedException(userId, workId, 2);

    // Rule 3: Work must have available copies
    if (!work.isAvailable())
      throw new BorrowingFailedException(userId, workId, 3);

    // Rule 4: Cannot exceed borrow limit (depends on behaviour)
    if (user.getActiveBorrowCount() >= user.getMaxBorrowedWorks())
      throw new BorrowingFailedException(userId, workId, 4);
      
    // Rule 5: Cannot borrow reference works
    if (work.getCategory() == Category.REFERENCE){
        throw new BorrowingFailedException(userId, workId, 5);
    }

    // Rule 6: Non-CUMPRIDOR users cannot borrow works over 25 EUR
    if (user.getBehaviour() != UserBehaviour.CUMPRIDOR && work.getPrice() > 25)
      throw new BorrowingFailedException(userId, workId, 6);

    // All rules passed - create the request
    Request request = new Request(work, _currentDate, user);
    user.addRequest(request);
    work.decrementAvailableCopies();

    // Notify interested observers
    work.notifyBorrowing();

    // Remove availability interest (user got the work)
    work.removeInterest(user, NotificationType.DISPONIBILIDADE);

    return request.getDueDate();
  }

  /**
   * Processes a work return.
   * Updates request status, work availability, user behaviour, fines, and status.
   * 
   * @param userId the user's ID
   * @param workId the work's ID
   * @throws NoSuchEntityException if user or work does not exist
   * @throws ReturnFailedException if user doesn't have this work borrowed
   */
  public void returnWork(int userId, int workId) throws NoSuchEntityException, ReturnFailedException{
    if (!hasUser(userId))
      throw new NoSuchEntityException("utente", String.valueOf(userId));
    if (!hasWork(workId))
      throw new NoSuchEntityException("obra", String.valueOf(workId));

    User user = _users.get(userId);
    Work work = _works.get(workId);

    // Verify user actually has this work borrowed
    Request request = user.getActiveRequest(work);
    if (request == null)
      throw new ReturnFailedException(userId, workId);

    // Process the return and update all related states
    request.markAsReturned(_currentDate);
    user.recordReturn(request.wasReturnedOnTime(_currentDate));  // Updates behaviour
    work.incrementAvailableCopies();  // May trigger availability notifications
    user.updateOverdueFine(_currentDate);
    user.updateStatus(_currentDate);
  }


  //                    Interest/Notification Management

  /**
   * Registers a user's interest in being notified about a work.
   * 
   * @param userId the user's ID
   * @param workId the work's ID
   * @param type the notification type (DISPONIBILIDADE or REQUISIÇÃO)
   * @throws NoSuchEntityException if user or work does not exist
   */
  public void registerInterest(int userId, int workId, NotificationType type) throws NoSuchEntityException{
    if (!hasUser(userId))
      throw new NoSuchEntityException("utente", String.valueOf(userId));
    if (!hasWork(workId))
      throw new NoSuchEntityException("obra", String.valueOf(workId));
      
    User user = _users.get(userId);
    Work work = _works.get(workId);
    work.addInterest(user, type);
  }

  /**
   * Gets all pending notifications for a user and clears them.
   * Notifications are one-time and removed after retrieval.
   * 
   * @param userId the user's ID
   * @return list of pending notifications
   * @throws NoSuchEntityException if user does not exist
   */
  public List<Notification> getUserNotifications(int userId) throws NoSuchEntityException{
    if (!hasUser(userId))
      throw new NoSuchEntityException("utente", String.valueOf(userId));
    User user = _users.get(userId);
    return user.getAndClearNotifications();
  }


  //                    Search Methods

  /**
   * Searches for works containing the specified term.
   * Searches in title, authors (for books), and director (for DVDs).
   * Search is case-insensitive.
   * 
   * @param term the search term (can be empty for all works)
   * @return multi-line string with matching works sorted by ID
   */
  public String searchWorks(String term){
    if (term == null || term.isEmpty()) term = "";
    String needle = term.toLowerCase();

    List<Work> works = new ArrayList<>(_works.values());
    works.sort((w1, w2) -> Integer.compare(w1.getId(), w2.getId()));

    StringBuilder result = new StringBuilder();
    for (Work w : works){
      String line = w.toString();
      
      // Check if work's string representation contains the search term
      if (line.toLowerCase().contains(needle)){
        if (result.length() > 0) 
          result.append("\n");
        result.append(line);
      }
    }
    return result.toString();
  }


  //                    Private Helper Methods

  /**
   * Removes a work from its creators and removes creators with no remaining works.
   * This maintains the invariant that only creators with works exist in the system.
   * 
   * @param work the work being removed
   */
  private void removeWorkAndOrphanedCreators(Work work){
    // Handle book authors
    if (work instanceof Book) {
      Book book = (Book) work;
      for (Creator author : book.getAuthors()) {
        author.removeWork(work);
        
        // Remove creator if they have no more works
        if (author.getWorks().isEmpty()){
          _creators.remove(author.getName());
        }
      }
    } 
    // Handle DVD director
    else if (work instanceof DVD) {
      DVD dvd = (DVD) work;
      dvd.getDirector().removeWork(work);
      
      // Remove creator if they have no more works
      if (dvd.getDirector().getWorks().isEmpty()){
        _creators.remove(dvd.getDirector().getName());
      }
    }
  }
}
