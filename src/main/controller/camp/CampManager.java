package main.controller.camp;

import main.controller.request.StaffManager;
import main.model.camp.Camp;
//import main.model.camp.CampStatus;
import main.model.user.Student;
import main.model.user.Faculty;
import main.model.user.Staff;
import main.repository.camp.CampRepository;
import main.repository.user.StaffRepository;
import main.repository.user.StudentRepository;
import main.utils.config.Location;
import main.utils.exception.ModelAlreadyExistsException;
import main.utils.exception.ModelNotFoundException;
import main.utils.iocontrol.CSVReader;
import main.utils.parameters.EmptyID;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class manages the camp
 */
public class CampManager {

    /**
     * Change the title of a camp
     *
     * @param campID the ID of the camp
     * @param newTitle  the new title of the camp
     * @throws ModelNotFoundException if the camp is not found
     */
    // public static void changecampTitle(String campID, String newTitle) throws ModelNotFoundException {
    //     Camp p1 = CampRepository.getInstance().getByID(campID);
    //     p1.setcampTitle(newTitle);
    //     CampRepository.getInstance().update(p1);
    //     CampManager.updatecampsStatus();
    // }

    /**
     * Change the supervisor of a camp
     *
     * @return the new supervisor
     */
    public static List<Camp> viewAllcamp() {
        return CampRepository.getInstance().getList();
    }

    /**
     * View all the camps that are available
     *
     * @return the list of available camps
     */
    // public static List<Camp> viewAvailablecamps() {
    //     return CampRepository.getInstance().findByRules(p -> p.getStatus() == campStatus.AVAILABLE);
    // }

    /**
     * create a new camp
     *
     * @param campID    the ID of the camp
     * @param campTitle the title of the camp
     * @param supervisorID the ID of the supervisor
     * @throws ModelAlreadyExistsException if the camp already exists
     */
    public static void createcamp(String campID, String campTitle, String staffID) throws ModelAlreadyExistsException {
        Camp c1 = new Camp(campID, campTitle, staffID);
        CampRepository.getInstance().add(c1);
        //CampManager.updatecampsStatus();
    }

    /**
     * create a new camp
     *
     * @param campTitle the title of the camp
     * @param supervisorID the ID of the supervisor
     * @throws ModelAlreadyExistsException if the camp already exists
     *
     * @return the new camp
     */
    public static Camp createcamp(String campTitle, String staffID) throws ModelAlreadyExistsException {
        Camp c1 = new Camp(getNewcampID(), campTitle, staffID);
        CampRepository.getInstance().add(c1);
        //CampManager.updatecampsStatus();
        return c1;
    }

    /**
     * get the list of all camps
     *
     * @return the list of all camps
     */
    public static List<Camp> getAllcamp() {
        return CampRepository.getInstance().getList();
    }

    /**
     * get the list of all camps by status
     *
     * @param campStatus the status of the camp
     * @return the list of all camps
     */
    // public static List<Camp> getAllcampByStatus(CampStatus campStatus) {
    //     return CampRepository.getInstance().findByRules(camp -> camp.getStatus().equals(campStatus));
    // }

    /**
     * get the list of all camps by supervisor
     *
     * @return the list of all camps
     */
    public static String getNewcampID() {
        int max = 0;
        for (Camp p : CampRepository.getInstance()) {
            int id = Integer.parseInt(p.getID().substring(1));
            if (id > max) {
                max = id;
            }
        }
        return "C" + (max + 1);
    }

