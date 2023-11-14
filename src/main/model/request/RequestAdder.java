package main.model.request; 
import main.model.user.Staff;
import main.model.user.Student;
import main.model.user.User;
import main.repository.user.StaffRepository;
import main.repository.user.StudentRepository;
import main.utils.exception.ModelAlreadyExistsException;
import main.model.request.EnquiryRepository; 

public class RequestAdder{

 public static void addRequest(Request request) throws ModelAlreadyExistsException {
        if (request instanceof Enquiry enquiry) {
            addEnquiry(enquiry);
        } else if (request instanceof Suggestion suggestion) {
            addSuggestion(suggestion);
        }
    }

    private static void addEnquiry(Enquiry enquiry) throws ModelAlreadyExistsException {
        EnquiryRepository.getInstance().add(enquiry);
    }
    
    /**
     * Adds the specified Suggestion to the database.
     *
     * @param suggestion the suggestion to be added
     * @throws ModelAlreadyExistsException if the suggestion already exists in the database
     */
    private static void addSuggestion(Suggestion suggestion) throws ModelAlreadyExistsException {
        SuggestionRepository.getInstance().add(suggestion);
    }
}

}