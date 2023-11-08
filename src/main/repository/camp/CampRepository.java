package main.repository.camp;

import main.model.camp.Camp;
import main.repository.Repository;

import java.util.List;
import java.util.Map;

import static main.utils.config.Location.RESOURCE_LOCATION;

/**
 * The ProjectRepository class is a repository that manages the persistence and retrieval of Project objects
 * through file I/O operations.
 * It extends the Repository class, which provides basic CRUD operations for the repository.
 */
public class CampRepository extends Repository<Camp> {

    /**
     * The file path of the project data file.
     */
    private static final String FILE_PATH = "/data/camp/camp.txt";

    /**
     * Constructs a new ProjectRepository object and loads the data from the project data file.
     */
    CampRepository() {
        super();
        load();
    }

    /**
     * Gets a new ProjectRepository object.
     *
     * @return a new ProjectRepository object
     */
    public static CampRepository getInstance() {
        return new CampRepository();
    }

    /**
     * Gets the file path of the project data file.
     *
     * @return the file path of the project data file
     */
    @Override
    public String getFilePath() {
        return RESOURCE_LOCATION + FILE_PATH;
    }

    /**
     * Sets the list of mappable objects by converting a list of maps to a list of Project objects.
     *
     * @param listOfMappableObjects the list of mappable objects
     */
    @Override
    public void setAll(List<Map<String, String>> listOfMappableObjects) {
        for (Map<String, String> map : listOfMappableObjects) {
            getAll().add(new Camp(map));
        }
    }
}
