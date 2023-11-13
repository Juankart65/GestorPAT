package controller;

import java.io.IOException;

import application.App;
import dataStructures.SimpleList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.Activity;
import model.Handler;
import model.Process;
import model.Rol;
import model.State;
import model.Task;
import persistence.Persistencia;

public class ActivitiesViewController {

	// Attribute declaration

	@FXML
	private TableView<Activity> activitiesTable;

	@FXML
	private Button btnDeleteActivity;

	@FXML
	private Button btnCreateActivity;

	@FXML
	private Label txtDescriptionProcess;

	@FXML
	private Label txtOwnerProcess;

	@FXML
	private Button btnRefresh;

	@FXML
	private Button btnback;

	@FXML
	private Button btnDownActivity;

	@FXML
	private Button btnUpActivity;

	@FXML
	private TableColumn<Activity, String> idCol;

	@FXML
	private Button btnVisualizeActivity;

	@FXML
	private TableColumn<Activity, String> nameCol;

	@FXML
	private Button btnUpdateActivty;

	@FXML
	private Label txtIdProcess;

	@FXML
	private TableColumn<Activity, State> stateCol;

	@FXML
	private Label txtNameProcess;

	@FXML
	private TextField txtSearchActivity;

	@FXML
	private Button btnSearchActivity;

	private ObservableList<Activity> allActivities;

	private App app;
	private boolean okClicked = false;

	/**
	 * 
	 * Method that
	 *
	 * @param event
	 */
	@FXML
	void refreshEvent(ActionEvent event) {
		app.showActivitiesView(Process.getCurrentActivity());
	}

	@FXML
	void searchActivity(ActionEvent event) {
		// Obtener el texto de búsqueda
		String searchText = txtSearchActivity.getText();

		// Filtrar la lista de actividades
		ObservableList<Activity> filteredActivities;

		if (searchText.isEmpty()) {
			// Si el campo de búsqueda está vacío, mostrar todas las actividades
			filteredActivities = allActivities;
		} else {
			// Filtrar las actividades por nombre
			filteredActivities = allActivities.filtered(activity -> activity.getName().contains(searchText));
		}

		// Actualizar la tabla con las actividades filtradas o todas las actividades
		activitiesTable.setItems(filteredActivities);
	}

	/**
	 * 
	 * Method that
	 *
	 * @param event
	 */
	@FXML
	void backEvent(ActionEvent event) {
		app.showMainView(App.getCurrentUser());
	}

	/**
	 * 
	 * Method that
	 *
	 * @param event
	 */
	@FXML
	void createActivityEvent(ActionEvent event) {
		Activity tempActivity = new Activity(null, null, Handler.generateRandomIdAsString(), null, null);
		boolean okClicked = app.showCreateActivities(tempActivity);

		if (okClicked) {
			ModelFactoryController.getInstance().createActivity(tempActivity);
			activitiesTable.getItems().add(tempActivity);
		}
	}

