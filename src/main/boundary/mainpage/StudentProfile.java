package main.boundary.mainpage;

import java.util.Scanner;

import main.boundary.modelviewer.ModelViewer;
import main.controller.camp.CampManager;
import main.controller.request.StudentManager;
import main.model.camp.Camp;
import main.model.user.Student;
import main.repository.camp.CampRepository;
import main.utils.exception.ModelNotFoundException;
import main.utils.exception.PageBackException;
import main.utils.parameters.EmptyID;
import main.utils.ui.ChangePage;

public class StudentProfile {

	private int attribute;

	public StudentProfile() {
		// TODO - implement StudentProfile.StudentProfile
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param User
	 */
	public void studentMainPage(int User) {
		// TODO - implement StudentProfile.studentMainPage
		throw new UnsupportedOperationException();
	}

	private void viewCampList() {
		// TODO - implement StudentProfile.viewCampList
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Student
	 */
	private void viewRegisteredCamps(int Student) {
		// TODO - implement StudentProfile.viewRegisteredCamps
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Student
	 */
	private void registerCamp(Student student) {
		// TODO - implement StudentProfile.registerCamp
		ChangePage.changePage();
        if (student.getStatus() == StudentStatus.REGISTERED || student.getStatus() == StudentStatus.DEREGISTERED) {
            System.out.println("You are already registered/deregistered for a project.");
            System.out.println("Press Enter to go back.");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }
        System.out.println("Here is the list of available projects: ");
        ModelViewer.displayListOfDisplayable(CampManager.getAllVisibleCamps());
        System.out.print("Please enter the project ID: ");
        String campID = new Scanner(System.in).nextLine();
        if (CampManager.notContainsCampByID(campID)) {
            System.out.println("Project not found.");
            System.out.println("Press Enter to go back, or enter [r] to retry.");
            String choice = new Scanner(System.in).nextLine();
            if (choice.equals("r")) {
                registerCamp(student);
            }
            throw new PageBackException();
        }
        Camp camp;
        try {
            camp = CampManager.getByID(campID);
            if (Camp.getStatus() != CampStatus.AVAILABLE) {
                System.out.println("Project is not available.");
                System.out.println("Press Enter to go back, or enter [r] to retry.");
                String choice = new Scanner(System.in).nextLine();
                if (choice.equals("r")) {
                    registerCamp(student);
                }
                throw new PageBackException();
            }

        } catch (ModelNotFoundException e) {
            throw new RuntimeException(e);
        }
        ChangePage.changePage();
        System.out.println("Here is the project information: ");
        try {
            Camp camp1 = CampRepository.getInstance().getByID(campID);
            ModelViewer.displaySingleDisplayable(camp1);
        } catch (ModelNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.print("Are you sure you want to register for this project? (y/[n]): ");
        String choice = new Scanner(System.in).nextLine();
        if (choice.equalsIgnoreCase("y")) {
            try {
                StudentManager.registerStudent(campID, student.getID());
                System.out.println("Request submitted!");
            } catch (Exception e) {
                System.out.println("Enter [b] to go back, or press enter to retry.");
                String yNChoice = new Scanner(System.in).nextLine();
                if (yNChoice.equals("b")) {
                    throw new PageBackException();
                } else {
                    registerCamp(student);
                }
            }
        } else {
            System.out.println("Request cancelled.");

        }

        System.out.println("Press Enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }


	/**
	 * 
	 * @param Student
	 */
	private void withdrawCamp(Student student) {
		// TODO - implement StudentProfile.withdrawCamp
		     ChangePage.changePage();

        if (EmptyID.isEmptyID(student.getID())) {
            System.out.println("You are not registered for any project.");
            System.out.println("Press Enter to go back.");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }

        System.out.println("Your current project is: ");

        try {
            Camp camp = CampRepository.getInstance().getByID(student.getID());
            ModelViewer.displaySingleDisplayable(camp);
        } catch (ModelNotFoundException e) {
            throw new IllegalArgumentException("Project not found.");
        }

        System.out.println("Are you sure you want to deregister from this project? (y/[n])");
        String choice = new Scanner(System.in).nextLine();
        if (!choice.equals("y")) {
            System.out.println("Deregistration cancelled.");
            System.out.println("Press Enter to go back.");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }

        String campID = student.getID();

        try {
            StudentManager.deregisterStudent(campID, student.getID());
        } catch (Exception e) {
            System.out.println("Deregistration Error: " + e.getMessage());
            System.out.println("Enter [b] to go back, or press enter to retry.");
            String choice2 = new Scanner(System.in).nextLine();
            if (!choice2.equals("b")) {
                deregisterForCamp(student);
            }
            throw new PageBackException();
        }
        System.out.println("Successfully sent a request to deregister");
        System.out.println("Press Enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }


	private void viewCampInfo() {
		// TODO - implement StudentProfile.viewCampInfo
		throw new UnsupportedOperationException();
	}

	private void viewEnquiry() {
		// TODO - implement StudentProfile.viewEnquiry
		throw new UnsupportedOperationException();
	}

	private void submitEnquiry() {
		// TODO - implement StudentProfile.submitEnquiry
		throw new UnsupportedOperationException();
	}

	private void editEnquiry() {
		// TODO - implement StudentProfile.editEnquiry
		throw new UnsupportedOperationException();
	}

	private void deleteEnquiry() {
		// TODO - implement StudentProfile.deleteEnquiry
		throw new UnsupportedOperationException();
	}

	private void replyEnquiry() {
		// TODO - implement StudentProfile.replyEnquiry
		throw new UnsupportedOperationException();
	}

}