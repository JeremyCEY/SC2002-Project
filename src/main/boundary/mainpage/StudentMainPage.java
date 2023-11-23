package main.boundary.mainpage;

import main.boundary.account.ChangeAccountPassword;
import main.boundary.account.Logout;
import main.boundary.account.ViewUserProfile;
import main.boundary.modelviewer.CampViewer;
import main.controller.request.StudentManager;
import main.model.user.*;
import main.repository.user.StudentRepository;
import main.utils.exception.ModelNotFoundException;
import main.utils.exception.PageBackException;
import main.utils.iocontrol.IntGetter;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.ChangePage;


import java.util.Scanner;

/**
 * This is a Java class that represents the main page of a student in a system or application. It contains several methods for displaying different functionalities of the student main page, such as viewing the user profile, changing the password, viewing project lists, registering/deregistering for projects, changing project title, viewing project history and status, and logging out.
 */
public class StudentMainPage {
    /**
     * This method displays the main page of a student. It takes a User object as a parameter and displays a menu of options for the student to choose from. The user's choice is then processed using a switch statement, which calls different methods based on the choice.
     *
     * @param user The user object of the student.
     * @throws ModelNotFoundException
     */
    public static void studentMainPage(User user) throws ModelNotFoundException {
        if (user instanceof Student student) {
            ChangePage.changePage();
            System.out.println(BoundaryStrings.separator);
            System.out.println("Welcome to Student Main Page");
            System.out.println("Hello, " + student.getUserName() + "!");
            System.out.println();
            System.out.println("\t1. View my profile");
            System.out.println("\t2. Change my password");
            System.out.println("\t3. View all Camps");
            System.out.println("\t4. View Registered Camps");
            System.out.println("\t5. Camp Attendee Registration");
            System.out.println("\t6. Withdraw from Camp");
            System.out.println("\t7. Camp Committee Registration");
            System.out.println("\t8. Submit Enquiry");
            System.out.println("\t9. View Enquiry (Edit/Delete)");//edit and delete option inside
            System.out.println("\t10. Submit Suggestion");
            System.out.println("\t11. View Suggestion (Edit/Delete)");//edit and delete option inside
            System.out.println("\t12. Logout");
            System.out.println(BoundaryStrings.separator);

            System.out.println();
            System.out.print("Please enter your choice: ");

            int choice = IntGetter.readInt();

            try {
                student = StudentRepository.getInstance().getByID(student.getID());
            } catch (ModelNotFoundException e) {
                e.printStackTrace();
            }

            try {
                switch (choice) {
                    case 1 -> ViewUserProfile.viewUserProfilePage(student);
                    case 2 -> ChangeAccountPassword.changePassword(UserType.STUDENT, student.getID());
                    case 3 -> CampViewer.viewVisibleFacultyCampList(student);
                    case 4 -> CampViewer.viewStudentCamps(student);
                    case 5 -> StudentManager.registerCampAttendee(student);
                    case 6 -> StudentManager.withdrawCampAttendee(student);
                    case 7 -> StudentManager.registerCampCommittee(student);
                    case 8 -> StudentManager.submitEnquiry(student);
                    case 9 -> StudentManager.viewEnquiry(student);
                    case 10 -> StudentManager.submitSuggestion(student);
                    case 11 -> StudentManager.viewSuggestion(student);
                    case 12 -> Logout.logout();
                    default -> {
                        System.out.println("Invalid choice. Please press enter to try again.");
                        new Scanner(System.in).nextLine();
                        throw new PageBackException();
                    }
                }
            } 
            // catch (PageBackException | ModelNotFoundException e) {
            //     StudentMainPage.studentMainPage(student);
            // }
            catch (PageBackException e) {
                 StudentMainPage.studentMainPage(student);
            }


        } else {
            throw new IllegalArgumentException("User is not a student.");
        }
    }


    // /**
    //  * This private method is called to get the camp ID from the user. It prompts the user to enter the camp ID and returns it as a string. If the user wants to go back, a PageBackException is thrown.
    //  *
    //  * @return the camp ID.
    //  * @throws PageBackException if the user wants to go back.
    //  */
    // private static String getCampID() throws PageBackException {
    //     System.out.println("Please enter the project ID: ");
    //     String campID = new Scanner(System.in).nextLine();
    //     if (CampRepository.getInstance().contains(campID)) {
    //         System.out.println("Camp found.");
    //         System.out.println("Here is the camp information: ");
    //         try {
    //             Camp camp = CampRepository.getInstance().getByID(campID);
    //             ModelViewer.displaySingleDisplayable(camp);
    //         } catch (ModelNotFoundException e) {
    //             //e.printStackTrace();
    //         }
    //     } else {
    //         System.out.println("Camp not found.");
    //         System.out.println("Press Enter to go back.");
    //         new Scanner(System.in).nextLine();
    //         throw new PageBackException();
    //     }
    //     return campID;
    // }
    

    
}
