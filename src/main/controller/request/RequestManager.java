/*
 * @Author: tyt1130 tangyutong0306@gmail.com
 * @Date: 2023-11-08 19:38:58
 * @LastEditors: tyt1130 tangyutong0306@gmail.com
 * @LastEditTime: 2023-11-26 11:27:27
 * @FilePath: \SC2002-Project\src\main\controller\request\RequestManager.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package main.controller.request; 

import main.controller.camp.CampManager;
import main.model.user.*;
import main.model.camp.Camp;
import main.model.request.Request;
import main.model.request.Enquiry;
import main.model.request.Suggestion;
import main.repository.camp.CampRepository;
import main.repository.user.StaffRepository;
import main.repository.user.StudentRepository;
import main.repository.request.EnquiryRepository;
import main.repository.request.SuggestionRepository;
import main.model.request.RequestStatus;
import main.utils.config.Location;
import main.utils.exception.PasswordIncorrectException;
import main.utils.exception.ModelAlreadyExistsException;
import main.utils.exception.ModelNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manages requests, including enquiries and suggestions.
 */
public class RequestManager {
    /**
     * Retrieves a list of all enquiries.
     *
     * @return the list of all enquiries.
     */
    public static List<Enquiry> viewAllEnquiry() {
        return EnquiryRepository.getInstance().getList();
    }

    /**
     * Retrieves enquiries sent by a specific student.
     *
     * @param studentID the ID of the student.
     * @return the list of enquiries sent by the student.
     */
    public static List<Enquiry> viewEnquiryBySender(String studentID) {
        return EnquiryRepository.getInstance().findByRules(e -> e.getSenderID().equals(studentID));
    }

    /**
     * Retrieves a list of all suggestions.
     *
     * @return the list of all suggestions.
     */
    public static List<Suggestion> viewAllSuggestion() {
        return SuggestionRepository.getInstance().getList();
    }

    /**
     * Retrieves suggestions sent by a specific student.
     *
     * @param studentID the ID of the student.
     * @return the list of suggestions sent by the student.
     */
    public static List<Suggestion> viewSuggestionBySender(String studentID) {
        return SuggestionRepository.getInstance().findByRules(e -> e.getSenderID().equals(studentID));
        }


    /**
     * Generates a new ID for an enquiry.
     *
     * @return the new enquiry ID.
     */
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

    /**
     * Generates a new ID for a suggestion.
     *
     * @return the new suggestion ID.
     */
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

    /**
     * Creates a new enquiry.
     *
     * @param requestID   the ID of the request related to the enquiry.
     * @param campID   the ID of the camp related to the enquiry.
     * @param studentID the ID of the student creating the enquiry.
     * @param message   the message of the enquiry.
     * @throws ModelAlreadyExistsException if the enquiry already exists.
     */
	public static void createEnquiry(String requestID, String campID, String studentID, 
			String message) throws ModelAlreadyExistsException{
        
        Enquiry e = new Enquiry(requestID, campID, studentID, message);
        EnquiryRepository.getInstance().add(e);
    }

    /**
     * Creates a new enquiry.
     *
     * @param campID   the ID of the camp related to the enquiry.
     * @param studentID the ID of the student creating the enquiry.
     * @param message   the message of the enquiry.
     * @return the created enquiry.
     * @throws ModelAlreadyExistsException if the enquiry already exists.
     */
    public static Enquiry createEnquiry(String campID, String studentID, 
			String message) throws ModelAlreadyExistsException{
		Enquiry e = new Enquiry(getNewEnquiryID(), campID, studentID, message);
		EnquiryRepository.getInstance().add(e);
		return e;
    }

    /**
     * Updates an existing enquiry.
     *
     * @param enquiryID       the ID of the enquiry to update.
     * @param updatedEnquiry the updated enquiry.
     * @throws ModelNotFoundException if the enquiry is not found.
     */
    public static void updateEnquiry(String enquiryID, Enquiry updatedEnquiry) throws ModelNotFoundException {
        updatedEnquiry.setID(enquiryID);
        EnquiryRepository.getInstance().update(updatedEnquiry);
    }

    /**
     * Creates a new suggestion.
     *
     * @param requestID   the ID of the request related to the enquiry.
     * @param campID    the ID of the camp related to the suggestion.
     * @param studentID the ID of the student creating the suggestion.
     * @throws ModelAlreadyExistsException if the suggestion already exists.
     */
	public static void createSuggestion(String requestID, String campID, String studentID) 
            throws ModelAlreadyExistsException{
        
        Suggestion s = new Suggestion(requestID, campID, studentID);
        SuggestionRepository.getInstance().add(s);
        
    }

    /**
     * Creates a new suggestion.
     *
     * @param campID    the ID of the camp related to the suggestion.
     * @param studentID the ID of the student creating the suggestion.
     * @return the created suggestion.
     * @throws ModelAlreadyExistsException if the suggestion already exists.
     */
    public static Suggestion createSuggestion(String campID, String studentID) 
            throws ModelAlreadyExistsException{

		Suggestion s = new Suggestion(getNewSuggestionID(), campID, studentID);
		SuggestionRepository.getInstance().add(s);
		return s;
    }

