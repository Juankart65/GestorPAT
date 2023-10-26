package controller;

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

/**
 * Este es el controlador de la ventana para crear o editar cultivos
 * 
 * @author USER
 *
 */
public class EditarCrearLaboresController {
	@FXML
	private Button btnCancelarLabores;

	@FXML
	private TextField txtEditIdLabores;

	@FXML
	private TextField txtEditEstadoLabores;

	@FXML
	private TextArea txtDescripcionLabores;

	@FXML
	private TextField txtEditNombreLabores;

	@FXML
	private Button btnAceptarLabor;

	@FXML
	private ComboBox<EstadoLabor> cbxEstadoLabor = new ComboBox<>();

	/**
	 * 
	 */
	private Stage dialogStage;
	private Labor labor;
	private boolean okClicked = false;

	/**
	 * Evento para aceptar los cambios del cultivo
	 * 
	 * @param event
	 */
	@FXML
	void aceptarLaborEvent(ActionEvent event) {

		if (isInputValid()) {
			labor.setIdLabor(txtEditIdLabores.getText());
			labor.setNombreLabor(txtEditNombreLabores.getText());

			switch (cbxEstadoLabor.getValue().name()) {
			case "Completa":
				labor.setEstadoLabor(EstadoLabor.Completa);
				break;
			case "Pendiente":
				labor.setEstadoLabor(EstadoLabor.Pendiente);
				break;
			case "Media":
				labor.setEstadoLabor(EstadoLabor.Media);
				break;

			default:
				break;
			}
			labor.setDescripcionLabor(txtDescripcionLabores.getText());

			okClicked = true;
			dialogStage.close();
		}
	}

	/**
	 * Evento para cancelar los cambios
	 * 
	 * @param event
	 */
	@FXML
	void cancelarLaborEvent(ActionEvent event) {
		dialogStage.close();
	}

	/**
	 * Metodo initialize predefinidp
	 */
	@FXML
	private void initialize() {
		llenarComboBox();
	}

	/**
	 * LLena el comboBox con los tipos de cuenta disponibles
	 */
	public void llenarComboBox() {
		ObservableList<EstadoLabor> estados = FXCollections.observableArrayList();

		for (EstadoLabor estadoLabor : EstadoLabor.values()) {
			estados.add(estadoLabor);
		}

		this.cbxEstadoLabor.setItems(estados);
	}

	/**
	 * Metodo para comprobar que los campos de textos sean correctos
	 * 
	 * @return
	 */
	private boolean isInputValid() {
		String errorMensaje = "";

		if (txtEditIdLabores == null || txtEditIdLabores.getText().length() == 0) {
			errorMensaje += "No es valido el id!\n";
		}

		if (txtEditNombreLabores == null || txtEditNombreLabores.getText().length() == 0) {
			errorMensaje += "No es valido el nombre!\n";
		}

		if (txtDescripcionLabores == null || txtDescripcionLabores.getText().length() == 0) {
			errorMensaje += "No es valida la descripcion!\n";
		}

		if (errorMensaje.length() == 0) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Campos Invalidos");
			alert.setHeaderText("Por favor corrija los campos incorrectos");
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
	public void mostrarLabor(Labor labor) {
		this.labor = labor;

		txtEditIdLabores.setText(labor.getIdLabor());
		txtEditNombreLabores.setText(labor.getNombreLabor());
		txtDescripcionLabores.setText(labor.getDescripcionLabor());
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
