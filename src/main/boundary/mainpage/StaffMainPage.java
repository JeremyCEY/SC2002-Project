
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import main.boundary.account.ChangeAccountPassword;
import main.boundary.account.Logout;
import main.boundary.account.ViewUserProfile;
import main.boundary.modelviewer.CampViewer;
import main.boundary.modelviewer.ModelViewer;
//import main.boundary.modelviewer.ProjectViewer;
import main.controller.request.StaffManager;
import main.controller.request.RequestManager;
//import main.model.request.Request;
//import main.model.request.RequestStatus;
//import main.model.request.RequestType;
import main.model.user.Staff;
import main.model.user.Student;
import main.model.user.User;
import main.model.user.UserType;
import main.repository.user.StaffRepository;
import main.utils.exception.ModelNotFoundException;
import main.utils.exception.PageBackException;
import main.utils.iocontrol.CSVWritter;
import main.utils.iocontrol.IntGetter;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.ChangePage;
import java.util.Scanner;

/**
 * This class provides a user interface for {@link Staff}s to view their main page.
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
            System.out.println("\t3. View all projects");
            System.out.println("\t4. View pending requests");
            System.out.println("\t5. View all requests' history and status");
            // if (CoordinatorManager.getAllPendingRequestsCoordinatorCanManage().size() > 0) {
            //     System.out.println("\t6. Accept or reject requests " + BoundaryStrings.NEW);
            // } else {
            //     System.out.println("\t6. Accept or reject requests");
            // }
            System.out.println("\t7. Generate project details");
            System.out.println("\t8. Logout");
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
                    //case 3 -> ProjectViewer.viewAllProject();
                    //case 4 -> viewPendingRequests();
                    //case 5 -> viewAllRequests();
                    //case 6 -> acceptOrRejectRequest();
                    //case 7 -> generateProjectDetails();
                    //case 8 -> Logout.logout();
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

    private static void createCamp(User user) throws PageBackException {
        ChangePage.changePage();
        System.out.println(BoundaryStrings.separator);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a camp Name:");
        String name = scanner.nextLine();
        System.out.println("Enter a camp Date, like 20120304:");
        String date = scanner.nextLine();
        System.out.println("Enter a camp Registration Closing Date, like 20120304:");
        String registrationClosingDateDate = scanner.nextLine();
        System.out.println("Enter a camp Faculty Open To:");
        Faculty faculty = Faculty.valueOf(scanner.nextLine());
        System.out.println("Enter a camp Location:");
        String location = scanner.nextLine();
        System.out.println("Enter a camp Total Slots:");
        int totalSlots = scanner.nextInt();
        System.out.println("Enter a camp Description:");
        String desc = scanner.next();
        Camp camp =
            CampManager.createCamp(
                name, date, registrationClosingDateDate, faculty, location, 0, totalSlots,
                0, desc, user.getID(), "True");
        System.out.println("Successfully created a new camp:");
        CampViewer.viewCamp(camp);
        System.out.println(BoundaryStrings.separator);
        System.out.println();
        System.out.println("Press enter to go back.");
        scanner.nextLine();
        throw new PageBackException();
    }

    private static void editExistingCamp(User user) throws PageBackException, ModelNotFoundException {
        ChangePage.changePage();
        System.out.println(BoundaryStrings.separator);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which camp do you want to update");
        CampViewer.viewStaffCamps((Staff) user);
        String campID = scanner.nextLine();
        Camp camp = CampManager.getCampByID(campID);
        System.out.println("Which field do you want to update, press 0 to go back to upper menu");
        System.out.println("\t0. Cancel");
        System.out.println("\t1. Camp Name");
        System.out.println("\t2. Camp Dates");
        System.out.println("\t3. Camp Registration Closing Date");
        System.out.println("\t4. Camp Faculty Open To");
        System.out.println("\t5. Camp Location");
        System.out.println("\t6. Camp Total Slots");
        System.out.println("\t7. Camp Description");
        System.out.println("\t8. Camp Staff ID In Charge");
        System.out.println("\t9. Camp Visibility");
        int choice = IntGetter.readInt();
        System.out.println("The new value of the field to update");
        switch (choice) {
            case 0 -> throw new PageBackException();
            case 1 -> camp.setCampName(scanner.nextLine());
            case 2 -> camp.setDates(scanner.nextLine());
            case 3 -> camp.setRegistrationClosingDate(scanner.nextLine());
            case 4 -> camp.setCampType(Faculty.valueOf(scanner.nextLine()));
            case 5 -> camp.setLocation(scanner.nextLine());
            case 6 -> camp.setTotalSlots(scanner.nextInt());
            case 7 -> camp.setDescription(scanner.nextLine());
            case 8 -> camp.setStaffID(scanner.nextLine());
            case 9 -> camp.setVisibility(String.valueOf(scanner.nextBoolean()));
        }
        System.out.println("Have other field to update?");
        System.out.println("\t0. No");
        System.out.println("\t1. Yes");
        choice = scanner.nextInt();
        if (choice == 1) {
            editExistingCamp(user);
        }
        CampManager.updateCamp(campID, camp);
        System.out.println("Successfully updated a new camp:");
        CampViewer.viewCamp(camp);
        System.out.println(BoundaryStrings.separator);
        System.out.println();
        System.out.println("Press enter to go back.");
        scanner.nextLine();
        throw new PageBackException();
    }

    private static void viewAndReplyPendingEnquiries(User user) throws ModelNotFoundException, PageBackException {
        ChangePage.changePage();
        System.out.println(BoundaryStrings.separator);
        System.out.println("View Pending Enquiries");
        System.out.println();
        ModelViewer.displayListOfDisplayable(
            CampManager.getAllPendingEnquiriesByStaff((Staff) user));
        System.out.println("Which enquiry ID do you want to reply");
        Scanner scanner = new Scanner(System.in);
        String enquiryID = scanner.nextLine();
        Enquiry enquiry = (Enquiry) RequestRepository.getInstance().getByID(enquiryID);
        System.out.println("Reply Message");
        String message = scanner.nextLine();
        enquiry.setMessage(message);
        enquiry.setReplierID(user.getID());
        enquiry.setRequestStatus(RequestStatus.REPLIED);
        RequestRepository.getInstance().update(enquiry);
        System.out.println("Successfully replied an enquiry:");
        System.out.println();
        System.out.println("Have other enquiry to reply?");
        System.out.println("\t0. No");
        System.out.println("\t1. Yes");
        int choice = scanner.nextInt();
        if (choice == 1) {
            viewAndReplyPendingEnquiries(user);
        }
        ModelViewer.displaySingleDisplayable(enquiry);
        System.out.println(BoundaryStrings.separator);
        System.out.println("Press enter to go back.");
        scanner.nextLine();
        throw new PageBackException();
    }

    private static void viewAndHandlePendingSuggestions(User user) throws ModelNotFoundException, PageBackException {
        ChangePage.changePage();
        System.out.println(BoundaryStrings.separator);
        System.out.println("View Pending Suggestions");
        System.out.println();
        ModelViewer.displayListOfDisplayable(
            CampManager.getAllPendingSuggestionsByStaff((Staff) user));
        System.out.println("Which Suggestion ID do you want to reply");
        Scanner scanner = new Scanner(System.in);
        String suggestionID = scanner.nextLine();
        Suggestion suggestion = (Suggestion) RequestRepository.getInstance().getByID(suggestionID);
        System.out.println("Handle Suggestion, type Approve[Y] or Reject[N]");
        System.out.println("\t0. Go Back");
        System.out.println("\t1. Approve");
        System.out.println("\t2. Reject");
        int choice = scanner.nextInt();
        switch (choice) {
            case 0 -> viewAndHandlePendingSuggestions(user);
            case 1 -> suggestion.setRequestStatus(RequestStatus.APPROVED);
            case 2 -> suggestion.setRequestStatus(RequestStatus.DENIED);
        }
        suggestion.setReplierID(user.getID());
        RequestRepository.getInstance().update(suggestion);
        System.out.println("Successfully handled a suggestion:");
        System.out.println();
        System.out.println("Have other suggestion to handle?");
        System.out.println("\t0. No");
        System.out.println("\t1. Yes");
        choice = scanner.nextInt();
        if (choice == 1) {
            viewAndHandlePendingSuggestions(user);
        }
        System.out.println(BoundaryStrings.separator);
        System.out.println("Press enter to go back.");
        scanner.nextLine();
        throw new PageBackException();
    }

    private static void generateReports(User user) throws IOException, PageBackException {
        ChangePage.changePage();
        System.out.println(BoundaryStrings.separator);
        System.out.println("Generating Camps Report");
        System.out.println("\t0. Go Back");
        System.out.println("\t1. Camps Report");
        System.out.println("\t2. Attendee Report");
        System.out.println("\t3. Committee Report");
        System.out.println("\t4. Enquires Report");
        System.out.println("\t5. Committee Performance Report");
        System.out.println();
        int choice = IntGetter.readInt();
        List<Camp> camps = CampManager.getAllCampsByStaff((Staff) user);
        List<String> campIDs = camps.stream()
            .map(c -> c.getID()).collect(Collectors.toList());
        switch (choice) {
            case 0:
                throw new PageBackException();
            case 1:
                generateCampReports(camps);
                break;
            case 2:
                List<Student> attendees =
                    StudentRepository.getInstance().findByRules(
                        s -> campIDs.contains(s.getACamps())).stream()
                        .collect(Collectors.toList());
                generateAttendeeReports(attendees);
                break;
            case 3:
                List<Student> committees =
                    StudentRepository.getInstance().findByRules(
                            s -> campIDs.contains(s.getCCamps())).stream()
                        .collect(Collectors.toList());
                generateCommitteeReports(committees);
                break;
            case 4:
                List<Enquiry> enquiries =
                    RequestRepository.getInstance().findByRules(
                        r -> campIDs.contains(r.getCampID()),
                        r -> r.getRequestType() == RequestType.ENQUIRY).stream()
                        .map(r -> (Enquiry) r)
                        .collect(Collectors.toList());
                generateEnquiryReports(enquiries);
            case 5:
                generateCommitteePerformanceReports();
        }
        System.out.println();
        System.out.println("Have other suggestion to handle?");
        System.out.println("\t0. No");
        System.out.println("\t1. Yes");
        choice = IntGetter.readInt();
        if (choice == 1) {
            generateReports(user);
        }
        System.out.println("Press enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * Allows the staff to accept or reject requests.
     *
     * @throws PageBackException if the user chooses to go back to the previous page.
     */
