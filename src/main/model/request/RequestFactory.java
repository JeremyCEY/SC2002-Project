package main.model.request;

import java.util.Map;

public class RequestFactory {
    public static Request createRequest(Map<String, String> map){
        return switch(RequestType.valueOf(map.get("requestType"))){
            case ENQUIRY -> new Enquiry(map);
            case SUGGESTION -> new Suggestion(map);
        };
    }
}
