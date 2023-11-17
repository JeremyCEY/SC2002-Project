package main.boundary.modelviewer;

import main.controller.camp.CampManager;
import main.model.camp.Camp;
//import main.model.project.ProjectStatus;
import main.model.user.Student;
import main.model.user.Staff;
//import main.model.user.StudentStatus;
import main.repository.camp.CampRepository;
import main.repository.user.StaffRepository;
import main.utils.exception.ModelNotFoundException;
import main.utils.exception.PageBackException;
import main.utils.iocontrol.IntGetter;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.ChangePage;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

/**
 * Displays the project details.
 */
public class CampViewer {

    /**
     * Displays a menu to the user to select a project status and returns the selected ProjectStatus enum value. If an invalid option is selected, it prompts the user to retry or go back, and throws a PageBackException if the user chooses to go back.
     *
     * @return the selected ProjectStatus enum value.
     * @throws PageBackException if the user chooses to go back to the previous page.
     */
    // public static CampStatus getCampStatus() throws PageBackException {
    //     System.out.println("\t1. Available");
    //     System.out.println("\t2. Unavailable");
    //     System.out.println("\t3. Reserved");
    //     System.out.println("\t4. Allocated");
    //     System.out.print("Please enter your choice: ");
    //     int option = IntGetter.readInt();
    //     return switch (option) {
    //         case 1 -> ProjectStatus.AVAILABLE;
    //         case 2 -> ProjectStatus.UNAVAILABLE;
    //         case 3 -> ProjectStatus.RESERVED;
    //         case 4 -> ProjectStatus.ALLOCATED;
    //         default -> {
    //             System.out.println("Please enter a number between 1-4.");
    //             System.out.println("Press Enter to retry or enter 0 and press Enter to exit.");
    //             String input = new Scanner(System.in).nextLine().trim();
    //             if (input.equals("0")) {
    //                 throw new PageBackException();
    //             } else {
    //                 yield getProjectStatus();
    //             }
    //         }
    //     };
    // }

    /**
     * Prompts the user to enter a project ID and displays the details of the project with the matching ID using the ProjectRepository and Project object's displayProject() method.
     *
     * @throws PageBackException if the user chooses to go back to the previous page.
     */
    // public static void generateDetailsByCampID() throws PageBackException {
    //     System.out.println("Please Enter the ProjectID to search: ");
    //     String s1 = new Scanner(System.in).nextLine();
    //     try {
    //         Camp p1 = CampRepository.getInstance().getByID(s1);
    //         p1.displayCamp();
    //     } catch (ModelNotFoundException e) {
    //         System.out.println("Cannot find the camp matching this ID");
    //         System.out.println("Press enter to retry, or enter [b] to go back");
    //         String input = new Scanner(System.in).nextLine().trim();
    //         if (input.equals("b")) {
    //             throw new PageBackException();
    //         } else {
    //             generateDetailsByCampID();
    //         }
    //     }
    //     System.out.println("Enter <Enter> to continue");
    //     new Scanner(System.in).nextLine();
    //     throw new PageBackException();
    // }

    /**
     * Prompts the user to enter a supervisor ID and displays the details of projects supervised by the supervisor with the matching ID. It checks if the supervisor ID is valid using the FacultyRepository and handles cases where the supervisor is not found or the user wants to go back by throwing a PageBackException.
     * @throws PageBackException if the user wants to go back
     */
    // public static void generateDetailsBySupervisorID() throws PageBackException {
    //     System.out.println("Please enter the SupervisorID to search: ");
    //     String s1 = new Scanner(System.in).nextLine();
    //     if (!CampRepository.getInstance().contains(s1)) {
    //         System.out.println("Supervisor Not Found.");
    //         System.out.println("Press enter to retry, or enter [b] to go back");
    //         String input = new Scanner(System.in).nextLine().trim();
    //         if (input.equalsIgnoreCase("b")) {
    //             throw new PageBackException();
    //         } else {
    //             generateDetailsBySupervisorID();
    //             return;
    //         }
    //     }
    //     List<Camp> campList = CampRepository.getInstance().findByRules(p -> p.getSupervisorID().equalsIgnoreCase(s1));
    //     ModelViewer.displayListOfDisplayable(projectList);
    //     System.out.println("Enter <Enter> to continue");
    //     new Scanner(System.in).nextLine();
    //     throw new PageBackException();
    // }


    /**
     * Prompts the user to enter a student ID and displays the details of projects that require the student with the matching ID. It uses the ProjectRepository to find projects that match the student ID and calls the displayProject() method on each project to display their details. It also handles cases where no projects are found and prompts the user to go back using a PageBackException.
     * @throws PageBackException if the user wants to go back
     */
    // public static void generateDetailsByStudentID() throws PageBackException {
    //     System.out.println("Enter the StudentID to search");
    //     String s1 = new Scanner(System.in).nextLine();
    //     ModelViewer.displayListOfDisplayable(CampRepository.getInstance().findByRules(p -> Objects.equals(p.getStudentID(), s1)));
    //     System.out.println("Enter <Enter> to continue");
    //     new Scanner(System.in).nextLine();
    //     throw new PageBackException();
    // }

