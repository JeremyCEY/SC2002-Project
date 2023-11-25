package main.boundary.modelviewer;

import main.controller.camp.CampManager;
import main.model.camp.Camp;
import main.model.user.Student;
import main.model.user.Staff;
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

import java.util.Collections;
import java.util.Comparator;

/**
 * Displays the project details.
 */
public class CampViewer {

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

    // FOR STUDENTS /////////////////////////////////////////////////////////////////////////////////////////////
    public static void viewVisibleFacultyCampList(Student student) throws PageBackException {
        ChangePage.changePage();
        List<Camp> camps = CampManager.getCampsForStudent(student);

        // Sort camps alphabetically by default
        Comparator<Camp> campComparator = null;
        campComparator = Comparator.comparing(Camp::getCampName, String.CASE_INSENSITIVE_ORDER);
        Collections.sort(camps, campComparator);

        System.out.println("List of Available Camps (Alphabetical Order):");
        ModelViewer.displayListOfDisplayable(camps);

        System.out.println("Choose an option:");
        System.out.println("\t1. Sort camps");
        System.out.println("\t2. Go Back");

        System.out.print("Enter your choice: ");
        int choice = IntGetter.readInt();

        switch (choice) {
            case 1:
                // Prompt the user to choose a sorting option
                sortCampsByOption(camps, student);
                break;
            case 2:
                throw new PageBackException();
            default:
                System.out.println("Invalid choice. Try again.");
                new Scanner(System.in).nextLine();
                viewVisibleFacultyCampList(student);
        }
    }

