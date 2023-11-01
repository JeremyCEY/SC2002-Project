package Userdata;

public class Staff implements User{

	private String staffID;
	private String staffName;
	private String email;
	private String hashedPassword;
	private String faculty;

	public Staff() {
		// TODO - implement Staff.Staff
		throw new UnsupportedOperationException();
	}



	//override
	public String getEmail() {
		return this.email;
	}

	//override
	public String getUserName() {
		// TODO - implement Staff.getUserName
		throw new UnsupportedOperationException();
	}


	public void setStaffID(int String) {
		// TODO - implement Staff.setStuffID
		throw new UnsupportedOperationException();
	}
	

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	//override
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public String getID() {
		// TODO - implement Staff.getID
		throw new UnsupportedOperationException();
	}

	public User getUser(int Map) {
		// TODO - implement Staff.getUser
		throw new UnsupportedOperationException();
	}

	//override
	public String getHashedPassword() {
		return this.hashedPassword;
	}

	//override
	public void setEmail(String email) {
		this.email = email;
	}

	//override
	public boolean checkUsername(){
		//TO DO- inherited from USER interface
		return true;
	}


}