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

import main.controller.account.password.PasswordHashManager;

/**
 * A factory class for creating User objects based on the given parameters.
 */
public class UserFactory {
    /**
     * Creates a new User object based on the given parameters.
     *
     * @param userType The type of user to be created (student, faculty, or coordinator).
     * @param userID   The user's ID.
     * @param password The user's password in plaintext.
     * @param name     The user's name.
     * @param email    The user's email address.
     * @param faculty  The faculty associated with the user.
     * @return A new User object of the specified type.
     */
    public static User create(UserType userType, String userID, String password, String name, String email,
            Faculty faculty) {
        // Hash the password using the PasswordHashManager
        String hashedPassword = PasswordHashManager.hashPassword(password);

        // Create a new User object based on the user type
        return switch (userType) {
            case STUDENT -> new Student(userID, name, email, hashedPassword, faculty);
            case STAFF -> new Staff(userID, name, email, hashedPassword, faculty);
        };
    }
}
