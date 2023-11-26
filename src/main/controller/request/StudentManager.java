package main.controller.request;

import main.boundary.modelviewer.ModelViewer;

import main.controller.camp.CampManager;
import main.controller.request.StudentManager;
import main.model.camp.Camp;
import main.model.user.Faculty;
import main.model.user.Student;
import main.model.request.Enquiry;
import main.model.request.RequestStatus;
import main.model.request.Suggestion;
import main.repository.camp.CampRepository;
import main.repository.request.EnquiryRepository;
import main.repository.request.SuggestionRepository;
import main.repository.user.StudentRepository;

import main.utils.exception.ModelAlreadyExistsException;
import main.utils.exception.ModelNotFoundException;
import main.utils.exception.PageBackException;
import main.utils.iocontrol.IntGetter;
import main.utils.parameters.EmptyID;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.ChangePage;
import main.utils.config.CurrentDate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import java.util.stream.Collectors;
import static main.utils.config.Location.RESOURCE_LOCATION;

/**
 * Manages operations related to student registrations and withdrawals from camps.
 */
public class StudentManager {
    /**
     * get the student by ID
     * 
     * @param studentID the student ID
     * @return the student
     * @throws ModelNotFoundException if the student is not found
     */
    public static Student getByID(String studentID) throws ModelNotFoundException {
        return StudentRepository.getInstance().getByID(studentID);
    }

