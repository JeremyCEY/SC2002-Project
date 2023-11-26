/**
 * The main.repository.request package contains the EnquiryRepository class, which is a repository
 * that manages the persistence and retrieval of Enquiry objects through file I/O operations.
 * It extends the Repository class, providing basic CRUD operations for the repository.
 */
package main.repository.request;

import main.model.request.Enquiry;
import main.repository.Repository;

import java.util.List;
import java.util.Map;

import static main.utils.config.Location.RESOURCE_LOCATION;

/**
 * The EnquiryRepository class is a repository that manages the persistence and retrieval of
 * Enquiry objects through file I/O operations. It extends the Repository class, providing
 * basic CRUD operations for the repository.
 */
public class EnquiryRepository extends Repository<Enquiry> {

    /**
     * The file path of the enquiry data file.
     */
    private static final String FILE_PATH = "/data/request/enquiry.txt";

    /**
     * Constructs a new EnquiryRepository object and loads the data from the enquiry data file.
     */
    EnquiryRepository() {
        super();
        load();
    }

    /**
     * Gets the file path of the enquiry data file.
     *
     * @return the file path of the enquiry data file
     */
    @Override
    public String getFilePath() {
        return RESOURCE_LOCATION + FILE_PATH;
    }

    /**
     * Gets a new EnquiryRepository object.
     *
     * @return a new EnquiryRepository object
     */
    public static EnquiryRepository getInstance() {
        return new EnquiryRepository();
    }

    /**
     * Sets the list of mappable objects by converting a list of maps to a list of Enquiry objects.
     *
     * @param listOfMappableObjects the list of mappable objects
     */
    @Override
    public void setAll(List<Map<String, String>> listOfMappableObjects) {
        for (Map<String, String> map : listOfMappableObjects) {
            getAll().add(new Enquiry(map));
        }
    }
}
