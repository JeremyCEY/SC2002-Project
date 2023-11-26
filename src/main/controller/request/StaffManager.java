/*
 * @Author: tyt1130 tangyutong0306@gmail.com
 * @Date: 2023-11-08 19:38:58
 * @LastEditors: tyt1130 tangyutong0306@gmail.com
 * @LastEditTime: 2023-11-26 11:31:14
 * @FilePath: \SC2002-Project\src\main\controller\request\StaffManager.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package main.controller.request;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import main.boundary.modelviewer.CampViewer;
import main.boundary.modelviewer.ModelViewer;
import main.controller.camp.CampManager;
import main.model.camp.Camp;
import main.model.request.Enquiry;
import main.model.request.RequestStatus;
import main.model.request.Suggestion;
import main.model.user.Faculty;
import main.model.user.Staff;
import main.model.user.Student;
import main.model.user.User;
import main.repository.camp.CampRepository;
import main.repository.request.EnquiryRepository;
import main.repository.request.SuggestionRepository;
import main.repository.user.StudentRepository;
import main.utils.exception.ModelAlreadyExistsException;
import main.utils.exception.ModelNotFoundException;
import main.utils.exception.PageBackException;
import main.utils.iocontrol.IntGetter;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.ChangePage;

import static main.utils.config.Location.RESOURCE_LOCATION;

public class StaffManager {

 /**
  * @description: Staff creates the camp for students
  * @param {User} user
  * @return {*}
  */
  public static void createCamp(User user) throws PageBackException {
    ChangePage.changePage();
    System.out.println(BoundaryStrings.separator);
    String name; 
    do{
        System.out.println("Enter a camp Name:");
        name = new Scanner(System.in).nextLine();
        if (name==""){
            System.out.println("You have not entered anything. Please reenter!"); 
        }
    }while(name==""); 
    String date; 
    do{
        System.out.println("Enter a camp Date, YYYYMMDD-YYYYMMDD:");
        date = new Scanner(System.in).nextLine();
        if (date==""){
            System.out.println("You have not entered anything. Please reenter!"); 
        }
    }while(date==""); 
    String registrationClosingDateDate; 
    do{
        System.out.println("Enter a camp Registration Closing Date, YYYYMMDD:");
        registrationClosingDateDate = new Scanner(System.in).nextLine();
        if (registrationClosingDateDate==""){
            System.out.println("You have not entered anything. Please reenter!"); 
        }
    }while(registrationClosingDateDate==""); 

    System.out.println("Please select school that camp is open to:");
    System.out.println("NTU,ADM,EEE,NBS,SCSE,SSS");
    int included=0; 
    String suggt; 
    Faculty faculty=Faculty.NTU; 
    Scanner scanner=new Scanner(System.in); 
    do {
        suggt=scanner.nextLine(); 
        suggt=suggt.toUpperCase(); 
        //System.out.println("suggt is: "+suggt); 
        //System.out.println("suggest is: "+suggest); 
        included=0; 
        Faculty fc[]=Faculty.values();
        for (Faculty fcl: fc){
            if (fcl.name().equalsIgnoreCase(suggt)){
                included=1; 
                faculty=fcl; 
                break; 
            }
        }
        if (included==0){
            System.out.println("Please enter one of the following faculties name (case is not important):"); 
            System.out.println("    NA,\r\n" + //
            "    NTU,\r\n" + //
            "    ADM,\r\n" + //
            "    EEE,\r\n" + //
            "    NBS,\r\n" + //
            "    SCSE,\r\n" + //
            "    SSS"); 
        }
    }while (included==0); 
    

    System.out.println("Selected faculty: " + faculty);

    String location; 
    do{
        System.out.println("Enter a camp Location:");
        location = new Scanner(System.in).nextLine();
        if (location==""){
            System.out.println("You have not entered anything. Please reenter!"); 
        }
    }while(location==""); 

    int totalSlots; 
    while (true) {
        System.out.println("Enter a camp attendee Slots:");
        try {
            // Try to read an integer from the user input
            totalSlots = new Scanner(System.in).nextInt();

            // Process the integer input
            System.out.println("You entered: " + totalSlots);

            // Break out of the loop if a valid integer is entered
            break;
        } catch (InputMismatchException e) {
            // Handle the case where the input is not an integer
            System.out.println("Invalid input. Please enter a valid integer.");
        }
    }

    int campCommSlots; 
    while (true) {
        System.out.println("Enter a camp committee Slots:");
        try {
            // Try to read an integer from the user input
            campCommSlots = new Scanner(System.in).nextInt();
            // Process the integer input
            System.out.println("You entered: " + campCommSlots);

            // Break out of the loop if a valid integer is entered
            break;
        } catch (InputMismatchException e) {
            // Handle the case where the input is not an integer
            System.out.println("Invalid input. Please enter a valid integer.");
        }
    }

    String description; 
    do{
        System.out.println("Enter a camp Description:");
        description = new Scanner(System.in).nextLine();
        if (description==""){
            System.out.println("You have not entered anything. Please reenter!"); 
        }
    }while(description==""); 

    Camp camp;
    name=name.toUpperCase(); 
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


public static void editExistingCamp(User user) throws PageBackException, ModelNotFoundException {
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
    int choice; 
    do {
        choice=IntGetter.readInt();
        if (choice == 0) throw new PageBackException();
        if (choice>10 || choice<0){
            System.out.println("Please enter field from 1 to 10!"); 
        }
    }while(choice>10 || choice<1); 
    int suggest=-1; 
    String suggt=""; 
    String booln=""; 
    System.out.println("The new value of the field to update: ");
    do{
        suggt=""; 
        int totalSlots; 
        if ((choice==6) || (choice==7)){
            while (true) {
                System.out.println("Enter a camp attendee Slots:");
                try {
                    // Try to read an integer from the user input
                    suggest= new Scanner(System.in).nextInt();
                    // Process the integer input
                    System.out.println("You entered: " + suggest);
                    // Break out of the loop if a valid integer is entered
                    break;
                } catch (InputMismatchException e) {
                    // Handle the case where the input is not an integer
                     System.out.println("Invalid input. Please enter a valid integer.");
                }
            }
            suggt="default"; 
        }
        else if (choice==4){
            System.out.println("Please enter one of the following faculties name (case is not important):"); 
            System.out.println("    NA,\r\n" + //
                    "    NTU,\r\n" + //
                    "    ADM,\r\n" + //
                    "    EEE,\r\n" + //
                    "    NBS,\r\n" + //
                    "    SCSE,\r\n" + //
                    "    SSS"); 
            //String faculty=scanner.nextLine(); 
            int included=0; 
            do {
                suggt=scanner.nextLine(); 
                suggt=suggt.toUpperCase(); 
                //System.out.println("suggt is: "+suggt); 
                //System.out.println("suggest is: "+suggest); 
                included=0; 
                Faculty fc[]=Faculty.values();
                for (Faculty fcl: fc){
                    if (fcl.name().equalsIgnoreCase(suggt)){
                        included=1; 
                        break; 
                    }
                }
                if (included==0){
                    System.out.println("Please enter one of the following faculties name (case is not important):"); 
                    System.out.println("    NA,\r\n" + //
                    "    NTU,\r\n" + //
                    "    ADM,\r\n" + //
                    "    EEE,\r\n" + //
                    "    NBS,\r\n" + //
                    "    SCSE,\r\n" + //
                    "    SSS"); 
                }
            }while (included==0); 
        }
        else if (choice==10){
            int validinput=0; 
            do {
                booln=scanner.nextLine(); 
                booln=booln.toUpperCase(); 
                if (booln.equals("FALSE") || booln.equals("TRUE")){
                    validinput=1; 
                    suggt="default"; 
                }
                if (validinput!=1){
                    System.out.println("booln is: "+booln); 
                    System.out.println("Please enter true or false (case is not important)!");
                    System.out.println("Please indicate your choice: ");
                }
            }while(validinput!=1); 
        }
        else {//(choice==0 || choice==1 || choice==2 || choice==3 || choice==5 || choice==8 || choice==9){
            suggt=scanner.nextLine();
        }
        //System.out.println("Suggest is: "+suggest); 
        if ((Integer.toString(suggest)=="") || (suggt=="")){
            System.out.println("You have not entered any new value, please reenter!"); 
        }
    }while((Integer.toString(suggest)=="") || (suggt=="")); 
    switch (choice) {
        case 0 -> throw new PageBackException();
        case 1 -> camp.setCampName(suggt);
        case 2 -> camp.setDates(suggt);
        case 3 -> camp.setRegistrationClosingDate(suggt);
        case 4 -> camp.setCampType(Faculty.valueOf(suggt));
        case 5 -> camp.setLocation(suggt);
        case 6 -> camp.setTotalSlots(suggest);
        case 7 -> camp.setCampCommSlots(suggest);
        case 8 -> camp.setDescription(suggt);
        case 9 -> camp.setStaffID(suggt);
        case 10 -> camp.setVisibility(String.valueOf(booln));
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

public static void deleteExistingCamp(User user) throws PageBackException, ModelNotFoundException {
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

    public static void viewAndReplyPendingEnquiries(User user) throws ModelNotFoundException, PageBackException {
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

    public static void viewAndHandlePendingSuggestions(User user) throws ModelNotFoundException, PageBackException {
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

        ChangePage.changePage();
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

    public static void generateReports(User user) throws IOException, PageBackException {
        ChangePage.changePage();
        CampViewer.viewStaffCamps((Staff) user);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select the type of report to generate:");
        System.out.println("\t1. Generate reports for all camps");
        System.out.println("\t2. Generate reports for a specific camp");
        System.out.println("\t3. Go Back");

        System.out.print("Enter your choice: ");
        int reportChoice = IntGetter.readInt();
        System.out.println(BoundaryStrings.separator);

        switch (reportChoice) {
            case 1:
                System.out.println("Select the type of report for all camps:");
                System.out.println("\t1. All Students List");
                System.out.println("\t2. Attendee List");
                System.out.println("\t3. Committee List");
                System.out.println("\t4. Enquiries Report");
                System.out.println("\t5. Committee Performance Report");
                System.out.println("\t6. Go Back");
                System.out.print("Enter your choice: ");
                int allCampsReportChoice = IntGetter.readInt();

                switch (allCampsReportChoice) {
                    case 1:
                        generateStudentsReportForStaffCamps(CampManager.getAllCampsByStaff((Staff) user), (Staff) user);
                        break;
                    case 2:
                        generateAttendeeReportForStaffCamps(CampManager.getAllCampsByStaff((Staff) user), (Staff) user);
                        break;
                    case 3:
                        generateCommitteeReportForStaffCamps(CampManager.getAllCampsByStaff((Staff) user),
                                (Staff) user);
                        break;
                    case 4:
                        generateEnquiryReportForStaffCamps(CampManager.getAllCampsByStaff((Staff) user), (Staff) user);
                        break;
                    case 5:
                        generatePerformanceReportForStaffCamps(CampManager.getAllCampsByStaff((Staff) user),
                                (Staff) user);
                        break;
                    case 6:
                        throw new PageBackException();
                    default:
                        System.out.println("Invalid choice. Try again.");
                        new Scanner(System.in).nextLine();
                        generateReports(user);
                }
                break;

            case 2:
                System.out.println("Enter Camp ID of the camp to generate reports:");
                String campID = scanner.nextLine();

                System.out.println("Select the type of report for the specific camp:");
                System.out.println("\t1. All Students List");
                System.out.println("\t2. Attendee List");
                System.out.println("\t3. Committee List");
                System.out.println("\t4. Enquiries Report");
                System.out.println("\t5. Committee Performance Report");
                System.out.println("\t6. Go Back");
                System.out.print("Enter your choice: ");
                int specificCampReportChoice = IntGetter.readInt();

                switch (specificCampReportChoice) {
                    case 1:
                        try {
                            generateStudentsReport(campID);
                        } catch (ModelNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        try {
                            generateAttendeeReport(campID);
                        } catch (ModelNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        try {
                            generateCommitteeReport(campID);
                        } catch (ModelNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 4:
                        try {
                            generateEnquiryReport(campID);
                        } catch (ModelNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 5:
                        try {
                            generatePerformaceReport(campID);
                        } catch (ModelNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 6:
                        throw new PageBackException();
                    default:
                        System.out.println("Invalid choice. Try again.");
                        new Scanner(System.in).nextLine();
                        generateReports(user);
                }
                break;

            case 3:
                throw new PageBackException();

            default:
                System.out.println("Invalid choice. Try again.");
                new Scanner(System.in).nextLine();
                generateReports(user);
        }

        System.out.println();
        System.out.println("Do you want to generate other reports?");
        System.out.println("\t0. No");
        System.out.println("\t1. Yes");
        int choice = IntGetter.readInt();
        if (choice == 1) {
            generateReports(user);
        }
        System.out.println("Press enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    private static void generateStudentsReport(String campID) throws PageBackException, ModelNotFoundException {
        Scanner sc = new Scanner(System.in);
        ChangePage.changePage();
        Camp camp = CampRepository.getInstance().getByID(campID);
        if (camp == null) {
            // Throw ModelNotFoundException if the camp is not found
            throw new ModelNotFoundException("Camp not found with ID: " + campID);
        }

        System.out.printf("Generating Camp Report for %s...\n", camp.getCampName());

        String FILE_PATH = RESOURCE_LOCATION + "/data/report/report_students_" + campID + ".csv";

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
            writer.write("List of Camp Committee Members");
            writer.newLine();
            for (Student campCommMember : campCommMembers) {
                writer.write(campCommMember.getUserName()); // Include any relevant camp
                // committee member information
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

    public static void generateAttendeeReport(String campID) throws PageBackException, ModelNotFoundException {
        Scanner sc = new Scanner(System.in);
        ChangePage.changePage();
        Camp camp = CampRepository.getInstance().getByID(campID);
        if (camp == null) {
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

            System.out.printf("Camp Report for attendees %s generated successfully and saved at %s.\n", campName,
                    FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        System.out.println("Press enter to go back.");
        sc.nextLine();
        throw new PageBackException();
    }

    private static void generateCommitteeReport(String campID) throws PageBackException, ModelNotFoundException {
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

            // Writing list of camp committee members to the CSV file
            writer.write("List of Camp Committee Members,Points");
            writer.newLine();
            for (Student campCommMember : campCommMembers) {
                writer.write(String.format("%s,%d",
                        campCommMember.getUserName(), campCommMember.getPoints()));
                writer.newLine();
            }

            System.out.printf(
                    "Camp Committee Members Performance report for %s generated successfully and saved at %s.\n",
                    campName,
                    FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        System.out.println("Press enter to go back.");
        sc.nextLine();
        throw new PageBackException();
    }

    public static void generateEnquiryReport(String campID) throws PageBackException, ModelNotFoundException {
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

            // Writing list of camp committee members to the CSV file
            writer.newLine();
            writer.write("Enquiries");
            writer.newLine();
            for (Enquiry e : enquiries) {

                writer.write("RequestID,RequestStatus,SenderID,ReplierID,Message,Reply");
                writer.newLine();
                writer.write(String.format("%s,%s,%s,%s,%s,%s",
                        e.getID(), e.getRequestStatus().toString(), e.getSenderID(), e.getReplierID(), e.getMessage(),
                        e.getReply()));
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

    private static void generateStudentsReportForStaffCamps(List<Camp> camps, Staff staff) throws PageBackException {
        Scanner sc = new Scanner(System.in);
        ChangePage.changePage();
        System.out.printf("Generating Camp Report for %s...\n", staff.getID());

        String staffID = staff.getID();
        String FILE_PATH = RESOURCE_LOCATION + "/data/report/report_students_" + staffID + ".csv";

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

    private static void generateAttendeeReportForStaffCamps(List<Camp> camps, Staff staff) throws PageBackException {
        Scanner sc = new Scanner(System.in);
        ChangePage.changePage();
        System.out.printf("Generating Camp Report for %s...\n", staff.getID());

        String staffID = staff.getID();
        String FILE_PATH = RESOURCE_LOCATION + "/data/report/report_attendees_" + staffID + ".csv";

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
            }

            System.out.printf("All camp reports for %s generated successfully and saved at %s.\n", staffID, FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        System.out.println("Press enter to go back.");
        sc.nextLine();
        throw new PageBackException();
    }

    private static void generateCommitteeReportForStaffCamps(List<Camp> camps, Staff staff) throws PageBackException {
        Scanner sc = new Scanner(System.in);
        ChangePage.changePage();
        System.out.printf("Generating Camp Report for %s...\n", staff.getID());

        String staffID = staff.getID();
        String FILE_PATH = RESOURCE_LOCATION + "/data/report/report_committee_" + staffID + ".csv";

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

                List<Student> campCommMembers = getAllCampCommByCamp(camp);

                // Writing camp details to the CSV file
                writer.write(String.format("%s,%s,%s,%s,%s,%d,%d,%d,%d,%s",
                        campName, campDates, campRegistrationDeadline, openTo.toString(), location,
                        currentAttendeeSlots, totalAttendeeSlots, currentCampCommSlots, totalCampCommSlots,
                        description));
                writer.newLine();
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

    private static void generateEnquiryReportForStaffCamps(List<Camp> camps, Staff staff) throws PageBackException {
        Scanner sc = new Scanner(System.in);
        ChangePage.changePage();
        System.out.printf("Generating Enquiry Report for %s...\n", staff.getID());

        String staffID = staff.getID();
        String FILE_PATH = RESOURCE_LOCATION + "/data/report/report_enquiry_" + staffID + ".csv";

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

                List<Enquiry> enquiries = getAllEnquiriesByCamp(camp);

                // Writing camp details to the CSV file
                writer.write(String.format("%s,%s,%s,%s,%s,%d,%d,%d,%d,%s",
                        campName, campDates, campRegistrationDeadline, openTo.toString(), location,
                        currentAttendeeSlots, totalAttendeeSlots, currentCampCommSlots, totalCampCommSlots,
                        description));
                writer.newLine();
                writer.newLine();

                // Writing list of camp committee members to the CSV file
                writer.newLine();
                writer.write("Enquiries");
                writer.newLine();
                for (Enquiry e : enquiries) {

                    writer.write("RequestID,RequestStatus,SenderID,ReplierID,Message,Reply");
                    writer.newLine();
                    writer.write(String.format("%s,%s,%s,%s,%s,%s",
                            e.getID(), e.getRequestStatus().toString(), e.getSenderID(), e.getReplierID(),
                            e.getMessage(), e.getReply()));
                    writer.newLine();
                    writer.newLine();
                }

                System.out.printf("Enquiry report for %s generated successfully and saved at %s.\n", campName,
                        FILE_PATH);
            }

            System.out.printf("All camp reports for %s generated successfully and saved at %s.\n", staffID, FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        System.out.println("Press enter to go back.");
        sc.nextLine();
        throw new PageBackException();
    }

    private static void generatePerformanceReportForStaffCamps(List<Camp> camps, Staff staff) throws PageBackException {
        Scanner sc = new Scanner(System.in);
        ChangePage.changePage();
        System.out.printf("Generating Performance Report for %s's Camps\n", staff.getID());

        String staffID = staff.getID();
        String FILE_PATH = RESOURCE_LOCATION + "/data/report/report_performance_" + staffID + ".csv";

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

                List<Student> campCommMembers = getAllCampCommByCamp(camp);

                // Writing camp details to the CSV file
                writer.write(String.format("%s,%s,%s,%s,%s,%d,%d,%d,%d,%s",
                        campName, campDates, campRegistrationDeadline, openTo.toString(), location,
                        currentAttendeeSlots, totalAttendeeSlots, currentCampCommSlots, totalCampCommSlots,
                        description));
                writer.newLine();
                writer.newLine();

                // Writing list of camp committee members to the CSV file
                writer.write("List of Camp Committee Members,Points");
                writer.newLine();
                for (Student campCommMember : campCommMembers) {
                    writer.write(String.format("%s,%d",
                            campCommMember.getUserName(), campCommMember.getPoints()));
                    writer.newLine();
                }

            }

            System.out.printf("Performance report for %s generated successfully and saved at %s.\n", staffID,
                    FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        System.out.println("Press enter to go back.");
        sc.nextLine();
        throw new PageBackException();
    }

    private static List<Student> getAllAttendeesByCamp(Camp camp) {
        return StudentRepository.getInstance().findByRules(
                s -> s.getACamps().toLowerCase().contains(camp.getID().toLowerCase()))
                .stream()
                .map(s -> (Student) s)
                .collect(Collectors.toList());
    }

    private static List<Student> getAllCampCommByCamp(Camp camp) {
        return StudentRepository.getInstance().findByRules(
                s -> s.getCCamps().toLowerCase().contains(camp.getID().toLowerCase()))
                .stream()
                .map(s -> (Student) s)
                .collect(Collectors.toList());
    }

    private static List<Enquiry> getAllEnquiriesByCamp(Camp camp) {
        return EnquiryRepository.getInstance().findByRules(
                e -> e.getCampID().toLowerCase().contains(camp.getID().toLowerCase()))
                .stream()
                .map(e -> (Enquiry) e)
                .collect(Collectors.toList());
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

}