package main.model.user;

import main.utils.parameters.EmptyID;
import main.utils.parameters.NotNull;

import java.util.Map;

public class Staff implements User{

	private String userID;
	private String name;
	private String hashedPassword;
	private String faculty;

	//Constructor
	//Constructor of new Staff object with specified Staff ID, Name and username
	public Staff(String staffID, String staffName, String faculty) {
		this.userID = staffID;
		this.name = staffName;
		this.faculty = faculty;
	}

	//Constructor of new Staff Object with specified Staff ID and password
	public Staff(String staffID, String staffName, String faculty, @NotNull String hashedPassword){
		this.userID = staffID;
		this.name = staffName;
		this.faculty = faculty;
		this.hashedPassword = hashedPassword;
	}

	//MAPPING CONSTRUCTOR
	public Staff(Map<String, String> map){
		this.fromMap(map);
	}
	

	//Default Constructor
	public Staff(){
		super();
		this.userID = EmptyID.EMPTY_ID;
		this.name = EmptyID.EMPTY_ID;
		this.userID = EmptyID.EMPTY_ID;
	}


	//Methods

	public static User getUser(Map<String, String> map){
		return new Staff(map);
	}

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

	public void setUserID(String userID){
		this.userID = userID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public void setFaculty(String faculty){
		this.faculty = faculty;
	}
}