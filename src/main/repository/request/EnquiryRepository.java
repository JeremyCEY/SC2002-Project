package main.repository.request; 
import java.util.List;
import java.util.Map;

import main.model.request.Enquiry;
import main.repository.Repository; 

import static main.utils.config.Location.RESOURCE_LOCATION;

public class EnquiryRepository extends Repository<Enquiry>{

    /**
     * The file path of the project data file.
     */
    private static final String FILE_PATH = "/data/request/enquiry.txt";

    EnquiryRepository() {
        super();
        load();
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

    public static EnquiryRepository getInstance() {
        return new EnquiryRepository();
    }
    
    
    public void setAll(List<Map<String, String>> listOfMappableObjects) {
        for (Map<String, String> map : listOfMappableObjects) {
            getAll().add(new Enquiry(map));
        }
    }


}