/**
 * The main.model.request package contains classes representing various request types
 * made within the application, such as inquiries and suggestions. These classes
 * implement the Request interface and provide methods to manage and retrieve
 * information related to the respective request types.
 */
package main.model.request;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import main.model.camp.Camp;
import main.model.user.Student;
import main.repository.request.EnquiryRepository;
import main.repository.user.StudentRepository;
import main.utils.parameters.EmptyID;

/**
 * The Enquiry class represents an inquiry request made by a student regarding a camp.
 * It implements the Request interface, providing methods to manage and retrieve information
 * about the inquiry request.
 */
public class Enquiry implements Request {
	private String requestID;
	private RequestStatus requestStatus = RequestStatus.PENDING;
	private String campID;
	private String senderID;
	private String replierID;
	private String message;
	private String reply;

    /**
     * Constructor for creating an Enquiry object.
     *
     * @param requestID The unique identifier for the inquiry request.
     * @param campID The identifier of the camp related to the inquiry.
     * @param senderID The student ID of the sender making the inquiry.
     * @param message The message content of the inquiry.
     */
	public Enquiry(String requestID, String campID, String senderID, String message) {

		this.requestID = requestID;
		this.campID = campID;
		this.senderID = senderID;
		this.replierID = EmptyID.EMPTY_ID;
		this.message = message;
		this.reply = EmptyID.EMPTY_ID;
	}

    /**
     * Constructor for creating an Enquiry object from a map of attributes.
     *
     * @param map A map containing attribute-value pairs for initializing the Enquiry object.
     */
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


    // Getter and Setter methods...

    /**
     * Gets a displayable string representing the details of the inquiry request.
     *
     * @return A formatted string containing key information about the inquiry request.
     */
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
	/**
     * Gets a displayable string representing the details of the inquiry request
     * with a specific type.
     *
     * @param type The type of display to include (e.g., "show").
     * @return A formatted string containing key information about the inquiry request.
     */
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