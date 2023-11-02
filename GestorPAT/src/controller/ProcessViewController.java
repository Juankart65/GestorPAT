package controller;

import java.util.ArrayList;
import java.util.List;

import application.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Process;
import model.Rol;
import model.User;
import javafx.scene.control.Alert.AlertType;

public class ProcessViewController {

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
    private TableColumn<Process, ?> stateColProcess;

    @FXML
    private Button btnUpdateProcess;

	private App app;
	private User user = App.currentUser;
	private CreateProcessController createProcessController = new CreateProcessController();

	private boolean okClicked = false;

	@FXML
	void createProcessEvent(ActionEvent event) {
		Process tempProcess = new Process(null, null, null, null);
		boolean okClicked = app.showCreateProcess(tempProcess);

		if (okClicked) {
	        app.getProcessList().agregarFinal(tempProcess);
	        processTable.getItems().add(tempProcess);
		}
	}

	@FXML
	void updateProcessEvent(ActionEvent event) {
		Process selectProcess = processTable.getSelectionModel().getSelectedItem();
		if (selectProcess != null) {
			boolean okClicked = app.showCreateProcess(selectProcess);
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
	void deleteProcessEvent(ActionEvent event) {
		int selectedIndex = processTable.getSelectionModel().getSelectedIndex();

		if (selectedIndex >= 0) {
			processTable.getItems().remove(selectedIndex);
			app.getProcessList().eliminarNodo(selectedIndex);
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
	void visualizeProcessEvent(ActionEvent event) {
		Process selectProcess = processTable.getSelectionModel().getSelectedItem();

		if (selectProcess != null) {

			App.setCurrentProcess(selectProcess);

			boolean okClicked = app.showActivitiesView(selectProcess);
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
	void logOutProcessEvent(ActionEvent event) {
		app.showLogin();
	}

	public void setAplicacion(App app) {
		this.app = app;
		
		
        // Convierte la lista enlazada simple en una lista convencional
        List<Process> processList = new ArrayList<>(app.getProcessList().convertArraylist(app.getProcessList()));

        // Crea un ObservableList a partir de la lista convencional
        ObservableList<Process> process = FXCollections.observableArrayList(processList);

        // Asigna la lista de procesos al TableView
        processTable.setItems(process);
	}

	@FXML
	void initialize() {
		// Tabla de procesos
		idColProcess.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		nameColProcess.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		
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
