package model.request;

public class Enquiry {

	private RequestStates requestStatus;
	private RequestType requestType;
	private String requestID;
	private String campID;
	private String senderID;
	private String replierID;
	private String message;
	private int attribute;

	public Enquiry(){
		//To DO
		
		//constructor
	}

	public RequestStates getRequestStatus() {
		return this.requestStatus;
	}

	public RequestType getRequestType() {
		return this.requestType;
	}

	public String getRequestID() {
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

	/**
	 * 
	 * @param requestStatus
	 */
	public void setRequestStatus(RequestStates requestStatus) {
		this.requestStatus = requestStatus;
	}

	/**
	 * 
	 * @param requestType
	 */
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	/**
	 * 
	 * @param requestID
	 */
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	/**
	 * 
	 * @param campID
	 */
	public void setCampID(String campID) {
		this.campID = campID;
	}

	/**
	 * 
	 * @param senderID
	 */
	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}

	/**
	 * 
	 * @param replierID
	 */
	public void setReplierID(String replierID) {
		this.replierID = replierID;
	}

	/**
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}