//     private static void acceptOrRejectRequest() throws PageBackException {
//         ChangePage.changePage();
// //        System.out.println(BoundaryStrings.separator);
//         System.out.println("Accept or Reject Requests");
//         ModelViewer.displayListOfDisplayable(CoordinatorManager.getPendingRequests());
//         System.out.println("Please enter the ID of the request you want to accept or reject. (Enter 0 to go back.)");
// //        System.out.println(BoundaryStrings.separator);
//         System.out.print("Please enter your choice: ");

//         String requestID = new Scanner(System.in).nextLine();
//         if (requestID.equals("0")) {
//             throw new PageBackException();
//         }

//         Request request;
//         try {
//             request = RequestManager.getRequest(requestID);

//             if (Objects.isNull(request)) {
//                 throw new ModelNotFoundException();
//             }

//             if (request.getStatus() != RequestStatus.PENDING) {
//                 System.out.println("Request is not pending.");
//                 System.out.println("Press enter to go back, or enter [0] to try again.");
//                 String choice = new Scanner(System.in).nextLine();
//                 if (choice.equals("0")) {
//                     acceptOrRejectRequest();
//                 }
//                 throw new PageBackException();
//             }
//         } catch (ModelNotFoundException e) {
//             System.out.println("Request not found.");
//             System.out.println("Press enter to go back, or enter [0] to try again.");
//             String choice = new Scanner(System.in).nextLine();
//             if (choice.equals("0")) {
//                 acceptOrRejectRequest();
//             }
//             throw new PageBackException();
//         }

