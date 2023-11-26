/**
 * This package contains classes that represent user interfaces for various main pages
 * within the application. These classes provide functionality for different user roles,
 * such as students, staff, or coordinators, to interact with the system and perform
 * relevant actions. Each class is designed to present a clear and organized user interface,
 * facilitating user navigation and interaction.
 */
package main.boundary.mainpage;

import main.boundary.account.ChangeAccountPassword;
import main.boundary.account.Logout;
import main.boundary.account.ViewUserProfile;
import main.boundary.modelviewer.CampViewer;
import main.boundary.modelviewer.ModelViewer;
import main.controller.request.StaffManager;
import main.controller.camp.CampManager;
import main.controller.request.RequestManager;
import main.model.camp.Camp;
import main.model.request.Suggestion;
import main.model.request.RequestStatus;
import main.model.user.Faculty;
import main.model.user.Staff;
import main.model.user.Student;
import main.model.user.User;
import main.model.user.UserType;
import main.model.request.Enquiry;
import main.model.request.Suggestion;
import main.repository.request.EnquiryRepository;
import main.repository.request.SuggestionRepository;
import main.repository.user.StaffRepository;
import main.repository.user.StudentRepository;
import main.repository.camp.CampRepository;
import main.utils.exception.ModelAlreadyExistsException;
import main.utils.exception.ModelNotFoundException;
import main.utils.exception.PageBackException;
import main.utils.iocontrol.CSVWritter;
import main.utils.config.Location;
import main.utils.iocontrol.IntGetter;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.ChangePage;
import main.controller.request.RequestManager;
import main.controller.request.StudentManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Scanner;
import static main.utils.config.Location.RESOURCE_LOCATION;

/**
 * This class provides a user interface for {@link Staff}s to view their main
 * page.
 */
public class StaffMainPage {

    /**
     * Displays the staff main page for the given user.
     *
     * @param user the user whose profile is to be displayed.
     */
    public static void staffMainPage(User user) {
        if (user instanceof Staff staff) {
            ChangePage.changePage();
            System.out.println(BoundaryStrings.separator);
            System.out.println("Welcome to Staff Main Page");
            System.out.println("Hello, " + user.getUserName() + "!");
            System.out.println();
            System.out.println("\t1. View my profile");
            System.out.println("\t2. Change my password");
            System.out.println("\t3. View all Camps");
            System.out.println("\t4. Create Camp");
            System.out.println("\t5. View and edit my Camps");
            System.out.println("\t6. Delete Camp");
            System.out.println("\t7. View and Reply Pending Enquiries");
            System.out.println("\t8. View and Handle Pending Suggestions");
            System.out.println("\t9. Generate Reports");
            System.out.println("\t10. Logout");
            System.out.println(BoundaryStrings.separator);

            System.out.println();
            System.out.print("Please enter your choice: ");

            int choice = IntGetter.readInt();

            try {
                staff = StaffRepository.getInstance().getByID(staff.getID());
            } catch (ModelNotFoundException e) {
                e.printStackTrace();
            }

            try {
                switch (choice) {
                    case 0 -> Logout.logout();
                    case 1 -> ViewUserProfile.viewUserProfilePage(staff);
                    case 2 -> ChangeAccountPassword.changePassword(UserType.STAFF, user.getID());
                    case 3 -> CampViewer.selectCampTypeAndDisplay(staff);
                    case 4 -> StaffManager.createCamp(user);
                    case 5 -> StaffManager.editExistingCamp(user);
                    case 6 -> StaffManager.deleteExistingCamp(user);
                    case 7 -> StaffManager.viewAndReplyPendingEnquiries(user);
                    case 8 -> StaffManager.viewAndHandlePendingSuggestions(user);
                    case 9 -> StaffManager.generateReports(user);
                    case 10 -> Logout.logout();
                    default -> {
                        System.out.println("Invalid choice. Please press <enter> to try again.");
                        new Scanner(System.in).nextLine();
                        throw new PageBackException();
                    }
                }
            } catch (PageBackException | ModelNotFoundException e) {
                if (e instanceof ModelNotFoundException) {
                    System.out.println("ID not found, going back to the main menu.");
                }
                StaffMainPage.staffMainPage(staff);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            throw new IllegalArgumentException("User is not a staff.");
        }
    }


}

