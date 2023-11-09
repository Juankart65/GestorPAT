package controller;

import java.time.Duration;

import application.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.State;
import model.Task;

/**
 * Este es el controlador de la ventana para crear o editar cultivos
 * 
 * @author USER
 *
 */
public class CreateTaskController {

	@FXML
	private Button btnAcceptTask;

	@FXML
	private ComboBox<State> cbxStateTask = new ComboBox<State>();

	@FXML
	private ComboBox<Duration> cbxDurationTask = new ComboBox<Duration>();

	@FXML
	private TextArea txtDescriptionTask;

	@FXML
	private ComboBox<Boolean> cbxMandatoryTask = new ComboBox<Boolean>();

	@FXML
	private Button btnCancelTask;

	@FXML
	private TextField txtIdTask;

	@FXML
	private TextField txtNameTask;

	private Stage dialogStage;
	private Task task;
	private boolean okClicked = false;

	@FXML
	void acceptActivityEvent(ActionEvent event) {
		if (isInputValid()) {
			task.setId(txtIdTask.getText());
			task.setState(cbxStateTask.getValue());
			task.setName(txtNameTask.getText());
			task.setDescription(txtDescriptionTask.getText());
			task.setDuration(cbxDurationTask.getValue());
			task.setMandatoryTask(cbxMandatoryTask.getValue());
			task.setOwner(App.getCurrentUser());

			okClicked = true;
			dialogStage.close();

		}
	}

	@FXML
	void cancelActivityEvent(ActionEvent event) {
		dialogStage.close();
	}

	/**
	 * Fill the comboBox with the available states
	 */
	public void fillComboBox() {
		ObservableList<State> statesProcess = FXCollections.observableArrayList();

		for (State stateProcess : State.values()) {
			statesProcess.add(stateProcess);
		}

		this.cbxStateTask.setItems(statesProcess);

		this.cbxDurationTask.getItems().addAll(Duration.ofMinutes(15), Duration.ofMinutes(30), Duration.ofHours(1),
				Duration.ofHours(2));
		this.cbxMandatoryTask.getItems().addAll(true, false);
	}

	/**
	 * Predefined initialize method
	 */
	@FXML
	private void initialize() {
		fillComboBox();
	}
	
	/**
	 * Method to check that the text fields are correct
	 * 
	 * @return
	 */
	private boolean isInputValid() {
		String errorMensaje = "";

		if (txtIdTask == null || txtIdTask.getText().length() == 0) {
			errorMensaje += "The ID is not valid!\n";
		}

		if (txtNameTask == null || txtNameTask.getText().length() == 0) {
			errorMensaje += "The name is not valid!\n";
		}

		if (txtDescriptionTask == null || txtDescriptionTask.getText().length() == 0) {
			errorMensaje += "The description is not valid!\n";
		}
		
		if (cbxStateTask.getValue() == null || cbxStateTask.getValue().name().length() == 0) {
			errorMensaje += "The rol is not valid!\n";
		}

		if (errorMensaje.length() == 0) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid fields");
			alert.setHeaderText("Please correct the incorrect fields");
			alert.setContentText(errorMensaje);

			alert.showAndWait();

			return false;
		}
	}

	/**
	 * Method to display a dialog window
	 * 
	 * @param dialogStage
	 */
	public void mostrarDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * This method displays the parameters of the selected crop in the table.
	 * 
	 * @param cultivo
	 */
	public void showTask(Task task) {
		this.task = task;

//		txtIdTask.setText(task.getId());
//		txtNameTask.setText(task.getName());
//		txtDescriptionTask.setText(task.getDescription());
	}

	/**
	 * Method that tells if the accept button was pressed
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}
}
