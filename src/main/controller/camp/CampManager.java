package main.controller.camp;

import main.model.camp.Camp;
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

import java.util.List;
import java.util.Map;
import java.util.HashMap;
/**
 * Manages camps and provides functionalities for camp creation, updating, retrieval,
 * attendee registration, withdrawal, and loading from CSV resources.
 */
public class CampManager {
    /**
     * Updates the camp with the specified ID.
     *
     * @param campID      the ID of the camp to be updated
     * @param updatedCamp the updated camp object
     * @throws ModelNotFoundException if the camp is not found
     */
    public static void updateCamp(String campID, Camp updatedCamp) throws ModelNotFoundException {
        updatedCamp.setCampID(campID);
        CampRepository.getInstance().update(updatedCamp);
    }

    /**
     * Retrieves the list of all camps.
     *
     * @return the list of all camps
     */
    public static List<Camp> viewAllcamp() {
        return CampRepository.getInstance().getList();
    }

    /**
     * Retrieves the list of all camps.
     *
     * @return the list of all camps
     */
    public static List<Camp> getAllcamps() {
        return CampRepository.getInstance().getList();
    }

    /**
     * Retrieves the list of available camps.
     *
     * @return the list of available camps
     */
    public static List<Camp> viewAvailableCamps() {
        return CampRepository.getInstance().findByRules(camp -> camp.getVisibility().equals("true"));
    }

    /**
     * Creates a new camp with the provided details.
     *
     * @return the created camp
     * @throws ModelAlreadyExistsException if the camp already exists
     */
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
     * Retrieves a new camp ID for camp creation.
     *
     * @return the new camp ID
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
     * Withdraws a student from a camp.
     *
     * @param campID    the ID of the camp
     * @param studentID the ID of the student
     * @throws ModelNotFoundException if the camp or student is not found
     */
    public static void withdrawCampAttendee(String campID, String studentID) throws ModelNotFoundException {
        Camp camp = CampRepository.getInstance().getByID(campID);
        Student student;
        try {
            student = StudentRepository.getInstance().getByID(studentID);
        } catch (ModelNotFoundException e) {
            throw new IllegalStateException("Student not found");
        }

        student.removeACamp(campID);
        student.addPCamp(campID);
        camp.setFilledSlots(camp.getFilledSlots() - 1);
        CampRepository.getInstance().update(camp);
        StudentRepository.getInstance().update(student);
    }

    /**
     * Registers a student for a camp.
     *
     * @param campID    the ID of the camp
     * @param studentID the ID of the student
     * @throws ModelNotFoundException if the camp or student is not found
     */
    public static void registerCampAttendee(String campID, String studentID) throws ModelNotFoundException {
        Camp camp = CampRepository.getInstance().getByID(campID);
        Student student;
        try {
            student = StudentRepository.getInstance().getByID(studentID);
        } catch (ModelNotFoundException e) {
            throw new IllegalStateException("Student not found");
        }

        student.addACamp(campID);
        camp.setFilledSlots(camp.getFilledSlots() + 1);

        CampRepository.getInstance().update(camp);
        StudentRepository.getInstance().update(student);
    }

    /**
     * Registers a student as a camp committee member.
     *
     * @param campID    the ID of the camp
     * @param studentID the ID of the student
     * @throws ModelNotFoundException if the camp or student is not found
     */
    public static void registerCampCommittee(String campID, String studentID) throws ModelNotFoundException {
        Camp camp = CampRepository.getInstance().getByID(campID);
        Student student;
        try {
            student = StudentRepository.getInstance().getByID(studentID);
        } catch (ModelNotFoundException e) {
            throw new IllegalStateException("Student not found");
        }

        student.addCCamp(campID);
        camp.setFilledCampCommSlots(camp.getFilledCampCommSlots() + 1);

        CampRepository.getInstance().update(camp);
        StudentRepository.getInstance().update(student);
        // CampManager.updatecampsStatus();
    }


    /**
    * Loads camps from the CSV resource file.
    */
    public static void loadCamps() {
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
    * Checks if the camp repository is empty.
    *
    * @return true if the repository is empty, false otherwise.
    */
    public static boolean repositoryIsEmpty() {
        return CampRepository.getInstance().isEmpty();
    }

    /**
     * Checks if the camp with the specified ID is not in the repository.
    *
    * @param campID the ID of the camp to check.
    * @return true if the camp is not in the repository, false otherwise.
    */
    public static boolean notContainsCampByID(String campID) {
        return !CampRepository.getInstance().contains(campID);
    }

    /**
    * Checks if the camp with the specified ID is in the repository.
    *
    * @param campID the ID of the camp to check.
    * @return true if the camp is in the repository, false otherwise.
    */
    public static boolean containscampByID(String campID) {
        return CampRepository.getInstance().contains(campID);
    }

    /**
    * Retrieves the camps associated with a student and their roles (Committee or Attendee).
    *
    * @param student the student for whom to retrieve camps.
    * @return a map of camps and their associated roles, or null if no camps are associated.
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
    * Retrieves the camp with the specified ID.
    *
    * @param campID the ID of the camp to retrieve.
    * @return the camp with the specified ID.
    * @throws ModelNotFoundException if the camp is not found.
    */
    public static Camp getByID(String campID) throws ModelNotFoundException {
        return CampRepository.getInstance().getByID(campID);
    }

    /**
    * Retrieves all available camps.
    *
    * @return a list of all available camps.
    */
    public static List<Camp> getAllVisibleCamps() {
        return CampRepository.getInstance().findByRules(p -> p.getVisibility().equals("true"));
    }

    /**
    * Retrieves all invisible camps.
    *
    * @return a list of all invisible camps.
    */
    public static List<Camp> getAllInvisibleCamps() {
        return CampRepository.getInstance().findByRules(p -> p.getVisibility().equals("false"));
    }

    /**
    * Retrieves camps available to a student based on their faculty.
    *
    * @param student the student for whom to retrieve camps.
    * @return a list of camps available to the student.
    */
    public static List<Camp> getCampsForStudent(Student student) {
        String studentFaculty = student.getFaculty().toString();

        return CampRepository.getInstance().findByRules(camp ->
                camp.getVisibility().equals("true") &&
                (camp.getOpenTo().toString().equals("NTU") || camp.getOpenTo().toString().equals(studentFaculty))
        );
    }

    /**
    * Retrieves all camps associated with a specific staff member.
    *
    * @param staff the staff member for whom to retrieve camps.
    * @return a list of camps associated with the staff member.
    */
    public static List<Camp> getAllCampsByStaff(Staff staff) {
        return CampRepository.getInstance().findByRules(c -> c.getStaffID().equals(staff.getID()));
    }


   /**
    * Retrieves a camp based on its unique identifier (ID).
    *
    * @param campID the ID of the camp to retrieve.
    * @return the camp with the specified ID.
    * @throws ModelNotFoundException if the camp is not found.
    */
    public static Camp getCampByID(String campID) throws ModelNotFoundException {
        return CampRepository.getInstance().getByID(campID);
    }

}
