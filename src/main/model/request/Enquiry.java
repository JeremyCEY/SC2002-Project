package main.model.request;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import main.model.camp.Camp;
import main.model.user.Student;
import main.repository.request.EnquiryRepository;
import main.repository.user.StudentRepository;
import main.utils.parameters.EmptyID;

public class Enquiry implements Request {
	private String requestID;
	private RequestStatus requestStatus = RequestStatus.PENDING;
	private String campID;
	private String senderID;
	private String replierID;
	private String message;
	private String reply;

	// Constructor
	public Enquiry(String requestID, String campID, String senderID, String message) {

		this.requestID = requestID;
		this.campID = campID;
		this.senderID = senderID;
		this.replierID = EmptyID.EMPTY_ID;
		this.message = message;
		this.reply = EmptyID.EMPTY_ID;
	}

	public Enquiry(Map<String, String> map) {
		fromMap(map);
	}

	// Methods
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

	public String getReply() {
		return this.reply;
	}

	public RequestStatus getRequestStatus() {
		return this.requestStatus;
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

	public void setReply(String reply) {
		this.reply = reply;
	}

	public void setRequestStatus(RequestStatus status) {
		this.requestStatus = status;
	}

	public String getDisplayableString() {
		String status = null;
		if (getRequestStatus() == RequestStatus.PENDING)
			status = "PENDING";
		else if (getRequestStatus() == RequestStatus.REPLIED)
			status = "REPLIED";
		else
			status = "ERROR";

		return String.format("|                     %-26s    |\n", status) +
				"|---------------------------------------------------|\n" +
				String.format("| Enquiry ID             | %-24s |\n", getID()) +
				String.format("| Student ID             | %-24s |\n", getSenderID()) +
				String.format("| Camp ID                | %-24s |\n", getCampID()) +
				String.format("| Message                | %-24s |\n", getMessage()) +
				String.format("| Reply                  | %-24s |\n", getReply()) +
				String.format("| Replier ID             | %-24s |\n", getReplierID());
	}

	// method for show
	public String getDisplayableStringWithType(String type) {
		String status = null;
		if (getRequestStatus() == RequestStatus.PENDING)
			status = "PENDING";
		else if (getRequestStatus() == RequestStatus.REPLIED)
			status = "REPLIED";
		else
			status = "ERROR";

		return String.format("|                       %-24s    |\n", status) +
				"|---------------------------------------------------|\n" +
				String.format("| Enquiry ID             | %-24s |\n", getID()) +
				String.format("| Student ID             | %-24s |\n", getSenderID()) +
				String.format("| Camp ID                | %-24s |\n", getCampID()) +
				String.format("| Message                | %-24s |\n", getMessage()) +
				String.format("| Reply                  | %-24s |\n", getReply()) +
				String.format("| Replier ID             | %-24s |\n", getReplierID());
	}

}