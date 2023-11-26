package main.controller.account;

import main.controller.account.password.PasswordManager;
import main.controller.account.user.UserAdder;
import main.controller.account.user.UserFinder;
import main.controller.account.user.UserUpdater;
import main.model.user.*;
import main.repository.user.StaffRepository;
import main.repository.user.StudentRepository;
import main.utils.config.Location;
import main.utils.exception.PasswordIncorrectException;
import main.utils.exception.ModelAlreadyExistsException;
import main.utils.exception.ModelNotFoundException;
import main.utils.iocontrol.CSVReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages user accounts and provides functionalities for login, password change,
 * user registration, user retrieval, and loading users from CSV resources.
 */
public class AccountManager {
    /**
     * Logs in a user with the provided user type, user ID, and password.
     *
     * @param userType the type of the user to be logged in
     * @param userID   the ID of the user to be logged in
     * @param password the password of the user to be logged in
     * @return the logged-in user
     * @throws PasswordIncorrectException if the password is incorrect
     * @throws ModelNotFoundException     if the user is not found
     */
    public static User login(UserType userType, String userID, String password)
            throws PasswordIncorrectException, ModelNotFoundException {
        User user = UserFinder.findUser(userID, userType);
        // System.err.println("User found: " + user.getUserName() + " " + user.getID());
        if (PasswordManager.checkPassword(user, password)) {
            return user;
        } else {
            throw new PasswordIncorrectException();
        }
    }

    /**
     * Changes the password of a user with the provided user type, user ID, old password, and new password.
     *
     * @param userType    the type of the user to change the password
     * @param userID      the ID of the user to change the password
     * @param oldPassword the old password of the user
     * @param newPassword the new password for the user
     * @throws PasswordIncorrectException if the old password is incorrect
     * @throws ModelNotFoundException     if the user is not found
     */
    public static void changePassword(UserType userType, String userID, String oldPassword, String newPassword)
            throws PasswordIncorrectException, ModelNotFoundException {
        User user = UserFinder.findUser(userID, userType);
        PasswordManager.changePassword(user, oldPassword, newPassword);
        UserUpdater.updateUser(user);
    }

    /**
     * Retrieves a list of users with the specified user name.
     *
     * @param userName the user name to search for
     * @return a list of users with the given user name
     */
    public static List<User> getUsersByUserName(String userName) {
        List<Student> studentList = StudentRepository.getInstance().findByRules(
                student -> student.checkUsername(userName));
        List<Staff> staffList = StaffRepository.getInstance().findByRules(
                staff -> staff.checkUsername(userName));
        List<User> userList = new ArrayList<>();
        userList.addAll(studentList);
        userList.addAll(staffList);
        return userList;
    }

    /**
     * Registers a new user with the provided user type, user ID, password, name, email, and faculty.
     *
     * @param userType the type of the user to be registered
     * @param userID   the ID of the user to be registered
     * @param password the password for the user
     * @param name     the name of the user
     * @param email    the email of the user
     * @param faculty  the faculty of the user
     * @return the registered user
     * @throws ModelAlreadyExistsException if the user already exists
     */
    public static User register(UserType userType, String userID, String password, String name, String email,
            Faculty faculty)
            throws ModelAlreadyExistsException {
        User user = UserFactory.create(userType, userID, password, name, email, faculty);
        UserAdder.addUser(user);
        return user;
    }

    /**
     * Registers a new user with the provided user type, user ID, name, email, and faculty.
     *
     * @param userType the type of the user to be registered
     * @param userID   the ID of the user to be registered
     * @param name     the name of the user
     * @param email    the email of the user
     * @param faculty  the faculty of the user
     * @return the registered user
     * @throws ModelAlreadyExistsException if the user already exists
     */
    public static User register(UserType userType, String userID, String name, String email, Faculty faculty)
            throws ModelAlreadyExistsException {
        return register(userType, userID, "password", name, email, faculty);
    }

    /**
     * Loads the students and coordinators from the CSV file
     *
     * @param email the email of the user
     * @return the ID of the user
     */
    private static String getID(String email) {
        return email.split("@")[0];
    }

    /**
     * Loads the students from the CSV resource file
     */
    private static void loadStudents() {
        List<List<String>> studentList = CSVReader.read(Location.RESOURCE_LOCATION + "/resources/StudentList.csv",
                true);
        for (List<String> row : studentList) {
            String name = row.get(0);
            String email = row.get(1);
            String userID = getID(email);
            String facultyString = row.get(2);
            Faculty faculty = Faculty.NTU;

            switch (facultyString) {
                case "ADM" -> faculty = Faculty.ADM;
                case "EEE" -> faculty = Faculty.EEE;
                case "NBS" -> faculty = Faculty.NBS;
                case "SCSE" -> faculty = Faculty.SCSE;
                case "SSS" -> faculty = Faculty.SSS;
            }

            try {
                register(UserType.STUDENT, userID, name, email, faculty);
            } catch (ModelAlreadyExistsException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Loads the staff members from the CSV resource file
     */
    private static void loadStaffs() {
        List<List<String>> staffList = CSVReader.read(Location.RESOURCE_LOCATION + "/resources/StaffList.csv",
                true);
        for (List<String> row : staffList) {
            String name = row.get(0);
            String email = row.get(1);
            String userID = getID(email);
            String facultyString = row.get(2);
            Faculty faculty = Faculty.NTU;

            switch (facultyString) {
                case "ADM" -> faculty = Faculty.ADM;
                case "EEE" -> faculty = Faculty.EEE;
                case "NBS" -> faculty = Faculty.NBS;
                case "SCSE" -> faculty = Faculty.SCSE;
                case "SSS" -> faculty = Faculty.SSS;
            }

            try {
                register(UserType.STAFF, userID, name, email, faculty);
            } catch (ModelAlreadyExistsException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Loads all users from the CSV resource file
     */
    public static void loadUsers() {
        loadStudents();
        loadStaffs();
    }

    /**
     * Checks if both student and staff repositories are empty.
     *
     * @return true if both repositories are empty, false otherwise
     */
    public static boolean repositoryIsEmpty() {
        return StudentRepository.getInstance().isEmpty() &&
                StaffRepository.getInstance().isEmpty();
    }

    /**
     * Retrieves a user by the provided user type and ID.
     *
     * @param userType the type of the user
     * @param ID       the ID of the user
     * @return the user with the domain and ID
     * @throws ModelNotFoundException if the user is not found
     */
    public static User getByDomainAndID(UserType userType, String ID) throws ModelNotFoundException {
        return switch (userType) {
            case STUDENT -> StudentRepository.getInstance().getByID(ID);
            case STAFF -> StaffRepository.getInstance().getByID(ID);
        };
    }
}
