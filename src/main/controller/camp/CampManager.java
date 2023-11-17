package main.controller.camp;

import main.controller.request.StaffManager;
import main.model.camp.Camp;
//import main.model.camp.CampStatus;
import main.model.request.Enquiry;
import main.model.request.RequestStatus;
import main.model.request.RequestType;
import main.model.user.Student;
import main.model.user.Faculty;
import main.model.user.Staff;
import main.repository.camp.CampRepository;
import main.repository.user.StaffRepository;
import main.repository.user.StudentRepository;
// import main.repository.request.RequestRepository;
import main.model.request.Suggestion;
import main.utils.config.Location;
import main.utils.exception.ModelAlreadyExistsException;
import main.utils.exception.ModelNotFoundException;
import main.utils.iocontrol.CSVReader;
import main.utils.parameters.EmptyID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

/**
 * A class manages the camp
 */
public class CampManager {

    /**
     * Change the title of a camp
     *
     * @param campID   the ID of the camp
     * @param newTitle the new title of the camp
     * @throws ModelNotFoundException if the camp is not found
     */
    // public static void changecampTitle(String campID, String newTitle) throws
    // ModelNotFoundException {
    // Camp p1 = CampRepository.getInstance().getByID(campID);
    // p1.setcampTitle(newTitle);
    // CampRepository.getInstance().update(p1);
    // CampManager.updatecampsStatus();
    // }

    public static void updateCamp(String campID, Camp updatedCamp) throws ModelNotFoundException {
        updatedCamp.setCampID(campID);
        CampRepository.getInstance().update(updatedCamp);
    }

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
    public static List<Camp> viewAvailableCamps() {
        return CampRepository.getInstance().findByRules(camp -> camp.getVisibility().equals("true"));
    }

    /**
     * create a new camp
     *
     * @param campID       the ID of the camp
     * @param campTitle    the title of the camp
     * @param supervisorID the ID of the supervisor
     * @throws ModelAlreadyExistsException if the camp already exists
     */
    // public static void createCamp(String campID, String campName, String dates,
    // String registrationClosingDate,
    // Faculty openTo, String location, int filledSlots, int totalSlots, int
    // filledCampCommSlots,
    // int campCommSlots, String description,
    // String staffID, String visibility) throws ModelAlreadyExistsException {
    // Camp c1 = new Camp(campID, campName, dates, registrationClosingDate,
    // openTo, location, filledSlots, totalSlots, filledCampCommSlots,
    // campCommSlots, description, staffID,
    // visibility);
    // CampRepository.getInstance().add(c1);
    // // CampManager.updatecampsStatus();
    // }

    public static Camp createCamp(String campName, String dates, String registrationClosingDate,
            Faculty openTo, String location, int filledSlots, int totalSlots, int filledCampCommSlots,
            int campCommSlots,
            String description, String staffID, String visibility) throws ModelAlreadyExistsException {
        Camp c1 = new Camp(getNewCampID(), campName, dates, registrationClosingDate, openTo, location,
                filledSlots, totalSlots, filledCampCommSlots, campCommSlots, description, staffID, visibility);
        CampRepository.getInstance().add(c1);
        return c1;
    }

    /**
     * get the list of all camps
     *
     * @return the list of all camps
     */
    public static List<Camp> getAllcamps() {
        return CampRepository.getInstance().getList();
    }

    /**
     * get the list of all camps by status
     *
     * @param campStatus the status of the camp
     * @return the list of all camps
     */
    // public static List<Camp> getAllcampByStatus(CampStatus campStatus) {
    // return CampRepository.getInstance().findByRules(camp ->
    // camp.getStatus().equals(campStatus));
    // }

