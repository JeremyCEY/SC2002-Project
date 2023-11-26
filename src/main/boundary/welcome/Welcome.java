/**
 * The main.boundary.welcome package contains the Welcome class, which provides a user interface (UI)
 * for the user to enter the system, including options for login, forgetting UserID, and exiting the system.
 */
package main.boundary.welcome;

import main.boundary.account.ForgetUserID;
import main.boundary.account.LoginUI;
import main.utils.exception.PageBackException;
import main.utils.iocontrol.IntGetter;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.ChangePage;

/**
 * The Welcome class provides a UI for the user to enter the system.
 * It displays a welcome page with options for login, forgetting UserID, and exiting the system.
 */
public class Welcome {
    /**
     * Displays a welcome page, prompting the user to choose between login, forgetting UserID, or exiting the system.
     * Continues to prompt the user until a valid choice is made.
     *
     * @throws PageBackException if the user chooses to go back to the previous page.
     */
    public static void welcome() {
        ChangePage.changePage();
        System.out.println("Welcome to NTU's Camp Management System!\n");
        System.out.println(BoundaryStrings.separator);
        System.out.println("Please enter your choice to continue.");
        System.out.println("\t1. Login");
        System.out.println("\t2. Forget UserID");
        System.out.println("\t3. Exit");
        System.out.print("Your choice (1-3): ");
        try {
            while (true) {
                int choice = IntGetter.readInt();
                switch (choice) {
                    case 1:
                        LoginUI.login();
                        break;
                    case 2:
                        ForgetUserID.forgotUserID();
                        break;
                    case 3:
                        ExitPage.exitPage();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        } catch (PageBackException e) {
            welcome();
        }
    }
}

