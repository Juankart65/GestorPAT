package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Este es el controlador de la ventana para editar y crear trabajadores
 * 
 * @author USER
 *
 */
public class EditarCrearTrabajadoresController {

	/**
	 * bot�n para cancelar los cambios
	 */
	@FXML
	private Button btnCancelarPersona;

	/**
	 * campo de texto cargo persona
	 */
	@FXML
	private TextField txtEditCargoPersona;

	/**
	 * campo de texto edad persona
	 */
	@FXML
	private TextField txtEditEdadPersona;

	/**
	 * campo de texto suledo persona
	 */
	@FXML
	private TextField txtEditSueldoPersona;

	/**
	 * campo de texto id persona
	 */
	@FXML
	private TextField txtEditIdPersona;

	/**
	 * bot�n aceptar los cambios
	 */
	@FXML
	private Button btnAceptarPersona;

	/**
	 * campo de texto nombre persona
	 */
	@FXML
	private TextField txtEditNombrePersona;

	@FXML
	private ComboBox<CargoPersona> cbxCargoTrabajador = new ComboBox<>();

	/**
	 * 
	 */
	private Stage dialogStage;
	private Persona persona;
	private boolean okClicked = false;

	/**
	 * Evento para aceptar los cambios de una persona
	 * 
	 * @param event
	 */
	@FXML
	void aceptarPersonaEvent(ActionEvent event) {
		if (isInputValid()) {
			persona.setIdentificacion(txtEditIdPersona.getText());
			persona.setNombre(txtEditNombrePersona.getText());

			switch (cbxCargoTrabajador.getValue().name()) {
			case "Trabajador":
				persona.setCargo(CargoPersona.Trabajador);
				break;
			case "Cocinera":
				persona.setCargo(CargoPersona.Cocinera);
				break;
			case "Contratista":
				persona.setCargo(CargoPersona.Contratista);
				break;
			case "Administrador":
				persona.setCargo(CargoPersona.Administrador);
				break;

			default:
				persona.setCargo(CargoPersona.Otro);
				break;
			}
			persona.setEdad(Integer.parseInt(txtEditEdadPersona.getText()));
			persona.setSueldo(Float.parseFloat(txtEditSueldoPersona.getText()));
		}

		okClicked = true;
		dialogStage.close();
	}

	/**
	 * Evento para cancelar los cambios de una persona
	 * 
	 * @param event
	 */
	@FXML
	void cancelarPersonaEvent(ActionEvent event) {
		dialogStage.close();
	}

	/**
	 * Metodo initialize predefinido
	 */
	@FXML
	private void initialize() {
		llenarComboBox();
	}

	/**
	 * LLena el comboBox con los tipos de cuenta disponibles
	 */
	public void llenarComboBox() {
		ObservableList<CargoPersona> cargo = FXCollections.observableArrayList();

		for (CargoPersona tipo : CargoPersona.values()) {
			cargo.add(tipo);
		}

		this.cbxCargoTrabajador.setItems(cargo);
	}

	/**
	 * Metodo para comprobar que los campos de texto sean validos
	 * 
	 * @return
	 */
	private boolean isInputValid() {
		String errorMensaje = "";

		if (txtEditIdPersona == null || txtEditIdPersona.getText().length() == 0) {
			errorMensaje += "No es valido el id!\n";
		}

		if (txtEditNombrePersona == null || txtEditNombrePersona.getText().length() == 0) {
			errorMensaje += "No es valido el nombre!\n";
		}

		if (txtEditEdadPersona == null || txtEditEdadPersona.getText().length() == 0) {
			errorMensaje += "No es valida la edad!\n";
		}

		if (txtEditSueldoPersona == null || txtEditSueldoPersona.getText().length() == 0) {
			errorMensaje += "No es valido el sueldo!\n";
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
	 * Metodo que muestra una ventana de dialogo
	 * 
	 * @param dialogStage
	 */
	public void mostrarDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Metodo que muestra los datos de la persona seleccionada
	 * 
	 * @param persona
	 */
	public void mostrarPersona(Persona persona) {
		this.persona = persona;

		txtEditIdPersona.setText(persona.getIdentificacion());
		txtEditNombrePersona.setText(persona.getNombre());
		txtEditEdadPersona.setText(String.valueOf(persona.getEdad()));
		txtEditSueldoPersona.setText(String.valueOf(persona.getSueldo()));
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