    /**
     * transfer a student to a new supervisor
     *
     * @param campID    the ID of the camp
     * @param supervisorID the ID of the supervisor
     * @throws ModelNotFoundException if the camp is not found
     */
    // public static void transferToNewSupervisor(String campID, String supervisorID) throws ModelNotFoundException {
    //     camp p1 = CampRepository.getInstance().getByID(campID);
    //     if (!FacultyRepository.getInstance().contains(supervisorID)) {
    //         throw new IllegalStateException("Supervisor Not Found!");
    //     }
    //     Supervisor oldsupervisor = FacultyRepository.getInstance().getByID(p1.getSupervisorID());
    //     Supervisor newsupervisor = FacultyRepository.getInstance().getByID(supervisorID);
    //     Student student = StudentRepository.getInstance().getByID(p1.getStudentID());
    //     student.setSupervisorID(supervisorID);
    //     p1.setSupervisorID(supervisorID);
    //     CampRepository.getInstance().update(p1);
    //     FacultyRepository.getInstance().update(oldsupervisor);
    //     FacultyRepository.getInstance().update(newsupervisor);
    //     StudentRepository.getInstance().update(student);
    //     CampManager.updatecampsStatus();
    // }


    /**
     * deallocate a camp
     *
     * @param campID the ID of the camp
     * @throws ModelNotFoundException if the camp is not found
     */
    // public static void deallocatecamp(String campID) throws ModelNotFoundException {
    //     Camp p1 = CampRepository.getInstance().getByID(campID);
    //     if (p1.getStatus() != campStatus.ALLOCATED) {
    //         throw new IllegalStateException("The camp status is not ALLOCATED");
    //     }
    //     Student student;
    //     try {
    //         student = StudentRepository.getInstance().getByID(p1.getStudentID());
    //     } catch (ModelNotFoundException e) {
    //         throw new IllegalStateException("Student not found");
    //     }
    //     String supervisorID = p1.getSupervisorID();
    //     Supervisor supervisor = FacultyRepository.getInstance().getByID(supervisorID);
    //     student.setcampID(EmptyID.EMPTY_ID);
    //     student.setSupervisorID(EmptyID.EMPTY_ID);
    //     student.setStatus(StudentStatus.DEREGISTERED);
    //     p1.setStudentID(EmptyID.EMPTY_ID);
    //     p1.setStatus(campStatus.AVAILABLE);
    //     CampRepository.getInstance().update(p1);
    //     StudentRepository.getInstance().update(student);
    //     FacultyRepository.getInstance().update(supervisor);
    //     campManager.updatecampsStatus();
    // }

    /**
     * allocate a camp
     *
     * @param campID the ID of the camp
     * @param studentID the ID of the student
     * @throws ModelNotFoundException if the camp is not found
     */
    // public static void allocatecamp(String campID, String studentID) throws ModelNotFoundException {
    //     Camp p1 = CampRepository.getInstance().getByID(campID);
    //     Student student;
    //     try {
    //         student = StudentRepository.getInstance().getByID(studentID);
    //     } catch (ModelNotFoundException e) {
    //         throw new IllegalStateException("Student not found");
    //     }
    //     if (p1.getStatus() == campStatus.ALLOCATED) {
    //         throw new IllegalStateException("camp is already allocated");
    //     }
    //     if (student.getStatus() == StudentStatus.REGISTERED) {
    //         throw new IllegalStateException("Student is already registered");
    //     }
    //     p1.setStatus(campStatus.ALLOCATED);
    //     p1.setStudentID(studentID);
    //     student.setcampID(campID);
    //     student.setSupervisorID(p1.getSupervisorID());
    //     student.setStatus(StudentStatus.REGISTERED);
    //     String supervisorID = p1.getSupervisorID();
    //     Supervisor supervisor = FacultyRepository.getInstance().getByID(supervisorID);
    //     CampRepository.getInstance().update(p1);
    //     StudentRepository.getInstance().update(student);
    //     FacultyRepository.getInstance().update(supervisor);
    //     campManager.updatecampsStatus();
    // }

