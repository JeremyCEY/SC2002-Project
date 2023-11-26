/**
 * This package contains classes and interfaces related to user management in the application.
 * Users represent individuals interacting with the system, and different user types,
 * such as Staff and Student, define specific roles and attributes. The User interface
 * provides a common set of methods for user-related operations, and its implementing
 * classes define the details for each user type. The Faculty enum represents various
 * faculties associated with users. Additionally, utility classes for handling user-related
 * parameters and empty IDs are included.
 */
package main.model.user;

import main.model.Model;

/**
 * The User interface represents a generic user within the system. Users are identified
 * by unique IDs and possess basic attributes such as a username, hashed password, and email.
 * This interface extends the Model interface, indicating that user entities can be managed
 * and persisted. Implementing classes, such as Staff and Student, define specific user types
 * with additional attributes and behaviors. The interface includes methods for retrieving and
 * modifying user information, as well as a default method for checking if a given username
 * matches the user's username, ignoring case.
 */
public interface User extends Model {
    /**
     * Gets the user ID of the user.
     *
     * @return the ID of the user.
     */
    String getID();

    /**
     * Gets the username of the user
     *
     * @return the name of the user
     */
    String getUserName();

    /**
     * Gets the hashed password of the user
     *
     * @return the hashed password of the user
     */
    String getHashedPassword();

    /**
     * Sets the hashed password of the user
     *
     * @param hashedPassword the hashed password of the user
     */
    void setHashedPassword(String hashedPassword);

    /**
     * Gets the email of the user
     *
     * @return the email of the user
     */
    String getEmail();

    /**
     * The function to check if username is equal to the user's username regardless
     * of case
     *
     * @param username the username to be checked
     *
     * @return true if the username is equal to the user's username regardless of
     *         case
     */
    default boolean checkUsername(String username) {
        return this.getUserName().equalsIgnoreCase(username);
    }
}
