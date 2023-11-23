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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
                    case 3 -> CampViewer.viewAllCamps();
                    case 4 -> createCamp(user);
                    case 5 -> editExistingCamp(user);
                    case 6 -> deleteExistingCamp(user);
                    case 7 -> viewAndReplyPendingEnquiries(user);
                    case 8 -> viewAndHandlePendingSuggestions(user);// to implement
                    case 9 -> generateReports(user);// to implement
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

    private static void createCamp(User user) throws PageBackException {
        ChangePage.changePage();
        System.out.println(BoundaryStrings.separator);
        System.out.println("Enter a camp Name:");
        String name = new Scanner(System.in).nextLine();
        System.out.println("Enter a camp Date, YYYYMMDD-YYYMMDD:");
        String date = new Scanner(System.in).nextLine();
        System.out.println("Enter a camp Registration Closing Date, YYYMMDD:");
        String registrationClosingDateDate = new Scanner(System.in).nextLine();
        System.out.println("Please select school that camp is open to:");
        System.out.println("NTU,ADM,EEE,NBS,SCSE,SSS");
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
            camp = CampManager.createCamp(
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
        System.out.println("Camp created successfully!");
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
        System.out.println("\t6. Camp Attendee Total Slots");
        System.out.println("\t7. Camp Committee Total Slots");
        System.out.println("\t8. Camp Description");
        System.out.println("\t9. Camp Staff ID In Charge");
        System.out.println("\t10. Camp Visibility(true/false)");
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
            case 7 -> camp.setCampCommSlots(scanner.nextInt());
            case 8 -> camp.setDescription(scanner.nextLine());
            case 9 -> camp.setStaffID(scanner.nextLine());
            case 10 -> camp.setVisibility(String.valueOf(scanner.nextBoolean()));
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
                RequestManager.getAllPendingEnquiriesByStaff((Staff) user));
        System.out.println("Which enquiry ID do you want to reply");
        Scanner scanner = new Scanner(System.in);
        String enquiryID = scanner.nextLine();
        Enquiry enquiry = (Enquiry) EnquiryRepository.getInstance().getByID(enquiryID);
        System.out.println("Reply Message");
        String message = scanner.nextLine();
        enquiry.setMessage(message);
        enquiry.setReplierID(user.getID());
        enquiry.setRequestStatus(RequestStatus.REPLIED);
        EnquiryRepository.getInstance().update(enquiry);
        System.out.println("Successfully replied an enquiry:");
        System.out.println();
        ChangePage.changePage();
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
                RequestManager.getAllPendingSuggestionsByStaff((Staff) user));
        System.out.println("Which Suggestion ID do you want to handle?:");
        Scanner scanner = new Scanner(System.in);
        String suggestionID = scanner.nextLine();
        Suggestion suggestion = (Suggestion) SuggestionRepository.getInstance().getByID(suggestionID);
        System.out.println("Handle Suggestion, type Approve[Y] or Reject[N]");
        System.out.println("\t0. Go Back");
        System.out.println("\t1. Approve");
        System.out.println("\t2. Reject");
        int choice = scanner.nextInt();
        switch (choice) {
            case 0 -> viewAndHandlePendingSuggestions(user);
            case 1 -> RequestManager.approveSuggestion(suggestion);
            case 2 -> suggestion.setRequestStatus(RequestStatus.DENIED);
        }
        suggestion.setReplierID(user.getID());
        SuggestionRepository.getInstance().update(suggestion);
        System.out.println(suggestion.getSenderID());
        Student student = StudentRepository.getInstance().getByID(suggestion.getSenderID());
        student.addPoint();
        StudentRepository.getInstance().update(student);
        ChangePage.changePage();
        System.out.println("Successfully handled a suggestion:");
        System.out.println("A point has been rewarded to the suggestion sender!");
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
        System.out.println("\t1. My Camps Report");
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
                System.out.println("My Camp Report");
                generateCampList(camps, (Staff) user);
                break;
            case 2:
                List<Student> attendees = StudentRepository.getInstance().findByRules(
                        s -> campIDs.contains(s.getACamps())).stream()
                        .collect(Collectors.toList());
                        
                        try {
                            generateAttendeeReports(campID);
                        } catch (ModelNotFoundException e) {
                            // Handle the exception or log an error message
                            e.printStackTrace();
                        }
                System.out.println("Attendee List");
                break;
            case 3:
                // List<Student> committees =
                // StudentRepository.getInstance().findByRules(
                // s -> campIDs.contains(s.getCCamps())).stream()
                // .collect(Collectors.toList());
                // //generateCommitteeReports(committees);
                try {
                            generateCommitteeReports(campID);
                        } catch (ModelNotFoundException e) {
                            // Handle the exception or log an error message
                            e.printStackTrace();
                        }
                // System.out.println("Committee List");
                break;
            case 4:
                try {
                            generateEnquiryReports(campID);
                        } catch (ModelNotFoundException e) {
                            // Handle the exception or log an error message
                            e.printStackTrace();
                        }
                System.out.println("Enquiry Report");
            case 5:
            try {
                            generatePerformaceReport(campID);
                        } catch (ModelNotFoundException e) {
                            // Handle the exception or log an error message
                            e.printStackTrace();
                        }
                ;
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

    public static void generateCampList(List<Camp> camps, Staff staff) throws PageBackException {
        Scanner sc = new Scanner(System.in);
        ChangePage.changePage();
        System.out.printf("Generating Camp Report for %s...\n", staff.getID());

        String staffID = staff.getID();
        String FILE_PATH = RESOURCE_LOCATION + "/data/report/report_camp_" + staffID + ".csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {

            for (Camp camp : camps) {
                // Writing headers to the CSV file
                writer.write("Camp Name,Camp Dates,Registration Deadline,Open To,Location,"
                        + "Current Attendee Slots,Total Attendee Slots,Current Camp Comm Slots,Total Camp Comm Slots,Description");
                writer.newLine();

                String campName = camp.getCampName();
                String campDates = camp.getDates();
                String campRegistrationDeadline = camp.getRegistrationClosingDate();
                Faculty openTo = camp.getOpenTo();
                String location = camp.getLocation();
                int currentAttendeeSlots = camp.getFilledSlots();
                int totalAttendeeSlots = camp.getTotalSlots();
                int currentCampCommSlots = camp.getFilledCampCommSlots();
                int totalCampCommSlots = camp.getCampCommSlots();
                String description = camp.getDescription();

                List<Student> attendees = getAllAttendeesByCamp(camp);
                List<Student> campCommMembers = getAllCampCommByCamp(camp);

                // Writing camp details to the CSV file
                writer.write(String.format("%s,%s,%s,%s,%s,%d,%d,%d,%d,%s",
                        campName, campDates, campRegistrationDeadline, openTo.toString(), location,
                        currentAttendeeSlots, totalAttendeeSlots, currentCampCommSlots, totalCampCommSlots,
                        description));
                writer.newLine();
                writer.newLine();

                // Writing list of attendees to the CSV file
                writer.write("List of Attendees");
                writer.newLine();
                for (Student attendee : attendees) {
                    writer.write(attendee.getUserName()); // Include any relevant attendee information
                    writer.newLine();
                }
                writer.newLine();

                // Writing list of camp committee members to the CSV file
                writer.write("List of Camp Committee Members");
                writer.newLine();
                for (Student campCommMember : campCommMembers) {
                    writer.write(campCommMember.getUserName()); // Include any relevant camp committee member
                                                                // information
                    writer.newLine();
                }
                writer.newLine();
                writer.newLine();
            }

            System.out.printf("All camp reports for %s generated successfully and saved at %s.\n", staffID, FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        System.out.println("Press enter to go back.");
        sc.nextLine();
        throw new PageBackException();
    }

    public static List<Student> getAllAttendeesByCamp(Camp camp) {
        return StudentRepository.getInstance().findByRules(
                s -> s.getACamps().toLowerCase().contains(camp.getID().toLowerCase()))
                .stream()
                .map(s -> (Student) s)
                .collect(Collectors.toList());
    }

    public static List<Student> getAllCampCommByCamp(Camp camp) {
        return StudentRepository.getInstance().findByRules(
                s -> s.getCCamps().toLowerCase().contains(camp.getID().toLowerCase()))
                .stream()
                .map(s -> (Student) s)
                .collect(Collectors.toList());
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////

    public static void generateAttendeeReports(String campID) throws PageBackException, ModelNotFoundException {
        Scanner sc = new Scanner(System.in);
        ChangePage.changePage();
        Camp camp = CampRepository.getInstance().getByID(campID);
        if (camp == null) {
            // Throw ModelNotFoundException if the camp is not found
            throw new ModelNotFoundException("Camp not found with ID: " + campID);
        }

        System.out.printf("Generating Camp Report for %s...\n", camp.getCampName());

        String FILE_PATH = RESOURCE_LOCATION + "/data/report/report_attendee_" + campID + ".csv";

        String campName = camp.getCampName();
        String campDates = camp.getDates();
        String campRegistrationDeadline = camp.getRegistrationClosingDate();
        Faculty openTo = camp.getOpenTo();
        String location = camp.getLocation();
        int currentAttendeeSlots = camp.getFilledSlots();
        int totalAttendeeSlots = camp.getTotalSlots();
        int currentCampCommSlots = camp.getFilledCampCommSlots();
        int totalCampCommSlots = camp.getCampCommSlots();
        String description = camp.getDescription();

        List<Student> attendees = getAllAttendeesByCamp(camp);
        // List<Student> campCommMembers = getAllCampCommByCamp(camp);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // Writing headers to the CSV file
            writer.write("Camp Name,Camp Dates,Registration Deadline,Open To,Location,"
                    + "Current Attendee Slots,Total Attendee Slots,Current Camp Comm Slots,Total Camp Comm Slots,Description");
            writer.newLine();

            // Writing camp details to the CSV file
            writer.write(String.format("%s,%s,%s,%s,%s,%d,%d,%d,%d,%s",
                    campName, campDates, campRegistrationDeadline, openTo.toString(), location,
                    currentAttendeeSlots, totalAttendeeSlots, currentCampCommSlots, totalCampCommSlots, description));
            writer.newLine();

            // Writing list of attendees to the CSV file
            writer.write("List of Attendees");
            writer.newLine();
            for (Student attendee : attendees) {
                writer.write(attendee.getUserName()); // Include any relevant attendee information
                writer.newLine();
            }

            // Writing list of camp committee members to the CSV file
            // writer.write("List of Camp Committee Members");
            // writer.newLine();
            // for (Student campCommMember : campCommMembers) {
            // writer.write(campCommMember.getUserName()); // Include any relevant camp
            // committee member information
            // writer.newLine();
            // }

            System.out.printf("Camp Report for attendees %s generated successfully and saved at %s.\n", campName,
                    FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        System.out.println("Press enter to go back.");
        sc.nextLine();
        throw new PageBackException();
    }
    /////////////////////////////////////////////////////////////////////////////////

    public static void generateCommitteeReports(String campID) throws PageBackException, ModelNotFoundException {
        Scanner sc = new Scanner(System.in);
        ChangePage.changePage();
        Camp camp = CampRepository.getInstance().getByID(campID);
        // Check if the camp is null
        if (camp == null) {
            // Throw ModelNotFoundException if the camp is not found
            throw new ModelNotFoundException("Camp not found with ID: " + campID);
        }

        System.out.printf("Generating Camp Report for %s...\n", camp.getCampName());

        String FILE_PATH = RESOURCE_LOCATION + "/data/report/report_committee_" + campID + ".csv";

        String campName = camp.getCampName();
        String campDates = camp.getDates();
        String campRegistrationDeadline = camp.getRegistrationClosingDate();
        Faculty openTo = camp.getOpenTo();
        String location = camp.getLocation();
        int currentAttendeeSlots = camp.getFilledSlots();
        int totalAttendeeSlots = camp.getTotalSlots();
        int currentCampCommSlots = camp.getFilledCampCommSlots();
        int totalCampCommSlots = camp.getCampCommSlots();
        String description = camp.getDescription();

        // List<Student> attendees = getAllAttendeesByCamp(camp);
        List<Student> campCommMembers = getAllCampCommByCamp(camp);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // Writing headers to the CSV file
            writer.write("Camp Name,Camp Dates,Registration Deadline,Open To,Location,"
                    + "Current Attendee Slots,Total Attendee Slots,Current Camp Comm Slots,Total Camp Comm Slots,Description");
            writer.newLine();

            // Writing camp details to the CSV file
            writer.write(String.format("%s,%s,%s,%s,%s,%d,%d,%d,%d,%s",
                    campName, campDates, campRegistrationDeadline, openTo.toString(), location,
                    currentAttendeeSlots, totalAttendeeSlots, currentCampCommSlots, totalCampCommSlots, description));
            writer.newLine();

            // Writing list of attendees to the CSV file
            // writer.write("List of Attendees");
            // writer.newLine();
            // for (Student attendee : attendees) {
            // writer.write(attendee.getUserName()); // Include any relevant attendee
            // information
            // writer.newLine();
            // }

            // Writing list of camp committee members to the CSV file
            writer.write("List of Camp Committee Members");
            writer.newLine();
            for (Student campCommMember : campCommMembers) {
                writer.write(campCommMember.getUserName()); // Include any relevant camp committee member information
                writer.newLine();
            }

            System.out.printf("Camp Report for attendees %s generated successfully and saved at %s.\n", campName,
                    FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        System.out.println("Press enter to go back.");
        sc.nextLine();
        throw new PageBackException();
    }
    //////////////////////////////////////////////////////////////////////////////
    
    public static void generatePerformaceReport(String campID) throws PageBackException, ModelNotFoundException {
        Scanner sc = new Scanner(System.in);
        ChangePage.changePage();
        Camp camp = CampRepository.getInstance().getByID(campID);
        // Check if the camp is null
        if (camp == null) {
            // Throw ModelNotFoundException if the camp is not found
            throw new ModelNotFoundException("Camp not found with ID: " + campID);
        }

        System.out.printf("Generating Camp Committee Members Performance report %s...\n", camp.getCampName());

        String FILE_PATH = RESOURCE_LOCATION + "/data/report/report_performance_" + campID + ".csv";

        String campName = camp.getCampName();
        String campDates = camp.getDates();
        String campRegistrationDeadline = camp.getRegistrationClosingDate();
        Faculty openTo = camp.getOpenTo();
        String location = camp.getLocation();
        int currentAttendeeSlots = camp.getFilledSlots();
        int totalAttendeeSlots = camp.getTotalSlots();
        int currentCampCommSlots = camp.getFilledCampCommSlots();
        int totalCampCommSlots = camp.getCampCommSlots();
        String description = camp.getDescription();

        // List<Student> attendees = getAllAttendeesByCamp(camp);
        List<Student> campCommMembers = getAllCampCommByCamp(camp);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // Writing headers to the CSV file
            writer.write("Camp Name,Camp Dates,Registration Deadline,Open To,Location,"
                    + "Current Attendee Slots,Total Attendee Slots,Current Camp Comm Slots,Total Camp Comm Slots,Description");
            writer.newLine();

            // Writing camp details to the CSV file
            writer.write(String.format("%s,%s,%s,%s,%s,%d,%d,%d,%d,%s",
                    campName, campDates, campRegistrationDeadline, openTo.toString(), location,
                    currentAttendeeSlots, totalAttendeeSlots, currentCampCommSlots, totalCampCommSlots, description));
            writer.newLine();

            // Writing list of attendees to the CSV file
            // writer.write("List of Attendees");
            // writer.newLine();
            // for (Student attendee : attendees) {
            // writer.write(attendee.getUserName()); // Include any relevant attendee
            // information
            // writer.newLine();
            // }

            // Writing list of camp committee members to the CSV file
            writer.write("List of Camp Committee Members,Points");
            writer.newLine();
            for (Student campCommMember : campCommMembers) {
                writer.write(String.format("%s,%d",
                    campCommMember.getUserName(), campCommMember.getPoints()));
                writer.newLine();
            }

            System.out.printf("Camp Committee Members Performance report for %s generated successfully and saved at %s.\n", campName,
                    FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        System.out.println("Press enter to go back.");
        sc.nextLine();
        throw new PageBackException();
    }
    //////////////////////////////////////////////////////////////////////////////////////
    public static void generateEnquiryReports(String campID) throws PageBackException, ModelNotFoundException {
        Scanner sc = new Scanner(System.in);
        ChangePage.changePage();
        Camp camp = CampRepository.getInstance().getByID(campID);
        // Check if the camp is null
        if (camp == null) {
            // Throw ModelNotFoundException if the camp is not found
            throw new ModelNotFoundException("Camp not found with ID: " + campID);
        }

        System.out.printf("Generating Enquiry report for %s...\n", camp.getCampName());

        String FILE_PATH = RESOURCE_LOCATION + "/data/report/report_enquiry_" + campID + ".csv";

        String campName = camp.getCampName();
        String campDates = camp.getDates();
        String campRegistrationDeadline = camp.getRegistrationClosingDate();
        Faculty openTo = camp.getOpenTo();
        String location = camp.getLocation();
        int currentAttendeeSlots = camp.getFilledSlots();
        int totalAttendeeSlots = camp.getTotalSlots();
        int currentCampCommSlots = camp.getFilledCampCommSlots();
        int totalCampCommSlots = camp.getCampCommSlots();
        String description = camp.getDescription();

        List<Enquiry> enquiries = getAllEnquiriesByCamp(camp);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // Writing headers to the CSV file
            writer.write("Camp Name,Camp Dates,Registration Deadline,Open To,Location,"
                    + "Current Attendee Slots,Total Attendee Slots,Current Camp Comm Slots,Total Camp Comm Slots,Description");
            writer.newLine();

            // Writing camp details to the CSV file
            writer.write(String.format("%s,%s,%s,%s,%s,%d,%d,%d,%d,%s",
                    campName, campDates, campRegistrationDeadline, openTo.toString(), location,
                    currentAttendeeSlots, totalAttendeeSlots, currentCampCommSlots, totalCampCommSlots, description));
            writer.newLine();

            // Writing list of attendees to the CSV file
            // writer.write("List of Attendees");
            // writer.newLine();
            // for (Student attendee : attendees) {
            // writer.write(attendee.getUserName()); // Include any relevant attendee
            // information
            // writer.newLine();
            // }

            // Writing list of camp committee members to the CSV file
           writer.newLine();
            writer.write("Enquiries");
            writer.newLine();
            for (Enquiry e : enquiries) {
                
                writer.write("RequestID,RequestStatus,SenderID,ReplierID,Message,Reply");
                writer.newLine();
                writer.write(String.format("%s,%s,%s,%s,%s,%s",
                e.getID(), e.getRequestStatus().toString(),e.getSenderID(),e.getReplierID(),e.getMessage(),e.getReply()));
                writer.newLine();
                writer.newLine();
            }

            System.out.printf("Enquiry report for %s generated successfully and saved at %s.\n", campName,
                    FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        System.out.println("Press enter to go back.");
        sc.nextLine();
        throw new PageBackException();
    }

    	public static List<Enquiry> getAllEnquiriesByCamp(Camp camp) {
		return EnquiryRepository.getInstance().findByRules(
				e -> e.getCampID().toLowerCase().contains(camp.getID().toLowerCase()))
				.stream()
				.map(e -> (Enquiry) e)
				.collect(Collectors.toList());
	}

}
