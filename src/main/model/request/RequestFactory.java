package main.model.request;

import java.util.Map;

public class RequestFactory {
    public static Request createRequest(Map<String, String> map){
        return switch(RequestType.valueOf(map.get("requestType"))){
            case ENQUIRY -> new Enquiry(map);
            case SUGGESTION -> new Suggestion(map);
        };
    }
    public static Request createRequest(RequestType type, String requestID, String campid, String studentID, String message, String replierID){
        return switch(type){
            case ENQUIRY -> new Enquiry(requestID, campid, studentID, message, replierID);
            case SUGGESTION -> new Suggestion(requestID, campid, studentID, replierID, message);
        };
    }

}
