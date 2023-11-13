/**
 * 
 */
package model;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import controller.ModelFactoryController;
import dataStructures.SimpleList;

/**
 * 
 */
public class Handler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
			if (user1.getName().equals(user) && verifyPassword(pw, user1.getPassword())) {
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
			if (user1.getName().equals(user) && verifyPassword(pw, user1.getPassword())) {
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

	/**
	 * 
	 * Method that
	 *
	 * @param password
	 * @return
	 */
	public String encryptPassword(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

			// Convertir el hash en una representación hexadecimal
			StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
			for (byte b : encodedhash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * Method that
	 *
	 * @param enteredPassword
	 * @param storedHash
	 * @return
	 */
	public static boolean verifyPassword(String enteredPassword, String storedHash) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] enteredPasswordHash = digest.digest(enteredPassword.getBytes(StandardCharsets.UTF_8));

			// Convierte el hash de la contraseña ingresada en una representación
			// hexadecimal
			StringBuilder hexString = new StringBuilder(2 * enteredPasswordHash.length);
			for (byte b : enteredPasswordHash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}

			// Compara el hash de la contraseña ingresada con el hash almacenado
			return hexString.toString().equals(storedHash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String generateRandomIdAsString() {
		Random random = new Random();
		int minId = 1;
		int maxId = 2000;

		// La expresión (maxId - minId + 1) genera un número aleatorio en el rango de
		// [minId, maxId].
		int randomId = random.nextInt(maxId - minId + 1) + minId;

		// Convertir el número entero a una cadena
		String randomIdAsString = String.valueOf(randomId);
		return randomIdAsString;
	}

	/**
	 * Method that
	 *
	 * @param user
	 */
	public void createUser(User user) {
		ModelFactoryController.getInstance().getHandler().getUserList().addEnd(user);
	}

	/**
	 * Method that
	 *
	 * @param process
	 */
	public void createProcess(Process process) {
		ModelFactoryController.getInstance().getHandler().getProcessList().addEnd(process);
	}
}
