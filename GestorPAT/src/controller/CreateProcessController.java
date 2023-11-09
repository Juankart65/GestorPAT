package controller;



import application.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Process;
import model.State;

/**
 * Este es el controlador de la ventana para crear o editar cultivos
 * 
 * @author USER
 *
 */
public class CreateProcessController {

	// Attribute declaration

	@FXML
	private Button btnCancelProcess;

	@FXML
	private TextArea txtDescription;

	@FXML
	private Button btnAcceptProcess;

	@FXML
	private TextField txtIdProcess;

	@FXML
	private TextField txtNameProcess;

	@FXML
	private ComboBox<State> cbxStateProcess = new ComboBox<State>();

	private Stage dialogStage;
	private Process process;
	private boolean okClicked = false;

	/**
	 * 
	 * Method that
	 *
	 * @param event
	 */
	@FXML
	void acceptProcessEvent(ActionEvent event) {
		if (isInputValid()) {
			process.setId(txtIdProcess.getText());
			process.setState(cbxStateProcess.getValue());
			process.setName(txtNameProcess.getText());
			process.setDescription(txtDescription.getText());
			process.setOwner(App.getCurrentUser());

			okClicked = true;
			dialogStage.close();

		}
	}

	/**
	 * 
	 * Method that
	 *
	 * @param event
	 */
	@FXML
	void cancelProcessEvent(ActionEvent event) {
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

		this.cbxStateProcess.setItems(statesProcess);
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

		if (txtIdProcess == null || txtIdProcess.getText().length() == 0) {
			errorMensaje += "The ID is not valid!\n";
		}

		if (txtNameProcess == null || txtNameProcess.getText().length() == 0) {
			errorMensaje += "The name is not valid!\n";
		}

		if (txtDescription == null || txtDescription.getText().length() == 0) {
			errorMensaje += "The description is not valid!\n";
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
	public void showProcess(Process process) {
		this.process = process;

		txtIdProcess.setText(process.getId());
		txtNameProcess.setText(process.getName());
		txtDescription.setText(process.getDescription());
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
