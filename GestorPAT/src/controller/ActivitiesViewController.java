package controller;

import java.io.IOException;

import application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import model.Activity;
import model.Handler;
import model.Process;
import model.State;
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
			ModelFactoryController.getInstance().activities().addEnd(tempActivity);
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
		
		for (Activity activity : ModelFactoryController.getInstance().activities()) {
			activitiesTable.getItems().add(activity);
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

}
