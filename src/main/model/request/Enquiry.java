package main.model.request;

import java.util.Map;
import main.utils.parameters.EmptyID;

public class Enquiry implements Request{


	//private final RequestType requestType = RequestType.ENQUIRY;
	private String requestID; 
	private RequestStatus requestStatus = RequestStatus.PENDING; 
	private String campID;
	private String senderID;
	private String replierID;
	private String message;
	private String reply;

	//Constructor
	public Enquiry(String requestID, String campID, String senderID, String message){
		
		this.requestID = requestID;
		this.campID = campID;
		this.senderID = senderID;
		this.replierID = EmptyID.EMPTY_ID;
		this.message = message;
		this.reply = EmptyID.EMPTY_ID;
	}

	public Enquiry(Map<String, String> map){
		fromMap(map);
	}


	//Methods
	public String getID() {
		return this.requestID;
	}

	public String getCampID() {
		return this.campID;
	}

	public String getSenderID() {
		return this.senderID;
	}

	public String getReplierID() {
		return this.replierID;
	}

	public String getMessage() {
		return this.message;
	}

	public String getReply(){
		return this.reply;
	}

	public RequestStatus getRequestStatus(){
		return this.requestStatus;
	}

	// public RequestType getRequestType(){
	// 	return this.requestType;
	// }



	public void setID(String requestID) {
		this.requestID = requestID;
	}


	public void setCampID(String campID) {
		this.campID = campID;
	}


	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}


	public void setReplierID(String replierID) {
		this.replierID = replierID;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setReply(String reply){
		this.reply = reply;
	}

	public void setRequestStatus(RequestStatus status){
		this.requestStatus = status;
	}


	public String getDisplayableString(){
        String status = null;
		if (getRequestStatus()==RequestStatus.PENDING)
			status = "PENDING";
		else if (getRequestStatus()==RequestStatus.REPLIED)
			status = "REPLIED";
		else
			status = "ERROR";
		
        int maxTitleLength = 10;
        String titleLine1;
        String titleLine2;

        if (status.length() <= maxTitleLength) {
            int leftPadding = (maxTitleLength - status.length()) / 2;
            int rightPadding = maxTitleLength - status.length() - leftPadding;
            titleLine1 = String.format("| %-" + leftPadding + "s%-" + status.length() + "s%-" + rightPadding + "s |\n", "", status, "");
            titleLine2 = "";
        } else {
            String[] words = status.split("\\s+");
            String firstLine = "";
            String secondLine = "";
            int remainingLength = maxTitleLength;
            int i = 0;
            while (i < words.length) {
                if (firstLine.length() + words[i].length() + 1 <= maxTitleLength) {
                    firstLine += words[i] + " ";
                    remainingLength = maxTitleLength - firstLine.length();
                    i++;
                } else {
                    break;
                }
            }
            for (; i < words.length; i++) {
                if (secondLine.length() + words[i].length() + 1 <= maxTitleLength) {
                    secondLine += words[i] + " ";
                } else {
                    break;
                }
            }
            int leftPadding1 = (maxTitleLength - firstLine.length()) / 2;
            int leftPadding2 = (maxTitleLength - secondLine.length()) / 2;
            int rightPadding1 = maxTitleLength - firstLine.length() - leftPadding1;
            int rightPadding2 = maxTitleLength - secondLine.length() - leftPadding2;
            titleLine1 = String.format("| %-" + leftPadding1 + "s%-" + firstLine.length() + "s%-" + rightPadding1 + "s |\n", "", firstLine.trim(), "");
            titleLine2 = String.format("| %-" + leftPadding2 + "s%-" + secondLine.length() + "s%-" + rightPadding2 + "s |\n", "", secondLine.trim(), "");
        }

        return titleLine1 + titleLine2 +
                "|--------------------------------------------------------------|\n" +
                String.format("| Enquiry ID                    | %-30s |\n", getID()) +
                String.format("| Student ID                    | %-30s |\n", getSenderID()) +
                String.format("| Camp ID                       | %-30s |\n", getCampID()) +
				String.format("| Message                       | %-30s |\n", getMessage()) +
				String.format("| Replier ID                    | %-30s |\n", getReplierID());
	}

}