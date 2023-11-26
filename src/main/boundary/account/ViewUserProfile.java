/*
 * @Author: tyt1130 tangyutong0306@gmail.com
 * @Date: 2023-11-09 11:03:36
 * @LastEditors: tyt1130 tangyutong0306@gmail.com
 * @LastEditTime: 2023-11-26 13:22:48
 * @FilePath: \SC2002-Project\src\main\boundary\account\ViewUserProfile.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
/**
 * The main.boundary.account package contains classes responsible for providing user interfaces
 * related to user account actions, including viewing user profiles.
 */
package main.boundary.account;

import main.model.user.Student;
import main.model.user.User;
import main.utils.exception.PageBackException;
import main.utils.parameters.EmptyID;
import main.utils.ui.ChangePage;
import main.utils.ui.UserTypeGetter;

import java.util.Scanner;

/**
 * The ViewUserProfile class provides a user interface (UI) for the user to view his/her profile.
 */
public class ViewUserProfile {
    /**
     * Displays the user's profile.
     *
     * @param user the user whose profile is to be displayed.
     */
    public static void viewUserProfile(User user) {
        // Determine the user type for proper display
        String userType = UserTypeGetter.getUserTypeInCamelCase(user);
        int points = 0;
        
        // Display profile information based on user type
        if ("Student".equals(userType)) {
            String type = "x";

            if (user instanceof Student) {
                Student student = (Student) user;
                points = student.getPoints();
                if (EmptyID.isEmptyID(student.getACamps()) && EmptyID.isEmptyID(student.getCCamps())) {
                    type = "No camp registrations";
                } else if (!EmptyID.isEmptyID(student.getACamps()) && EmptyID.isEmptyID(student.getCCamps())) {
                    type = "Camp Attendee";
                } else if (EmptyID.isEmptyID(student.getACamps()) && !EmptyID.isEmptyID(student.getCCamps())) {
                    type = "Camp Committee";
                } else if (!EmptyID.isEmptyID(student.getACamps()) && !EmptyID.isEmptyID(student.getCCamps())) {
                    type = "Camp Attendee & Committee";
                }
            }

            System.out.println("Welcome to View " + userType + " Profile");
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-15s | %-30s | %-15s | %-26s | %-11s |\n", "Name", "Email", userType + " ID", "Role",
                    "Points");
            System.out.println(
                    "|-----------------|--------------------------------|-----------------|----------------------------|-------------|");
            System.out.printf("| %-15s | %-30s | %-15s | %-26s | %-11s |\n", user.getUserName(), user.getEmail(),
                    user.getID(), type, points);
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------");
        }
        
        // Display staff profile information
        if ("Staff".equals(userType)) {
            System.out.println("Welcome to View " + userType + " Profile");
            System.out.println("----------------------------------------------------------------------");
            System.out.printf("| %-15s | %-30s | %-15s |\n", "Name", "Email", userType + " ID");
            System.out.println("|-----------------|--------------------------------|-----------------|");
            System.out.printf("| %-15s | %-30s | %-15s |\n", user.getUserName(), user.getEmail(), user.getID());
            System.out.println("----------------------------------------------------------------------");
        }
    }

    /**
     * Displays the user's profile.
     *
     * @param user the user whose profile is to be displayed.
     * @throws PageBackException if the user chooses to go back to the previous
     *                           page.
     */
    public static void viewUserProfilePage(User user) throws PageBackException {
        // Change the page for a cleaner UI and display the user's profile
        ChangePage.changePage();
        viewUserProfile(user);
        
        // Prompt user to go back
        System.out.println("Press enter to go back.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        
        // Throw an exception to indicate the user wants to go back
        throw new PageBackException();
    }
}
