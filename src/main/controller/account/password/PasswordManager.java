package main.controller.account.password;

import main.model.user.User;
import main.utils.exception.PasswordIncorrectException;

/**
 * Manages user passwords.
 */
public class PasswordManager {
    /**
     * Verifies if the provided password matches the user's stored hashed password.
     *
     * @param user     the user whose password is being verified
     * @param password the password to be verified
     * @return true if the password is correct, false otherwise
     */
    public static boolean checkPassword(User user, String password) {
        return user.getHashedPassword().equals(PasswordHashManager.hashPassword(password));
    }

    /**
     * Changes the user's password.
     *
     * @param user        the user whose password is to be changed
     * @param oldPassword the old password
     * @param newPassword the new password
     * @throws PasswordIncorrectException if the old password is incorrect
     */
    public static void changePassword(User user, String oldPassword, String newPassword)
            throws PasswordIncorrectException {
        if (checkPassword(user, oldPassword)) {
            user.setHashedPassword(PasswordHashManager.hashPassword(newPassword));
        } else {
            throw new PasswordIncorrectException();
        }
    }
}
