package Userdata;

public class Student {

	private String id;
	private String name;
	private String userName;
	private String hashedPassword;
	private String faculty;
	private String[] registeredCamps;
	private String status;

	public Student() {
		// TODO - implement Student.Student
		throw new UnsupportedOperationException();
	}

	public String getID() {
		return this.id;
	}

	public String getName(){
		return this.name;
	}

	public String getUserName() {
		return this.userName;
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

	public String getStatus() {
		return this.status;
	}

	public void setID(String id){
		this.id = id;
	}

	public void setName(String name){
		this.name = name;
	}

	public void setUserName(String userName){
		this.userName = userName;
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

	public void setStatus(String status){
		//participant or committee
		this.status = status;
	}

}