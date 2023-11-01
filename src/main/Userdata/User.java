package Userdata;

public interface User {

	String getID();

	String getEmail();

	String getUserName();

	String getHashedPassword();

	String getFaculty();

	void setID(String id);
	
	void setEmail(String email);

	void setUsername(String username);

	void setHashedPassword(String hashedPassword);

	void setFaculty(String faculty);

	boolean checkUsername(String username);

	//hash function?
}