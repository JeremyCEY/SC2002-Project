package main.model.user;

import main.utils.parameters.EmptyID;
import main.utils.parameters.NotNull;

import java.util.Map;

public class Student implements User{

	private String userID;
	private String name;
	private String hashedPassword;
	private String faculty;
	private String[] registeredCamps;
	//private String status;//check for camp comm status???
	private String[] campDates;
	private int points;


	//Constructors

	//Constructor for new Student object with specified student ID and default password
	public Student(String studentID, String studentName, String faculty) {
		this.userID = studentID;
		this.name = studentName;
		this.faculty = faculty;
		this.points = 0;
	}

	//Constructor for new Student object with speicified student ID and password
	public Student(String studentID, String studentName, String faculty, @NotNull String hashedPassword){
		this.userID = studentID;
		this.name = studentName;
		this.faculty = faculty;
		this.hashedPassword = hashedPassword;
		this.points = 0;
	}

	public Student(Map<String, String> informationMap){
		fromMap(informationMap);
	}
	

	//Default Constructor
	public Student(){
		super();
		this.userID = EmptyID.EMPTY_ID;
		this.name = EmptyID.EMPTY_ID;
		this.faculty = EmptyID.EMPTY_ID;
		this.points = 0;
	}

	//Methods
	public String getUserID() {
		return this.userID;
	}

	public String getName(){
		return this.name;
	}


	public String getHashedPassword() {
		return this.hashedPassword;
	}

	public String getFaculty(){
		return this.faculty;
	}

	public String[] getRegisteredCamps(){
		return this.registeredCamps;
	}

	/*public String getStatus() {
		return this.status;
	}*/

	public String[] getCampDates(){
		return this.campDates;
	}

	public int getPoints(){
		return this.points;
	}

	public void setUserID(String userID){
		this.userID = userID;
	}

	public void setName(String name){
		this.name = name;
	}


	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public void setFaculty(String faculty){
		this.faculty = faculty;
	}

	public void setRegisteredCamps(String[] registeredCamps){
		//do the appending in manager, retrieve,append,set
		this.registeredCamps = registeredCamps;
	}

	/*public void setStatus(String status){
		//participant or committee
		this.status = status;
	}*/

	public void setCampDates(String[] campDates){
		this.campDates = campDates;
	}

	public void setPoints(int points){
		this.points = points;
	}
}