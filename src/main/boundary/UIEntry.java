/**
 * The main.boundary package contains the UIEntry class, which serves as the entry point
 * of the application. It is responsible for checking if the application is being run for
 * the first time and starting the application accordingly.
 */
package main.boundary;

import main.boundary.welcome.Welcome;
import main.controller.account.AccountManager;
import main.controller.camp.CampManager;

/**
 * The UIEntry class is the entry point of the application.
 * It checks if the application is being run for the first time and starts the application.
 */
public class UIEntry {
    /**
     * Checks if the application is being run for the first time.
     *
     * @return true if the application is being run for the first time, false otherwise.
     */
    private static boolean firstStart() {
        return AccountManager.repositoryIsEmpty() && CampManager.repositoryIsEmpty();
    }

    /**
     * Starts the application. If the application is being run for the first time,
     * it loads the default users and projects. Then it displays the welcome page.
     */
    public static void start() {
        if (firstStart()) {
            AccountManager.loadUsers();
            CampManager.loadCamps();
        }
        Welcome.welcome();
    }
}

