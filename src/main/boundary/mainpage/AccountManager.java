package main.boundary.mainpage;

import main.boundary.account.ChangeAccountPassword;
import main.boundary.account.Logout;
import main.boundary.account.ViewUserProfile;
import main.boundary.modelviewer.ModelViewer;
import main.controller.request.CoordinatorManager;
import main.controller.request.RequestManager;
import main.model.request.Request;
import main.model.request.RequestStatus;
import main.model.request.RequestType;
import main.model.user.Coordinator;
import main.model.user.User;
import main.model.user.UserType;
import main.repository.user.CoordinatorRepository;
import main.utils.exception.ModelNotFoundException;
import main.utils.exception.PageBackException;
import main.utils.exception.SupervisorStudentsLimitExceedException;
import main.utils.iocontrol.IntGetter;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.ChangePage;


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
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param String
	 */
	private String getID(int String) {
		// TODO - implement AccountManager.getID
		throw new UnsupportedOperationException();
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