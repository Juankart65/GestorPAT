/**
 * 
 */
package model;

import dataStructures.SimpleList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 */
public class Handler {

	private SimpleList<User> userList = new SimpleList<User>();
	private SimpleList<Process> processList = new SimpleList<Process>();

	public SimpleList<User> getUserList() {
		return userList;
	}

	public void setUserList(SimpleList<User> userList) {
		this.userList = userList;
	}

	public Handler() {
		super();
	}

	/**
	 * 
	 * Method that verifies if a user exists on the platform
	 *
	 * @param user
	 * @param pw
	 * @return
	 */
	public boolean verifyUser(String user, String pw) {
		for (User user1 : userList) {
			if (user1.getName().equals(user) && user1.getPassword().equals(pw)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * Method that obtains a user from the platform
	 *
	 * @param user
	 * @param pw
	 * @return
	 */
	public User getUser(String user, String pw) {
		for (User user1 : userList) {
			if (user1.getName().equals(user) && user1.getPassword().equals(pw)) {
				return user1;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * Method that obtains a user from the platform
	 *
	 * @param user
	 * @return
	 */
	public User getUser(String user) {
		for (User user1 : userList) {
			if (user1.getName().equals(user)) {
				return user1;
			}
		}
		return null;
	}

	/**
	 * Getter of processList
	 *
	 * @return the processList
	 */
	public SimpleList<Process> getProcessList() {
		return processList;
	}

	/**
	 * Setter of processList
	 *
	 * @param processList the processList to set
	 */
	public void setProcessList(SimpleList<Process> processList) {
		this.processList = processList;
	}
}
