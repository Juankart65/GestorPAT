package application;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Activity;
import model.Process;
import model.State;
import model.Task;
import model.User;
import persistence.Persistencia;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import controller.CreateProcessController;
import controller.CreateTaskController;
import controller.LoginController;
import controller.ModelFactoryController;
import controller.ActivitiesViewController;
import controller.CreateActivitiesController;
import controller.ProcessViewController;
import controller.SignUpController;
import controller.TasksViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * This class represents to
 *
 * @author Juanes Cardona
 */
public class App extends Application {

	// Variable declaration

	private Stage primaryStage;
	public static User currentUser;
	public static Process currentProcess;
	private boolean notificationShown = false;
	private long previousLowestDuration = Long.MAX_VALUE;

	/**
	 * 
	 * Method that gets the current user
	 *
	 * @return
	 */
	public static User getCurrentUser() {
		return currentUser;
	}

	/**
	 * 
	 * Method that changes the current user
	 *
	 * @param userActual
	 */
	public static void setCurrentUser(User userActual) {
		App.currentUser = userActual;
	}

	/**
	 * Getter of currentProcess
	 *
	 * @return the currentProcess
	 */
	public static Process getCurrentProcess() {
		return currentProcess;
	}

	/**
	 * Setter of currentProcess
	 *
	 * @param currentProcess the currentProcess to set
	 */
	public static void setCurrentProcess(Process currentProcess) {
		App.currentProcess = currentProcess;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		System.setProperty("log4j2.disable.jmx", "true");
		this.primaryStage = primaryStage;
		this.primaryStage.initStyle(StageStyle.TRANSPARENT);
		this.primaryStage.centerOnScreen();
		this.primaryStage.setTitle("GestorPAT");
		showLogin();
		ModelFactoryController.getInstance().getHandler().getUserList();
		ModelFactoryController.getInstance().getHandler().getProcessList();

		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				updateTaskDurations();
				try {
					Persistencia.saveProcess(ModelFactoryController.getInstance().getHandler().getProcessList());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 1000, 1000);

	}

	/**
	 * Gets the main stage
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Change the main scenario
	 * 
	 * @param primaryStage
	 */
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	/**
	 * Shows the window for the user to log in
	 */
	public void showLogin() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("../view/LoginView.fxml"));

			AnchorPane rootLayout = (AnchorPane) loader.load();

			LoginController inicioSesionController = loader.getController();
			inicioSesionController.setAplicacion(this);

			Scene scene = new Scene(rootLayout);

			// Establecer el color de relleno del Scene a transparente
			scene.setFill(Color.TRANSPARENT);
			// Agregar el archivo de estilos style.css
			scene.getStylesheets().add(getClass().getResource("../resources/Styles.css").toString());

			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Shows the window to register a user
	 * 
	 * @param user
	 * @return
	 */
	public boolean showSignUp(User user) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("../view/SignUpView.fxml"));

			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Sign Up User");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.initStyle(StageStyle.TRANSPARENT);
			dialogStage.centerOnScreen();

			Scene scene = new Scene(page);
			// Establecer el color de relleno del Scene a transparente
			scene.setFill(Color.TRANSPARENT);
			// Agregar el archivo de estilos style.css
			scene.getStylesheets().add(getClass().getResource("../resources/Styles.css").toString());
			dialogStage.setScene(scene);

			SignUpController signUpController = loader.getController();
			signUpController.showDialogStage(dialogStage);
			signUpController.showUser(user);

			dialogStage.showAndWait();

			return signUpController.isOkClicked();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * Method that
	 *
	 * @param usuarios
	 * @return
	 */
	public boolean showMainView(User usuarios) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("../view/ProcessView.fxml"));

			AnchorPane rootLayout = (AnchorPane) loader.load();

			ProcessViewController processViewController = loader.getController();
			processViewController.setAplicacion(this);

			Scene scene = new Scene(rootLayout);

			// Establecer el color de relleno del Scene a transparente
			scene.setFill(Color.TRANSPARENT);
			// Agregar el archivo de estilos style.css
			scene.getStylesheets().add(getClass().getResource("../resources/Styles.css").toString());

			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
			primaryStage.show();

			return processViewController.isOkClicked();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * Method that
	 *
	 * @param process
	 * @return
	 */
	public boolean showCreateProcess(Process process) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("../view/CreateProcess.fxml"));

			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Create Process");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.initStyle(StageStyle.TRANSPARENT);
			dialogStage.centerOnScreen();

			Scene scene = new Scene(page);
			// Establecer el color de relleno del Scene a transparente
			scene.setFill(Color.TRANSPARENT);
			// Agregar el archivo de estilos style.css
			scene.getStylesheets().add(getClass().getResource("../resources/Styles.css").toString());
			dialogStage.setScene(scene);

			CreateProcessController createProcessController = loader.getController();
			createProcessController.mostrarDialogStage(dialogStage);
			createProcessController.showProcess(process);

			dialogStage.showAndWait();

			return createProcessController.isOkClicked();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

