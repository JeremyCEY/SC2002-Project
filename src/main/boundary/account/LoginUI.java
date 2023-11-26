/**
 * The main.boundary.account package contains classes responsible for providing user interfaces
 * related to user account login, including login page display and user authentication.
 */
package main.boundary.account;

import main.boundary.mainpage.StaffMainPage;
import main.boundary.mainpage.StudentMainPage;
import main.controller.account.AccountManager;
import main.model.user.User;
import main.model.user.UserType;
import main.utils.exception.PasswordIncorrectException;
import main.utils.exception.ModelNotFoundException;
import main.utils.exception.PageBackException;
import main.utils.ui.ChangePage;

import java.util.Scanner;

/**
 * The LoginUI class provides a user interface (UI) for the user to login.
 */
public class LoginUI {
    /**
     * Displays a login page.
     *
     * @throws PageBackException if the user chooses to go back to the previous
     *                           page.
     */
    public static void login() throws PageBackException {
        // Change the page for a cleaner UI
        ChangePage.changePage();
        
        // Prompt user to select their domain
        UserType domain = AttributeGetter.getDomain();
        
        // Prompt user to enter their UserID; if empty, allow the user to recover their UserID
        String userID = AttributeGetter.getUserID();
        if (userID.equals("")) {
            try {
                ForgetUserID.forgotUserID();
            } catch (PageBackException e) {
                // If the user chooses to go back, recall the login method
                login();
            }
        }

        // Prompt user to enter their password
        String password = AttributeGetter.getPassword();

        try {
            // Attempt to login with the provided credentials
            User user = AccountManager.login(domain, userID, password);
            
            // Redirect to the appropriate main page based on user type
            switch (domain) {
                case STUDENT -> StudentMainPage.studentMainPage(user);
                case STAFF -> StaffMainPage.staffMainPage(user); // to change
                default -> throw new IllegalStateException("Unexpected value: " + domain);
            }
            
            // Return from the method after successful login
            return;
        } catch (PasswordIncorrectException e) {
            System.out.println("Password incorrect.");
        } catch (ModelNotFoundException e) {
            System.out.println("User not found.");
        }
        
        // Handle unsuccessful login attempts
        System.out.println("Enter [b] to go back, or any other key to try again.");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        if (choice.equals("b")) {
            // If the user chooses to go back, throw an exception
            throw new PageBackException();
        } else {
            // If the user wants to try again, recursively call the login method
            System.out.println("Please try again.");
            login();
        }
    }
}