    /**
     * Updates an existing suggestion.
     *
     * @param suggestionID       the ID of the suggestion to update.
     * @param updatedSuggestion the updated suggestion.
     * @throws ModelNotFoundException if the suggestion is not found.
     */
    public static void updateSuggestion(String suggestionID, Suggestion updatedSuggestion) throws ModelNotFoundException {
        updatedSuggestion.setID(suggestionID);
        SuggestionRepository.getInstance().update(updatedSuggestion);
    }

    /**
     * Retrieves an enquiry by its ID.
     *
     * @param enquiryID the ID of the enquiry.
     * @return the enquiry with the specified ID.
     * @throws ModelNotFoundException if the enquiry is not found.
     */
    public static Enquiry getEnquiryByID(String enquiryID) throws ModelNotFoundException{
        return EnquiryRepository.getInstance().getByID(enquiryID);
    }

    /**
     * Retrieves a suggestion by its ID.
     *
     * @param suggestionID the ID of the suggestion.
     * @return the suggestion with the specified ID.
     * @throws ModelNotFoundException if the suggestion is not found.
     */
    public static Suggestion getSuggestionByID(String suggestionID) throws ModelNotFoundException{
        return SuggestionRepository.getInstance().getByID(suggestionID);
    }

    /**
     * Retrieves all pending enquiries related to a specific camp.
     *
     * @param campID the ID of the camp.
     * @return the list of pending enquiries related to the camp.
     * @throws ModelNotFoundException if the camp is not found.
     */
    public static List<Enquiry> getAllPendingEnquiriesByCampID(String campID) throws ModelNotFoundException{
        return EnquiryRepository.getInstance().findByRules(
            e -> e.getRequestStatus() == RequestStatus.PENDING,
            e -> campID.contains(e.getCampID()))
            .stream()
            .map(e -> (Enquiry) e)
            .collect(Collectors.toList());
    }

    /**
    * Retrieves all pending enquiries related to a specific staff member.
    *
    * @param staff the staff member.
    * @return the list of pending enquiries related to the staff member.
    */
    public static List<Enquiry> getAllPendingEnquiriesByStaff(Staff staff) {
        List<String> campIDs = CampManager.getAllCampsByStaff(staff).stream()
        .filter(
        c -> c.getStaffID().equals(staff.getID())
        && c.getVisibility().equals("true"))
        .map(Camp::getID)
        .collect(Collectors.toList());
        return EnquiryRepository.getInstance().findByRules(
        e -> e.getRequestStatus() == RequestStatus.PENDING,
        e -> campIDs.contains(e.getCampID()))
        .stream()
        .map(e -> (Enquiry) e)
        .collect(Collectors.toList());
    }

    /**
    * Retrieves all pending suggestions related to a specific staff member.
    *
    * @param staff the staff member.
    * @return the list of pending suggestions related to the staff member.
    */
    public static List<Suggestion> getAllPendingSuggestionsByStaff(Staff staff) {
        List<String> campIDs = CampManager.getAllCampsByStaff(staff).stream()
        .filter(
        c -> c.getStaffID().equals(staff.getID())
        && c.getVisibility().equals("true"))
        .map(Camp::getID)
        .collect(Collectors.toList());
        return SuggestionRepository.getInstance().findByRules(
        s -> s.getRequestStatus() == RequestStatus.PENDING,
        s -> campIDs.contains(s.getCampID()))
        .stream()
        .map(r -> (Suggestion) r)
        .collect(Collectors.toList());
    }

    /**
    * Approves a suggestion, updating the associated camp and student points.
    *
    * @param suggestion the suggestion to be approved.
    * @throws ModelNotFoundException if the suggestion or associated camp is not found.
    */
    public static void approveSuggestion(Suggestion s) throws ModelNotFoundException {
        String campID = s.getCampID();
        Camp c = CampManager.getCampByID(campID);
    
        if (!s.getCampName().equals("null")) {
            c.setCampName(s.getCampName());
        }
    
        if (!s.getDates().equals("null")) {
            c.setDates(s.getDates());
        }
    
        if (!s.getRegistrationClosingDate().equals("null")) {
            c.setRegistrationClosingDate(s.getRegistrationClosingDate());
        }
    
        if (s.getCampType() != Faculty.NA) {
            c.setCampType(s.getCampType());
        }
    
        if (!s.getLocation().equals("null")) {
            c.setLocation(s.getLocation());
        }
    
        if (s.getTotalSlots() != -1) {
            c.setTotalSlots(s.getTotalSlots());
        }
    
        if (s.getCampCommSlots() != -1) {
            c.setCampCommSlots(s.getCampCommSlots());
        }
    
        if (!s.getDescription().equals("null")) {
            c.setDescription(s.getDescription());
        }
    
        if (!s.getCampStaff().equals("null")) {
            c.setStaffID(s.getCampStaff());
        }
    
        CampManager.updateCamp(campID, c);
        s.setRequestStatus(RequestStatus.APPROVED);

        Student student = StudentRepository.getInstance().getByID(s.getSenderID());
        student.addPoint();
        StudentRepository.getInstance().update(student);
    }
}