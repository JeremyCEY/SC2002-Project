package main.model.suggestion;

import main.model.camp.userGroup;
import main.utils.parameters.EmptyID;

public class Suggestion {

	private String suggestionID;
	private String campID;
	private String studentID;
	private String staffID;
	private SuggestionStatus status;

	private String suggestedCampName;
	private String[] suggestedDates;
	private String suggestedRegistrationClosingDate;
	private String suggestedLocation;
	private String suggestedDescription;
	private int suggestedCampTotalSlots;
	private int suggestedCampCommSlots;
	private userGroup suggestedOpenTo;

	//Constructor
	public Suggestion(String suggestionID, String campID, String studentID, String staffID, SuggestionStatus status) {
		this.suggestionID = suggestionID;
		this.campID = campID;
		this.studentID = studentID;
		this.staffID = staffID;
		this.status = status;

		this.suggestedCampName = EmptyID.EMPTY_ID;
		this.suggestedDates = new String[1];//figure this out
		this.suggestedRegistrationClosingDate = EmptyID.EMPTY_ID;
		this.suggestedLocation = EmptyID.EMPTY_ID;
		this.suggestedDescription = EmptyID.EMPTY_ID;
		this.suggestedCampTotalSlots = -1;//set to negative, ui put if statement
		this.suggestedCampCommSlots = -1;//set to negative, ui put if statement
		//suggestedOpenTo
	}

	//Methods
	public String getSuggestionID() {
		return this.suggestionID;
	}

	public String getCampID() {
		return this.campID;
	}

	public String getStudentID() {
		return this.studentID;
	}

	public String getStaffID() {
		return this.staffID;
	}

	public SuggestionStatus getStatus(){
		return this.status;
	}

	public String getSuggestedCampName(){
		return this.suggestedCampName;
	}

	public String[] getSuggestedDates(){
		return this.suggestedDates;
	}

	public String getSuggestedRegistrationClosingDate(){
		return this.suggestedRegistrationClosingDate;
	}

	public String getSuggestedLocation(){
		return this.suggestedLocation;
	}

	public String getSuggestedDescription(){
		return this.suggestedDescription;
	}

	public int getSuggestedCampTotalSlots(){
		return this.suggestedCampTotalSlots;
	}

	public int getSuggestedCampCommSlots(){
		return this.suggestedCampCommSlots;
	}

	public userGroup getSuggestedOpenTo(){
		return this.suggestedOpenTo;
	}


	public void setRequestID(String suggestionID) {
		this.suggestionID = suggestionID;
	}

	public void setCampID(String campID) {
		this.campID = campID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public void setStatus(SuggestionStatus status){
		this.status = status;
	}

	public void setSuggestedCampName(String suggestedCampName){
		this.suggestedCampName = suggestedCampName;
	}

	public void setSuggestedDates(String[] suggestedDates){
		this.suggestedDates = suggestedDates;
	}

	public void setSuggestedRegistrationClosingDate(String SuggestedRegistrationClosingDate){
		this.suggestedRegistrationClosingDate = SuggestedRegistrationClosingDate;
	}

	public void setSuggestedLocation(String suggestedLocation){
		this.suggestedLocation = suggestedLocation;
	}

	public void setSuggestedDescription(String suggestedDescription){
		this.suggestedDescription = suggestedDescription;
	}

	public void setSuggestedCampTotalSlots(int suggestedCampTotalSlots){
		this.suggestedCampTotalSlots = suggestedCampTotalSlots;
	}

	public void setSuggestedCampCommSlots(int suggestedCampCommSlots){
		this.suggestedCampCommSlots = suggestedCampCommSlots;
	}

	public void setSuggestedOpenTo(userGroup suggestedOpenTo){
		this.suggestedOpenTo = suggestedOpenTo;
	}

}