/**
 * The main.repository.user package contains the StaffRepository class, which is a repository
 * that manages the persistence and retrieval of Staff objects through file I/O operations.
 * It extends the Repository class, providing basic CRUD operations for the repository.
 */
package main.repository.user;

import main.model.user.Staff;
import main.repository.Repository;

import java.util.List;
import java.util.Map;

import static main.utils.config.Location.RESOURCE_LOCATION;

/**
 * The StaffRepository class is a repository that manages the persistence and retrieval of
 * Staff objects through file I/O operations. It extends the Repository class, providing
 * basic CRUD operations for the repository.
 */
public class StaffRepository extends Repository<Staff> {

    /**
     * The path of the repository file.
     */
    private static final String FILE_PATH = "/data/user/staff.txt";

    /**
     * Constructor for creating a new StaffRepository object.
     */
    StaffRepository() {
        super();
        load();
    }

    /**
     * Gets a new instance of StaffRepository.
     *
     * @return a new instance of StaffRepository
     */
    public static StaffRepository getInstance() {
        return new StaffRepository();
    }

    /**
     * Gets the file path of the repository.
     *
     * @return the file path of the repository
     */
    @Override
    public String getFilePath() {
        return RESOURCE_LOCATION + FILE_PATH;
    }

    /**
     * Sets the list of mappable objects in the repository.
     *
     * @param listOfMappableObjects the list of mappable objects to set
     */
    @Override
    public void setAll(List<Map<String, String>> listOfMappableObjects) {
        for (Map<String, String> map : listOfMappableObjects) {
            getAll().add(new Staff(map));
        }
    }
}

