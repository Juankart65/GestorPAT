package controller;

import java.time.Duration;
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
import model.Process;
import model.State;
import model.Task;

public class TasksViewController {

	// Attribute declaration

	@FXML
	private Label txtDescriptionActivity;

	@FXML
	private Button btnBack;

	@FXML
	private Label txtIdActivity;

	@FXML
	private Button btnVisualizeTask;

	@FXML
	private Button btnCreateTask;

	@FXML
	private Button btnDeleteTask;

	@FXML
	private Label txtOwnerActivity;

	@FXML
	private TableView<Task> taskTable;

	@FXML
	private Button btnUpdateTask;

	@FXML
	private Button btnRefresh;

	@FXML
	private TableColumn<Task, String> idCol;

	@FXML
	private TableColumn<Task, String> nameCol;

	@FXML
	private TableColumn<Task, State> stateCol;
	
	@FXML
	private TableColumn<Task, Duration> durationCol;
	
	@FXML
	private TableColumn<Task, Boolean> mandatoryCol;

	@FXML
	private Label txtNameActivity;
	
	private App app;
	private boolean okClicked = false;

	@FXML
	void refreshEvent(ActionEvent event) {
		app.showActivitiesView(Process.getCurrentActivity());
	}

	@FXML
	void backEvent(ActionEvent event) {
		app.showProcessView(App.getCurrentProcess());
	}

	@FXML
	void createTaskEvent(ActionEvent event) {
		Task tempTask = new Task(null, null, null, false, null, null, null);
		boolean okClicked = app.showCreateTask(tempTask);

		if (okClicked) {
			Process.getCurrentActivity().getTasks().agregarFinal(tempTask);
			taskTable.getItems().add(tempTask);
		}
	}

	@FXML
	void updateTaskEvent(ActionEvent event) {
		Task selectTask = taskTable.getSelectionModel().getSelectedItem();
		if (selectTask != null) {
			@SuppressWarnings("unused")
			boolean okClicked = app.showCreateTask(selectTask);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(app.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("You did not select a process");
			alert.setContentText("Please select a process in the table");

			alert.showAndWait();
		}
	}

	@FXML
	void deleteTaskEvent(ActionEvent event) {
		int selectedIndex = taskTable.getSelectionModel().getSelectedIndex();

		if (selectedIndex >= 0) {
			taskTable.getItems().remove(selectedIndex);
			Process.getCurrentActivity().getTasks().eliminarPorIndice(selectedIndex);
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

	@FXML
	void visualizeTaskEvent(ActionEvent event) {

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
		durationCol.setCellValueFactory(cellData -> cellData.getValue().durationProperty());
		mandatoryCol.setCellValueFactory(cellData -> cellData.getValue().mandatoryProperty());
	}

	/**
	 * Method that
	 *
	 * @param currentProcess
	 */
	public void showDetailActivity(Activity activity) {
		txtIdActivity.setText(activity.getId());
		txtNameActivity.setText(activity.getName());
		txtDescriptionActivity.setText(activity.getDescription());
		txtOwnerActivity.setText(activity.getOwner().getName());
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
