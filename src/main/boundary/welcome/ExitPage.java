/**
 * The main.boundary.welcome package contains the ExitPage class, which provides a user interface (UI)
 * for the user to exit the system.
 */
package main.boundary.welcome;

import main.utils.ui.ChangePage;

/**
 * The ExitPage class provides a UI for the user to exit the system.
 */
public class ExitPage {
    /**
     * Displays an exit page, allowing the user to gracefully exit the system.
     * This method prints a thank-you message and terminates the program.
     */
    public static void exitPage() {
        ChangePage.changePage();
        System.out.println("Thank you!");
        System.exit(0);
    }
}

