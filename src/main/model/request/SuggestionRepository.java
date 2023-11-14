package main.model.request; 
import main.repository.Repository; 
import java.util.List;
import java.util.Map;
public class SuggestionRepository extends Repository<Suggestion>{

    SuggestionRepository() {
        super();
        load();
    }
    public String getFilePath() {
        return null;
    }
    public static SuggestionRepository getInstance() {
        return new EnquiryRepository();
    }
    public void setAll(List<Map<String, String>> listOfMappableObjects) {
        for (Map<String, String> map : listOfMappableObjects) {
            getAll().add(new Suggestion(map));
        }
    }

}