//         if (request.getRequestType() == RequestType.STUDENT_CHANGE_TITLE) {
//             System.out.println("You do not have permission to accept or reject this request.");
//             System.out.println("Press enter to go back.");
//             new Scanner(System.in).nextLine();
//             throw new PageBackException();
//         }

//         ChangePage.changePage();

//         System.out.println("Accept or Reject Requests");

//         ModelViewer.displaySingleDisplayable(request);

//         System.out.println("\t1. Approve");
//         System.out.println("\t2. Reject");
//         System.out.println("\t3. Go back");
//         System.out.println("Please enter your choice: ");
//         int choice = IntGetter.readInt();
//         switch (choice) {
//             case 1 -> {
//                 try {
//                     RequestManager.approveRequest(requestID);
//                 } catch (SupervisorStudentsLimitExceedException e) {
//                     System.out.println("Supervisor's students limit exceeded.");
//                     System.out.println("Press enter to go back, or enter [0] to try again.");
//                     String choice2 = new Scanner(System.in).nextLine();
//                     if (choice2.equals("0")) {
//                         acceptOrRejectRequest();
//                     }
//                     throw new PageBackException();
//                 }
//             }
//             case 2 -> {
//                 RequestManager.rejectRequest(requestID);
//             }
//             case 3 -> {
//                 acceptOrRejectRequest();
//                 throw new PageBackException();
//             }
//             default -> {
//                 System.out.println("Invalid choice. Please try again.");
//                 System.out.println("Press enter to go back, or enter [0] to try again.");
//                 String choice2 = new Scanner(System.in).nextLine();
//                 if (choice2.equals("0")) {
//                     acceptOrRejectRequest();
//                 }
//                 throw new PageBackException();
//             }
//         }
//         ChangePage.changePage();
//         try {
//             request = RequestManager.getRequest(requestID);
//         } catch (ModelNotFoundException e) {
//             e.printStackTrace();
//         }
//         System.out.println("Here is the updated request:");
//         ModelViewer.displaySingleDisplayable(request);
//         System.out.println("Press enter to go back.");
//         new Scanner(System.in).nextLine();
//         throw new PageBackException();
//     }

}
