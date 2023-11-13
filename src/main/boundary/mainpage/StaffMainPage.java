package main.boundary.mainpage;

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

import main.controller.request.StaffManager;
import main.controller.camp.CampManager;
import main.controller.request.RequestManager;
//import main.model.request.Request;
//import main.model.request.RequestStatus;
//import main.model.request.RequestType;
import main.model.camp.Camp;
import main.model.request.Enquiry;
import main.model.request.RequestStatus;
import main.model.request.RequestType;
import main.model.request.Suggestion;
import main.model.user.Faculty;
import main.model.user.Staff;
import main.model.user.Student;
import main.model.user.User;
import main.model.user.UserType;
import main.repository.request.RequestRepository;
import main.repository.user.StaffRepository;
import main.repository.user.StudentRepository;
import main.repository.camp.CampRepository;
import main.utils.exception.ModelAlreadyExistsException;
import main.utils.exception.ModelNotFoundException;
import main.utils.exception.PageBackException;
//import main.utils.exception.SupervisorStudentsLimitExceedException;
//import main.utils.iocontrol.CSVWritter;
import main.utils.config.Location;
import main.utils.iocontrol.IntGetter;
import main.utils.iocontrol.CSVWritter;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.ChangePage;

import java.util.Objects;
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
            System.out.println("\t3. View all Camps");
            System.out.println("\t4. Create a new Camp");
            System.out.println("\t5. Edit an existing Camp");
            System.out.println("\t6. Delete an existing Camp");
            System.out.println("\t7. View and Reply enquiries");
            System.out.println("\t8. View and Handle suggestions");
            System.out.println("\t9. Generate Reports");
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
                    case 3 -> CampViewer.viewAllCamps();
                    case 4 -> createCamp(user);
                    case 5 -> editExistingCamp(user);
                    case 6 -> deleteExistingCamp(user);
                    case 7 -> viewAndReplyPendingEnquiries(user);
                    case 8 -> viewAndHandlePendingSuggestions(user);
                    case 9 -> generateReports(user);
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
        System.out.println("Enter a camp Name:");
        String name = new Scanner(System.in).nextLine();
        System.out.println("Enter a camp Date, YYYY-MM-DD:");
        String date = new Scanner(System.in).nextLine();
        System.out.println("Enter a camp Registration Closing Date, YYYY-MM-DD:");
        String registrationClosingDateDate = new Scanner(System.in).nextLine();
        System.out.println("Please select school that camp is open to:");
        String userInput = new Scanner(System.in).nextLine();
        Faculty faculty = Faculty.NTU;

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
                System.out.println("Invalid input. Please enter a valid school code.");
            }
        }

        System.out.println("Selected faculty: " + faculty);
    
        System.out.println("Enter a camp Location:");
        String location = new Scanner(System.in).nextLine();
        System.out.println("Enter a camp attendee Slots:");
        int totalSlots = new Scanner(System.in).nextInt();
        System.out.println("Enter a camp committee Slots:");
        int campCommSlots = new Scanner(System.in).nextInt();
    
        System.out.println("Enter a camp Description:");
        String description = new Scanner(System.in).nextLine();
        Camp camp;
        try {
            camp =
            CampManager.createCamp(
                name, date, registrationClosingDateDate, faculty, location, 0, totalSlots,
                0, campCommSlots, description, user.getID(), "true");
        } catch (ModelAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
        
        System.out.println("The camp details are as follows:");
        ModelViewer.displaySingleDisplayable(camp);
        System.out.println("Are you sure you want to create this camp? (Y/N)");
        String input = new Scanner(System.in).nextLine();
        if (!input.equalsIgnoreCase("Y")) {
            System.out.println("Camp creation cancelled!");
            try {
                CampRepository.getInstance().remove(camp.getID());
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

    private static void editExistingCamp(User user) throws PageBackException, ModelNotFoundException {
        ChangePage.changePage();
        CampViewer.viewStaffCamps((Staff) user);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Camp ID of camp to edit");
        
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
        System.out.println("\t9. Camp Visibility(true/false)");
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
            CampManager.updateCamp(campID, camp);
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

    private static void deleteExistingCamp(User user) throws PageBackException, ModelNotFoundException {
        ChangePage.changePage();
        CampViewer.viewStaffCamps((Staff) user);
    
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Camp ID of camp to delete");
    
        String campID = scanner.nextLine();
    
        // Check if the camp with the provided ID exists
    
        Camp campToDelete = CampManager.getCampByID(campID);
        System.out.println("Are you sure you want to delete this camp? (Y/N)");
    
        String input = scanner.nextLine();
        if (!input.equalsIgnoreCase("Y")) {
            System.out.println("Camp deletion cancelled!");
            System.out.println("Press enter to continue");
            scanner.nextLine();
            throw new PageBackException();
        }
    
        // Try to delete the camp
        try {
            CampRepository.getInstance().remove(campToDelete.getID());
        } catch (ModelNotFoundException e) {
            throw new RuntimeException(e);
        }
    
        System.out.println("Camp deleted successfully!");
        System.out.println("Press enter to continue");
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
        CampViewer.viewStaffCamps((Staff) user);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Camp ID of camp to generate report");
        String campID = scanner.nextLine();
        System.out.println(BoundaryStrings.separator);

        System.out.println("Generating Camps Report");
        System.out.println("\t0. Go Back");
        System.out.println("\t1. Camps Report");
        System.out.println("\t2. Attendee List");
        System.out.println("\t3. Committee List");
        System.out.println("\t4. Enquiries Report");
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
                System.out.println("Camp Report");
                //generateCampReports(camps);
                break;
            case 2:
                List<Student> attendees =
                    StudentRepository.getInstance().findByRules(
                        s -> campIDs.contains(s.getACamps())).stream()
                        .collect(Collectors.toList());
                //generateAttendeeReports(attendees);
                System.out.println("Attendee List");
                break;
            case 3:
                List<Student> committees =
                    StudentRepository.getInstance().findByRules(
                            s -> campIDs.contains(s.getCCamps())).stream()
                        .collect(Collectors.toList());
                //generateCommitteeReports(committees);
                System.out.println("Committee List");
                break;
            case 4:
                // List<Enquiry> enquiries =
                //     RequestRepository.getInstance().findByRules(
                //         r -> campIDs.contains(r.getCampID()),
                //         r -> r.getRequestType() == RequestType.ENQUIRY).stream()
                //         .map(r -> (Enquiry) r)
                //         .collect(Collectors.toList());
                // generateEnquiryReports(enquiries);
                System.out.println("Enquiry Report");
            case 5:
                //generateCommitteePerformanceReports();
                System.out.println("Camp Committee Performance Report");
        }
        
        System.out.println();
        System.out.println("Have other reports to generate?");
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
