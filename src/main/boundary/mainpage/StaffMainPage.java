package main.boundary.mainpage;

import main.boundary.account.ChangeAccountPassword;
import main.boundary.account.Logout;
import main.boundary.account.ViewUserProfile;
import main.boundary.modelviewer.ModelViewer;
import main.boundary.modelviewer.CampViewer;
import main.controller.request.StaffManager;
import main.controller.camp.CampManager;
import main.controller.request.RequestManager;
//import main.model.request.Request;
//import main.model.request.RequestStatus;
//import main.model.request.RequestType;
import main.model.camp.Camp;
import main.model.request.Request;
import main.model.request.RequestStatus;
import main.model.request.RequestType;
import main.model.user.Faculty;
import main.model.user.Staff;
import main.model.user.User;
import main.model.user.UserType;
import main.controller.camp.CampManager;
import main.repository.camp.CampRepository;
import main.repository.user.StaffRepository;
import main.utils.exception.ModelAlreadyExistsException;
import main.utils.exception.ModelNotFoundException;
import main.utils.exception.PageBackException;
//import main.utils.exception.SupervisorStudentsLimitExceedException;
import main.utils.iocontrol.IntGetter;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.ChangePage;

import java.util.Objects;
import java.util.Scanner;

//import static main.boundary.modelviewer.ProjectViewer.generateProjectDetails;

