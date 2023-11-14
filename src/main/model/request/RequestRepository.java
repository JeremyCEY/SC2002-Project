package main.model.request; 
import java.util.List;
import java.util.Map;

import main.repository.Repository; 
public class RequestRepository extends Repository<Enquiry>{

    RequestRepository() {
        super();
        load();
    }
    public String getFilePath() {
        return null;
    }
    public static RequestRepository getInstance() {
        return new RequestRepository();
    }
    /*public void setAll(List<Map<String, String>> listOfMappableObjects) {
        for (Map<String, String> map : listOfMappableObjects) {
            getAll().add(new Request(map));
        }
    }*/
    public void setAll(List<Map<String, String>> listOfMappableObjects) {
    }

}