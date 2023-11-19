package main.controller.request; 

import main.model.user.*;
import main.repository.camp.CampRepository;
import main.model.camp.Camp;
import main.model.request.Request;
import main.repository.request.EnquiryRepository;
import main.model.request.Suggestion;
import main.repository.user.StaffRepository;
import main.repository.user.StudentRepository;
import main.repository.request.EnquiryRepository;
import main.repository.request.SuggestionRepository;
import main.model.request.Enquiry;
import main.utils.config.Location;
import main.utils.exception.PasswordIncorrectException;
import main.utils.exception.ModelAlreadyExistsException;
import main.utils.exception.ModelNotFoundException;

import java.util.List;
import java.util.Map;

public class RequestManager {


    public static List<Enquiry> viewAllEnquiry() {
        return EnquiryRepository.getInstance().getList();
    }

    public static List<Enquiry> viewEnquiryBySender(String studentID) {
        return EnquiryRepository.getInstance().findByRules(e -> e.getSenderID().equals(studentID));
        }

    public static List<Suggestion> viewAllSuggestion() {
        return SuggestionRepository.getInstance().getList();
    }

    public static List<Suggestion> viewSuggestionBySender(String studentID) {
        return SuggestionRepository.getInstance().findByRules(e -> e.getSenderID().equals(studentID));
        }


    public static String getNewEnquiryID() {
        int max = 0;
        for (Enquiry p :EnquiryRepository.getInstance()) {
            int id = Integer.parseInt(p.getID().substring(1));
            if (id > max) {
                max = id;
            }
        }
        return "E" + (max + 1);
    }

    public static String getNewSuggestionID() {
        int max = 0;
        for (Suggestion p :SuggestionRepository.getInstance()) {
            int id = Integer.parseInt(p.getID().substring(1));
            if (id > max) {
                max = id;
            }
        }
        return "S" + (max + 1);
    }

	public static void createEnquiry(String requestID, String campID, String studentID, 
			String message) throws ModelAlreadyExistsException{
        
        Enquiry e = new Enquiry(requestID, campID, studentID, message);
        EnquiryRepository.getInstance().add(e);
        
    }

    public static Enquiry createEnquiry(String campID, String studentID, 
			String message) throws ModelAlreadyExistsException{
		Enquiry e = new Enquiry(getNewEnquiryID(), campID, studentID, message);
		EnquiryRepository.getInstance().add(e);
		return e;
    }

    public static void updateEnquiry(String enquiryID, Enquiry updatedEnquiry) throws ModelNotFoundException {
        updatedEnquiry.setID(enquiryID);
        EnquiryRepository.getInstance().update(updatedEnquiry);
    }



	public static void createSuggestion(String requestID, String campID, String studentID) 
            throws ModelAlreadyExistsException{
        
        Suggestion s = new Suggestion(requestID, campID, studentID);
        SuggestionRepository.getInstance().add(s);
        
    }

    public static Suggestion createSuggestion(String campID, String studentID) 
            throws ModelAlreadyExistsException{

		Suggestion s = new Suggestion(getNewSuggestionID(), campID, studentID);
		SuggestionRepository.getInstance().add(s);
		return s;
    }

    public static void updateSuggestion(String suggestionID, Suggestion updatedSuggestion) throws ModelNotFoundException {
        updatedSuggestion.setID(suggestionID);
        SuggestionRepository.getInstance().update(updatedSuggestion);
    }

    public static Enquiry getEnquiryByID(String enquiryID) throws ModelNotFoundException{
        return EnquiryRepository.getInstance().getByID(enquiryID);
    }

    public static Suggestion getSuggestionByID(String suggestionID) throws ModelNotFoundException{
        return SuggestionRepository.getInstance().getByID(suggestionID);
    }

}