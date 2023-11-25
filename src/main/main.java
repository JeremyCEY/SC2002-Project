/** 
 * The main class is the entry point of the program.
 * It initializes the UI and starts the program by calling the start method of the UIEntry class.
 */

package main;

import java.util.*;

import main.boundary.UIEntry;
import main.boundary.mainpage.StaffMainPage;
import main.boundary.mainpage.StudentMainPage;
import main.controller.account.AccountManager;
import main.controller.camp.CampManager;
import main.model.camp.Camp;
import main.model.user.User;
import main.model.user.UserType;
import main.utils.exception.ModelNotFoundException;
import main.utils.exception.PasswordIncorrectException;

/**
 * The Main class is the entry point of the program.
 */
public class main {
    /**
     * The main method is the entry point of the program.
     * It initializes the UI and starts the program by invoking the start method of the UIEntry class.
     *
     * @param args The command line arguments passed to the program (not used in this implementation).
     */
    public static void main(String[] args) {
        // For testing the app from login
        UIEntry.start();
 
        // Skip to student page
        //AccountManager.loadUsers();
        //CampManager.loadcamps();
        // try {
        //     User user = AccountManager.login(UserType.STUDENT, "YCHERN", "password");
        //     StudentMainPage.studentMainPage(user);
        //     return;
        // } catch (PasswordIncorrectException e) {
        //     System.out.println("Password incorrect.");
        // } catch (ModelNotFoundException e) {
        //     System.out.println("User not found.");
        // }

        // Skip to staff page
        // AccountManager.loadUsers();
        // try {
        //     User user = AccountManager.login(UserType.STUDENT, "YCHERN", "password");
        //     StudentMainPage.studentMainPage(user);
        //     return;
        // } catch (PasswordIncorrectException e) {
        //     System.out.println("Password incorrect.");
        // } catch (ModelNotFoundException e) {
        //     System.out.println("User not found.");
        // }
        
        //AccountManager.loadUsers();
        //CampManager.loadcamps();
        //CampManager.createcamp("SOP", "HUKUMAR");
        // System.out.println(CampManager.getAllcamps().get(0));
        // List<Camp> camps = CampManager.getAllcamps();
        // for (int i = 0; i < camps.size(); i++) {
        //     System.out.println(camps.get(i).getCampName());
        // }
    }
}