//
	/**
	 * Muestra la ventana principal
	 * 
	 * @return
	 */
	public boolean showProcessView(Process process) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("../view/ActivitiesView.fxml"));

			AnchorPane rootLayout = (AnchorPane) loader.load();

			ActivitiesViewController activitiesViewController = loader.getController();
			activitiesViewController.setAplicacion(this);
			activitiesViewController.showDetailProcess(App.getCurrentProcess());

			Scene scene = new Scene(rootLayout);

			// Establecer el color de relleno del Scene a transparente
			scene.setFill(Color.TRANSPARENT);
			// Agregar el archivo de estilos Styles.css
			scene.getStylesheets().add(getClass().getResource("../resources/Styles.css").toString());
			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
			primaryStage.show();

			return activitiesViewController.isOkClicked();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Muestra la ventana para crear o editar cultivos
	 */
	public boolean showCreateActivities(Activity activity) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("../view/CreateActivitiesView.fxml"));

			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Create or Edit Activity");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.initStyle(StageStyle.TRANSPARENT);
			dialogStage.centerOnScreen();

			Scene scene = new Scene(page);
			// Establecer el color de relleno del Scene a transparente
			scene.setFill(Color.TRANSPARENT);
			// Agregar el archivo de estilos style.css
			scene.getStylesheets().add(getClass().getResource("../resources/Styles.css").toString());

			dialogStage.setScene(scene);

			CreateActivitiesController createActivitiesController = loader.getController();
			createActivitiesController.mostrarDialogStage(dialogStage);
			createActivitiesController.showActivity(activity);

			dialogStage.showAndWait();

			return createActivitiesController.isOkClicked();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Muestra la ventana principal
	 * 
	 * @return
	 */
	public boolean showActivitiesView(Activity activity) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("../view/TasksView.fxml"));

			AnchorPane rootLayout = (AnchorPane) loader.load();

			TasksViewController activitiesViewController = loader.getController();
			activitiesViewController.setAplicacion(this);
			activitiesViewController.showDetailActivity(Process.getCurrentActivity());

			Scene scene = new Scene(rootLayout);

			// Establecer el color de relleno del Scene a transparente
			scene.setFill(Color.TRANSPARENT);
			// Agregar el archivo de estilos Styles.css
			scene.getStylesheets().add(getClass().getResource("../resources/Styles.css").toString());
			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
			primaryStage.show();

			return activitiesViewController.isOkClicked();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Muestra la ventana para crear o editar cultivos
	 */
	public boolean showCreateTask(Task task) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("../view/CreateTaskView.fxml"));

			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Create or Edit Task");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.initStyle(StageStyle.TRANSPARENT);
			dialogStage.centerOnScreen();

			Scene scene = new Scene(page);
			// Establecer el color de relleno del Scene a transparente
			scene.setFill(Color.TRANSPARENT);
			// Agregar el archivo de estilos style.css
			scene.getStylesheets().add(getClass().getResource("../resources/Styles.css").toString());

			dialogStage.setScene(scene);

			CreateTaskController createTaskController = loader.getController();
			createTaskController.mostrarDialogStage(dialogStage);
			createTaskController.showTask(task);

			dialogStage.showAndWait();

			return createTaskController.isOkClicked();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	private void updateTaskDurations() {
		ObservableList<Task> tasks = getTask();

		// Actualizar la duración de cada tarea
		for (Task task : tasks) {
			if (task.getState() == State.Running) {
				if (task.getDuration().getSeconds() > 0) {
					task.setDuration(task.getDuration().minusSeconds(1));
				} else {
					// La tarea ha finalizado, cambia el estado a State.Exit
					task.setState(State.Finished);
					Persistencia.guardaRegistroLog("The task '" + task.getName() + "' is finished", 1, "Finished");
					try {
						Persistencia.saveProcess(ModelFactoryController.getInstance().getHandler().getProcessList());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		List<Task> tasksWithLowestDuration = getTasksWithLowestDuration(tasks);
		if (!tasksWithLowestDuration.isEmpty() && tasksWithLowestDuration.get(0).getDuration().getSeconds() <= 300
				&& tasksWithLowestDuration.get(0).getDuration().getSeconds() > 299 && !notificationShown) {
			long currentLowestDuration = tasksWithLowestDuration.get(0).getDuration().getSeconds();

			// Mostrar la notificación solo si la duración más baja cambió y la tarea está
			// en estado Running
			if (currentLowestDuration != previousLowestDuration
					&& tasksWithLowestDuration.get(0).getState() == State.Running) {
				Platform.runLater(() -> {
					for (Task task : tasksWithLowestDuration) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Task about to finish");
						alert.setHeaderText(null);
						alert.setContentText("Task '" + task.getName() + "' is about to end.");

						alert.showAndWait();
					}
					notificationShown = true; // Marcar que la notificación se ha mostrado
					previousLowestDuration = currentLowestDuration; // Actualizar la duración más baja anterior
				});
			}
		}
	}

	private List<Task> getTasksWithLowestDuration(ObservableList<Task> tasks) {
		// Encontrar todas las tareas con la duración más baja
		long lowestDuration = tasks.stream().filter(task -> task.getDuration().getSeconds() > 0)
				.mapToLong(task -> task.getDuration().getSeconds()).min().orElse(Long.MAX_VALUE);

		return tasks.stream().filter(task -> task.getDuration().getSeconds() == lowestDuration)
				.collect(Collectors.toList());
	}

	private ObservableList<Task> getTask() {
		ObservableList<Task> tasks = FXCollections.observableArrayList();

		for (Process process : ModelFactoryController.getInstance().getHandler().getProcessList()) {
			for (Activity actividad : process.getActivities()) {
				for (Task task : actividad.getTasks()) {
					tasks.add(task);
				}
			}
		}
		return tasks;
	}

	/**
	 * Main method of the project
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		App.launch(new String[0]);
	}

}
