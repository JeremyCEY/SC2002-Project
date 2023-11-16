package main.model.request;

import java.util.Map;

import main.model.request.Suggestion;;

public class RequestFactory {
    public static Request createRequest(Map<String, String> map){
        return switch(RequestType.valueOf(map.get("requestType"))){
            case ENQUIRY -> new Enquiry(map);
            case SUGGESTION -> new Suggestion(map);
        };
    }
    public static Request createRequest(RequestType type, String requestID, String campid, String studentID, String message){
        return switch(type){
            case ENQUIRY -> new Enquiry(requestID, campid, studentID, message);
            case SUGGESTION -> new Suggestion(requestID, campid, studentID, message);
        };
    }

}