    /**
     * load camps from csv resource file
     */
    public static void loadcamps() {
        List<List<String>> camps = CSVReader.read(Location.RESOURCE_LOCATION + "/resources/CampList.csv", true);
        for (List<String> camp : camps) {
            try {
                String staffName = camp.get(0);
                String campName = camp.get(1);
                List<Staff> staff = StaffRepository.getInstance().findByRules(s -> s.checkUsername(staffName));
                
                if (staff.size() == 0) {
                    System.out.println("Load camp " + campName + " failed: staff " + staffName + " not found");
                } else if (staff.size() == 1) {
                    CampManager.createcamp(campName, staff.get(0).getID());
                } else {
                    System.out.println("Load camp " + campName + " failed: multiple staff found");
                }
            } catch (ModelAlreadyExistsException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * check if the repository is empty
     *
     * @return true if the repository is empty
     */
    public static boolean repositoryIsEmpty() {
        return CampRepository.getInstance().isEmpty();
    }

    /**
     * Check if the camp is not in the repository
     *
     * @param campID the ID of the camp
     * @return true if the camp is not in the repository
     */
    public static boolean notContainscampByID(String campID) {
        return !CampRepository.getInstance().contains(campID);
    }

    /**
     * Check if the camp is in the repository
     *
     * @param campID the ID of the camp
     * @return true if the camp is in the repository
     */
    public static boolean containscampByID(String campID) {
        return CampRepository.getInstance().contains(campID);
    }

    /**
     * get the camp of a student
     *
     * @param student the student
     * @return the camp of the student
     */
    // public static Camp getStudentcamp(Student student) {
    //     if (EmptyID.isEmptyID(student.getcampID())) {
    //         return null;
    //     } else {
    //         try {
    //             return CampRepository.getInstance().getByID(student.getcampID());
    //         } catch (ModelNotFoundException e) {
    //             throw new IllegalStateException("camp " + student.getcampID() + " not found");
    //         }
    //     }
    // }

    /**
     * get the camp of a supervisor
     *
     * @param campID the ID of the camp
     * @return the camp of the supervisor
     * @throws ModelNotFoundException if the camp is not found
     */
    public static Camp getByID(String campID) throws ModelNotFoundException {
        return CampRepository.getInstance().getByID(campID);
    }

    /**
     * get all available camps
     *
     * @return all available camps
     */
    // public static List<Camp> getAllAvailablecamp() {
    //     return CampRepository.getInstance().findByRules(p -> p.getStatus() == campStatus.AVAILABLE);
    // }

    /**
     * get camp by the camp ID
     * @param campID the ID of the camp
     * @return the camp
     * @throws ModelNotFoundException if the camp is not found
     */
    // public static Camp getcampByID(String campID) throws ModelNotFoundException {
    //     return CampRepository.getInstance().getByID(campID);
    // }

    /**
     * get all camps by supervisor
     *
     * @param supervisorID the ID of the supervisor
     * @return all camps by supervisor
     */
    // public static List<Camp> getAllcampsBySupervisor(String supervisorID) {
    //     return CampRepository.getInstance().findByRules(p -> p.getSupervisorID().equalsIgnoreCase(supervisorID));
    // }

    /**
     * update the status of all camps
     */
    // public static void updatecampsStatus() {
    //     List<Staff> staffs = StaffManager.getAllUnavailableStaff();
    //     Set<String> supervisorIDs = new HashSet<>();
    //     for (Supervisor supervisor : supervisors) {
    //         supervisorIDs.add(supervisor.getID());
    //     }
    //     List<camp> camps = CampRepository.getInstance().getList();
    //     for (camp camp : camps) {
    //         if (supervisorIDs.contains(camp.getSupervisorID()) && camp.getStatus() == campStatus.AVAILABLE) {
    //             camp.setStatus(campStatus.UNAVAILABLE);
    //         }
    //         if (!supervisorIDs.contains(camp.getSupervisorID()) && camp.getStatus() == campStatus.UNAVAILABLE) {
    //             camp.setStatus(campStatus.AVAILABLE);
    //         }
    //     }
    //     CampRepository.getInstance().updateAll(camps);
    // }
}
