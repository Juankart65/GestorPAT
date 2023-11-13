package controller;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import application.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Activity;
import model.Handler;
import model.Process;
import model.Rol;
import model.State;
import persistence.Persistencia;
import javafx.scene.control.Alert.AlertType;

public class ProcessViewController {

	// Attribute declaration

	@FXML
	private Button btnVisualizeProcess;

	@FXML
	private Button btnLogOut;

	@FXML
	private TableView<Process> processTable;

	@FXML
	private TableColumn<Process, String> nameColProcess;

	@FXML
	private Button btnCreateProcess;

	@FXML
	private Button btnDelete;

	@FXML
	private TableColumn<Process, String> idColProcess;

	@FXML
	private TableColumn<Process, State> stateColProcess;

	@FXML
	private Button btnUpdateProcess;

	@FXML
	private Button btnTimeProcess;

	private App app;

	private boolean okClicked = false;

	/**
	 * 
	 * Method that
	 *
	 * @param event
	 */
	@FXML
	void createProcessEvent(ActionEvent event) {
		Process tempProcess = new Process(null, null, Handler.generateRandomIdAsString(), null, null);
		boolean okClicked = app.showCreateProcess(tempProcess);

		if (okClicked) {
			ModelFactoryController.getInstance().createProcess(tempProcess);
			processTable.getItems().add(tempProcess);
		}
	}

	/**
	 * 
	 * Method that
	 *
	 * @param event
	 */
	@FXML
	void updateProcessEvent(ActionEvent event) {
		Process selectProcess = processTable.getSelectionModel().getSelectedItem();
		if (selectProcess != null) {
			@SuppressWarnings("unused")
			boolean okClicked = app.showCreateProcess(selectProcess);
			try {
				Persistencia.saveProcess(ModelFactoryController.getInstance().getHandler().getProcessList());
				Persistencia.guardaRegistroLog("The process was updated", 1, "updateProcess");
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
	void deleteProcessEvent(ActionEvent event) {
		int selectedIndex = processTable.getSelectionModel().getSelectedIndex();

		if (selectedIndex >= 0) {
			processTable.getItems().remove(selectedIndex);
			ModelFactoryController.getInstance().getHandler().getProcessList().deleteNode(selectedIndex);
			try {
				Persistencia.saveProcess(ModelFactoryController.getInstance().getHandler().getProcessList());
				Persistencia.guardaRegistroLog("The process was deleted", 1, "deleteProcess");
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
	void visualizeProcessEvent(ActionEvent event) {
		Process selectProcess = processTable.getSelectionModel().getSelectedItem();

		if (selectProcess != null) {

			App.setCurrentProcess(selectProcess);

			@SuppressWarnings("unused")
			boolean okClicked = app.showProcessView(selectProcess);
			Persistencia.guardaRegistroLog("Window changed", 1, "visualizeProcess");
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
	void timeProcessEvent(ActionEvent event) {

		Process selectProcess = processTable.getSelectionModel().getSelectedItem();

		String durationProces = selectProcess.getDuration();

		JOptionPane.showMessageDialog(null, "The duration of the process is: " + durationProces);
	}

	/**
	 * 
	 * Method that
	 *
	 * @param event
	 */
	@FXML
	void logOutProcessEvent(ActionEvent event) {
		app.showLogin();
	}

	/**
	 * 
	 * Method that
	 *
	 * @param app
	 */
	public void setAplicacion(App app) {
		this.app = app;

		// Convierte la lista enlazada simple en una lista convencional
		List<Process> processList = new ArrayList<>(ModelFactoryController.getInstance().getHandler().getProcessList()
				.convertArraylist(ModelFactoryController.getInstance().getHandler().getProcessList()));

		// Crea un ObservableList a partir de la lista convencional
		ObservableList<Process> process = FXCollections.observableArrayList(processList);

		// Asigna la lista de procesos al TableView
		processTable.setItems(process);
	}

	/**
	 * 
	 * Method that
	 *
	 */
	@FXML
	void initialize() {
		// Tabla de procesos
		idColProcess.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		nameColProcess.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		stateColProcess.setCellValueFactory(cellData -> cellData.getValue().stateProperty());

		if (App.getCurrentUser().getRol().equals(Rol.User)) {
			btnCreateProcess.setDisable(true);
			btnUpdateProcess.setDisable(true);
			btnDelete.setDisable(true);
		}

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
