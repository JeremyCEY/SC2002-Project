package main.model.user;

public interface User {

	String getUserID();

	String getName();

	String getHashedPassword();

	String getFaculty();

	void setUserID(String userID);

	void setName(String name);

	void setHashedPassword(String hashedPassword);

	void setFaculty(String faculty);

	default boolean checkUserID(String userID){
		return this.getUserID().equalsIgnoreCase(userID);
	}

}