    /**
     * Prompts the user to select a project status and displays the details of projects with the matching status. It calls the getProjectStatus() method to get the selected status, and uses the ProjectRepository to find projects that match the status. It handles cases where no projects are found and prompts the user to go back using a PageBackException.
     *
     * @throws PageBackException if the user wants to go back
     */
    // public static void generateDetailsByStatus() throws PageBackException {
    //     CampStatus status = getCampStatus();
    //     ModelViewer.displayListOfDisplayable(CampRepository.getInstance().findByRules(p -> Objects.equals(p.getStatus(), status)));
    //     System.out.println("Enter <Enter> to continue");
    //     new Scanner(System.in).nextLine();
    //     throw new PageBackException();

    // }

    /**
     * Provides a menu to the user to select the way to search for project details and calls the corresponding method based on the user's selection. It also handles cases where the user wants to go back by calling the ChangePage.changePage() method and throwing a PageBackException.
     *
     * @throws PageBackException if the user wants to go back
     */
    // public static void generateCampDetails() throws PageBackException {
    //     ChangePage.changePage();
    //     System.out.println(BoundaryStrings.separator);
    //     System.out.println("Please select the way to search:");
    //     System.out.println("\t 1. By ProjectID");
    //     System.out.println("\t 2. By SupervisorID");
    //     System.out.println("\t 3. By Student");
    //     System.out.println("\t 4. By Status");
    //     System.out.println("\t 0. Go Back");
    //     System.out.println(BoundaryStrings.separator);
    //     System.out.print("Please enter your choice: ");
    //     int c = IntGetter.readInt();
    //     if (c == 0) {
    //         throw new PageBackException();
    //     }
    //     try {
    //         switch (c) {
    //             case 1 -> generateDetailsByCampID();
    //             case 2 -> generateDetailsBySupervisorID();
    //             case 3 -> generateDetailsByStudentID();
    //             case 4 -> generateDetailsByStatus();
    //             default -> {
    //                 System.out.println("Invalid choice. Please enter again. ");
    //                 new Scanner(System.in).nextLine();
    //                 throw new PageBackException();
    //             }
    //         }
    //     } catch (PageBackException e) {
    //         generateCampDetails();
    //     }
    // }

    // /**
    //  * Displays the project details.
    //  * @param student the student to display the project details for
    //  * @throws PageBackException if the user wants to go back
    //  */
    public static void viewVisibleCampList() throws PageBackException {
        ChangePage.changePage();

        System.out.println("View Available Camp List");
        ModelViewer.displayListOfDisplayable(CampManager.viewAvailableCamps());
        
        System.out.println("Press Enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * Displays the project details.
     *
     * @throws PageBackException if the user wants to go back
     */
    public static void viewAllCamps() throws PageBackException {
        ChangePage.changePage();
        System.out.println("View All Camp List");
        ModelViewer.displayListOfDisplayable(CampManager.viewAllcamp());
        System.out.println("Press Enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    public static void viewCamp(Camp camp) throws PageBackException {
        ChangePage.changePage();
        System.out.println("View Camp");
        ModelViewer.displaySingleDisplayable(camp);
        System.out.println("Press Enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }
    
    
    public static void viewStaffCamps(Staff staff) throws PageBackException {
        // ChangePage.changePage();
        // System.out.println("View Camp created by " + staff.getUserName());
        // ModelViewer.displayListOfDisplayable(CampManager.getAllCampsByStaff(staff));
        // System.out.println("Press Enter to go back.");
        // new Scanner(System.in).nextLine();
        // throw new PageBackException();
        List<Camp> camps = CampRepository.getInstance().findByRules(camp -> Objects.equals(camp.getStaffID(), staff.getID()));
        System.out.println("Here are all your camps:");
        ModelViewer.displayListOfDisplayable(camps);
    }
    
    /**
     * Displays the project details.
     *
     * @param student the student
     * @throws PageBackException if the user wants to go back
     */
    public static void viewStudentCamps(Student student) throws PageBackException {
       ChangePage.changePage();
       System.out.println("View Registered Camps");
       Map<Camp,String> camps = CampManager.getStudentcamps(student);
       if (camps == null) {
           System.out.println("Student has not registered into any camps yet.");
      } else {
           ModelViewer.displayListOfCampsWithType(camps);
       }
       System.out.println("Press Enter to go back.");
       new Scanner(System.in).nextLine();
       throw new PageBackException();
    }
}
