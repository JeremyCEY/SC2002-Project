package main.model.user;

public interface User {

	String getID();

	String getName();

	String getUserName();

	String getHashedPassword();

	String getFaculty();

	void setID(String id);

	void setName(String name);

	void setUserName(String userName);

	void setHashedPassword(String hashedPassword);

	void setFaculty(String faculty);

	boolean checkUsername(String username);

	//hash function?
}