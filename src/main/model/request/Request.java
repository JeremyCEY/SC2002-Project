package main.model.request;

import main.model.Displayable;
import main.model.Model;

public interface Request extends Model, Displayable {

	String getID();
	String getCampID();
	String getSenderID();
	String getReplierID();
	// String getMessage();
	RequestStatus getRequestStatus();
	RequestType getRequestType();


	void setID(String id);
	void setCampID(String campID);
	void setSenderID(String senderID);
	void setReplierID(String replyID);
	// void setMessage(String message);
	void setRequestStatus(RequestStatus status);


	default String getSplitter(){
		return "====================================================";
	}
	

	

	



}