package main.model.camp;

import main.model.Displayable;
import main.model.Model;
import main.model.user.Student;
import main.model.user.Staff;
import main.repository.user.StudentRepository;
import main.repository.user.StaffRepository;
import main.utils.exception.ModelNotFoundException;
import main.utils.parameters.EmptyID;

import java.util.Map;

public class Camp {

	private String campID;
	private String campName;
	private String[] dates; //format DD-MM-YYYY
	private String registrationClosingDate;
	private userGroup openTo;
	private String location;
	private int totalSlots;
	private int campCommSlots;
	private String description;
	private String staffID;
	private boolean visibility;

	//Constructor TO EDIT
	public Camp(String campID, String campName, String location, String staffID) {
		this.campID = campID;
		this.campName = campName;
		this.dates =  new String[30];//to decide how much space needed
		this.registrationClosingDate = EmptyID.EMPTY_ID;
		this.openTo = userGroup.SCHOOL;
		this.location = location;
		this.totalSlots = 0;
		this.campCommSlots = 0;
		this.description = EmptyID.EMPTY_ID;
		this.staffID = staffID;
		this.visibility = false;
	}

	public Camp(Map<String, String> map){
		fromMap(map);
	}

	/*private void displayStudentList{
		
	}*/


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

	public userGroup getOpenTo() {
		return this.openTo;
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

	public void setCampType(userGroup userGroup) {
		//true = open to whole of NTU
		//false = open only to faculty
		this.openTo = userGroup;
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