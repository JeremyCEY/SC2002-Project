/**
 * The main.boundary.account package contains classes responsible for providing user interfaces
 * related to user account management, such as retrieving forgotten UserIDs.
 */
package main.boundary.account;

import main.controller.account.AccountManager;
import main.model.user.User;
import main.utils.exception.PageBackException;
import main.utils.ui.ChangePage;

import java.io.IOException;
import java.util.List;

import static main.controller.account.user.UserDomainGetter.getUserDomain;

/**
 * The ForgetUserID class provides a user interface (UI) for retrieving the UserID
 * associated with a given username in case the user forgot it.
 */
public class ForgetUserID {
    /**
     * Displays a list of UserIDs associated with the given username and their
     * corresponding user domains.
     *
     * @throws PageBackException if the user chooses to go back to the previous
     *                           page.
     */
    public static void forgotUserID() throws PageBackException {
        // Change the page for a cleaner UI
        ChangePage.changePage();
        
        // Prompt user to enter the username
        String name = AttributeGetter.getUserName();
        
        // Retrieve a list of users with the given username
        List<User> users = AccountManager.getUsersByUserName(name);
        
        // Display information based on the result
        if (users.isEmpty()) {
            System.out.println("No user found with name " + name + ".");
        } else {
            System.out.println("Found " + users.size() + " user(s) with name " + name + ".");
            System.out.println("The list of UserID associated with " + name + " is:");
            System.out.println("----------------------------------------");
            if (users.isEmpty()) {
                System.out.println("| No user IDs found for " + name + " |");
            } else {
                System.out.println("| User ID          | User Domain       |");
                System.out.println("|--------------------------------------|");
                for (User user : users) {
                    System.out.printf("| %-17s| %-18s|\n", user.getID(), getUserDomain(user));
                }
            }
            System.out.println("----------------------------------------");
        }
        
        // Prompt user to go back
        System.out.println("Press Enter key to go back.");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Throw an exception to indicate the user wants to go back
        throw new PageBackException();
    }
}
