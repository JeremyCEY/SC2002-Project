/**
 * The main.boundary.account package contains classes responsible for interacting with users
 * to retrieve various attributes such as domain, password, UserID, and username.
 */
package main.boundary.account;

import main.model.user.UserType;
import main.utils.iocontrol.IntGetter;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.PasswordReader;

import java.util.Scanner;

/**
 * The AttributeGetter class provides methods for interacting with users
 * to retrieve information such as domain, password, UserID, and username.
 */
public class AttributeGetter {
    /**
     * Prompts the user to select their domain and returns their chosen UserType.
     *
     * @return The UserType chosen by the user.
     */
    public static UserType getDomain() {
        // Display domain options and prompt user for selection
        System.out.println(BoundaryStrings.separator);
        System.out.println("\t1. Student");
        System.out.println("\t2. Staff");
        System.out.println(BoundaryStrings.separator);
        System.out.print("Please select your domain (1-2): ");
        
        UserType userType = null;
        while (userType == null) {
            // Read user input and handle exceptions
            Scanner scanner = new Scanner(System.in);
            int domain;
            try {
                domain = IntGetter.readInt();
            } catch (Exception e) {
                System.out.println("Please enter a number.");
                continue;
            }
            
            // Convert user input to UserType
            userType = switch (domain) {
                case 1 -> UserType.STUDENT;
                case 2 -> UserType.STAFF;
                default -> null;
            };
            
            // Handle invalid domain selection
            if (userType == null) {
                System.out.println("Invalid domain. Please try again.");
            }
        }
        return userType;
    }

    /**
     * Prompts the user to enter their password securely without displaying it on the screen.
     *
     * @return The password entered by the user.
     */
    public static String getPassword() {
        // Prompt user to enter password securely
        System.out.print("Please enter your password: ");
        return PasswordReader.getPassword();
    }

    /**
     * Prompts the user to enter their UserID.
     *
     * @return The UserID entered by the user.
     */
    public static String getUserID() {
        // Prompt user to enter UserID
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your UserID (Press enter if you forget): ");
        return scanner.nextLine();
    }

    /**
     * Prompts the user to enter their username.
     *
     * @return The username entered by the user.
     */
    public static String getUserName() {
        // Prompt user to enter username
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your username: ");
        return scanner.nextLine();
    }
}
