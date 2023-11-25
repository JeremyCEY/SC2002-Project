package main.repository.request;

import main.repository.Repository;
import main.model.request.Suggestion;
import java.util.List;
import java.util.Map;

import static main.utils.config.Location.RESOURCE_LOCATION;

public class SuggestionRepository extends Repository<Suggestion> {

    /**
     * The file path of the suggestion data file.
     */
    private static final String FILE_PATH = "/data/request/suggestion.txt";

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

    public static SuggestionRepository getInstance() {
        return new SuggestionRepository();
    }

    public void setAll(List<Map<String, String>> listOfMappableObjects) {
        for (Map<String, String> map : listOfMappableObjects) {
            getAll().add(new Suggestion(map));
        }
    }

}