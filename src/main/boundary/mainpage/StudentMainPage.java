/**
 * This class represents the main page of a student in the system or application.
 * It provides various methods for displaying functionalities such as viewing the user profile,
 * changing the password, viewing and registering for camps, managing inquiries and suggestions,
 * and logging out. The class handles user input and directs the flow based on the selected option.
 */
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
 * This is a Java class that represents the main page of a student in a system
 * or application. It contains several methods for displaying different
 * functionalities of the student main page, such as viewing the user profile,
 * changing the password, viewing project lists, registering/deregistering for
 * projects, changing project title, viewing project history and status, and
 * logging out.
 */
public class StudentMainPage {
    /**
     * This method displays the main page of a student. It takes a User object as a
     * parameter and displays a menu of options for the student to choose from. The
     * user's choice is then processed using a switch statement, which calls
     * different methods based on the choice.
     *
     * @param user The user object of the student.
     * @throws ModelNotFoundException
     */
    public static void studentMainPage(User user) {
        if (user instanceof Student student) {
            if (!student.getCCamps().equals("null")) {
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
                System.out.println("\t8. Submit new Enquiry");
                System.out.println("\t9. View my Enquiries (Edit/Delete)");
                System.out.println("\t10. Logout");
                System.out.println();
                System.out.println("Camp Committee Menu");
                System.out.println();
                System.out.println("\t11. Submit new Suggestion");
                System.out.println("\t12. View my Suggestions (Edit/Delete)");
                System.out.println("\t13. View & Reply Enquiries");
                System.out.println("\t14. Generate Camp List");

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
                        case 10 -> Logout.logout();
                        case 11 -> StudentManager.submitSuggestion(student);
                        case 12 -> StudentManager.viewSuggestion(student);
                        case 13 -> StudentManager.commViewPendingEnquiries(student);
                        case 14 -> StudentManager.generateCampList(student);
                        default -> {
                            System.out.println("Invalid choice. Please press enter to try again.");
                            new Scanner(System.in).nextLine();
                            throw new PageBackException();
                        }
                    }
                } catch (PageBackException | ModelNotFoundException e) {
                    StudentMainPage.studentMainPage(student);
                }
            } else {
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
                System.out.println("\t8. Submit new Enquiry");
                System.out.println("\t9. View my Enquiries (Edit/Delete)");
                System.out.println("\t10. Logout");
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
                        case 10 -> Logout.logout();
                        default -> {
                            System.out.println("Invalid choice. Please press enter to try again.");
                            new Scanner(System.in).nextLine();
                            throw new PageBackException();
                        }
                    }
                } catch (PageBackException | ModelNotFoundException e) {
                    StudentMainPage.studentMainPage(student);
                }
            }
        }

        else {
            throw new IllegalArgumentException("User is not a student.");
        }
    }

}
