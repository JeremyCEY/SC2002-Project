package main.model.request;

import java.util.Map;
import main.utils.parameters.EmptyID;

public class Enquiry implements Request{


	private final RequestType requestType = RequestType.ENQUIRY;
	private String requestID; 
	private RequestStatus requestStatus = RequestStatus.PENDING; 
	private String campID;
	private String senderID;
	private String replierID;
	private String message;
	private String reply;

	//Constructor
	public Enquiry(String requestID, String campID, String senderID, String replierID, String message){
		
		this.requestID = requestID;
		this.campID = campID;
		this.senderID = senderID;
		this.replierID = replierID;
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

	public RequestType getRequestType(){
		return this.requestType;
	}



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
		return "";//to edit
	}

	public static int getEnquiryID(){
		for (int i=0; i<)
	}
}