/**
 * This class provides a user interface for coordinators to view their main page.
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
            System.out.println("\t1. View my profile (Feature Complete)");
            System.out.println("\t2. Change my password (Feature Complete)");
            System.out.println("\t3. View all camps");
            System.out.println("\t4. Create new camp");
            System.out.println("\t5. Edit camp");//view separate menu list of camps staff created
            System.out.println("\t6. Delete camp");
            System.out.println("\t5. View all requests' history and status");//view and reply to enquires and approve suggestions
            // if (CoordinatorManager.getAllPendingRequestsCoordinatorCanManage().size() > 0) {
            //     System.out.println("\t6. Accept or reject requests " + BoundaryStrings.NEW);
            // } else {
            //     System.out.println("\t6. Accept or reject requests");
            // }
            System.out.println("\t7. Generate camp report");
            System.out.println("\t7. Generate camp committee performance report");   
            System.out.println("\t9. Logout");
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
                    case 1 -> ViewUserProfile.viewUserProfilePage(staff);
                    case 2 -> ChangeAccountPassword.changePassword(UserType.STAFF, user.getID());
                    case 3 -> CampViewer.viewAllCamp();
                    case 4 -> staffCreateCamp(staff);
                    //case 5 -> staffEditCamp(staff);
                    //case 6 -> staffDeleteCamp(staff);
                    //case 7 -> acceptOrRejectRequest();
                    //case 7 -> generateProjectDetails();
                    //case 8 -> Logout.logout();
                    default -> {
                        System.out.println("Invalid choice. Please press <enter> to try again.");
                        new Scanner(System.in).nextLine();
                        throw new PageBackException();
                    }
                }
            } catch (PageBackException e) {
                StaffMainPage.staffMainPage(staff);
            }

        } else {
            throw new IllegalArgumentException("User is not a staff.");
        }
    }

    /**
     * Displays the pending requests for the staff.
     *
     * @throws PageBackException if the user chooses to go back to the previous page.
     */
    public static void viewPendingRequests() throws PageBackException {
        ChangePage.changePage();
        System.out.println(BoundaryStrings.separator);
        System.out.println("View Pending Requests");
        System.out.println();
        System.out.println("Here are the pending requests:");
        System.out.println();
        //ModelViewer.displayListOfDisplayable(CoordinatorManager.getPendingRequests());
        System.out.println();
        System.out.println("Press enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * Displays all requests for the staff.
     *
     * @throws PageBackException if the user chooses to go back to the previous page.
     */
     private static void viewAllRequests() throws PageBackException {
         ChangePage.changePage();
         System.out.println(BoundaryStrings.separator);
         System.out.println("View All Requests");
         System.out.println();
         System.out.println("Here are all the requests:");
         System.out.println();
         //ModelViewer.displayListOfDisplayable(Staff.getAllRequests());
         System.out.println();
         System.out.println("Press enter to go back.");
         new Scanner(System.in).nextLine();
         throw new PageBackException();
     }

    /**
     * Allows the staff to accept or reject requests.
     *
     * @throws PageBackException if the user chooses to go back to the previous page.
     */
    private static void acceptOrRejectRequest() throws PageBackException {
        ChangePage.changePage();
//        System.out.println(BoundaryStrings.separator);
        System.out.println("Accept or Reject Requests");
        ModelViewer.displayListOfDisplayable(CoordinatorManager.getPendingRequests());
        System.out.println("Please enter the ID of the request you want to accept or reject. (Enter 0 to go back.)");
//        System.out.println(BoundaryStrings.separator);
        System.out.print("Please enter your choice: ");

        String requestID = new Scanner(System.in).nextLine();
        if (requestID.equals("0")) {
            throw new PageBackException();
        }

        Request request;
        try {
            request = RequestManager.getRequest(requestID);

            if (Objects.isNull(request)) {
                throw new ModelNotFoundException();
            }

            if (request.getStatus() != RequestStatus.PENDING) {
                System.out.println("Request is not pending.");
                System.out.println("Press enter to go back, or enter [0] to try again.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("0")) {
                    acceptOrRejectRequest();
                }
                throw new PageBackException();
            }
        } catch (ModelNotFoundException e) {
            System.out.println("Request not found.");
            System.out.println("Press enter to go back, or enter [0] to try again.");
            String choice = new Scanner(System.in).nextLine();
            if (choice.equals("0")) {
                acceptOrRejectRequest();
            }
            throw new PageBackException();
        }

        if (request.getRequestType() == RequestType.STUDENT_CHANGE_TITLE) {
            System.out.println("You do not have permission to accept or reject this request.");
            System.out.println("Press enter to go back.");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }

        ChangePage.changePage();

        System.out.println("Accept or Reject Requests");

        ModelViewer.displaySingleDisplayable(request);

        System.out.println("\t1. Approve");
        System.out.println("\t2. Reject");
        System.out.println("\t3. Go back");
        System.out.println("Please enter your choice: ");
        int choice = IntGetter.readInt();
        switch (choice) {
            case 1 -> {
                try {
                    RequestManager.approveRequest(requestID);
                } catch (SupervisorStudentsLimitExceedException e) {
                    System.out.println("Supervisor's students limit exceeded.");
                    System.out.println("Press enter to go back, or enter [0] to try again.");
                    String choice2 = new Scanner(System.in).nextLine();
                    if (choice2.equals("0")) {
                        acceptOrRejectRequest();
                    }
                    throw new PageBackException();
                }
            }
            case 2 -> {
                RequestManager.rejectRequest(requestID);
            }
            case 3 -> {
                acceptOrRejectRequest();
                throw new PageBackException();
            }
            default -> {
                System.out.println("Invalid choice. Please try again.");
                System.out.println("Press enter to go back, or enter [0] to try again.");
                String choice2 = new Scanner(System.in).nextLine();
                if (choice2.equals("0")) {
                    acceptOrRejectRequest();
                }
                throw new PageBackException();
            }
        }
        ChangePage.changePage();
        try {
            request = RequestManager.getRequest(requestID);
        } catch (ModelNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Here is the updated request:");
        ModelViewer.displaySingleDisplayable(request);
        System.out.println("Press enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * This method allows the supervisor to create a project by entering the required details such as project title, project description, and project capacity.
     *
     * @param supervisor the supervisor.
     * @throws PageBackException if the user wants to go back to the previous page.
     */
    private static void staffCreateCamp(Staff staff) throws PageBackException {
        ChangePage.changePage();
        System.out.println("Creating a camp....");
        System.out.println("Please enter camp name:");
        String campName = new Scanner(System.in).nextLine();
        System.out.println("Please enter the camp dates:");
        String campDates = new Scanner(System.in).nextLine();
        System.out.println("Please enter registration closing date;");
        String registrationClosingDate = new Scanner(System.in).nextLine();
        System.out.println("Please enter camp location;");
        String location = new Scanner(System.in).nextLine();
        int filledSlots = 0;
        System.out.println("Please enter number of attendees;");
        int totalSlots = new Scanner(System.in).nextInt();
        int filledCampCommSlots = 0;
        System.out.println("Please enter number of camp committee members;");
        int campCommlots = new Scanner(System.in).nextInt();
        System.out.println("Please enter camp description(5 words);");
        String description = new Scanner(System.in).nextLine();
        
        String staffID = staff.getID();
        System.out.println("Please set visibility (1 for true, 2 for false):");
        
        String visibility = "false";
        do {
            System.out.println("Enter 1 for true or 2 for false:");
            int number = new Scanner(System.in).nextInt();

            switch (number) {
                case 1:
                    visibility = "true";
                    System.out.println("Visibility set to true.");
                    break;
                case 2:
                    visibility = "false";
                    System.out.println("Visibility set to false.");
                    break;
                default:
                    System.out.println("Invalid input. Please enter 1 for true or 2 for false.");
            }
        } while (!visibility.equals("true") && !visibility.equals("false"));
        

        System.out.println("Please select school that camp is open to:");
        String userInput = new Scanner(System.in).nextLine();
        Faculty faculty;

        switch (userInput) {
            case "ADM" -> {
                faculty = Faculty.ADM;
                break;
            }
            case "EEE" -> {
                faculty = Faculty.EEE;
                break;
            }
            case "NBS" -> {
                faculty = Faculty.NBS;
                break;
            }
            case "NTU" -> {
                faculty = Faculty.NTU;
                break;
            }
            case "SCSE" -> {
                faculty = Faculty.SCSE;
                break;
            }
            case "SSS" -> {
                faculty = Faculty.SSS;
                break;
            }
            default -> {
                faculty = Faculty.NTU;
                System.out.println("Invalid input. school has been set to NTU.");
                return; // or handle the invalid input accordingly
            }
        }

        System.out.println("Selected faculty: " + faculty);

        Camp p;
        
        
        try {
            p = CampManager.createcamp(campName, campDates, registrationClosingDate, faculty, location, filledSlots, 
            totalSlots, filledCampCommSlots, campCommlots, description, staffID, visibility);
        } catch (ModelAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
        System.out.println("The project details are as follows:");
        ModelViewer.displaySingleDisplayable(p);
        System.out.println("Are you sure you want to create this project? (Y/N)");
        String input = new Scanner(System.in).nextLine();
        if (!input.equalsIgnoreCase("Y")) {
            System.out.println("Project creation cancelled!");
            try {
                CampRepository.getInstance().remove(p.getID());
                //CampManager.updateCampsStatus();
            } catch (ModelNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Enter enter to continue");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }
        System.out.println("Project created successfully!");
        System.out.println("Enter enter to continue");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }



}
