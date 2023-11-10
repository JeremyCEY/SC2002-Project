package main.boundary.mainpage;

import java.util.Scanner;

import main.boundary.account.AttributeGetter;
import main.boundary.account.ChangeAccountPassword;
import main.boundary.account.ForgetUserID;
import main.boundary.account.Logout;
import main.boundary.account.ViewUserProfile;
import main.boundary.modelviewer.ModelViewer;
import main.controller.request.AttendeeManager;
import main.controller.request.RequestManager;
import main.model.request.Request;
import main.model.request.RequestStatus;
import main.model.request.RequestType;
import main.model.user.Staff;
import main.model.user.Student;
import main.model.user.User;
import main.model.user.UserType;
import main.repository.user.StaffRepository;
import main.repository.user.StudentRepository;
import main.repository.Repository;
import main.utils.exception.ModelNotFoundException;
import main.utils.exception.PageBackException;
import main.utils.exception.PasswordIncorrectException;
import main.utils.exception.ModelAlreadyExistsException;
import main.utils.iocontrol.IntGetter;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.ChangePage;
import main.utils.ui.PasswordReader;
import main.utils.ui.UserTypeGetter;


public class AccountManager {

	public AccountManager() {
		// TODO - implement AccountManager.AccountManager
		throw new UnsupportedOperationException();
		
		}

	/**
	 * 
	 * @param UserType
	 * @param String
	 * @param parameter
	 */
	public User login(int UserType, int String, int parameter) {
		// TODO - implement AccountManager.login
		ChangePage.changePage();
        UserType domain = AttributeGetter.getDomain();
        String userID = AttributeGetter.getUserID();
        if (userID.equals("")) {
            try {
                ForgetUserID.forgotUserID();
            } catch (PageBackException e) {
                login();
            }
        }
        String password = AttributeGetter.getPassword();
//        System.err.println("Logging in...");
//        System.err.println("Domain: " + domain);
//        System.err.println("User ID: " + userID);
//        System.err.println("Password: " + password);
//        new Scanner(System.in).nextLine();
        try {
            User user = AccountManager.login(domain, userID, password);
            switch (domain) {
                case STUDENT -> StudentMainPage.studentMainPage(user);
                case STAFF -> StaffMainPage.staffMainPage(user);
                case CAMPCOM -> campComMainPage.campComMainPage(user);
                default -> throw new IllegalStateException("Unexpected value: " + domain);
            }
            return;
        } catch (PasswordIncorrectException e) {
            System.out.println("Password incorrect.");
        } catch (ModelNotFoundException e) {
            System.out.println("User not found.");
        }
        System.out.println("Enter [b] to go back, or any other key to try again.");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        if (choice.equals("b")) {
            throw new PageBackException();
        } else {
            System.out.println("Please try again.");
            login();
        }
    }

	/**
	 * 
	 * @param UserType
	 * @param String
	 * @param parameter
	 * @param parameter2
	 */
	public void changePassword(int UserType, int String, int parameter, int parameter2) {
		// TODO - implement AccountManager.changePassword
		 ChangePage.changePage();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Change " + UserTypeGetter.getUserTypeInCamelCase(userType) + " Password");
        System.out.print("Please enter your old password: ");
        String oldPassword = PasswordReader.getPassword();

        try {
            AccountManager.login(userType, userID, oldPassword);
        } catch (ModelNotFoundException e) {
            throw new RuntimeException(e);
        } catch (PasswordIncorrectException e) {
            System.out.println("Password incorrect.");
            askToRetry(userType, userID);
        }

        String newPassword;
        String newPasswordAgain;

        do {
            // read the new password
            System.out.print("Please enter your new password: ");
            newPassword = PasswordReader.getPassword();
            // read the new password again
            System.out.print("Please enter your new password again: ");
            newPasswordAgain = PasswordReader.getPassword();
            // if the new password is not the same as the new password again, ask the user to enter again

            if (!newPassword.equals(newPasswordAgain)) {
                System.out.println("Two passwords are not the same.");
                askToRetry(userType, userID);
            } else if (newPassword.equals(oldPassword)) {
                System.out.println("New password cannot be the same as the old password.");
                askToRetry(userType, userID);
            }
        } while (!newPassword.equals(newPasswordAgain));

        if (newPassword.length() < 8) {
            System.out.println("Password must be at least 8 characters long.");
            askToRetry(userType, userID);
        }

        try {
            AccountManager.changePassword(userType, userID, oldPassword, newPassword);

            System.out.println("Password changed successfully.");

            System.out.println("Press [Enter] to go back to the main page.");
            scanner.nextLine();
            throw new PageBackException();
        } catch (PasswordIncorrectException | ModelNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

	/**
	 * 
	 * @param String
	 */
	private String getID(int String) {
		// TODO - implement AccountManager.getID
		Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your UserID (Press enter if you forget): ");
        return scanner.nextLine();

	}

	/**
	 * 
	 * @param loadFile
	 */
	public void initializeLists(int loadFile) {
		// TODO - implement AccountManager.initializeLists
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param UserType
	 * @param String
	 * @param parameter
	 * @param parameter2
	 */
	public void register(int UserType, int String, int parameter, int parameter2) {
		// TODO - implement AccountManager.register
		throw new UnsupportedOperationException();
	}

}