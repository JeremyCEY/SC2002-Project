package main.boundary.mainpage;

import main.boundary.account.ChangeAccountPassword;
import main.boundary.account.Logout;
import main.boundary.account.ViewUserProfile;
import main.boundary.modelviewer.ModelViewer;
import main.boundary.modelviewer.CampViewer;
import main.controller.account.AccountManager;
import main.controller.camp.CampManager;
import main.controller.request.StudentManager;
import main.model.camp.Camp;
import main.model.camp.Camp;
import main.model.user.*;
import main.repository.camp.CampRepository;
import main.repository.user.StudentRepository;
import main.utils.exception.ModelNotFoundException;
import main.utils.exception.PageBackException;
import main.utils.iocontrol.IntGetter;
import main.utils.parameters.EmptyID;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.ChangePage;

import java.util.Scanner;

/**
 * This is a Java class that represents the main page of a student in a system or application. It contains several methods for displaying different functionalities of the student main page, such as viewing the user profile, changing the password, viewing project lists, registering/deregistering for projects, changing project title, viewing project history and status, and logging out.
 */
public class StudentMainPage {
    /**
     * This method displays the main page of a student. It takes a User object as a parameter and displays a menu of options for the student to choose from. The user's choice is then processed using a switch statement, which calls different methods based on the choice.
     *
     * @param user The user object of the student.
     */
    public static void studentMainPage(User user) {
        if (user instanceof Student student) {
            ChangePage.changePage();
            System.out.println(BoundaryStrings.separator);
            System.out.println("Welcome to Student Main Page");
            System.out.println("Hello, " + student.getUserName() + "!");
            System.out.println();
            System.out.println("\t1. View my profile (Feature Complete)");
            System.out.println("\t2. Change my password (Feature Complete)");
            System.out.println("\t3. View all camps");
            System.out.println("\t4. View registered camps");
            System.out.println("\t5. Register for a camp as attendee");
            System.out.println("\t6. Deregister for a camp as attendee");
            System.out.println("\t7. Register for a camp as committee");
            System.out.println("\t8. Handle enquiry");
            System.out.println("\t9. Handle suggesstion");
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
                    case 3 -> CampViewer.viewVisibleCampList();
                    //case 4 -> CampViewer.viewStudentCamps(student);
                    case 5 -> registerCamp(student);
                    case 6 -> Logout.logout();
                    case 7 -> Logout.logout();
                    case 8 -> Logout.logout();
                    case 9 -> Logout.logout();
                    case 10 -> Logout.logout();
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
    private static void registerCamp(Student student) throws PageBackException {
        ChangePage.changePage();
        if (student.getStatus() == StudentStatus.REGISTERED || student.getStatus() == StudentStatus.DEREGISTERED) {
            System.out.println("You are already registered/deregistered for a project.");
            System.out.println("Press Enter to go back.");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }
        System.out.println("Here is the list of available projects: ");
        ModelViewer.displayListOfDisplayable(CampManager.getAllAvailablecamp());
        System.out.print("Please enter the project ID: ");
        String projectID = new Scanner(System.in).nextLine();
        if (CampManager.notContainsCampByID(campID)) {
            System.out.println("Camp not found.");
            System.out.println("Press Enter to go back, or enter [r] to retry.");
            String choice = new Scanner(System.in).nextLine();
            if (choice.equals("r")) {
                registerCamp(student);
            }
            throw new PageBackException();
        }
        Camp camp;
        try {
            camp = CampManager.getByID(campID);
            if (camp.getStatus() != CampStatus.AVAILABLE) {
                System.out.println("Camp is not available.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCamp(student);
                }
                throw new PageBackException();
            }

        } catch (ModelNotFoundException e) {
            throw new RuntimeException(e);
        }
        ChangePage.changePage();
        System.out.println("Here is the project information: ");
        try {
            Camp camp1 = CampRepository.getInstance().getByID(projectID);
            ModelViewer.displaySingleDisplayable(camp1);
        } catch (ModelNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.print("Are you sure you want to register for this project? (y/[n]): ");
        String choice = new Scanner(System.in).nextLine();
        if (choice.equalsIgnoreCase("y")) {
            try {
                StudentManager.registerStudent(campID, student.getID());
                System.out.println("Request submitted!");
            } catch (Exception e) {
                System.out.println("Enter [b] to go back, or press enter to retry.");
                String yNChoice = new Scanner(System.in).nextLine();
                if (yNChoice.equals("b")) {
                    throw new PageBackException();
                } else {
                    registerCamp(student);
                }
            }
        } else {
            System.out.println("Request cancelled.");

        }

        System.out.println("Press Enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * This private method is called when the student wants to deregister from a project. It prompts the student to enter the project ID, sends a request to deregister from the project, and displays a success message. If an error occurs, the student is given the option to go back or retry.
     *
     * @param student the student.
     * @throws PageBackException if the user wants to go back.
     */
    private static void deregisterCamp(Student student) throws PageBackException {
        ChangePage.changePage();

        if (EmptyID.isEmptyID(student.getID())) {
            System.out.println("You are not registered for any camp.");
            System.out.println("Press Enter to go back.");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }

        System.out.println("Your current project is: ");

        try {
            Camp project = CampRepository.getInstance().getByID(student.getID());
            ModelViewer.displaySingleDisplayable(project);
        } catch (ModelNotFoundException e) {
            throw new IllegalArgumentException("Camp not found.");
        }

        System.out.println("Are you sure you want to deregister from this camp? (y/[n])");
        String choice = new Scanner(System.in).nextLine();
        if (!choice.equals("y")) {
            System.out.println("Deregistration cancelled.");
            System.out.println("Press Enter to go back.");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }

        String CampID = student.getID();

        try {
            StudentManager.deregisterStudent(CampID, student.getID());
        } catch (Exception e) {
            System.out.println("Deregistration Error: " + e.getMessage());
            System.out.println("Enter [b] to go back, or press enter to retry.");
            String choice2 = new Scanner(System.in).nextLine();
            if (!choice2.equals("b")) {
                deregisterForCamp(student);
            }
            throw new PageBackException();
        }
        System.out.println("Successfully sent a request to deregister");
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
            } //catch (ModelNotFoundException e) {
                //e.printStackTrace();
            //}
        } else {
            System.out.println("Camp not found.");
            System.out.println("Press Enter to go back.");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }
        return campID;
    }

    /**
     * This private method is called to view the history and status of the student's project. It displays the project's information.
     *
     * @param student the student.
     * @throws PageBackException if the user wants to go back.
     */
    // private static void viewHistoryAndStatusOfMyProject(Student student) throws PageBackException {
    //     ChangePage.changePage();
    //     System.out.println("Here is the history and status of your project: ");
    //     ModelViewer.displayListOfDisplayable(StudentManager.getStudentRequestHistory(student.getID()));
    //     System.out.println("Press Enter to go back.");
    //     new Scanner(System.in).nextLine();
    //     throw new PageBackException();
    // }



    /**
     * This private method is called to view the supervisor of the student's project. It displays the supervisor's information.
     *
     * @param student the student.
     * @throws PageBackException if the user wants to go back.
     */
    // private static void viewMySupervisor(Student student) throws PageBackException {
    //     ChangePage.changePage();
    //     if (EmptyID.isEmptyID(student.getSupervisorID())) {
    //         System.out.println("You do not have a supervisor.");
    //     } else {
    //         try {
    //             Supervisor supervisor = (Supervisor) AccountManager.getByDomainAndID(UserType.FACULTY, student.getSupervisorID());
    //             ViewUserProfile.viewUserProfile(supervisor);
    //         } catch (ModelNotFoundException e) {
    //             System.out.println("Your supervisor is not found.");
    //         }
    //     }
    //     System.out.println("Press Enter to go back.");
    //     new Scanner(System.in).nextLine();
    //     throw new PageBackException();
    // }
}
