package main.controller.account.user;

import main.model.user.Staff;
import main.model.user.Student;
import main.model.user.User;
import main.repository.user.StaffRepository;
import main.repository.user.StudentRepository;
import main.utils.exception.ModelAlreadyExistsException;

/**
 * The UserAdder class allows us to add users to the database.
 */
public class UserAdder {
    /**
     * Adds the specific user to the database.
     *
     * @param user the user to be added
     * @throws ModelAlreadyExistsException if user already exists in the
     *                                     database
     */
    public static void addUser(User user) throws ModelAlreadyExistsException {
        if (user instanceof Student student) {
            addStudent(student);
        } else if (user instanceof Staff staff) {
            addStaff(staff);
        }
    }

    /**
     * Adds the specific staff to the database.
     *
     * @param staff the staff to be added
     * @throws ModelAlreadyExistsException if staff already exists in the
     *                                     database
     */
    private static void addStaff(Staff staff) throws ModelAlreadyExistsException {
        StaffRepository.getInstance().add(staff);
    }

    /**
     * Adds the specific student to the database.
     *
     * @param student the student to be added
     * @throws ModelAlreadyExistsException if student already exists in the
     *                                     database
     */
    private static void addStudent(Student student) throws ModelAlreadyExistsException {
        StudentRepository.getInstance().add(student);
    }
}
