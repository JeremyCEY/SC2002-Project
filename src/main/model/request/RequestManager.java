package main.model.request; 
import main.model.camp.Camp;
import main.model.request.Request; 

import main.controller.account.password.PasswordManager;
import main.controller.account.user.UserAdder;
import main.controller.account.user.UserFinder;
import main.controller.account.user.UserUpdater;
import main.model.user.*;
import main.repository.camp.CampRepository;
import main.repository.user.StaffRepository;
import main.repository.user.StudentRepository;
import main.utils.config.Location;
import main.utils.exception.PasswordIncorrectException;
import main.utils.exception.ModelAlreadyExistsException;
import main.utils.exception.ModelNotFoundException;
public class RequestManager {
    public static void loadUsers(RequestType request_type, String requestID,String campid, String studentID, String message, String replierID) {
        loadEnquiry(requestID,campid, studentID, message, replierID);
        //loadSuggestion();
    }


   
    public static Request register(RequestType request_type, String requestID,String campid, String studentID, String message, String replierID)
        throws ModelAlreadyExistsException {
            Request request = RequestFactory.createRequest(request_type, requestID, campid, studentID, message, replierID);
            RequestAdder.addRequest(request);
            return request; 
    }

    public static void loadEnquiry(String requestID,String campid, String studentID, String message, String replierID) {

        try {
            register(RequestType.ENQUIRY, requestID, campid, studentID, message, replierID); 
        } catch (ModelAlreadyExistsException e) {
            e.printStackTrace();
        }

    }

    public static void loadSuggestion(String requestID,String campid, String studentID, String message, String replierID) {
        try {
            register(RequestType.ENQUIRY, requestID, campid, studentID, message, replierID); 
        } catch (ModelAlreadyExistsException e) {
            e.printStackTrace();
        }


    }

    public static String getNewRequestID() {
        int max = 0;
        for (Enquiry p :RequestRepository.getInstance()) {
            int id = Integer.parseInt(p.getID().substring(1));
            if (id > max) {
                max = id;
            }
        }
        return "C" + (max + 1);
    }
}