    /**
     * get the list of all camps by supervisor
     *
     * @return the list of all camps
     */
    public static String getNewCampID() {
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
     * @param campID       the ID of the camp
     * @param supervisorID the ID of the supervisor
     * @throws ModelNotFoundException if the camp is not found
     */
    // public static void transferToNewSupervisor(String campID, String
    // supervisorID) throws ModelNotFoundException {
    // camp p1 = CampRepository.getInstance().getByID(campID);
    // if (!FacultyRepository.getInstance().contains(supervisorID)) {
    // throw new IllegalStateException("Supervisor Not Found!");
    // }
    // Supervisor oldsupervisor =
    // FacultyRepository.getInstance().getByID(p1.getSupervisorID());
    // Supervisor newsupervisor =
    // FacultyRepository.getInstance().getByID(supervisorID);
    // Student student = StudentRepository.getInstance().getByID(p1.getStudentID());
    // student.setSupervisorID(supervisorID);
    // p1.setSupervisorID(supervisorID);
    // CampRepository.getInstance().update(p1);
    // FacultyRepository.getInstance().update(oldsupervisor);
    // FacultyRepository.getInstance().update(newsupervisor);
    // StudentRepository.getInstance().update(student);
    // CampManager.updatecampsStatus();
    // }

    /**
     * withdraw from a camp
     *
     * @param campID the ID of the camp
     * @throws ModelNotFoundException if the camp is not found
     */
    public static void withdrawCampAttendee(String campID, String studentID) throws ModelNotFoundException {
        Camp camp = CampRepository.getInstance().getByID(campID);
        // if (p1.getStatus() != campStatus.ALLOCATED) {
        // throw new IllegalStateException("The camp status is not ALLOCATED");
        // }
        Student student;
        try {
            student = StudentRepository.getInstance().getByID(studentID);
        } catch (ModelNotFoundException e) {
            throw new IllegalStateException("Student not found");
        }

        student.removeACamp(campID);
        camp.setFilledSlots(camp.getFilledSlots() - 1);
        CampRepository.getInstance().update(camp);
        StudentRepository.getInstance().update(student);
        // campManager.updatecampsStatus();
    }

    /**
     * student register a camp
     *
     * @param campID    the ID of the camp
     * @param studentID the ID of the student
     * @throws ModelNotFoundException if the camp is not found
     */
    public static void registerCampAttendee(String campID, String studentID) throws ModelNotFoundException {
        Camp camp = CampRepository.getInstance().getByID(campID);
        Student student;
        try {
            student = StudentRepository.getInstance().getByID(studentID);
        } catch (ModelNotFoundException e) {
            throw new IllegalStateException("Student not found");
        }
        // if (camp.getFilledSlots() >= camp.getTotalSlots()) {
        // throw new IllegalStateException("Attendee slots maxed");
        // }

        // check whether student has register before

        student.addACamp(campID);
        camp.setFilledSlots(camp.getFilledSlots() + 1);

        CampRepository.getInstance().update(camp);
        StudentRepository.getInstance().update(student);
        // CampManager.updatecampsStatus();
    }

    public static void registerCampCommittee(String campID, String studentID) throws ModelNotFoundException {
        Camp camp = CampRepository.getInstance().getByID(campID);
        Student student;
        try {
            student = StudentRepository.getInstance().getByID(studentID);
        } catch (ModelNotFoundException e) {
            throw new IllegalStateException("Student not found");
        }
        // if (camp.getFilledSlots() >= camp.getTotalSlots()) {
        // throw new IllegalStateException("Attendee slots maxed");
        // }

        // check whether student has register before

        student.addCCamp(campID);
        camp.setFilledCampCommSlots(camp.getFilledCampCommSlots() + 1);

        CampRepository.getInstance().update(camp);
        StudentRepository.getInstance().update(student);
        // CampManager.updatecampsStatus();
    }


    /**
     * load camps from csv resource file
     */
    public static void loadcamps() {
        List<List<String>> camps = CSVReader.read(Location.RESOURCE_LOCATION + "/resources/CampList.csv", true);
        for (List<String> camp : camps) {
            try {
                String staffName = camp.get(10);
                String campName = camp.get(0);
                List<Staff> staff = StaffRepository.getInstance().findByRules(s -> s.checkUsername(staffName));

                Faculty faculty = Faculty.NTU;

                switch (camp.get(3)) {
                    case "ADM" -> faculty = Faculty.ADM;
                    case "EEE" -> faculty = Faculty.EEE;
                    case "NBS" -> faculty = Faculty.NBS;
                    case "NTU" -> faculty = Faculty.NTU;
                    case "SCSE" -> faculty = Faculty.SCSE;
                    case "SSS" -> faculty = Faculty.SSS;
                }

                if (staff.size() == 0) {
                    System.out.println("Load camp " + campName + " failed: staff " + staffName + " not found");
                } else if (staff.size() == 1) {
                    CampManager.createCamp(
                            camp.get(0), camp.get(1), camp.get(2), faculty, camp.get(4), Integer.parseInt(camp.get(5)), Integer.parseInt(camp.get(6)),
                            Integer.parseInt(camp.get(7)), Integer.parseInt(camp.get(8)), camp.get(9), staff.get(0).getID(), camp.get(11));
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
    public static boolean notContainsCampByID(String campID) {
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
     * get the camps of a student
     *
     * @param student the student
     * @return the camp of the student
     */
    public static Map<Camp, String> getStudentcamps(Student student) {
        if (EmptyID.isEmptyID(student.getCCamps()) && EmptyID.isEmptyID(student.getACamps())) {
            return null;
        } else {
            try {
                Map<Camp, String> camps = new HashMap<>();

                String cCamp;
                String aCamps;

                if (!EmptyID.isEmptyID(student.getCCamps())) {
                    cCamp = student.getCCamps();
                    Camp camp = CampRepository.getInstance().getByID(cCamp);
                    camps.put(camp, "Committee"); // Replace "Associated String" with the actual associated string
                }

                if (!EmptyID.isEmptyID(student.getACamps())) {
                    aCamps = student.getACamps();
                    String[] aCampsArray = aCamps.split(",");
                    for (String campId : aCampsArray) {
                        Camp camp = CampRepository.getInstance().getByID(campId);
                        camps.put(camp, "Attendee"); // Replace "Associated String" with the actual associated string
                    }
                }

                return camps;
            } catch (ModelNotFoundException e) {
                throw new IllegalStateException("camp " + student.getACamps() + " notfound");
            }
        }
    }

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
    public static List<Camp> getAllVisibleCamps() {
        return CampRepository.getInstance().findByRules(p -> p.getVisibility() != "true");
    }

    public static List<Camp> getAllCampsByStaff(Staff staff) {
        return CampRepository.getInstance().findByRules(c -> c.getStaffID().equals(staff.getID()));
    }

    // public static List<Enquiry> getAllPendingEnquiriesByStaff(Staff staff) {
    // List<String> campIDs = CampManager.getAllCampsByStaff(staff).stream()
    // .filter(
    // c -> c.getStaffID().equals(staff.getID())
    // && c.getVisibility().equals("true"))
    // .map(Camp::getID)
    // .collect(Collectors.toList());
    // return RequestRepository.getInstance().findByRules(
    // r -> r.getRequestType() == RequestType.ENQUIRY,
    // r -> r.getRequestStatus() == RequestStatus.PENDING,
    // r -> campIDs.contains(r.getCampID()))
    // .stream()
    // .map(r -> (Enquiry) r)
    // .collect(Collectors.toList());
    // }

    // public static List<Suggestion> getAllPendingSuggestionsByStaff(Staff staff) {
    // List<String> campIDs = CampManager.getAllCampsByStaff(staff).stream()
    // .filter(
    // c -> c.getStaffID().equals(staff.getID())
    // && c.getVisibility().equals("true"))
    // .map(Camp::getID)
    // .collect(Collectors.toList());
    // return RequestRepository.getInstance().findByRules(
    // r -> r.getRequestType() == RequestType.SUGGESTION,
    // r -> r.getRequestStatus() == RequestStatus.PENDING,
    // r -> campIDs.contains(r.getCampID()))
    // .stream()
    // .map(r -> (Suggestion) r)
    // .collect(Collectors.toList());
    // }

    /**
     * get camp by the camp ID
     * 
     * @param campID the ID of the camp
     * @return the camp
     * @throws ModelNotFoundException if the camp is not found
     */
    public static Camp getCampByID(String campID) throws ModelNotFoundException {
        return CampRepository.getInstance().getByID(campID);
    }

    /**
     * get all camps by supervisor
     *
     * @param supervisorID the ID of the supervisor
     * @return all camps by supervisor
     */
    // public static List<Camp> getAllcampsBySupervisor(String supervisorID) {
    // return CampRepository.getInstance().findByRules(p ->
    // p.getSupervisorID().equalsIgnoreCase(supervisorID));
    // }

    /**
     * update the status of all camps
     */
    // public static void updatecampsStatus() {
    // List<Staff> staffs = StaffManager.getAllUnavailableStaff();
    // Set<String> supervisorIDs = new HashSet<>();
    // for (Supervisor supervisor : supervisors) {
    // supervisorIDs.add(supervisor.getID());
    // }
    // List<camp> camps = CampRepository.getInstance().getList();
    // for (camp camp : camps) {
    // if (supervisorIDs.contains(camp.getSupervisorID()) && camp.getStatus() ==
    // campStatus.AVAILABLE) {
    // camp.setStatus(campStatus.UNAVAILABLE);
    // }
    // if (!supervisorIDs.contains(camp.getSupervisorID()) && camp.getStatus() ==
    // campStatus.UNAVAILABLE) {
    // camp.setStatus(campStatus.AVAILABLE);
    // }
    // }
    // CampRepository.getInstance().updateAll(camps);
    // }
}
