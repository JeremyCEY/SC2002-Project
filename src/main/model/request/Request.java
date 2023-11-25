package main.model.request;

import main.model.Displayable;
import main.model.Model;

public interface Request extends Model, Displayable {

	String getID();
	String getCampID();
	String getSenderID();
	String getReplierID();
	RequestStatus getRequestStatus();

	void setID(String id);
	void setCampID(String campID);
	void setSenderID(String senderID);
	void setReplierID(String replyID);
	void setRequestStatus(RequestStatus status);

	default String getSplitter(){
		return "====================================================";
	}	
}