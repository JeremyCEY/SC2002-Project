package repositories;

public class Suggestion {

	private RequestStatus requestStatus;
	private RequestType requestType;
	private String requestID;
	private String campID;
	private String senderID;
	private String approverID;

	public Suggestion() {
		// TODO - implement Suggestion.Suggestion
		throw new UnsupportedOperationException();
	}

	public RequestStatus getRequestStatus() {
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

	public String getApproverID() {
		return this.approverID;
	}

	/**
	 * 
	 * @param requestStatus
	 */
	public void setRequestStatus(RequestStatus requestStatus) {
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
	 * @param approverID
	 */
	public void setApproverID(String approverID) {
		this.approverID = approverID;
	}

}