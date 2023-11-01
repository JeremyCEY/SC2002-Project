package Userdata;

public interface User {

	String getID();

	String getEmail();

	/**
	 * 
	 * @param String
	 */
	boolean checkUsername(int String);

	/**
	 * 
	 * @param String
	 */
	void setHashedPassword(int String);

	String getUserName();

	String getHashedPassword();

	String getFaculty();

}