    /**
     * Initiates the process for a student to register as an attendee for a camp.
     *
     * @param student the student.
     * @throws PageBackException if the user wants to go back.
     */
    public static void registerCampAttendee(Student student) throws PageBackException {
        ChangePage.changePage();
        System.out.println("Here is the list of available camps: ");
        ModelViewer.displayListOfDisplayable(CampManager.getAllVisibleCamps());
        System.out.print("Please enter the camp ID: ");
        String campID = new Scanner(System.in).nextLine();
        campID = campID.toUpperCase();
        if (CampManager.notContainsCampByID(campID)) {
            System.out.println("Camp ID is invalid.");
            System.out.println("Press Enter to go back, or enter [r] to retry.");
            String choice = new Scanner(System.in).nextLine();
            if (choice.equals("r")) {
                registerCampAttendee(student);
            }
            throw new PageBackException();
        }
        Camp camp;
        try {
            camp = CampManager.getByID(campID);
            if (student.getPCamps().contains(campID)) {
                System.out
                        .println("You are not allowed to register from this camp that you withdrawn from previously.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCampAttendee(student);
                }
                throw new PageBackException();
            }
            else if (student.getCCamps().equalsIgnoreCase(campID)) {
                System.out.println("You are already a camp committee for this camp.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCampAttendee(student);
                }
                throw new PageBackException();
            }
            else if (student.getACamps().equalsIgnoreCase(campID)) {
                System.out.println("You are already an attendee for this camp.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCampAttendee(student);
                }
                throw new PageBackException();
            }
            else if (checkClash(student, camp)) {
                System.out.println("This camp's dates clashes with your other registered camps.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCampAttendee(student);
                }
                throw new PageBackException();
            }
            else if (Integer.parseInt(CurrentDate.DATE) >= Integer.parseInt(camp.getRegistrationClosingDate())) {
                System.out.println("Camp Registration Closed.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCampAttendee(student);
                }
                throw new PageBackException();
            }
            else if (camp.getFilledSlots() >= camp.getTotalSlots()) {
                System.out.println("Attendee Slots maxed.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCampAttendee(student);
                }
                throw new PageBackException();
            }
            else if (!(camp.getOpenTo().toString().equals("NTU"))
                    && !(camp.getOpenTo().toString().equals(student.getFaculty().toString())))
                                                                                               
            {
                System.out.println("You don't match the faculty.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCampAttendee(student);
                }
                throw new PageBackException();
            }

        } catch (ModelNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.print("Are you sure you want to register for this camp? (y/[n]): ");
        String choice = new Scanner(System.in).nextLine();
        if (choice.equalsIgnoreCase("y")) {
            try {
                CampManager.registerCampAttendee(campID, student.getID());
                System.out.println("Registered for Camp!");
            } catch (Exception e) {
                System.out.println("Enter [b] to go back, or press enter to retry.");
                String yNChoice = new Scanner(System.in).nextLine();
                if (yNChoice.equals("b")) {
                    throw new PageBackException();
                } else {
                    registerCampAttendee(student);
                }
            }
        } else {
            System.out.println("Registration cancelled.");

        }

        System.out.println("Press Enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * Checks for date clashes between the dates of the camp the student wants to register
     * for and the dates of their existing registered camps.
     *
     * @param student the student.
     * @param camp the camp the student wants to register for.
     * @return true if there is a date clash, false otherwise.
     */
    public static boolean checkClash(Student student, Camp camp) {
        if (student.getACamps().equals("null") && student.getCCamps().equals("null")) {
            return false;
        }

        List<String> campIds = new ArrayList<>();

        if (!student.getACamps().equals("null")) {
            String[] aCampsArray = student.getACamps().split(",");
            campIds.addAll(Arrays.asList(aCampsArray));
        }

        if (!student.getCCamps().equals("null")) {
            campIds.add(student.getCCamps());
        }

        for (String campId : campIds) {
            try {
                Camp registeredCamp = CampRepository.getInstance().getByID(campId);

                if (hasDateClash(registeredCamp.getDates(), camp.getDates())) {
                    return true; // Clash detected
                }
            } catch (ModelNotFoundException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * Checks for date clashes between two date ranges.
     *
     * @param dates1 the first date range.
     * @param dates2 the second date range.
     * @return true if there is a date clash, false otherwise.
     */
    public static boolean hasDateClash(String dates1, String dates2) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date startDate1 = dateFormat.parse(dates1.split("-")[0]);
            Date endDate1 = dateFormat.parse(dates1.split("-")[1]);
            Date startDate2 = dateFormat.parse(dates2.split("-")[0]);
            Date endDate2 = dateFormat.parse(dates2.split("-")[1]);

            return !(endDate1.before(startDate2) || startDate1.after(endDate2));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Initiates the process for a student to withdraw from being an attendee for a camp.
     *
     * @param student the student.
     * @throws PageBackException if the user wants to go back.
     */
    public static void withdrawCampAttendee(Student student) throws PageBackException {
        ChangePage.changePage();

        System.out.println(
                "Important notification: Students who is a member of the commitee of a camp cannot withdraw from that camp. ");

        if (EmptyID.isEmptyID(student.getACamps()) && EmptyID.isEmptyID(student.getCCamps())) {
            System.out.println("You are not registered for any camp.");
            System.out.println("Press Enter to go back.");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }

        Map<Camp, String> camps = CampManager.getStudentcamps(student);
        if (camps == null) {
            System.out.println("Student has not registered into any camps yet.");
        } else {
            ModelViewer.displayListOfCampsWithType(camps);
        }
        String campID; 
        do {
            System.out.print("Please enter the camp ID: ");
            campID = new Scanner(System.in).nextLine();
            if (campID==""){
                System.out.println("You have not entered anything. Please reenter!"); 
            }
            
        }while(campID==""); 
        campID = campID.toUpperCase();

        if (!student.getCCamps().equals("null")) {
            String CCamps = student.getCCamps();
            if (CCamps.toLowerCase().equals(campID.toLowerCase())) {
                System.out.printf("You are a commitee of camp %s, you are not allow to withdraw from this camp!\n",
                        campID);
                System.out.println("Press Enter to go back.");
                new Scanner(System.in).nextLine();
                throw new PageBackException();
            }
        }

        String ACamps = student.getACamps();
        if (!ACamps.toLowerCase().contains(campID.toLowerCase())) {
            System.out.println("Camp ID is invalid");
            System.out.println("Press Enter to go back.");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }

        System.out.println("Are you sure you want to deregister from this camp? (y/[n])");
        String choice = new Scanner(System.in).nextLine();
        if (!choice.equalsIgnoreCase("y")) {
            System.out.println("Deregistration cancelled.");
            System.out.println("Press Enter to go back.");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }

        try {
            CampManager.withdrawCampAttendee(campID, student.getID());
        } catch (Exception e) {
            System.out.println("Deregistration Error: " + e.getMessage());
            System.out.println("Enter [b] to go back, or press enter to retry.");
            String choice2 = new Scanner(System.in).nextLine();
            if (!choice2.equals("b")) {
                withdrawCampAttendee(student);
            }
            throw new PageBackException();
        }
        System.out.println("Successfully withdrawn from camp");
        System.out.println("Press Enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * Initiates the process for a student to register as a camp committee member.
     *
     * @param student the student.
     * @throws PageBackException if the user wants to go back.
     */
    public static void registerCampCommittee(Student student) throws PageBackException {
        ChangePage.changePage();
        if (!student.getCCamps().equals("null")) {
            System.out.println("You are already a camp committee for a camp.");
            System.out.println("Press Enter to go back.");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }

        System.out.println("Here is the list of available camps: ");
        ModelViewer.displayListOfDisplayable(CampManager.getAllVisibleCamps());
        System.out.print("Please enter the camp ID: ");
        String campID = new Scanner(System.in).nextLine();
        campID = campID.toUpperCase();
        if (CampManager.notContainsCampByID(campID)) {
            System.out.println("Camp ID is invalid.");
            System.out.println("Press Enter to go back, or enter [r] to retry.");
            String choice = new Scanner(System.in).nextLine();
            if (choice.equals("r")) {
                registerCampCommittee(student);
            }
            throw new PageBackException();
        }
        Camp camp;
        try {
            camp = CampManager.getByID(campID);
            if (student.getPCamps().contains(campID)) {
                System.out
                        .println("You are not allowed to register from this camp that you withdrawn from previously.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCampCommittee(student);
                }
                throw new PageBackException();
            }
            if (student.getACamps().contains(campID)) {
                System.out.println("You are already an attendee for this camp.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCampCommittee(student);
                }
                throw new PageBackException();
            }
            if (checkClash(student, camp)) {
                System.out.println("This camp's dates clashes with your other registered camps.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCampCommittee(student);
                }
                throw new PageBackException();
            }
            else if (Integer.parseInt(CurrentDate.DATE) >= Integer.parseInt(camp.getRegistrationClosingDate())) {
                System.out.println("Camp Registration Closed.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCampCommittee(student);
                }
                throw new PageBackException();
            }
            else if (camp.getFilledCampCommSlots() >= camp.getCampCommSlots()) {
                System.out.println("Camp Committee Slots maxed.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCampCommittee(student);
                }
                throw new PageBackException();
            }
            else if (!(camp.getOpenTo().toString().equals("NTU")) // check if it's open to everyone "NTU"
                    && !(camp.getOpenTo().toString().equals(student.getFaculty().toString()))) // check if it's your
                                                                                               // faculty
            {
                System.out.println("You don't match the faculty.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCampAttendee(student);
                }
                throw new PageBackException();
            }


        } catch (ModelNotFoundException e) {
            throw new RuntimeException(e);
        }
        ChangePage.changePage();
        System.out.println("Here is the camp information: ");
        try {
            Camp camp1 = CampRepository.getInstance().getByID(campID);
            ModelViewer.displaySingleDisplayable(camp1);
        } catch (ModelNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.print("Are you sure you want to register for this camp? (y/[n]): ");
        String choice = new Scanner(System.in).nextLine();
        if (choice.equalsIgnoreCase("y")) {
            try {
                CampManager.registerCampCommittee(campID, student.getID());
                System.out.println("Registered for Camp!");
            } catch (Exception e) {
                System.out.println("Enter [b] to go back, or press enter to retry.");
                String yNChoice = new Scanner(System.in).nextLine();
                if (yNChoice.equals("b")) {
                    throw new PageBackException();
                } else {
                    registerCampCommittee(student);
                }
            }
        } else {
            System.out.println("Registration cancelled.");

        }

        System.out.println("Press Enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * Initiates the process for a student to submit an enquiry for a camp.
     *
     * @param student the student.
     * @throws PageBackException if the user wants to go back.
     */
    public static void submitEnquiry(Student student) throws PageBackException {
        ChangePage.changePage();
        System.out.println("Here is the list of available camps: ");
        ModelViewer.displayListOfDisplayable(CampManager.getAllVisibleCamps());
        System.out.println("Our staff are always devoted themselves to resolve the enquiry from students!");
        System.out.println(
                "First of all, would you mind telling us the CampID that you want to submit this enquiry to: ");
        String studentID = student.getID();
        String campID = new Scanner(System.in).nextLine();
        campID = campID.toUpperCase();
        System.out.println("Warmly remindering: You are sending an enquiry to camp " + campID);
        try {
            CampManager.getCampByID(campID);
        } catch (ModelNotFoundException e) {
            ChangePage.changePage();
            System.out.println("Camp ID is invalid.");
            System.out.println("Enter [b] to go back, or press enter to retry.");
            String yNChoice = new Scanner(System.in).nextLine();
            if (yNChoice.equals("b")) {
                throw new PageBackException();
            } else {
                submitEnquiry(student);
            }
        }
        String message;
        do {
            System.out.printf("Please input the enquiry that you have regarding camp %s: \n", campID);
            message = new Scanner(System.in).nextLine();
            if (message == "") {
                System.out.println("You have not submitted any enquiry, please reenter!");
            }

        } while (message == "");
        try {
            RequestManager.createEnquiry(campID, studentID, message);
        } catch (ModelAlreadyExistsException e) {
            throw new RuntimeException(e);
        }

        ChangePage.changePage();

        System.out.println("Enquiry Submitted");
        System.out.println("Press Enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
    * Displays the list of enquiries made by the student and provides options to edit, delete, or exit.
    *
    * @param student the student.
    * @throws PageBackException if the user wants to go back.
    * @throws ModelNotFoundException if the model is not found.
    */
    public static void viewEnquiry(Student student) throws PageBackException, ModelNotFoundException {
        ChangePage.changePage();
        System.out.println("Here is the list of Enquiries you made");
        ModelViewer.displayListOfDisplayable((RequestManager.viewEnquiryBySender(student.getID())));// change to by user
        System.out.println();

        System.out.println("1. Edit Enquiry");
        System.out.println("2. Delete Enquiry");
        System.out.println("3. Exit");

        boolean isValidChoice;
        int choice; 
        do {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                    System.out.println("Please enter your choice:");
                    try {
                        choice= new Scanner(System.in).nextInt();
                        System.out.println("You entered: " + choice);
                        break;
                    } catch (InputMismatchException e) {
                         System.out.println("Invalid input. Please enter a valid integer.");
                    }
            }

            switch (choice) {
                case 1:
                    editEnquiry(student);
                    isValidChoice = true;
                    break;
                case 2:
                    deleteEnquiry(student);
                    isValidChoice = true;
                    break;
                case 3:
                    throw new PageBackException();
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    isValidChoice = false;
            }
        } while (!isValidChoice);
    }

    /**
    * Allows the student to edit an existing enquiry.
    *
    * @param student the student.
    * @throws PageBackException if the user wants to go back.
    * @throws ModelNotFoundException if the model is not found.
    */
    private static void editEnquiry(Student student) throws PageBackException, ModelNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ID of Enquiry to edit");
        String enquiryID = sc.nextLine();
        enquiryID = enquiryID.toUpperCase();
        try {
            RequestManager.getEnquiryByID(enquiryID);
        } catch (ModelNotFoundException e) {
            ChangePage.changePage();
            System.out.println("Enquiry ID is invalid.");
            System.out.println("Press enter to go back.");
            sc.nextLine();
            throw new PageBackException();
        }

        Enquiry enquiryToEdit = RequestManager.getEnquiryByID(enquiryID);
        String newMessage; 
        do {
        System.out.println("Enter new message:");
        newMessage = sc.nextLine();
        if (newMessage==""){
            System.out.println("You have not entered anything. Please reenter!"); 
        }
        }while(newMessage==""); 
        enquiryToEdit.setMessage(newMessage);
        RequestManager.updateEnquiry(enquiryID, enquiryToEdit);
        System.out.println("Successfully updated enquiry!");
        System.out.println(BoundaryStrings.separator);
        System.out.println();
        System.out.println("Press enter to go back.");
        sc.nextLine();
        throw new PageBackException();
    }

    /**
    * Allows the student to delete an existing enquiry.
    *
    * @param student the student.
    * @throws PageBackException if the user wants to go back.
    * @throws ModelNotFoundException if the model is not found.
    */
    private static void deleteEnquiry(Student student) throws PageBackException, ModelNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ID of Enquiry to delete");
        String enquiryID = sc.nextLine();
        enquiryID = enquiryID.toUpperCase();
        try {
            RequestManager.getEnquiryByID(enquiryID);
        } catch (ModelNotFoundException e) {
            ChangePage.changePage();
            System.out.println("Enquiry ID is invalid.");
            System.out.println("Press enter to go back.");
            sc.nextLine();
            throw new PageBackException();
        }

        Enquiry enquiryToDelete = RequestManager.getEnquiryByID(enquiryID);

        System.out.println("Are you sure you want to delete this Enquiry? (Y/N)");

        String input = sc.nextLine();
        if (!input.equalsIgnoreCase("Y")) {
            ChangePage.changePage();
            System.out.println("Enquiry deletion cancelled!");
            System.out.println("Press enter to continue");
            sc.nextLine();
            throw new PageBackException();
        }

        try {
            EnquiryRepository.getInstance().remove(enquiryToDelete.getID());
        } catch (ModelNotFoundException e) {
            throw new RuntimeException(e);
        }
        ChangePage.changePage();
        System.out.println("Enquiry deleted successfully!");
        System.out.println("Press enter to continue");
        sc.nextLine();
        throw new PageBackException();
    }
    
    /**
    * Allows the student to submit a suggestion if they are a camp committee member.
    *
    * @param student the student.
    * @throws PageBackException if the user wants to go back.
    * @throws ModelNotFoundException if the model is not found.
    */
    public static void submitSuggestion(Student student) throws PageBackException, ModelNotFoundException {
        Scanner sc = new Scanner(System.in);
        if (student.getCCamps().equals("null")) {
            ChangePage.changePage();
            System.out.println("You are not a camp committee member.");
            System.out.println("Press enter to go back.");
            sc.nextLine();
            throw new PageBackException();
        } else {
            System.out.println("Here is the list of available camps: ");
            ModelViewer.displayListOfDisplayable(CampManager.getAllVisibleCamps());

            String studentID = student.getID();
            String campID;
            do {
                System.out.println("Enter Camp ID to create Suggestion");
                campID = sc.nextLine(); 
                if (campID==""){
                    System.out.println("You have not entered anything. Please reenter!"); 
                }
            }while (campID==""); 
            campID = campID.toUpperCase();

            try {
                CampManager.getCampByID(campID);
            } catch (ModelNotFoundException e) {
                ChangePage.changePage();
                System.out.println("Camp ID is invalid.");
                System.out.println("Enter [b] to go back, or press enter to retry.");
                String yNChoice = sc.nextLine();
                if (yNChoice.equals("b")) {
                    throw new PageBackException();
                } else {
                    submitEnquiry(student);
                }
            }

            Suggestion s;

            try {
                s = RequestManager.createSuggestion(campID, studentID);
            } catch (ModelAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
            editSuggestion(s, student);
        }
    }
    
    /**
    * Allows the student to edit an existing suggestion.
    *
    * @param s       the suggestion to edit.
    * @param student the student.
    * @throws PageBackException      if the user wants to go back.
    * @throws ModelNotFoundException if the model is not found.
    */
    private static void editSuggestion(Suggestion s, Student student) throws PageBackException, ModelNotFoundException {
        Scanner scanner = new Scanner(System.in);
        ChangePage.changePage();

        System.out.println("Which field do you want to suggest changes, press 0 to go back to upper menu");
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

        int choice = IntGetter.readInt();
        if (choice == 0)
            throw new PageBackException();
        int valid = 1;
        do {
            if (valid == 0) {
                choice = IntGetter.readInt();
            }
            valid = 1;
            if (choice > 9) {
                valid = 0;
                System.out.println("Invalid choice of field, please reenter!");
            }
        } while (choice > 9);
        int suggest = -1;
        String suggt = "";
        System.out.println("The new value of the field to update");
        do {
            suggt = "";
            if ((choice == 6) || (choice == 7)) {
                
                while (true) {
                    System.out.println("Enter a camp attendee Slots:");
                    try {
                        suggest= new Scanner(System.in).nextInt();
                        System.out.println("You entered: " + suggest);
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid integer.");
                    }
                }
                suggt="default"; 
            } else if (choice == 4) {
                System.out.println("Please enter one of the following faculties name (case is not important):");
                System.out.println("    NA,\r\n" + //
                        "    NTU,\r\n" + //
                        "    ADM,\r\n" + //
                        "    EEE,\r\n" + //
                        "    NBS,\r\n" + //
                        "    SCSE,\r\n" + //
                        "    SSS");
                int included = 0;
                do {
                    suggt = scanner.nextLine();
                    suggt = suggt.toUpperCase();
                    included = 0;
                    Faculty fc[] = Faculty.values();
                    for (Faculty fcl : fc) {
                        if (fcl.name().equalsIgnoreCase(suggt)) {
                            included = 1;
                            break;
                        }
                    }
                    if (included == 0) {
                        System.out.println("Please enter one of the following faculties name (case is not important):");
                        System.out.println("    NA,\r\n" + //
                                "    NTU,\r\n" + //
                                "    ADM,\r\n" + //
                                "    EEE,\r\n" + //
                                "    NBS,\r\n" + //
                                "    SCSE,\r\n" + //
                                "    SSS");
                    }
                } while (included == 0);
            } else {
                suggt = scanner.nextLine();
            }
            if ((Integer.toString(suggest) == "") || (suggt == "")) {
                System.out.println("You have not entered any new value, please reenter!");
            }
        } while ((Integer.toString(suggest) == "") || (suggt == ""));
        switch (choice) {
            case 0 -> throw new PageBackException();
            case 1 -> s.setCampName(suggt);
            case 2 -> s.setDates(suggt);
            case 3 -> s.setRegistrationClosingDate(suggt);
            case 4 -> s.setCampType(Faculty.valueOf(suggt));
            case 5 -> s.setLocation(suggt);
            case 6 -> s.setTotalSlots(suggest);
            case 7 -> s.setCampCommSlots(suggest);
            case 8 -> s.setDescription(suggt);
            case 9 -> s.setCampStaff(suggt);
        }
        System.out.println("Have other field to update?");
        System.out.println("\t0. No");
        System.out.println("\t1. Yes");
        choice = scanner.nextInt();
        if (choice == 1) {
            RequestManager.updateSuggestion(s.getID(), s);
            editSuggestion(s, student);
        }
        RequestManager.updateSuggestion(s.getID(), s);
        ChangePage.changePage();
        System.out.println("Suggestion updated!");
        System.out.println("Press Enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }
    
    /**
    * Displays the list of suggestions made by the student and provides options to edit, delete, or exit.
    *
    * @param student the student.
    * @throws PageBackException      if the user wants to go back.
    * @throws ModelNotFoundException if the model is not found.
    */
    public static void viewSuggestion(Student student) throws PageBackException, ModelNotFoundException {
        Scanner sc = new Scanner(System.in);
        if (student.getCCamps().equals("null")) {
            ChangePage.changePage();
            System.out.println("You are not a camp committee member.");
            System.out.println("Press enter to go back.");
            sc.nextLine();
            throw new PageBackException();
        } else {
            ChangePage.changePage();
            System.out.println("Here is the list of Suggestion you made");
            ModelViewer.displayListOfDisplayable((RequestManager.viewSuggestionBySender(student.getID())));

            System.out.println();
            System.out.println("1. Edit Suggestion");
            System.out.println("2. Delete Suggestion");
            System.out.println("3. Exit");

            boolean isValidChoice;
            do {
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:

                        System.out.println("Enter ID of Suggestion to edit");
                        String suggestionID = sc.nextLine();
                        suggestionID = suggestionID.toUpperCase();
                        try {
                            RequestManager.getSuggestionByID(suggestionID);
                        } catch (ModelNotFoundException e) {
                            ChangePage.changePage();
                            System.out.println("Suggestion ID is invalid.");
                            System.out.println("Press enter to go back.");
                            sc.nextLine();
                            throw new PageBackException();
                        }
                        Suggestion suggestionToEdit = RequestManager.getSuggestionByID(suggestionID);
                        editSuggestion(suggestionToEdit, student);
                        isValidChoice = true;
                        break;
                    case 2:
                        deleteSuggestion(student);
                        isValidChoice = true;
                        break;
                    case 3:
                        throw new PageBackException();
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                        isValidChoice = false;
                }
            } while (!isValidChoice);
        }
    }

    /**
    * Deletes a suggestion based on the student's choice.
    *
    * @param student the student.
    * @throws PageBackException      if the user wants to go back.
    * @throws ModelNotFoundException if the model is not found.
    */    
    private static void deleteSuggestion(Student student) throws PageBackException, ModelNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ID of Suggestion to delete");
        String suggestionID = sc.nextLine();
        suggestionID = suggestionID.toUpperCase();
        try {
            RequestManager.getSuggestionByID(suggestionID);
        } catch (ModelNotFoundException e) {
            ChangePage.changePage();
            System.out.println("Suggestion ID is invalid.");
            System.out.println("Press enter to go back.");
            sc.nextLine();
            throw new PageBackException();
        }

        Suggestion suggestionToDelete = RequestManager.getSuggestionByID(suggestionID);

        System.out.println("Are you sure you want to delete this Suggestion? (Y/N)");

        String input = sc.nextLine();
        if (!input.equalsIgnoreCase("Y")) {
            ChangePage.changePage();
            System.out.println("Suggestion deletion cancelled!");
            System.out.println("Press enter to continue");
            sc.nextLine();
            throw new PageBackException();
        }

        try {
            SuggestionRepository.getInstance().remove(suggestionToDelete.getID());
        } catch (ModelNotFoundException e) {
            throw new RuntimeException(e);
        }
        ChangePage.changePage();
        System.out.println("Enquiry deleted successfully!");
        System.out.println("Press enter to continue");
        sc.nextLine();
        throw new PageBackException();
    }

    /**
    * Displays pending enquiries for a camp committee member to reply to.
    *
    * @param student the student.
    * @throws PageBackException      if the user wants to go back.
    * @throws ModelNotFoundException if the model is not found.
    */
    public static void commViewPendingEnquiries(Student student) throws PageBackException, ModelNotFoundException {
        Scanner sc = new Scanner(System.in);
        if (student.getCCamps().equals("null")) {
            ChangePage.changePage();
            System.out.println("You are not a camp committee member.");
            System.out.println("Press enter to go back.");
            sc.nextLine();
            throw new PageBackException();
        } else {
            ChangePage.changePage();
            System.out.println(BoundaryStrings.separator);
            System.out.println("View Pending Enquiries");
            System.out.println();
            String campID = student.getCCamps().toUpperCase();
            // if empty
            ModelViewer.displayListOfDisplayable(
                    RequestManager.getAllPendingEnquiriesByCampID(campID));
            System.out.println("Which enquiry ID do you want to reply to?");
            Scanner scanner = new Scanner(System.in);
            String enquiryID = scanner.nextLine();
            try {
                RequestManager.getEnquiryByID(enquiryID);
            } catch (ModelNotFoundException e) {
                ChangePage.changePage();
                System.out.println("Enquiry ID is invalid.");
                System.out.println("Press enter to go back.");
                sc.nextLine();
                throw new PageBackException();
            }

            Enquiry enquiry = (Enquiry) EnquiryRepository.getInstance().getByID(enquiryID);

            if (!enquiry.getCampID().equals(campID)) {
                ChangePage.changePage();
                System.out.println("Enquiry ID is invalid");
                System.out.println("Press enter to go back.");
                sc.nextLine();
                throw new PageBackException();
            }
            // check that member is not replying to himself
            if (enquiry.getSenderID().equals(student.getID())) {
                ChangePage.changePage();
                System.out.println("You cannot reply to an enquiry you made.");
                System.out.println("Press enter to go back.");
                sc.nextLine();
                throw new PageBackException();
            }

            else {
                System.out.println("Type Reply Message below:");
                String message = scanner.nextLine();
                enquiry.setReply(message);
                enquiry.setReplierID(student.getID());
                enquiry.setRequestStatus(RequestStatus.REPLIED);
                EnquiryRepository.getInstance().update(enquiry);
                student.addPoint();
                StudentRepository.getInstance().update(student);
                System.out.println("Successfully replied an enquiry:");
                System.out.println("A point has been rewarded to you!");
                System.out.println();
                ChangePage.changePage();
                System.out.println("Have other enquiry to reply?");
                System.out.println("\t0. No");
                System.out.println("\t1. Yes");
                int choice = scanner.nextInt();
                if (choice == 1) {
                    commViewPendingEnquiries(student);
                }
                ModelViewer.displaySingleDisplayable(enquiry);
                System.out.println(BoundaryStrings.separator);
                System.out.println("Press enter to go back.");
                scanner.nextLine();
                throw new PageBackException();
            }
        }
    }

    /**
    * Generates a report for a specific camp based on the student's choice.
    *
    * @param student the student.
    * @throws PageBackException      if the user wants to go back.
    * @throws ModelNotFoundException if the model is not found.
    */
    public static void generateCampList(Student student) throws PageBackException, ModelNotFoundException {
        Scanner sc = new Scanner(System.in);
        if (student.getCCamps().equals("null")) {
            ChangePage.changePage();
            System.out.println("You are not a camp committee member.");
            System.out.println("Press enter to go back.");
            sc.nextLine();
            throw new PageBackException();
        } else {
            ChangePage.changePage();
            String campID = student.getCCamps().toUpperCase();
            Camp camp = CampRepository.getInstance().getByID(campID);

            System.out.println("Select the type of report for the specific camp:");
            System.out.println("\t1. All Students List");
            System.out.println("\t2. Attendee List");
            System.out.println("\t3. Committee List");
            System.out.print("Enter your choice: ");
            int specificCampReportChoice = IntGetter.readInt();

            System.out.printf("Generating Camp Report for %s...\n", camp.getCampName());

            String studentID = student.getID();
            String FILE_PATH = RESOURCE_LOCATION + "/data/report/report_" + studentID + ".csv";

            switch (specificCampReportChoice) {
                case 1:
                    FILE_PATH = RESOURCE_LOCATION + "/data/report/report_students_" + campID + "_" + studentID + ".csv";
                    break;
                case 2:
                    FILE_PATH = RESOURCE_LOCATION + "/data/report/report_attendee_" + campID + "_" + studentID + ".csv";
                    break;
                case 3:
                    FILE_PATH = RESOURCE_LOCATION + "/data/report/report_committee_" + campID + "_" + studentID
                            + ".csv";
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
                    new Scanner(System.in).nextLine();
                    generateCampList(student);
            }

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
                writer.write("Camp Name,Camp Dates,Registration Deadline,Open To,Location,"
                        + "Current Attendee Slots,Total Attendee Slots,Current Camp Comm Slots,Total Camp Comm Slots,Description");
                writer.newLine();

                writer.write(String.format("%s,%s,%s,%s,%s,%d,%d,%d,%d,%s",
                        campName, campDates, campRegistrationDeadline, openTo.toString(), location,
                        currentAttendeeSlots, totalAttendeeSlots, currentCampCommSlots, totalCampCommSlots,
                        description));
                writer.newLine();

                if (specificCampReportChoice == 1) {
                    writer.write("List of Attendees");
                    writer.newLine();
                    for (Student attendee : attendees) {
                        writer.write(attendee.getUserName());
                        writer.newLine();
                    }

                    writer.write("List of Camp Committee Members");
                    writer.newLine();
                    for (Student campCommMember : campCommMembers) {
                        writer.write(campCommMember.getUserName());                         
                        writer.newLine();
                    }
                } else if (specificCampReportChoice == 2) {
                    writer.write("List of Attendees");
                    writer.newLine();
                    for (Student attendee : attendees) {
                        writer.write(attendee.getUserName());
                        writer.newLine();
                    }
                } else if (specificCampReportChoice == 3) {
                    writer.write("List of Camp Committee Members");
                    writer.newLine();
                    for (Student campCommMember : campCommMembers) {
                        writer.write(campCommMember.getUserName());
                                                                
                        writer.newLine();
                    }
                }

                System.out.printf("Camp Report for %s generated successfully and saved at %s.\n", campName, FILE_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Press enter to go back.");
            sc.nextLine();
            throw new PageBackException();
        }
    }

    /**
    * Retrieves a list of all attendees for a specific camp.
    *
    * @param camp the camp.
    * @return a list of attendees.
    */
    public static List<Student> getAllAttendeesByCamp(Camp camp) {
        return StudentRepository.getInstance().findByRules(
                s -> s.getACamps().toLowerCase().contains(camp.getID().toLowerCase()))
                .stream()
                .map(s -> (Student) s)
                .collect(Collectors.toList());
    }

    /**
    * Retrieves a list of all camp committee members for a specific camp.
    *
    * @param camp the camp.
    * @return a list of camp committee members.
    */
    public static List<Student> getAllCampCommByCamp(Camp camp) {
        return StudentRepository.getInstance().findByRules(
                s -> s.getCCamps().toLowerCase().contains(camp.getID().toLowerCase()))
                .stream()
                .map(s -> (Student) s)
                .collect(Collectors.toList());
    }
}
