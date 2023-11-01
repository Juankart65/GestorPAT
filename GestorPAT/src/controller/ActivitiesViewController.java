package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import model.Activity;
import model.Process;

public class ActivitiesViewController {

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
	private TableColumn<?, ?> stateCol;

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
		app.showActiviitiesView(null);
	}

	/**
	 * 
	 * Method that
	 *
	 * @param event
	 */
	@FXML
	void backEvent(ActionEvent event) {
		app.showProcessView(App.getCurrentUser());
	}

	/**
	 * 
	 * Method that
	 *
	 * @param event
	 */
	@FXML
	void createActivityEvent(ActionEvent event) {

	}

	/**
	 * 
	 * Method that
	 *
	 * @param event
	 */
	@FXML
	void updateActivityEvent(ActionEvent event) {

	}

	/**
	 * 
	 * Method that
	 *
	 * @param event
	 */
	@FXML
	void deleteActivityEvent(ActionEvent event) {

	}

	/**
	 * 
	 * Method that
	 *
	 * @param event
	 */
	@FXML
	void visualizeActivityEvent(ActionEvent event) {

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

		//	Activity table
//		idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
//		nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
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
	 * Metodo que dice si el boton de aceptar fue pulsado
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

}
