package application;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Activity;
import model.Process;
import model.Task;
import model.User;

import java.io.IOException;

import controller.CreateProcessController;
import controller.CreateTaskController;
import controller.LoginController;
import controller.ActivitiesViewController;
import controller.CreateActivitiesController;
import controller.ProcessViewController;
import controller.SignUpController;
import controller.TasksViewController;
import dataStructures.SimpleList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

	private ObservableList<User> userList = FXCollections.observableArrayList();
	private SimpleList<Process> processList = new SimpleList<Process>();

	public ObservableList<User> getUserList() {
		return userList;
	}

	public void setUserList(ObservableList<User> userList) {
		this.userList = userList;
	}

	/**
	 * @return
	 **/
	@Override
	public String toString() {
		return "App [userList=" + userList + "]";
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
		this.primaryStage = primaryStage;
		this.primaryStage.initStyle(StageStyle.TRANSPARENT);
		this.primaryStage.centerOnScreen();
		this.primaryStage.setTitle("GestorPAT");
		showLogin();
		getUserList();
		getProcessList();
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
//
//	/**
//	 * Obtiene la ultima ruta que visit� el usuario
//	 * 
//	 * @return
//	 */
//	public File getUserFilePath() {
//		Preferences prefs = Preferences.userNodeForPackage(Aplicacion.class);
//		String filePath = prefs.get("filePath", null);
//		if (filePath != null) {
//			return new File(filePath);
//		} else {
//			return null;
//		}
//	}
//
//	/**
//	 * 
//	 * @param file
//	 */
//	public void setUserFilePath(File file) {
//		Preferences prefs = Preferences.userNodeForPackage(Aplicacion.class);
//		if (file != null) {
//			prefs.put("filePath", file.getPath());
//
//			primaryStage.setTitle("CampApp - " + file.getName());
//		} else {
//			prefs.remove("filePath");
//
//			primaryStage.setTitle("CamApp");
//		}
//	}

//	/**
//	 * Muestra la ventana para crear o editar personas
//	 * 
//	 * @param persona
//	 * @return
//	 */
//	public boolean mostrarVentanaEditarPersonas(Persona persona) {
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(Aplicacion.class.getResource("../views/EditarCrearTrabajadores.fxml"));
//
//			AnchorPane page = (AnchorPane) loader.load();
//
//			Stage dialogStage = new Stage();
//			dialogStage.setTitle("Editar o Crear Personal");
//			dialogStage.initModality(Modality.WINDOW_MODAL);
//			dialogStage.initOwner(primaryStage);
//			dialogStage.initStyle(StageStyle.TRANSPARENT);
//			dialogStage.centerOnScreen();
//
//			Scene scene = new Scene(page);
//			// Establecer el color de relleno del Scene a transparente
//	        scene.setFill(Color.TRANSPARENT);
//	        // Agregar el archivo de estilos style.css
//	        scene.getStylesheets().add(getClass().getResource("../resource/Styles.css").toString());
//			dialogStage.setScene(scene);
//
//			EditarCrearTrabajadoresController editarCrearTrabajadoresController = loader.getController();
//			editarCrearTrabajadoresController.mostrarDialogStage(dialogStage);
//			editarCrearTrabajadoresController.mostrarPersona(persona);
//
//			dialogStage.showAndWait();
//
//			return editarCrearTrabajadoresController.isOkClicked();
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
//	}
//

//

//
//	public boolean mostrarVentanaEditarLabor(Labor labor) {
//
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(Aplicacion.class.getResource("../views/EditarCrearLabores.fxml"));
//
//			AnchorPane page = (AnchorPane) loader.load();
//
//			Stage dialogStage = new Stage();
//			dialogStage.setTitle("Registrar Usuario");
//			dialogStage.initModality(Modality.WINDOW_MODAL);
//			dialogStage.initOwner(primaryStage);
//			dialogStage.initStyle(StageStyle.TRANSPARENT);
//			dialogStage.centerOnScreen();
//
//			Scene scene = new Scene(page);
//			// Establecer el color de relleno del Scene a transparente
//	        scene.setFill(Color.TRANSPARENT);
//	        // Agregar el archivo de estilos style.css
//	        scene.getStylesheets().add(getClass().getResource("../resource/Styles.css").toString());
//			dialogStage.setScene(scene);
//
//			EditarCrearLaboresController editarCrearLaboresController = loader.getController();
//			editarCrearLaboresController.mostrarDialogStage(dialogStage);
//			editarCrearLaboresController.mostrarLabor(labor);
//
//			dialogStage.showAndWait();
//
//			return editarCrearLaboresController.isOkClicked();
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
//	}

	/**
	 * Main method of the project
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		App.launch(new String[0]);
	}

}