    private static void sortCampsByOption(List<Camp> camps, Student student) throws PageBackException {
        ChangePage.changePage();
        System.out.println("Sort camps by:");
        System.out.println("\t1. Camp ID");
        System.out.println("\t2. Camp Name");
        System.out.println("\t3. Camp Date");
        System.out.println("\t4. Camp Closing Date");
        System.out.println("\t5. Camp Location");
        System.out.println("\t6. Go Back");

        System.out.print("Enter your choice: ");
        int sortChoice = IntGetter.readInt();

        if (sortChoice == 6) {
            viewVisibleFacultyCampList(student);
        }

        String sortTitle = "List of Available Camps";

        Comparator<Camp> campComparator = null;

        switch (sortChoice) {
            case 1:
                sortTitle = "List of Available Camps (ID order):";
                campComparator = Comparator.comparingInt(camp -> {
                    try {
                        return Integer.parseInt(camp.getID().replaceAll("\\D", ""));
                    } catch (NumberFormatException e) {
                        return Integer.MAX_VALUE;
                    }
                });
                break;
            case 2:
                sortTitle = "List of Available Camps (Alphabetical order):";
                campComparator = Comparator.comparing(Camp::getCampName, String.CASE_INSENSITIVE_ORDER);
                break;
            case 3:
                sortTitle = "List of Available Camps (Date order):";
                campComparator = (camp1, camp2) -> {
                String date1 = camp1.getDates().substring(0, 8);
                String date2 = camp2.getDates().substring(0, 8);
                return date1.compareTo(date2);
            };
            
                break;
            case 4:
                sortTitle = "List of Available Camps (Closing Date order):";
                campComparator = Comparator.comparing(Camp::getRegistrationClosingDate);
                break;
            case 5:
                sortTitle = "List of Available Camps (Location order):";
                campComparator = Comparator.comparing(Camp::getLocation);
                break;
            default:
                System.out.println("Invalid choice. Try again.");
                new Scanner(System.in).nextLine();
                viewVisibleFacultyCampList(student);
        }

        Collections.sort(camps, campComparator);

        ChangePage.changePage();
        System.out.println(sortTitle);
        ModelViewer.displayListOfDisplayable(camps);

        System.out.println("Press Enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    // // FOR STAFFS - visible /////////////////////////////////////////////////////////////////////////////////////////////
    // public static void viewVisibleCampList(Staff staff) throws PageBackException {
    //     ChangePage.changePage();
    //     List<Camp> camps = CampManager.getAllVisibleCamps();

    //     Comparator<Camp> campComparator = null;
    //     campComparator = Comparator.comparing(Camp::getCampName, String.CASE_INSENSITIVE_ORDER);
    //     Collections.sort(camps, campComparator);

    //     System.out.println("List of Available Camps (Alphabetical Order):");
    //     ModelViewer.displayListOfDisplayable(camps);

    //     System.out.println("Choose an option:");
    //     System.out.println("\t1. Sort camps");
    //     System.out.println("\t2. Go Back");

    //     System.out.print("Enter your choice: ");
    //     int choice = IntGetter.readInt();

    //     switch (choice) {
    //         case 1:
    //             // Prompt the user to choose a sorting option
    //             sortCampsByOptionVisible(camps, staff);
    //             break;
    //         case 2:
    //             throw new PageBackException();
    //         default:
    //             System.out.println("Invalid choice. Try again.");
    //             new Scanner(System.in).nextLine();
    //             throw new PageBackException();
    //     }
    // }

    // private static void sortCampsByOptionVisible(List<Camp> camps, Staff staff) throws PageBackException {
    //     ChangePage.changePage();
    //     System.out.println("Sort camps by:");
    //     System.out.println("\t1. Camp ID");
    //     System.out.println("\t2. Camp Name");
    //     System.out.println("\t3. Camp Date");
    //     System.out.println("\t4. Camp Closing Date");
    //     System.out.println("\t5. Camp Location");
    //     System.out.println("\t6. Go Back");

    //     System.out.print("Enter your choice: ");
    //     int sortChoice = IntGetter.readInt();

    //     if (sortChoice == 6) {
    //         viewVisibleCampList(staff);
    //     }

    //     String sortTitle = "List of Available Camps";

    //     Comparator<Camp> campComparator = null;

    //     switch (sortChoice) {
    //         case 1:
    //             sortTitle = "List of Available Camps (ID order):";
    //             campComparator = Comparator.comparingInt(camp -> {
    //                 try {
    //                     return Integer.parseInt(camp.getID().replaceAll("\\D", ""));
    //                 } catch (NumberFormatException e) {
    //                     return Integer.MAX_VALUE;
    //                 }
    //             });
    //             break;
    //         case 2:
    //             sortTitle = "List of Available Camps (Alphabetical order):";
    //             campComparator = Comparator.comparing(Camp::getCampName, String.CASE_INSENSITIVE_ORDER);
    //             break;
    //         case 3:
    //             sortTitle = "List of Available Camps (Date order):";
    //             campComparator = (camp1, camp2) -> {
    //             String date1 = camp1.getDates().substring(0, 8);
    //             String date2 = camp2.getDates().substring(0, 8);
    //             return date1.compareTo(date2);
    //         };
            
    //             break;
    //         case 4:
    //             sortTitle = "List of Available Camps (Closing Date order):";
    //             campComparator = Comparator.comparing(Camp::getRegistrationClosingDate);
    //             break;
    //         case 5:
    //             sortTitle = "List of Available Camps (Location order):";
    //             campComparator = Comparator.comparing(Camp::getLocation);
    //             break;
    //         default:
    //             System.out.println("Invalid choice. Try again.");
    //             new Scanner(System.in).nextLine();
    //             viewVisibleCampList(staff);
    //     }

    //     Collections.sort(camps, campComparator);

    //     ChangePage.changePage();
    //     System.out.println(sortTitle);
    //     ModelViewer.displayListOfDisplayable(camps);

    //     System.out.println("Press Enter to go back.");
    //     new Scanner(System.in).nextLine();
    //     throw new PageBackException();
    // }

    // // FOR STAFFS - invisible /////////////////////////////////////////////////////////////////////////////////////////////
    // public static void viewInvisibleCampList(Staff staff) throws PageBackException {
    //     ChangePage.changePage();
    //     List<Camp> camps = CampManager.getAllInvisibleCamps();

    //     Comparator<Camp> campComparator = null;
    //     campComparator = Comparator.comparing(Camp::getCampName, String.CASE_INSENSITIVE_ORDER);
    //     Collections.sort(camps, campComparator);

    //     System.out.println("List of Available Camps (Alphabetical Order):");
    //     ModelViewer.displayListOfDisplayable(camps);

    //     System.out.println("Choose an option:");
    //     System.out.println("\t1. Sort camps");
    //     System.out.println("\t2. Go Back");

    //     System.out.print("Enter your choice: ");
    //     int choice = IntGetter.readInt();

    //     switch (choice) {
    //         case 1:
    //             // Prompt the user to choose a sorting option
    //             sortCampsByOptionInvisible(camps, staff);
    //             break;
    //         case 2:
    //             throw new PageBackException();
    //         default:
    //             System.out.println("Invalid choice. Try again.");
    //             new Scanner(System.in).nextLine();
    //             throw new PageBackException();
    //     }
    // }

    // private static void sortCampsByOptionInvisible(List<Camp> camps, Staff staff) throws PageBackException {
    //     ChangePage.changePage();
    //     System.out.println("Sort camps by:");
    //     System.out.println("\t1. Camp ID");
    //     System.out.println("\t2. Camp Name");
    //     System.out.println("\t3. Camp Date");
    //     System.out.println("\t4. Camp Closing Date");
    //     System.out.println("\t5. Camp Location");
    //     System.out.println("\t6. Go Back");

    //     System.out.print("Enter your choice: ");
    //     int sortChoice = IntGetter.readInt();

    //     if (sortChoice == 6) {
    //         viewVisibleCampList(staff);
    //     }

    //     String sortTitle = "List of Available Camps";

    //     Comparator<Camp> campComparator = null;

    //     switch (sortChoice) {
    //         case 1:
    //             sortTitle = "List of Available Camps (ID order):";
    //             campComparator = Comparator.comparingInt(camp -> {
    //                 try {
    //                     return Integer.parseInt(camp.getID().replaceAll("\\D", ""));
    //                 } catch (NumberFormatException e) {
    //                     return Integer.MAX_VALUE;
    //                 }
    //             });
    //             break;
    //         case 2:
    //             sortTitle = "List of Available Camps (Alphabetical order):";
    //             campComparator = Comparator.comparing(Camp::getCampName, String.CASE_INSENSITIVE_ORDER);
    //             break;
    //         case 3:
    //             sortTitle = "List of Available Camps (Date order):";
    //             campComparator = (camp1, camp2) -> {
    //             String date1 = camp1.getDates().substring(0, 8);
    //             String date2 = camp2.getDates().substring(0, 8);
    //             return date1.compareTo(date2);
    //         };
            
    //             break;
    //         case 4:
    //             sortTitle = "List of Available Camps (Closing Date order):";
    //             campComparator = Comparator.comparing(Camp::getRegistrationClosingDate);
    //             break;
    //         case 5:
    //             sortTitle = "List of Available Camps (Location order):";
    //             campComparator = Comparator.comparing(Camp::getLocation);
    //             break;
    //         default:
    //             System.out.println("Invalid choice. Try again.");
    //             new Scanner(System.in).nextLine();
    //             viewInvisibleCampList(staff);
    //     }

    //     Collections.sort(camps, campComparator);

    //     ChangePage.changePage();
    //     System.out.println(sortTitle);
    //     ModelViewer.displayListOfDisplayable(camps);

    //     System.out.println("Press Enter to go back.");
    //     new Scanner(System.in).nextLine();
    //     throw new PageBackException();
    // }


    // /**
    //  * Displays the project details.
    //  *
    //  * @throws PageBackException if the user wants to go back
    //  */
    // public static void viewAllCamps() throws PageBackException {
    //     ChangePage.changePage();
    //     System.out.println("View All Camp List");
    //     ModelViewer.displayListOfDisplayable(CampManager.viewAllcamp());
    //     System.out.println("Press Enter to go back.");
    //     new Scanner(System.in).nextLine();
    //     throw new PageBackException();
    // }

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

    /////////////////////////////////////////////////////////////////////////////////
        public static void selectCampTypeAndDisplay(Staff staff) throws PageBackException {
            ChangePage.changePage();
    
            System.out.println("Select the type of camps to view:");
            System.out.println("\t1. View All Camps");
            System.out.println("\t2. View Visible Camps");
            System.out.println("\t3. View Invisible Camps");
            System.out.println("\t4. Go Back");
    
            System.out.print("Enter your choice: ");
            int typeChoice = IntGetter.readInt();
    
            switch (typeChoice) {
                case 1:
                    viewAllCamps();
                    break;
                case 2:
                    viewCampList(staff, CampManager.getAllVisibleCamps(), true);
                    break;
                case 3:
                    viewCampList(staff, CampManager.getAllInvisibleCamps(), false);
                    break;
                case 4:
                    throw new PageBackException();
                default:
                    System.out.println("Invalid choice. Try again.");
                    new Scanner(System.in).nextLine();
                    selectCampTypeAndDisplay(staff);
            }
        }

            public static void viewCampList(Staff staff, List<Camp> camps, boolean isVisible) throws PageBackException {
                ChangePage.changePage();
        
                Comparator<Camp> campComparator = Comparator.comparing(Camp::getCampName, String.CASE_INSENSITIVE_ORDER);
                Collections.sort(camps, campComparator);
        
                System.out.println("List of Available Camps (Alphabetical Order):");
                ModelViewer.displayListOfDisplayable(camps);
        
                System.out.println("Choose an option:");
                System.out.println("\t1. Sort camps");
                System.out.println("\t2. Go Back");
        
                System.out.print("Enter your choice: ");
                int choice = IntGetter.readInt();
        
                switch (choice) {
                    case 1:
                        sortCampsByOption(camps, staff, isVisible);
                        break;
                    case 2:
                        throw new PageBackException();
                    default:
                        System.out.println("Invalid choice. Try again.");
                        new Scanner(System.in).nextLine();
                        throw new PageBackException();
                }
            }
        
            private static void sortCampsByOption(List<Camp> camps, Staff staff, boolean isVisible) throws PageBackException {
                ChangePage.changePage();
                System.out.println("Sort camps by:");
                System.out.println("\t1. Camp ID");
                System.out.println("\t2. Camp Name");
                System.out.println("\t3. Camp Date");
                System.out.println("\t4. Camp Closing Date");
                System.out.println("\t5. Camp Location");
                System.out.println("\t6. Go Back");
        
                System.out.print("Enter your choice: ");
                int sortChoice = IntGetter.readInt();
        
                if (sortChoice == 6) {
                    viewCampList(staff, camps, isVisible);
                }
        
                String sortTitle = "List of Available Camps";
        
                Comparator<Camp> campComparator = null;
        
                switch (sortChoice) {
                    case 1:
                        sortTitle = "List of Available Camps (ID order):";
                        campComparator = Comparator.comparingInt(camp -> {
                            try {
                                return Integer.parseInt(camp.getID().replaceAll("\\D", ""));
                            } catch (NumberFormatException e) {
                                return Integer.MAX_VALUE;
                            }
                        });
                        break;
                    case 2:
                        sortTitle = "List of Available Camps (Alphabetical order):";
                        campComparator = Comparator.comparing(Camp::getCampName, String.CASE_INSENSITIVE_ORDER);
                        break;
                    case 3:
                        sortTitle = "List of Available Camps (Date order):";
                        campComparator = Comparator.comparing(camp -> camp.getDates().substring(0, 8));
                        break;
                    case 4:
                        sortTitle = "List of Available Camps (Closing Date order):";
                        campComparator = Comparator.comparing(Camp::getRegistrationClosingDate);
                        break;
                    case 5:
                        sortTitle = "List of Available Camps (Location order):";
                        campComparator = Comparator.comparing(Camp::getLocation);
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                        new Scanner(System.in).nextLine();
                        viewCampList(staff, camps, isVisible);
                }
        
                Collections.sort(camps, campComparator);
        
                ChangePage.changePage();
                System.out.println(sortTitle);
                ModelViewer.displayListOfDisplayable(camps);
        
                System.out.println("Press Enter to go back.");
                new Scanner(System.in).nextLine();
                throw new PageBackException();
            }
        
            public static void viewAllCamps() throws PageBackException {
                viewCampList(null, CampManager.viewAllcamp(), false);
            }
        
            public static void viewVisibleCampList(Staff staff) throws PageBackException {
                viewCampList(staff, CampManager.getAllVisibleCamps(), true);
            }
        
            public static void viewInvisibleCampList(Staff staff) throws PageBackException {
                viewCampList(staff, CampManager.getAllInvisibleCamps(), false);
            }
        
    
}
