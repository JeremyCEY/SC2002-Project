package main.model.request;

public interface Request {

	String getCampID();

	void display();

	/**
	 * 
	 * @param RequestStatus
	 */
	void setStatus(int RequestStatus);

	String getID();

	RequestType getRequestType();

	String getSenderID();

	RequestStatus getStatus();

}