package model.camp;

public class Camp {

	private String campID;
	private String campName;
	private String[] dates; //format DD-MM-YYYY
	private String registrationClosingDate;
	private boolean campType;
	private String location;
	private int totalSlots;
	private int campCommSlots;
	private String description;
	private String staffID;
	private boolean visibility;

	//Constructor
	public Camp(String campID, String staffID) {
		this.campID = campID;

		//initialise all the variables
		this.campName = "";
		this.dates =  new String[30];//to decide how much space needed
		this.registrationClosingDate = "";
		this.campType = false;
		this.location = "";
		this.totalSlots = 0;
		this.campCommSlots = 0;
		this.description = "";
		this.visibility = false;
		
		//dont pass too many parameters in the constructor, we can use the set methods in CampManager????
		//1.create camp(constructs camp with the above initialised variables)
		//2.input details of camp(use mututator methods to set the variables, same for edit camp details)
	}

	public String getCampID() {
		return this.campID;
	}

	public String getCampName() {
		return this.campName;
	}

	public String[] getDates() {
		return this.dates;
	}

	public String getRegistrationClosingDate() {
		return this.registrationClosingDate;
	}

	public boolean getCampType() {
		return this.campType;
	}

	public String getLocation() {
		return this.location;
	}

	public int getTotalSlots() {
		return this.totalSlots;
	}

	public int getCampCommSlots() {
		return this.campCommSlots;
	}

	public String getDescription() {
		return this.description;
	}

	public String getStaffID() {
		return this.staffID;
	}

	public boolean getVisibility(){
		return this.visibility;
	}

	public void setCampID(String id) {
		this.campID = id;
	}

	public void setCampName(String name) {
		this.campName = name;
	}

	public void setDates(String[] dates) {
		this.dates = dates;
	}

	public void setRegistrationClosingDate(String closingDate) {
		this.registrationClosingDate = closingDate;
	}

	public void setCampType(boolean campType) {//may need to change it to String
		//true = open to whole of NTU
		//false = open only to faculty
		
		this.campType = campType;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setTotalSlots(int totalSlots) {
		this.totalSlots = totalSlots;
	}

	public void setCampCommSlots(int slots) {
		this.campCommSlots = slots;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public void setVisibility(boolean visibility){
		//true = on
		//false = off
		this.visibility = visibility;
	}

}