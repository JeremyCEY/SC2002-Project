package main.boundary.account;

import main.model.user.Student;
import main.model.user.User;
import main.utils.exception.PageBackException;
import main.utils.parameters.EmptyID;
import main.utils.ui.ChangePage;
import main.utils.ui.UserTypeGetter;

import java.util.Scanner;

/**
 * This class provides a UI for the user to view his/her profile.
 */
public class ViewUserProfile {
    /**
     * Displays the user's profile.
     *
     * @param user the user whose profile is to be displayed.
     */
    public static void viewUserProfile(User user) {
        String userType = UserTypeGetter.getUserTypeInCamelCase(user);
        int points = 0;
        if ("Student".equals(userType)) { // I know this is a weird way to do string compare in java LMAOOOO // delete
                                          // this later
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
                    "┌---------------------------------------------------------------------------------------------------------------┐");
            System.out.printf("| %-15s | %-30s | %-15s | %-26s | %-11s |\n", "Name", "Email", userType + " ID", "Role",
                    "Points");
            System.out.println(
                    "|-----------------|--------------------------------|-----------------|----------------------------|-------------|");
            System.out.printf("| %-15s | %-30s | %-15s | %-26s | %-11s |\n", user.getUserName(), user.getEmail(),
                    user.getID(), type, points);
            System.out.println(
                    "└---------------------------------------------------------------------------------------------------------------┘");
        }
        if ("Staff".equals(userType)) {
            System.out.println("Welcome to View " + userType + " Profile");
            System.out.println("┌--------------------------------------------------------------------┐");
            System.out.printf("| %-15s | %-30s | %-15s |\n", "Name", "Email", userType + " ID");
            System.out.println("|-----------------|--------------------------------|-----------------|");
            System.out.printf("| %-15s | %-30s | %-15s |\n", user.getUserName(), user.getEmail(), user.getID());
            System.out.println("└--------------------------------------------------------------------┘");
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
        ChangePage.changePage();
        viewUserProfile(user);
        System.out.println("Press enter to go back.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        throw new PageBackException();
    }
}