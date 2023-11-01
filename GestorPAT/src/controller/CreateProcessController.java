package controller;

import java.io.File;

import application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Process;

/**
 * Este es el controlador de la ventana para crear o editar cultivos
 * 
 * @author USER
 *
 */
public class CreateProcessController {

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
	private ComboBox<?> cbxStateProcess;

	private App aplicacion = new App();

	/**
	 * 
	 */
	private Stage dialogStage;
	private Process process;

	private boolean okClicked = false;

	@FXML
	void acceptProcessEvent(ActionEvent event) {
		if (isInputValid()) {
			process.setId(txtIdProcess.getText());
			process.setName(txtNameProcess.getText());
			process.setDescription(txtDescription.getText());
			process.setOwner(App.getCurrentUser());

			okClicked = true;
			dialogStage.close();

		}
	}

	@FXML
	void cancelProcessEvent(ActionEvent event) {
		dialogStage.close();
	}

	/**
	 * Metodo initialize predefinidp
	 */
	@FXML
	private void initialize() {
		// TODO Auto-generated method stub
	}

	/**
	 * Metodo para comprobar que los campos de textos sean correctos
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
	 * Metodo para mostrar una ventana de dialogo
	 * 
	 * @param dialogStage
	 */
	public void mostrarDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Este metodo muestra los parametros del cultivo que se haya seleccionado en la
	 * tabla
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
	 * Metodo que dice si el boton aceptar fue pulsado
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

}
