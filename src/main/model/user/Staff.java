package main.model.user;

public class Staff implements User{

	private String id;
	private String name;
	private String userName;
	private String hashedPassword;
	private String faculty;

	public Staff() {
		// TODO - implement Staff.Staff
		throw new UnsupportedOperationException();
	}

	public String getID(){
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

	public void setID(String id) {
		this.id = id;
	}

	public void setName(String name) {
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

	public boolean checkUsername(String username){
		//TO DO- inherited from USER interface
		return true;
	}


}