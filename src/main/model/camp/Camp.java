package main.model.camp;

import main.model.Displayable;
import main.model.Model;
import main.model.user.Student;
import main.model.user.Staff;
import main.repository.user.StudentRepository;
import main.repository.user.StaffRepository;
import main.utils.exception.ModelNotFoundException;
import main.utils.parameters.EmptyID;
import main.model.user.Faculty;

import java.util.Map;

public class Camp implements Model, Displayable {

	private String campID;
	private String campName;
	private String dates;//separated with comma DD-MM-YY,DD-MM-YY
	private String registrationClosingDate;
	private Faculty openTo;
	private String location;
	private int filledSlots;
	private int totalSlots; // assuming total attendee slots ONLY (exclude comm)
	private int campCommSlots;
	private String description;
	private String staffID;
	private String visibility;

	public Camp(String campID, String campName, String dates, String registrationClosingDate,
		Faculty openTo, String location, int filledSlots, int totalSlots, int campCommSlots, String description, String staffID, String visibility) {
		this.campID = campID;
		this.campName = campName;
		this.dates = dates;
		this.registrationClosingDate = registrationClosingDate;
		this.openTo = openTo;
		this.location = location;
		this.filledSlots = filledSlots; // number of attendees that has joined the camp
		this.totalSlots = totalSlots; // total number of attendees that can join the camp
		this.campCommSlots = campCommSlots; // number of comm that has joined the camp // total slots default to 10
		this.description = EmptyID.EMPTY_ID;
		this.staffID = staffID;
		this.visibility = visibility;
	}

	public Camp(Map<String, String> map){
		fromMap(map);
	}

	/*private void displayStudentList{
		
	}*/


	public String getID() {
		return this.campID;
	}

	public String getCampName() {
		return this.campName;
	}

	public String getDates() {
		return this.dates;
	}

	public String getRegistrationClosingDate() {
		return this.registrationClosingDate;
	}

	public Faculty getOpenTo() {
		return this.openTo;
	}

	public String getLocation() {
		return this.location;
	}

	public int getFilledSlots() {
		return this.filledSlots;
	}

	public int getTotalSlots() {
		return this.totalSlots;
	}

	public int getCampCommSlots() {
		return this.campCommSlots;
	}

	public String getDescription() {
		return this.description;
	}

	public String getStaffID() {
		return this.staffID;
	}

	public String getVisibility(){
		return this.visibility;
	}

	public void setCampID(String id) {
		this.campID = id;
	}

	public void setCampName(String name) {
		this.campName = name;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public void setRegistrationClosingDate(String closingDate) {
		this.registrationClosingDate = closingDate;
	}

	public void setCampType(Faculty faculty) {
		this.openTo = faculty;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setFilledSlots(int filledSlots) {
		this.filledSlots = filledSlots;
	}

	public void setTotalSlots(int totalSlots) {
		this.totalSlots = totalSlots;
	}

	public void setCampCommSlots(int slots) {
		this.campCommSlots = slots;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public void setVisibility(String visibility){
		//true = on
		//false = off
		this.visibility = visibility;
	}

	 /** TO DO TO DO TO DO
     * Display the complete information of the camp.
     */
    private String getSingleCampString() {
        String campName = getCampName();
        int maxTitleLength = 60;
        String titleLine1;
        String titleLine2;

        if (campName.length() <= maxTitleLength) {
            int leftPadding = (maxTitleLength - campName.length()) / 2;
            int rightPadding = maxTitleLength - campName.length() - leftPadding;
            titleLine1 = String.format("| %-" + leftPadding + "s%-" + campName.length() + "s%-" + rightPadding + "s |\n", "", campName, "");
            titleLine2 = "";
        } else {
            String[] words = campName.split("\\s+");
            String firstLine = "";
            String secondLine = "";
            int remainingLength = maxTitleLength;
            int i = 0;
            while (i < words.length) {
                if (firstLine.length() + words[i].length() + 1 <= maxTitleLength) {
                    firstLine += words[i] + " ";
                    remainingLength = maxTitleLength - firstLine.length();
                    i++;
                } else {
                    break;
                }
            }
            for (; i < words.length; i++) {
                if (secondLine.length() + words[i].length() + 1 <= maxTitleLength) {
                    secondLine += words[i] + " ";
                } else {
                    break;
                }
            }
            int leftPadding1 = (maxTitleLength - firstLine.length()) / 2;
            int leftPadding2 = (maxTitleLength - secondLine.length()) / 2;
            int rightPadding1 = maxTitleLength - firstLine.length() - leftPadding1;
            int rightPadding2 = maxTitleLength - secondLine.length() - leftPadding2;
            titleLine1 = String.format("| %-" + leftPadding1 + "s%-" + firstLine.length() + "s%-" + rightPadding1 + "s |\n", "", firstLine.trim(), "");
            titleLine2 = String.format("| %-" + leftPadding2 + "s%-" + secondLine.length() + "s%-" + rightPadding2 + "s |\n", "", secondLine.trim(), "");
        }

        return titleLine1 + titleLine2 +
                "|--------------------------------------------------------------|\n" +
                String.format("| Project ID                  | %-30s |\n", getID()) +
                getStaffID() +
                //getProjectStudentInformationString() +  //getStudentList?
                getDescription();
		//return "EXAMPLE";
    }

	@Override
    public String getDisplayableString() {
        return getSingleCampString();
    }

    @Override
    public String getSplitter() {
        return "================================================================";
    }

}