package main.model.enquiry;


public class Enquiry {

	private String enquiryID;
	private String campID;
	private String senderID;
	private String replierID;
	private String message;
	private boolean isReplied;

	//Constructor
	public Enquiry(String enquiryID, String campID, String senderID, String replierID, String message){
		this.enquiryID = enquiryID;
		this.campID = campID;
		this.senderID = senderID;
		this.replierID = replierID;
		this.message = message;
		this.isReplied = false;
	}


	//Methods
	public String getEnquiryID() {
		return this.enquiryID;
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

	public boolean getIsReplied(){
		return this.isReplied;
	}

	public void EnquiryID(String requestID) {
		this.enquiryID = requestID;
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

	public void setIsReplied(boolean isReplied){
		this.isReplied = isReplied;
	}

}