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

/**
 * An enumeration representing different types of users in the system.
 * User types include STUDENT and STAFF, defining the two main categories of users.
 * This enum is used to identify the type of a user and is often used in conjunction
 * with the UserFactory for creating specific user instances based on their type.
 */
public enum UserType {
    /**
     * Represents a student user type.
     */
    STUDENT,
    
    /**
     * Represents a staff user type.
     */
    STAFF
}
