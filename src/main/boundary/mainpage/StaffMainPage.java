
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
import main.controller.camp.CampManager;
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
import main.utils.config.Location;
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
            System.out.println("\t0. Logout");
            System.out.println("\t1. View my profile");
            System.out.println("\t2. Change my password");
            System.out.println("\t3. View all Camps");
            System.out.println("\t4. Create a new Camp");
            System.out.println("\t5. Edit an existing Camp");
            System.out.println("\t6. View and Reply enquiries");
            System.out.println("\t7. View and Handle suggestions");
            System.out.println("\t8. Generate Reports");
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
                    case 6 -> viewAndReplyPendingEnquiries(user);
                    case 7 -> viewAndHandlePendingSuggestions(user);
                    case 8 -> generateReports(user);
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

    private static void generateCampReports(List<Camp> camps) throws IOException {
        List<String> header = List.of(
            "Camp ID", "Camp Name", "Camp Dates", "Camp Registration Closing Date",
            "Faculty Open To", "Location", "Filled Slots", "Total Slots", "Committee Slots",
            "Description", "Stuff ID", "Visibility");
        CSVWritter csvWritter = CSVWritter.create(header);
        for (Camp camp: camps) {
            List<String> row =
                List.of(camp.getID(), camp.getCampName(), camp.getDates(),
                    camp.getRegistrationClosingDate(), camp.getOpenTo().name(),
                    camp.getLocation(), Integer.toString(camp.getFilledSlots()),
                    Integer.toString(camp.getTotalSlots()),
                    Integer.toString(camp.getCampCommSlots()), camp.getDescription(),
                    camp.getStaffID(), camp.getVisibility());
            csvWritter.addRow(row);
        }
        csvWritter.writeAndClose(Location.RESOURCE_LOCATION + "/resources/CampReport.csv");
        System.out.println("Successfully write camp report.");
        System.out.println();
        System.out.println(BoundaryStrings.separator);
    }

    private static void generateAttendeeReports(List<Student> attendees) throws IOException {
        List<String> header = List.of(
            "Attendee ID", "Attendee Name", "Attendee Email", "Camp ID");
        CSVWritter csvWritter = CSVWritter.create(header);
        for (Student student: attendees) {
            List<String> row = new ArrayList<>(
                List.of(
                    student.getID(), student.getUserName(), student.getEmail(), student.getACamps()));
            csvWritter.addRow(row);
        }
        csvWritter.writeAndClose(Location.RESOURCE_LOCATION + "/resources/AttendeeReport.csv");
        System.out.println("Successfully write attendee report.");
        System.out.println();
        System.out.println(BoundaryStrings.separator);
    }

    private static void generateCommitteeReports(List<Student> committees) throws IOException {
        List<String> header = List.of(
            "Committee ID", "Committee Name", "Committee Email", "Camp ID");
        CSVWritter csvWritter = CSVWritter.create(header);
        for (Student student: committees) {
            List<String> row = new ArrayList<>(
                List.of(
                    student.getID(), student.getUserName(), student.getEmail(), student.getCCamps()));
            csvWritter.addRow(row);
        }
        csvWritter.writeAndClose(Location.RESOURCE_LOCATION + "/resources/CommitteeReport.csv");
        System.out.println("Successfully write committee report.");
        System.out.println();
        System.out.println(BoundaryStrings.separator);
    }

    private static void generateEnquiryReports(List<Enquiry> enquiries) throws IOException {
        List<String> header = List.of(
            "Enquiry ID", "Enquiry Sender ID", "Enquiry Replier ID",
            "Enquiry Message", "Enquiry Request Status");
        CSVWritter csvWritter = CSVWritter.create(header);
        for (Enquiry enquiry: enquiries) {
            List<String> row = new ArrayList<>(
                List.of(
                    enquiry.getID(), enquiry.getSenderID(), enquiry.getReplierID(),
                    enquiry.getMessage(), enquiry.getRequestStatus().name());
            csvWritter.addRow(row);
        }
        csvWritter.writeAndClose(Location.RESOURCE_LOCATION + "/resources/EnquiryReport.csv");
        System.out.println("Successfully write enquiry report.");
        System.out.println();
        System.out.println(BoundaryStrings.separator);
    }

    private static void generateCommitteePerformanceReports() throws IOException, PageBackException {
        List<Student> committees;
        //  =  StudentManager.getAllCommittees();
        Map<Student, Integer> committeeToPoints = committees.stream()
                .collect(Collectors.toMap(c -> c, StaffMainPage::getCommitteePerformancePoints));
        CSVWritter csvWritter = CSVWritter.create(
            List.of("Committee ID", "Committee Name", "Committees Email", "Points"));
        for (Entry<Student, Integer> kv: committeeToPoints.entrySet()) {
            Student s = kv.getKey();
            csvWritter.addRow(
                List.of(s.getID(), s.getUserName(), s.getEmail(), Integer.toString(kv.getValue())));
        }
        csvWritter.writeAndClose(Location.RESOURCE_LOCATION + "/resources/CommitteeReport.csv");
        System.out.println("Successfully write committee performance report.");
        System.out.println();
        System.out.println(BoundaryStrings.separator);
    }

    private static int getCommitteePerformancePoints(Student student) {
        String studentID = student.getID();
        int points = RequestRepository.getInstance().findByRules(
            // Enquiries that the student applied
            r -> r.getReplierID().equals(studentID)
                // Suggestions that the student given
            || (r.getSenderID().equals(studentID)
                && r.getRequestType() == RequestType.SUGGESTION)).size();
        // Suggestions that the student given and got approved.
        return points + RequestRepository.getInstance().findByRules(
            r -> r.getSenderID().equals(studentID),
            r -> r.getRequestType() == RequestType.SUGGESTION,
            r -> r.getRequestStatus() == RequestStatus.APPROVED).size();
    }
}
