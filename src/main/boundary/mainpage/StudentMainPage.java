package main.boundary.mainpage;

import main.boundary.account.ChangeAccountPassword;
import main.boundary.account.Logout;
import main.boundary.account.ViewUserProfile;
import main.boundary.modelviewer.ModelViewer;
import main.boundary.modelviewer.CampViewer;
import main.controller.account.AccountManager;
import main.controller.request.StudentManager;
import main.controller.camp.CampManager;
import main.controller.request.RequestManager; 
import main.model.user.*;
import main.model.camp.Camp;
import main.model.request.Enquiry; 
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
                    case 5 -> registerCampAttendee(student);
                    case 6 -> withdrawCampAttendee(student);
                    case 7 -> registerCampCommittee(student);
                    case 8 -> submitEnquiry(student);
                    case 9 -> viewEnquiry(student);
                    case 10 -> submitSuggestion(student);
                    case 11 -> viewSuggestion(student);
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

    // private static void viewHistoryAndStatusOfMyRequest(Student student) throws PageBackException {
    //     ChangePage.changePage();
    //     System.out.println("Here is the history and status of your request: ");
    //     ModelViewer.displayListOfDisplayable(StudentManager.getStudentRequestHistory(student.getID()));
    //     System.out.println("Press Enter to go back.");
    //     new Scanner(System.in).nextLine();
    //     throw new PageBackException();
    // }

    /**
     * This private method is called when the student wants to change the title of their project. It displays the history and status of the student's project, prompts the student to enter a new title, and sends a request to change the title to the system. If an error occurs, the student is given the option to go back or retry.
     *
     * @param student the student.
     * @throws PageBackException if the user wants to go back.
     */
    // private static void changeTitleForCamps(Student student) throws PageBackException, ModelNotFoundException {
    //     ChangePage.changePage();
    //     Camp camp;
    //     try {
    //         camp = CampRepository.getInstance().getByID(student.getProjectID());
    //     } catch (ModelNotFoundException e) {
    //         System.out.println("You are not registered for any camps.");
    //         System.out.println("Press Enter to go back.");
    //         new Scanner(System.in).nextLine();
    //         throw new PageBackException();
    //     }
    //     System.out.println("Here is your camps: ");
    //     ModelViewer.displaySingleDisplayable(camp);
    //     System.out.println("Are you sure you want to change the title of this camp?");
    //     System.out.println("Enter [y] to confirm, or press enter to go back.");
    //     String choice = new Scanner(System.in).nextLine();
    //     if (!choice.equalsIgnoreCase("y")) {
    //         throw new PageBackException();
    //     }
    //     System.out.println("Please enter the new title: ");
    //     String newTitle = new Scanner(System.in).nextLine();
    //     project.setProjectTitle(newTitle);
    //     ChangePage.changePage();
    //     System.out.println("Your new camp is: ");
    //     ModelViewer.displaySingleDisplayable(camp);
    //     System.out.println("Are you sure you want to change the title of this camp?");
    //     System.out.println("Enter [y] to confirm, or press enter to go back.");
    //     String choice1 = new Scanner(System.in).nextLine();
    //     if (!choice1.equalsIgnoreCase("y")) {
    //         throw new PageBackException();
    //     }
    //     try {
    //         StudentManager.changeCampTitle(project.getID(), newTitle, student.getID());
    //     } catch (Exception e) {
    //         System.out.println("Change Title Error: " + e.getMessage());
    //         System.out.println("Enter [b] to go back, or press enter to retry.");
    //         String choice2 = new Scanner(System.in).nextLine();
    //         if (!choice2.equals("b")) {
    //             changeTitleForCamp(student);
    //         }
    //         throw new PageBackException();
    //     }
    //     System.out.println("Successfully sent a request to change title");
    //     System.out.println("Press Enter to go back.");
    //     new Scanner(System.in).nextLine();
    //     throw new PageBackException();
    // }

    /**
     * This private method is called when the student wants to register for a project. It prompts the student to enter the project ID, sends a request to register for the project, and displays a success message. If an error occurs, the student is given the option to go back or retry.
     *
     * @param student the student.
     * @throws PageBackException if the user wants to go back.
     */
    private static void registerCampAttendee(Student student) throws PageBackException {
        ChangePage.changePage();
        // if (student.getStatus() == StudentStatus.REGISTERED || student.getStatus() == StudentStatus.DEREGISTERED) {
        //     System.out.println("You are already registered/deregistered for a project.");
        //     System.out.println("Press Enter to go back.");
        //     new Scanner(System.in).nextLine();
        //     throw new PageBackException();
        // }
        System.out.println("Here is the list of available camps: ");
        ModelViewer.displayListOfDisplayable(CampManager.getAllVisibleCamps());
        System.out.print("Please enter the camp ID: ");
        String campID = new Scanner(System.in).nextLine();
        if (CampManager.notContainsCampByID(campID)) {
            System.out.println("Camp not found.");
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
            //Check if previously registered
            if (student.getPCamps().contains(campID)) {
                System.out.println("You are not allowed to register from this camp that you withdrawn from previously.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCampAttendee(student);
                }
                throw new PageBackException();
            }
            //Check if already camp comm for this camp might be able to remove based on how we display avail camps
            else if (student.getCCamps().equals(campID)) {
                System.out.println("You are already a camp committee for this camp.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCampAttendee(student);
                }
                throw new PageBackException();
            }
            //Check if no camps registered previously + clash?
            //Check deadline
            else if (checkClash(student, camp)) {
                System.out.println("This camp's dates clashes with your other registered camps.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCampAttendee(student);
                }
                throw new PageBackException();
            }
            //Check deadline
            else if (Integer.parseInt(CurrentDate.DATE) >= Integer.parseInt(camp.getRegistrationClosingDate())) {
                System.out.println("Camp Registration Closed.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCampAttendee(student);
                }
                throw new PageBackException();
            }
            //Attendee slots maxed
            else if (camp.getFilledSlots() >= camp.getTotalSlots()) {
                System.out.println("Attendee Slots maxed.");
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
        // ChangePage.changePage();
        // System.out.println("Here is the project information: ");
        // try {
        //     Camp camp1 = CampRepository.getInstance().getByID(projectID);
        //     ModelViewer.displaySingleDisplayable(camp1);
        // } catch (ModelNotFoundException e) {
        //     throw new RuntimeException(e);
        // }
        System.out.print("Are you sure you want to register for this camp? (y/[n]): ");
        String choice = new Scanner(System.in).nextLine();
        if (choice.equalsIgnoreCase("y")) {
            try {
                // StudentManager.registerStudent(campID, student.getID());
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

    public static boolean checkClash(Student student, Camp camp) {
    // no camps registered initially so confirm no clashes
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

    // have camps registered initially

    for (String campId : campIds) {
        try {
            Camp registeredCamp = CampRepository.getInstance().getByID(campId);

            // Check for date clash
            if (hasDateClash(registeredCamp.getDates(), camp.getDates())) {
                return true; // Clash detected
            }
        } catch (ModelNotFoundException e) {
            // Handle the case where the camp is not found
            e.printStackTrace();
        }
    }

    // No clashes found
    return false;
}

    public static boolean hasDateClash(String dates1, String dates2) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

            // Parse dates from strings
            Date startDate1 = dateFormat.parse(dates1.split("-")[0]);
            Date endDate1 = dateFormat.parse(dates1.split("-")[1]);
            Date startDate2 = dateFormat.parse(dates2.split("-")[0]);
            Date endDate2 = dateFormat.parse(dates2.split("-")[1]);

            // Check for date clash
            return !(endDate1.before(startDate2) || startDate1.after(endDate2));
        } catch (ParseException e) {
            // Handle parsing exception
            e.printStackTrace();
            return false; // Assuming a clash if there's an exception
        }
    }

    /**
     * This private method is called when the student wants to deregister from a project. It prompts the student to enter the project ID, sends a request to deregister from the project, and displays a success message. If an error occurs, the student is given the option to go back or retry.
     *
     * @param student the student.
     * @throws PageBackException if the user wants to go back.
     */
 private static void withdrawCampAttendee(Student student) throws PageBackException {
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
        System.out.print("Please enter the camp ID: ");
        String campID = new Scanner(System.in).nextLine();
        // try {
        // Camp project = CampRepository.getInstance().getByID(student.getID());
        // ModelViewer.displaySingleDisplayable(project);
        // } catch (ModelNotFoundException e) {
        // throw new IllegalArgumentException("Camp not found.");
        // }

        if (!student.getCCamps().equals("null")) {
            String CCamps = student.getCCamps();
            if (CCamps.contains(campID)) {
                System.out.printf("You are a commitee of camp %s, you are not allow to withdraw from this camp!",
                        campID);
                System.out.println("Press Enter to go back.");
                new Scanner(System.in).nextLine();
                throw new PageBackException();
            }
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

    private static void registerCampCommittee(Student student) throws PageBackException {
        ChangePage.changePage();
        if (!student.getCCamps().equals("null")) {
            System.out.println("You are already a camp committe for a camp.");
            System.out.println("Press Enter to go back.");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }

        System.out.println("Here is the list of available camps: ");
        ModelViewer.displayListOfDisplayable(CampManager.getAllVisibleCamps());
        System.out.print("Please enter the camp ID: ");
        String campID = new Scanner(System.in).nextLine();
        if (CampManager.notContainsCampByID(campID)) {
            System.out.println("Camp not found.");
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
            //Check if already attendee for this camp might be able to remove based on how we display avail camps
            if (student.getACamps().contains(campID)) {
                System.out.println("You are already an attendee for this camp.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCampCommittee(student);
                }
                throw new PageBackException();
            }
            //Check if no camps registered previously + clash?
            //Check deadline
            if (checkClash(student, camp)) {
                System.out.println("This camp's dates clashes with your other registered camps.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCampCommittee(student);
                }
                throw new PageBackException();
            }
            //Check deadline
            else if (Integer.parseInt(CurrentDate.DATE) >= Integer.parseInt(camp.getRegistrationClosingDate())) {
                System.out.println("Camp Registration Closed.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCampCommittee(student);
                }
                throw new PageBackException();
            }
            //Attendee slots maxed
            else if (camp.getFilledCampCommSlots() >= camp.getCampCommSlots()) {
                System.out.println("Camp Committee Slots maxed.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCampCommittee(student);
                }
                throw new PageBackException();
            }

        } catch (ModelNotFoundException e) {
            throw new RuntimeException(e);
        }
        // ChangePage.changePage();
        // System.out.println("Here is the project information: ");
        // try {
        //     Camp camp1 = CampRepository.getInstance().getByID(projectID);
        //     ModelViewer.displaySingleDisplayable(camp1);
        // } catch (ModelNotFoundException e) {
        //     throw new RuntimeException(e);
        // }
        System.out.print("Are you sure you want to register for this camp? (y/[n]): ");
        String choice = new Scanner(System.in).nextLine();
        if (choice.equalsIgnoreCase("y")) {
            try {
                // StudentManager.registerStudent(campID, student.getID());
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
     * This private method is called to get the camp ID from the user. It prompts the user to enter the camp ID and returns it as a string. If the user wants to go back, a PageBackException is thrown.
     *
     * @return the camp ID.
     * @throws PageBackException if the user wants to go back.
     */
    private static String getCampID() throws PageBackException {
        System.out.println("Please enter the project ID: ");
        String campID = new Scanner(System.in).nextLine();
        if (CampRepository.getInstance().contains(campID)) {
            System.out.println("Camp found.");
            System.out.println("Here is the camp information: ");
            try {
                Camp camp = CampRepository.getInstance().getByID(campID);
                ModelViewer.displaySingleDisplayable(camp);
            } catch (ModelNotFoundException e) {
                //e.printStackTrace();
            }
        } else {
            System.out.println("Camp not found.");
            System.out.println("Press Enter to go back.");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }
        return campID;
    }
    
    public static void submitEnquiry(Student student) throws PageBackException{
        ChangePage.changePage(); 
        System.out.println("Here is the list of available camps: ");
        ModelViewer.displayListOfDisplayable(CampManager.getAllVisibleCamps());
        System.out.println("Our staff are always devoted themselves to resolve the enquiry from students!"); 
        System.out.println("First of all, would you mind telling us the CampID that you want to submit this enquiry to: "); 
        String studentID = student.getID();
        String campID = new Scanner(System.in).nextLine();
        System.out.printf("Please input the enquiry that you have regarding camp %s: \n", campID);
        String message=new Scanner(System.in).nextLine(); 
        try{
            RequestManager.createEnquiry(campID, studentID, message); 
        } catch(ModelAlreadyExistsException e){
            throw new RuntimeException(e);
        }
        
        ChangePage.changePage();

        System.out.println("Enquiry Submitted");
        System.out.println("Press Enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    public static void viewEnquiry(Student student) throws PageBackException, ModelNotFoundException{
        ChangePage.changePage();
        System.out.println("Here is the list of Enquiries you made");
        ModelViewer.displayListOfDisplayable((RequestManager.viewEnquiryBySender(student.getID())));//change to by user
        System.out.println();
        
        //if empty dont print below
        System.out.println("1. Edit Enquiry");
        System.out.println("2. Delete Enquiry");
        System.out.println("3. Exit");
        int choice = new Scanner(System.in).nextInt();
        if(choice == 1){
            editEnquiry(student);
        }
        else if (choice == 2) {
            deleteEnquiry(student);
        }

        else{
            // System.out.println("Press Enter to go back.");
            // new Scanner(System.in).nextLine();
            throw new PageBackException();
        }
    }

    public static void editEnquiry(Student student) throws PageBackException, ModelNotFoundException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ID of Enquiry to edit");
        String enquiryID = sc.nextLine();
        //check if enquiry exists;

        Enquiry enquiryToEdit = RequestManager.getEnquiryByID(enquiryID);

        System.out.println("Enter new message:");
        String newMessage = sc.nextLine();
        enquiryToEdit.setMessage(newMessage);
        RequestManager.updateEnquiry(enquiryID, enquiryToEdit);
        System.out.println("Successfully updated enquiry!");
        System.out.println(BoundaryStrings.separator);
        System.out.println();
        System.out.println("Press enter to go back.");
        sc.nextLine();
        throw new PageBackException();
    }


    public static void deleteEnquiry(Student student) throws PageBackException, ModelNotFoundException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ID of Enquiry to delete");
        String enquiryID = sc.nextLine();
        //check if enquiry exists;

        Enquiry enquiryToDelete = RequestManager.getEnquiryByID(enquiryID);

        System.out.println("Are you sure you want to delete this Enquiry? (Y/N)");
    
        String input = sc.nextLine();
        if (!input.equalsIgnoreCase("Y")) {
            System.out.println("Enquiry deletion cancelled!");
            System.out.println("Press enter to continue");
            sc.nextLine();
            throw new PageBackException();
        }

        try{
            EnquiryRepository.getInstance().remove(enquiryToDelete.getID());
        } catch (ModelNotFoundException e){
            throw new RuntimeException(e);
        }
        ChangePage.changePage();
        System.out.println("Enquiry deleted successfully!");
        System.out.println("Press enter to continue");
        sc.nextLine();
        throw new PageBackException();
    }


    public static void submitSuggestion(Student student) throws PageBackException, ModelNotFoundException{
        
        System.out.println("Here is the list of available camps: ");
        ModelViewer.displayListOfDisplayable(CampManager.getAllVisibleCamps());//based on camp committee
    
        System.out.println("Enter Camp ID to create Suggestion");
        String studentID = student.getID();
        String campID = new Scanner(System.in).nextLine();
    
        // Validate campID here if needed
        
        Suggestion s;

        try{
            s = RequestManager.createSuggestion(campID, studentID);
        } catch(ModelAlreadyExistsException e){
            throw new RuntimeException(e);
        }
        editSuggestion(s, student);
    
        // ChangePage.changePage();
        // System.out.println("Suggestion updated");
        // System.out.println("Press Enter to go back.");
        // new Scanner(System.in).nextLine();
        // throw new PageBackException();
    }

    public static void editSuggestion(Suggestion s, Student student) throws PageBackException, ModelNotFoundException{
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
        System.out.println("The new value of the field to update");
        switch (choice) {
            case 0 -> throw new PageBackException();
            case 1 -> s.setCampName(scanner.nextLine());
            case 2 -> s.setDates(scanner.nextLine());
            case 3 -> s.setRegistrationClosingDate(scanner.nextLine());
            case 4 -> s.setCampType(Faculty.valueOf(scanner.nextLine()));
            case 5 -> s.setLocation(scanner.nextLine());
            case 6 -> s.setTotalSlots(scanner.nextInt());
            case 7 -> s.setCampCommSlots(scanner.nextInt());
            case 8 -> s.setDescription(scanner.nextLine());
            case 9 -> s.setCampStaff(scanner.nextLine());
        }
        System.out.println("Have other field to update?");
        System.out.println("\t0. No");
        System.out.println("\t1. Yes");
        choice = scanner.nextInt();
        if (choice == 1) {
            RequestManager.updateSuggestion(s.getID(),s);
            editSuggestion(s, student);
        }
        RequestManager.updateSuggestion(s.getID(),s);
        ChangePage.changePage();
        System.out.println("Suggestion updated!");
        System.out.println("Press Enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }


    public static void viewSuggestion(Student student) throws PageBackException, ModelNotFoundException{
        ChangePage.changePage();
        System.out.println("Here is the list of Suggestion you made");
        ModelViewer.displayListOfDisplayable((RequestManager.viewSuggestionBySender(student.getID())));

        System.out.println();
        System.out.println("1. Edit Suggestion");
        System.out.println("2. Delete Suggestion");
        System.out.println("3. Exit");
        int choice = new Scanner(System.in).nextInt();
        if(choice == 1){
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter ID of Suggestion to edit");
            String suggestionID = sc.nextLine();
            //check if suggestion exists;
            Suggestion suggestionToEdit = RequestManager.getSuggestionByID(suggestionID);
            editSuggestion(suggestionToEdit, student);
        }
        else if (choice == 2) {
            deleteSuggestion(student);
        }

        else{
            // System.out.println("Press Enter to go back.");
            // new Scanner(System.in).nextLine();
            throw new PageBackException();
        }
    }


    public static void deleteSuggestion(Student student) throws PageBackException, ModelNotFoundException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ID of Suggestion to delete");
        String suggestionID = sc.nextLine();
        //check if suggestion exists;

        Suggestion suggestionToDelete = RequestManager.getSuggestionByID(suggestionID);

        System.out.println("Are you sure you want to delete this Suggestion? (Y/N)");
    
        String input = sc.nextLine();
        if (!input.equalsIgnoreCase("Y")) {
            System.out.println("Suggestion deletion cancelled!");
            System.out.println("Press enter to continue");
            sc.nextLine();
            throw new PageBackException();
        }

        try{
            SuggestionRepository.getInstance().remove(suggestionToDelete.getID());
        } catch (ModelNotFoundException e){
            throw new RuntimeException(e);
        }
        ChangePage.changePage();
        System.out.println("Enquiry deleted successfully!");
        System.out.println("Press enter to continue");
        sc.nextLine();
        throw new PageBackException();
    }
    
}
