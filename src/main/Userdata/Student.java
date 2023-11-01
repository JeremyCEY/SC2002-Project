package Userdata;

public class Student {

	private String studentID;
	private String studentName;
	private String email;
	private String hashedPassword;
	private String faculty;
	private string[] registeredCamps;
	private String status;
	private int attribute;

	public Student() {
		// TODO - implement Student.Student
		throw new UnsupportedOperationException();
	}

	public String getID() {
		// TODO - implement Student.getID
		throw new UnsupportedOperationException();
	}

	public String getEmail() {
		return this.email;
	}

	public String getUserName() {
		// TODO - implement Student.getUserName
		throw new UnsupportedOperationException();
	}

	public String getHashedPassword() {
		return this.hashedPassword;
	}

	public String getStatus() {
		return this.status;
	}

	/**
	 * 
	 * @param hashedPassword
	 */
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	/**
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}