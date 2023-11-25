package main.model.user;

import main.utils.parameters.EmptyID;
import main.utils.parameters.NotNull;

import java.util.Map;

/**
 * This class represents a student, which is a type of user.
 * It extends the User class and includes a student ID field.
 */
public class Student implements User {
	/**
	 * The ID of the student.
	 */
	private String studentID;
	/**
	 * The name of a student
	 */
	private String studentName;
	/**
	 * The email of a student
	 */
	private String email;

	private String hashedPassword;

	private String aCamps;

	private String cCamps;

	private String pCamps;

	private Faculty faculty;

	private int points;

	/**
	 * Constructs a new Student object with the specified student ID and default
	 * password.
	 *
	 * @param studentID   the ID of the student.
	 * @param studentName the name of the student.
	 * @param email       the email of the student.
	 */
	public Student(String studentID, String studentName, String email, Faculty faculty) {
		this.studentID = studentID;
		this.studentName = studentName;
		this.email = email;
		this.aCamps = EmptyID.EMPTY_ID;
		this.cCamps = EmptyID.EMPTY_ID;
		this.faculty = faculty;
		this.pCamps = EmptyID.EMPTY_ID;
		this.points = 0;
	}

	/**
	 * Constructs a new Student object with the specified student ID and password.
	 *
	 * @param studentID      the ID of the student.
	 * @param studentName    the name of the student.
	 * @param email          the email of the student.
	 * @param hashedPassword the password of the student.
	 */
	public Student(String studentID, String studentName, String email, @NotNull String hashedPassword,
			Faculty faculty) {
		this.studentID = studentID;
		this.studentName = studentName;
		this.email = email;
		this.aCamps = EmptyID.EMPTY_ID;
		this.cCamps = EmptyID.EMPTY_ID;
		this.faculty = faculty;
		this.pCamps = EmptyID.EMPTY_ID;
		this.points = 0;
		this.hashedPassword = hashedPassword;
	}

	/**
	 * Constructs a new Student object with the specified student ID and password.
	 *
	 * @param informationMap the map
	 */
	public Student(Map<String, String> informationMap) {
		fromMap(informationMap);
	}

	/**
	 * default constructor for Student class
	 */
	public Student() {
		super();
		this.email = EmptyID.EMPTY_ID;
		this.studentID = EmptyID.EMPTY_ID;
		this.studentName = EmptyID.EMPTY_ID;
		this.aCamps = EmptyID.EMPTY_ID;
		this.cCamps = EmptyID.EMPTY_ID;
		this.faculty = Faculty.NA;
		this.pCamps = EmptyID.EMPTY_ID;
		this.points = 0;
	}

	/**
	 * 
	 * Creates a new Student object based on the information in the provided map.
	 * The map should contain the necessary information to construct the Student
	 * object,
	 * such as the student's name, email, and ID.
	 * 
	 * @param informationMap a map containing the information required to create a
	 *                       new Student object
	 * @return a new Student object with the information provided in the map
	 */
	public static User getUser(Map<String, String> informationMap) {
		return new Student(informationMap);
	}

	/**
	 * Gets the email of the user
	 *
	 * @return the email of the user
	 */
	@Override
	public String getID() {
		return this.studentID;
	}

	/**
	 * Gets the username of the user
	 *
	 * @return the name of the user
	 */
	@Override
	public String getUserName() {
		return this.studentName;
	}

	/**
	 * Gets the email of the user
	 *
	 * @return the email of the user
	 */
	@Override
	public String getEmail() {
		return this.email;
	}

	/**
	 * getter for the password
	 *
	 * @return hashedPassword
	 */
	public String getHashedPassword() {
		return hashedPassword;
	}

	/**
	 * setter for the password
	 *
	 * @param hashedPassword the password that to be set
	 */
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public String getACamps() {
		return aCamps;
	}

	public void setACamps(String aCamps) {
		this.aCamps = aCamps;
	}

	public String getCCamps() {
		return cCamps;
	}

	public void setCCamps(String cCamps) {
		this.cCamps = cCamps;
	}

	public Faculty getFaculty() {
		return this.faculty;
	}

	public String getPCamps() {
		return this.pCamps;
	}

	public void addPCamp(String campID) {
		if (!this.pCamps.contains(campID)) {
			if (this.pCamps.equals("null")) {
				this.pCamps = campID;
				return;
			}
			this.pCamps += "," + campID;
		}
	}

	public int getPoints() {
		return this.points;
	}

	public void addPoint() {
		this.points++;
	}

	public void addACamp(String campID) {
		if (!this.aCamps.contains(campID)) {
			if (this.aCamps.equals("null")) {
				this.aCamps = campID;
				return;
			}
			this.aCamps += "," + campID;
		}
	}

	public void removeACamp(String campId) {
		// Check if the campId is present in aCamps
		int index = this.aCamps.indexOf(campId);

		// If the campId is found, remove it
		if (index != -1) {
			// Calculate the end index for the substring to be removed
			int endIndex = index + campId.length();

			// Remove the campId from aCamps
			this.aCamps = this.aCamps.substring(0, index) + this.aCamps.substring(endIndex);

			// Remove any leading or trailing commas if they exist
			this.aCamps = this.aCamps.replaceAll("^,|,$|,,", "");
		}
		if (this.aCamps.equals("")) {
			this.aCamps = "null";
		}
	}

	public void addCCamp(String campID) {
		// Check if the campId is not already present in aCamps
		if (!this.cCamps.contains(campID)) {
			this.cCamps = campID;
			return;
		}
	}

}
