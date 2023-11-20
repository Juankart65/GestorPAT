/**
 * 
 */
package model;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import controller.ModelFactoryController;
import dataStructures.SimpleList;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import persistence.Persistencia;

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
	private boolean notificationShown = false;

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
	
	/**
	 * 
	 * Method that updates the time of the tasks and executes the tasks, activities 
	 * and processes sequentially
	 *
	 */
	public void updateTaskDurations() {
		SimpleList<Process> processes = ModelFactoryController.getInstance().getHandler().getProcessList();

		for (Process process : processes) {
			
			if (process.getState().equals(State.Finished)) {
				
			    // Verifica si al menos una tarea de la actividad está en ejecución (Running)
			    boolean anyActivityRunning = process.getActivities().stream().anyMatch(activity -> activity.getState().equals(State.Running));
			    if (anyActivityRunning) {
			    	process.setState(State.Running);
				}
			}
			
			if (process.getState().equals(State.Running)) {
				// Cambia el estado de las demás tareas a State.Waiting
				for (Process process1 : processes) {
					if (process1 != process && !process1.getState().equals(State.Finished)) {
						process1.setState(State.Waiting);
					}
				}
			}
			if (!process.getState().equals(State.Running)) {
				// Si el proceso no está en ejecución, continuar con el siguiente proceso
				continue;
			}

			for (Activity activity : process.getActivities()) {
				
				if (activity.getState().equals(State.Finished)) {
					
				    // Verifica si al menos una tarea de la actividad está en ejecución (Running)
				    boolean anyTaskRunning = activity.getTasks().stream().anyMatch(task -> task.getState().equals(State.Running));
				    if (anyTaskRunning) {
						activity.setState(State.Running);
					}
				}
				
				if (activity.getState().equals(State.Running)) {
					// Cambia el estado de las demás actividades a State.Waiting
					for (Activity activity1 : process.getActivities()) {
						if (activity1 != activity && !activity1.getState().equals(State.Finished)) {
							activity1.setState(State.Waiting);
						}
					}
				}

				// Verifica si la actividad está en espera y la actividad anterior ha finalizado
				if (activity.getState().equals(State.Waiting)
						&& isPreviousActivityFinished(process.getActivities(), activity)) {
					// Si la actividad está en espera y la actividad anterior ha finalizado,
					// cámbiela a ejecución (Running)
					activity.setState(State.Running);
				} else if (activity.getState().equals(State.Finished)) {
					// Si la actividad está terminó, avanza a la siguiente actividad y cámbiala
					// a ejecución (Running)
					int index = process.getActivities().indexOfValue(activity);
					if (index < process.getActivities().getSize() - 1) {
						Activity nextActivity = process.getActivities().getNodeValue(index + 1);
						if (!nextActivity.getState().equals(State.Finished)
								&& isPreviousActivityFinished(process.getActivities(), nextActivity)) {
							nextActivity.setState(State.Running);
						}
					}
				}

				if (activity.getState().equals(State.Running)) {
					SimpleList<Task> tasks = activity.getTasks();

					// Obtén la primera tarea en ejecución
					Task runningTask = tasks.stream().filter(task -> task.getState() == State.Running).findFirst()
							.orElse(null);

					// Cambia el estado de las demás tareas a State.Waiting
					for (Task task : tasks) {
						if (task != null && task != runningTask && !task.getState().equals(State.Finished)) {
							task.setState(State.Waiting);
						}
					}

					// Si hay una tarea en ejecución, actualizar su duración y estado
					if (runningTask != null) {
					    if (runningTask.getState().equals(State.Waiting)) {
					        // Cambia el estado de todas las tareas a State.Waiting
					        for (Task task : tasks) {
					            if (task != null && !task.getState().equals(State.Finished)) {
					                task.setState(State.Waiting);
					            }
					        }

					        // Cambia el estado de la tarea actual a State.Running
					        runningTask.setState(State.Running);

					    } else {

					        if (runningTask.getDuration().getSeconds() > 0) {
					            runningTask.setDuration(runningTask.getDuration().minusSeconds(1));
					            try {
					                Persistencia.saveProcess(processes);
					            } catch (IOException e) {
					                // TODO Auto-generated catch block
					                e.printStackTrace();
					            }

					            // Verifica si la duración de la tarea está dentro del rango
					            if (runningTask.getDuration().getSeconds() <= 30
					                    && runningTask.getDuration().getSeconds() > 29 && !notificationShown) {
					                // Muestra la notificación
					                Platform.runLater(() -> {
					                    Alert alert = new Alert(AlertType.INFORMATION);
					                    alert.setTitle("Task about to finish");
					                    alert.setHeaderText(null);
					                    alert.setContentText("Task '" + runningTask.getName() + "' is about to end.");

					                    alert.showAndWait();
					                    
					                    Persistencia.guardaRegistroLog("The task is about to finish", 1, "Finished");
					                });
					                notificationShown = true; // Marcar que la notificación se ha mostrado
					            }
					        } else {
					            // La tarea ha finalizado, cambia el estado a State.Finished
					            runningTask.setState(State.Finished);

					            // Verifica si hay tareas pendientes
					            int index = tasks.indexOfValue(runningTask);
					            if (index < tasks.getSize() - 1) {
					                Task nextTask = tasks.getNodeValue(index + 1);

					                // Espera a que la tarea anterior haya terminado (State.Finished)
					                while (!runningTask.getState().equals(State.Finished)) {
					                    // Puedes agregar un breve tiempo de espera o realizar otras acciones según sea necesario
					                }

					                // Cambia el estado de la siguiente tarea a State.Running
					                nextTask.setState(State.Running);
					            }
					        }
					    }
					}
					// Verifica si todas las tareas de la actividad están en State.Finished
					boolean allTasksFinished = tasks.stream().allMatch(task -> task.getState() == State.Finished);
					if (allTasksFinished) {
						// Cambia el estado de la actividad a State.Finished
						activity.setState(State.Finished);
					} else if (!activity.getState().equals(State.Waiting) && !allTasksFinished) {
						activity.setState(State.Running);
					}
				}

			}

			// Verifica si todas las actividades del proceso están en State.Finished
			boolean allActivitiesFinished = process.getActivities().stream()
					.allMatch(activity -> activity.getState() == State.Finished);
			if (allActivitiesFinished) {
				// Cambia el estado del proceso a State.Finished
				process.setState(State.Finished);
			} else if (!process.getState().equals(State.Waiting) && !allActivitiesFinished) {
				process.setState(State.Running);
			}
		}
	}

	// Método para verificar si la actividad anterior ha finalizado
	private boolean isPreviousActivityFinished(SimpleList<Activity> activities, Activity currentActivity) {
		int currentIndex = activities.indexOfValue(currentActivity);
		if (currentIndex > 0) {
			Activity previousActivity = activities.getNodeValue(currentIndex - 1);
			return previousActivity.getState().equals(State.Finished);
		}
		return true; // Si no hay actividad anterior, se considera que ha finalizado.
	}
}
