package main.model.request;

import main.model.user.Faculty;
import java.util.Map;
import main.utils.parameters.EmptyID;

public class Suggestion implements Request{

	private String requestID;
	private RequestStatus requestStatus = RequestStatus.PENDING;
	private String campID;
	private String studentID;
	private String staffID;
	
	private String campName;
	private String campDates;
	private String registrationClosing;
	private Faculty faculty;
	private String location;
	private int totalSlots;
	private int campCommSlots;
	private String description;
	private String campStaff;


	//Constructor
	public Suggestion(String requestID, String campID, String studentID) {
		this.requestID = requestID;
		this.campID = campID;
		this.studentID = studentID;
		this.staffID = EmptyID.EMPTY_ID;
		// this.message = message;

		this.campName = EmptyID.EMPTY_ID;
		this.campDates = EmptyID.EMPTY_ID;
		this.registrationClosing = EmptyID.EMPTY_ID;
		this.faculty = Faculty.NA;
		this.location = EmptyID.EMPTY_ID;
		this.totalSlots = -1;
		this.campCommSlots = -1;
		this.description = EmptyID.EMPTY_ID;
		this.campStaff = EmptyID.EMPTY_ID;
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

	// public String getMessage(){
	// 	return this.message;
	// }

	public RequestStatus getRequestStatus(){
		return this.requestStatus;
	}


	public String getCampName(){
		return this.campName;
	}

	public String getDates(){
		return this.campDates;
	}
	public String getRegistrationClosingDate(){
		return this.registrationClosing;
	}
	public Faculty getCampType(){
		return this.faculty;
	}

	public String getCampTypeString(Faculty faculty){
		if (faculty==Faculty.ADM)
			return "ADM";
		else if(faculty==Faculty.EEE)
			return "EEE";
		else if(faculty==Faculty.NBS)
			return "NBS";
		else if(faculty==Faculty.NTU)
			return "NTU";
		else if(faculty==Faculty.SCSE)
			return "SCSE";
		else if(faculty==Faculty.SSS)
			return "SSS";
		else
			return "NA";
	}

	public String getLocation(){
		return this.location;
	}
	public int getTotalSlots(){
		return this.totalSlots;
	}
	
	public int getCampCommSlots(){
		return this.campCommSlots;
	}
	public String getDescription(){
		return this.description;
	}
	
	public String getCampStaff(){
		return this.campStaff;
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

	// public void setMessage(String message){
	// 	this.message = message;
	// }

	public void setRequestStatus(RequestStatus status){
		this.requestStatus = status;
	}

	public void setCampName(String campName){
		this.campName = campName;
	}

	public void setDates(String campDates){
		this.campDates = campDates;
	}
	public void setRegistrationClosingDate(String registrationClosing){
		this.registrationClosing = registrationClosing;
	}
	public void setCampType(Faculty faculty){
		this.faculty = faculty;
	}

	public void setLocation(String location){
		this.location = location;
	}
	public void setTotalSlots(int totalSlots){
		this.totalSlots = totalSlots;
	}
	
	public void setCampCommSlots(int campCommSlots){
		this.campCommSlots = campCommSlots;
	}
	public void setDescription(String description){
		this.description = description;
	}
	
	public void setCampStaff(String campStaff){
		this.campStaff = campStaff;
	}
	
	
	
	
	public String getDisplayableString(){
        String status = null;
		if (getRequestStatus()==RequestStatus.PENDING)
			status = "PENDING"; //add colours to statuses
		else if (getRequestStatus()==RequestStatus.APPROVED)
			status = "APPROVED";
		else if (getRequestStatus()==RequestStatus.DENIED)
			status = "DENIED";
		else
			status = "ERROR";

		return String.format("|                     %-26s    |\n", status)+
                "|---------------------------------------------------|\n" +
                String.format("| Enquiry ID                | %-21s |\n", getID()) +
                String.format("| Student ID                | %-21s |\n", getSenderID()) +
                String.format("| Camp ID                   | %-21s |\n", getCampID()) +
				"|---------------------------------------------------|\n" +
                String.format("|                 Suggested Edits                   |\n") +
				"|---------------------------------------------------|\n" +
				String.format("| Camp Name                 | %-21s |\n", getCampName())+
				String.format("| Camp Dates                | %-21s |\n", getDates())+
				String.format("| Registration Closing Date | %-21s |\n", getRegistrationClosingDate())+
				String.format("| Camp Type                 | %-21s |\n", getCampTypeString(this.faculty))+
				String.format("| Location                  | %-21s |\n", getLocation())+
				String.format("| Attendee Slots            | %-21s |\n", getTotalSlots())+
				String.format("| Committee Slots           | %-21s |\n", getCampCommSlots())+
				String.format("| Description               | %-21s |\n", getDescription())+
				String.format("| Staff in Charge           | %-21s |\n", getCampStaff());
	}

		public String getDisplayableStringWithType(String type){
        String status = null;
		if (getRequestStatus()==RequestStatus.PENDING)
			status = "PENDING"; //add colours to statuses
		else if (getRequestStatus()==RequestStatus.APPROVED)
			status = "APPROVED";
		else if (getRequestStatus()==RequestStatus.DENIED)
			status = "DENIED";
		else
			status = "ERROR";

		return String.format("|                     %-26s    |\n", status)+
                "|---------------------------------------------------|\n" +
                String.format("| Enquiry ID                | %-21s |\n", getID()) +
                String.format("| Student ID                | %-21s |\n", getSenderID()) +
                String.format("| Camp ID                   | %-21s |\n", getCampID()) +
				"|---------------------------------------------------|\n" +
                String.format("|                 Suggested Edits                   |\n") +
				"|---------------------------------------------------|\n" +
				String.format("| Camp Name                 | %-21s |\n", getCampName())+
				String.format("| Camp Dates                | %-21s |\n", getDates())+
				String.format("| Registration Closing Date | %-21s |\n", getRegistrationClosingDate())+
				String.format("| Camp Type                 | %-21s |\n", getCampTypeString(this.faculty))+
				String.format("| Location                  | %-21s |\n", getLocation())+
				String.format("| Attendee Slots            | %-21s |\n", getTotalSlots())+
				String.format("| Committee Slots           | %-21s |\n", getCampCommSlots())+
				String.format("| Description               | %-21s |\n", getDescription())+
				String.format("| Staff in Charge           | %-21s |\n", getCampStaff());
	}

}