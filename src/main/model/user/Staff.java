package main.model.user;

import main.utils.parameters.EmptyID;
import main.utils.parameters.NotNull;

import java.util.Map;

/**
 * This class represents a staff, which is a type of user.
 * It extends the User class and includes a staff ID field.
 */
public class Staff implements User {
    /**
     * The ID of the staff.
     */
    private String staffID;
    /**
     * The name of the staff
     */
    private String staffName;
    /**
     * The email of a staff
     */
    private String email;
    private String hashedPassword;

    /**
     * Constructs a new staff object with the specified staff ID.
     *
     * @param staffID   the ID of the staff.
     * @param staffName the name of the staff.
     * @param email          the email of the staff.
     */
    public Staff(String staffID, String staffName, String email) {
        this.staffID = staffID;
        this.staffName = staffName;
        this.email = email;
    }

    /**
     * Constructs a new staff object with the specified staff ID and password.
     *
     * @param staffID   the ID of the staff.
     * @param staffName the name of the staff.
     * @param email          the email of the staff.
     * @param hashedPassword the password of the staff.
     */
    public Staff(String staffID, String staffName, String email, @NotNull String hashedPassword) {
        this.hashedPassword = hashedPassword;
        this.staffID = staffID;
        this.staffName = staffName;
        this.email = email;
    }

    /**
     * Constructs a new staff object from a map
     *
     * @param map the map
     */
    public Staff(Map<String, String> map) {
        this.fromMap(map);
    }

    /**
     * default constructor for staff class
     */
    public Staff() {
        this.staffID = EmptyID.EMPTY_ID;
        this.staffName = EmptyID.EMPTY_ID;
        this.email = EmptyID.EMPTY_ID;
    }

    public static User getUser(Map<String, String> map) {
        return new Staff(map);
    }

    /**
     * Gets the user ID of the user.
     *
     * @return the ID of the user.
     */
    @Override
    public String getID() {
        return this.staffID;
    }

    /**
     * Gets the username
     *
     * @return the name of the user
     */
    @Override
    public String getUserName() {
        return this.staffName;
    }

    /**
     * Gets the hashed password of the user
     *
     * @return the hashed password of the user
     */
    @Override
    public String getHashedPassword() {
        return this.hashedPassword;
    }

    /**
     * Sets the hashed password of the user
     *
     * @param hashedPassword the hashed password of the user
     */
    @Override
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    /**
     * Gets the email of the user
     *
     * @return the email of the user
     */
    @Override
    public String getEmail() {
        return this.email;
    }
}
