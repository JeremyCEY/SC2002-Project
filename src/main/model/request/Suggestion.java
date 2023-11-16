package main.model.request;

import java.util.Map;

import main.model.request.Request;
import main.model.request.RequestStatus;
import main.model.request.RequestType;
import main.utils.parameters.EmptyID;

public class Suggestion implements Request{

	private final RequestType requestType = RequestType.SUGGESTION;
	private String requestID;
	private RequestStatus requestStatus = RequestStatus.PENDING;
	private String campID;
	private String studentID;
	private String staffID;
	private String message;

	//Constructor
	public Suggestion(String requestID, String campID, String studentID, String message) {
		this.requestID = requestID;
		this.campID = campID;
		this.studentID = studentID;
		this.staffID = EmptyID.EMPTY_ID;
		this.message = message;
	}

	public Suggestion(Map<String, String> map){
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
		return this.studentID;
	}

	public String getReplierID() {
		return this.staffID;
	}

	public String getMessage(){
		return this.message;
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

	public void setSenderID(String studentID) {
		this.studentID = studentID;
	}

	public void setReplierID(String staffID) {
		this.staffID = staffID;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public void setRequestStatus(RequestStatus status){
		this.requestStatus = status;
	}

	public String getDisplayableString(){
		return "";//to edit
	}

}