package controller;

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
import model.Activity;
import model.State;

/**
 * Este es el controlador de la ventana para crear o editar cultivos
 * 
 * @author USER
 *
 */
public class CreateActivitiesController {

	@FXML
	private TextArea txtDescriptionActivity;

	@FXML
	private TextField txtIdActivity;

	@FXML
	private Button btnCancelActivity;

	@FXML
	private Button btnAcceptActivity;

	@FXML
	private ComboBox<State> cbxStateActivity = new ComboBox<State>();

	@FXML
	private TextField txtNameActivity;

	private Stage dialogStage;
	private Activity activity;
	private boolean okClicked = false;

	@FXML
	void acceptActivityEvent(ActionEvent event) {
		if (isInputValid()) {
			activity.setId(txtIdActivity.getText());
			activity.setState(cbxStateActivity.getValue());
			activity.setName(txtNameActivity.getText());
			activity.setDescription(txtDescriptionActivity.getText());
			activity.setOwner(App.getCurrentUser());

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

		this.cbxStateActivity.setItems(statesProcess);
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

		if (txtIdActivity == null || txtIdActivity.getText().length() == 0) {
			errorMensaje += "The ID is not valid!\n";
		}

		if (txtNameActivity == null || txtNameActivity.getText().length() == 0) {
			errorMensaje += "The name is not valid!\n";
		}

		if (txtDescriptionActivity == null || txtDescriptionActivity.getText().length() == 0) {
			errorMensaje += "The description is not valid!\n";
		}
		
		if (cbxStateActivity.getValue() == null || cbxStateActivity.getValue().name().length() == 0) {
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
	public void showActivity(Activity activity) {
		this.activity = activity;

		txtIdActivity.setText(activity.getId());
		txtNameActivity.setText(activity.getName());
		txtDescriptionActivity.setText(activity.getDescription());
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