	/**
	 * 
	 * Method that
	 *
	 * @param event
	 */
	@FXML
	void updateActivityEvent(ActionEvent event) {
		Activity selectActivity = activitiesTable.getSelectionModel().getSelectedItem();
		if (selectActivity != null) {
			@SuppressWarnings("unused")
			boolean okClicked = app.showCreateActivities(selectActivity);
			try {
				Persistencia.saveProcess(ModelFactoryController.getInstance().getHandler().getProcessList());
				Persistencia.guardaRegistroLog("The activity was updated", 1, "updateActivity");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(app.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("You did not select a process");
			alert.setContentText("Please select a process in the table");

			alert.showAndWait();
		}
	}

	/**
	 * 
	 * Method that
	 *
	 * @param event
	 */
	@FXML
	void deleteActivityEvent(ActionEvent event) {
		int selectedIndex = activitiesTable.getSelectionModel().getSelectedIndex();

		if (selectedIndex >= 0) {
			activitiesTable.getItems().remove(selectedIndex);
			App.getCurrentProcess().getActivities().deleteNode(selectedIndex);
			try {
				Persistencia.saveProcess(ModelFactoryController.getInstance().getHandler().getProcessList());
				Persistencia.guardaRegistroLog("The activity was deleted", 1, "deleteActivity");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// Nada seleccionado

			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(app.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("You did not select a process");
			alert.setContentText("Please select a process in the table");

			alert.showAndWait();
		}
	}

	/**
	 * 
	 * Method that
	 *
	 * @param event
	 */
	@FXML
	void visualizeActivityEvent(ActionEvent event) {
		Activity selectActivity = activitiesTable.getSelectionModel().getSelectedItem();

		if (selectActivity != null) {

			Process.setCurrentActivity(selectActivity);

			@SuppressWarnings("unused")
			boolean okClicked = app.showActivitiesView(selectActivity);
			Persistencia.guardaRegistroLog("Window changed", 1, "visualizeActivity");
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(app.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("You did not select a process");
			alert.setContentText("Please select a process in the table");

			alert.showAndWait();
		}
	}

	/**
	 * Method that
	 *
	 * @param app
	 */
	public void setAplicacion(App app) {
		this.app = app;
	}

	@FXML
	void initialize() {

		// Activity table
		idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		stateCol.setCellValueFactory(cellData -> cellData.getValue().stateProperty());

//		for (Activity activity : ModelFactoryController.getInstance().activities()) {
//			activitiesTable.getItems().add(activity);
//		}

		// Obtener la lista de actividades completa y almacenarla en la variable
		allActivities = FXCollections.observableArrayList(
				App.currentProcess.getActivities().convertArraylist(App.currentProcess.getActivities()));

		// Configurar la tabla con la lista completa de actividades
		activitiesTable.setItems(allActivities);

		if (App.getCurrentUser().getRol().equals(Rol.User)) {
			btnCreateActivity.setDisable(true);
			btnUpdateActivty.setDisable(true);
			btnDeleteActivity.setDisable(true);
		}
	}

	/**
	 * Method that
	 *
	 * @param currentProcess
	 */
	public void showDetailProcess(Process currentProcess) {
		txtIdProcess.setText(currentProcess.getId());
		txtNameProcess.setText(currentProcess.getName());
		txtDescriptionProcess.setText(currentProcess.getDescription());
		txtOwnerProcess.setText(currentProcess.getOwner().getName());
	}

	/**
	 * 
	 * Method that tells if the accept button was pressed
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	void upActivityEvent(ActionEvent event) {
		Activity selectedActivity = activitiesTable.getSelectionModel().getSelectedItem();
		if (selectedActivity != null) {
			int selectedIndex = activitiesTable.getSelectionModel().getSelectedIndex();
			if (selectedIndex > 0) {
				// Obtén la tarea que está arriba de la tarea seleccionada.
				Activity activityAbove = activitiesTable.getItems().get(selectedIndex - 1);

				// Intercambia las tareas en la lista.
				activitiesTable.getItems().set(selectedIndex - 1, selectedActivity);
				activitiesTable.getItems().set(selectedIndex, activityAbove);

				// Intercambia las tareas en la lista de tareas de la actividad.
				App.getCurrentProcess().getActivities().moveUp(selectedIndex);
				try {
					Persistencia.saveProcess(ModelFactoryController.getInstance().getHandler().getProcessList());
					Persistencia
							.guardaRegistroLog(
									"Activity " + App.getCurrentProcess().getActivities()
											.getNodeValue(selectedIndex - 1).getName() + " changed position",
									1, "moveUp");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@FXML
	void downActivityEvent(ActionEvent event) {
		Activity selectActivity = activitiesTable.getSelectionModel().getSelectedItem();
		if (selectActivity != null) {
			int selectedIndex = activitiesTable.getSelectionModel().getSelectedIndex();
			if (selectedIndex < activitiesTable.getItems().size() - 1) {
				// Obtén la actividad que está abajo de la tarea seleccionada.
				Activity activityBelow = activitiesTable.getItems().get(selectedIndex + 1);

				// Intercambia las tareas en la lista.
				activitiesTable.getItems().set(selectedIndex + 1, selectActivity);
				activitiesTable.getItems().set(selectedIndex, activityBelow);

				// Intercambia las actividades en la lista de actividades del proceso.
				App.getCurrentProcess().getActivities().moveDown(selectedIndex);
				try {
					Persistencia.saveProcess(ModelFactoryController.getInstance().getHandler().getProcessList());
					Persistencia.guardaRegistroLog("Activity " + App.getCurrentProcess().getActivities()
							.getNode(selectedIndex + 1).getValorNodo().getName() + " changed position", 1, "moveDown");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
