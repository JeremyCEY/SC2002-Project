/**
 * The main.repository.request package contains the SuggestionRepository class, which is a repository
 * that manages the persistence and retrieval of Suggestion objects through file I/O operations.
 * It extends the Repository class, providing basic CRUD operations for the repository.
 */
package main.repository.request;

import main.repository.Repository;
import main.model.request.Suggestion;

import java.util.List;
import java.util.Map;

import static main.utils.config.Location.RESOURCE_LOCATION;

/**
 * The SuggestionRepository class is a repository that manages the persistence and retrieval of
 * Suggestion objects through file I/O operations. It extends the Repository class, providing
 * basic CRUD operations for the repository.
 */
public class SuggestionRepository extends Repository<Suggestion> {

    /**
     * The file path of the suggestion data file.
     */
    private static final String FILE_PATH = "/data/request/suggestion.txt";

    /**
     * Constructs a new SuggestionRepository object and loads the data from the suggestion data file.
     */
    SuggestionRepository() {
        super();
        load();
    }

    /**
     * Gets the file path of the suggestion data file.
     *
     * @return the file path of the suggestion data file
     */
    @Override
    public String getFilePath() {
        return RESOURCE_LOCATION + FILE_PATH;
    }

    /**
     * Gets a new SuggestionRepository object.
     *
     * @return a new SuggestionRepository object
     */
    public static SuggestionRepository getInstance() {
        return new SuggestionRepository();
    }

    /**
     * Sets the list of mappable objects by converting a list of maps to a list of Suggestion objects.
     *
     * @param listOfMappableObjects the list of mappable objects
     */
    @Override
    public void setAll(List<Map<String, String>> listOfMappableObjects) {
        for (Map<String, String> map : listOfMappableObjects) {
            getAll().add(new Suggestion(map));
        }
    }
}
