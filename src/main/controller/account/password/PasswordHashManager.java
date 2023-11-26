package main.controller.account.password;

import main.utils.parameters.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * The PasswordHashManager class offers a utility for securely hashing passwords
 * using the SHA3-256 algorithm.
 */
public class PasswordHashManager {
    /**
     * The hashing algorithm employed for password hashing.
     */
    private static final String HASH_ALGORITHM = "SHA3-256";
    /**
     * The MessageDigest object responsible for carrying out the hashing process.
     */
    private static final MessageDigest messageDigest;

    static {
        try {
            messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            // If the specified algorithm is not available, throw a runtime exception
            throw new RuntimeException(e);
        }
    }

    /**
     * Hashes the provided password using the SHA3-256 algorithm.
     *
     * @param password the password to be hashed
     * @return a Base64-encoded string representing the hashed password
     */
    public static String hashPassword(@NotNull String password) {
        return Base64.getEncoder().encodeToString(messageDigest.digest(password.getBytes()));
    }
}
