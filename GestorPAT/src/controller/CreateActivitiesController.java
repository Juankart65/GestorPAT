package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Activity;

/**
 * Este es el controlador de la ventana para crear o editar cultivos
 * 
 * @author USER
 *
 */
public class CreateActivitiesController {

	/**
	 * Bot�n para aceptar los cambios
	 */
	@FXML
	private Button btnAceptarCult;

	/**
	 * campo de texto estado del cultivo
	 */
	@FXML
	private TextField txtEditEstadoCultivo;

	/**
	 * Bot�n para cancelar los cambios
	 */
	@FXML
	private Button btnCancelarCultivos;

	/**
	 * campo de texto nombre del cultivo
	 */
	@FXML
	private TextField txtEditNombreCultivos;

	/**
	 * campo de texto produccion del cultivo
	 */
	@FXML
	private TextField txtEditProduccionCultivos;

	/**
	 * campo de texto id del cultivo
	 */
	@FXML
	private TextField txtEditIdCultivos;

	/**
	 * campo de texto cantidad del cultivo
	 */
	@FXML
	private TextField txtEditCantidadCultivos;

	@FXML
	private ComboBox<EstadoCultivo> cbxEstadoCultivo;

	/**
	 * 
	 */
	private Stage dialogStage;
	private Activity cultivo;
	private boolean okClicked = false;

	/**
	 * Evento para aceptar los cambios del cultivo
	 * 
	 * @param event
	 */
	@FXML
	void aceptarCultivosEvent(ActionEvent event) {

		if (isInputValid()) {
			cultivo.setId(Integer.parseInt(txtEditIdCultivos.getText()));
			cultivo.setNombre(txtEditNombreCultivos.getText());
			
			switch (cbxEstadoCultivo.getValue().name()) {
			case "ABONADO":
				cultivo.setEstadoCultivo(EstadoCultivo.ABONADO);
				break;
			case "FUMIGACI�N":
				cultivo.setEstadoCultivo(EstadoCultivo.FUMIGACI�N);
				break;
			case "DESHIERBADO":
				cultivo.setEstadoCultivo(EstadoCultivo.DESHIERBADO);
				break;
			case "COSECHADO":
				cultivo.setEstadoCultivo(EstadoCultivo.COSECHA);
				break;
			case "PRODUCCI�N":
				cultivo.setEstadoCultivo(EstadoCultivo.PRODUCCI�N);
				break;
			case "ACABADO":
				cultivo.setEstadoCultivo(EstadoCultivo.ACABADO);
				break;
			case "PODAR":
				cultivo.setEstadoCultivo(EstadoCultivo.PODAR);
				break;
			case "SIEMBRA":
				cultivo.setEstadoCultivo(EstadoCultivo.SIEMBRA);
				break;

			default:
				cultivo.setEstadoCultivo(EstadoCultivo.OTRO);
				break;
			}
			cultivo.setCantidad(Float.parseFloat(txtEditCantidadCultivos.getText()));
			cultivo.setProduccion(Float.parseFloat(txtEditProduccionCultivos.getText()));

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
	void cancelarCultivoEvent(ActionEvent event) {
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
		ObservableList<EstadoCultivo> estado = FXCollections.observableArrayList();

		for (EstadoCultivo tipo : EstadoCultivo.values()) {
			estado.add(tipo);
		}

		this.cbxEstadoCultivo.setItems(estado);
	}

	/**
	 * Metodo para comprobar que los campos de textos sean correctos
	 * 
	 * @return
	 */
	private boolean isInputValid() {
		String errorMensaje = "";

		if (txtEditIdCultivos == null || txtEditIdCultivos.getText().length() == 0) {
			errorMensaje += "No es valido el id!\n";
		}

		if (txtEditNombreCultivos == null || txtEditNombreCultivos.getText().length() == 0) {
			errorMensaje += "No es valido el nombre!\n";
		}

		if (txtEditCantidadCultivos == null || txtEditCantidadCultivos.getText().length() == 0) {
			errorMensaje += "No es valida la cantidad!\n";
		}

		if (txtEditProduccionCultivos == null || txtEditProduccionCultivos.getText().length() == 0) {
			errorMensaje += "No es valido la produccion!\n";
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
	 * @param activity
	 */
	public void mostrarCultivo(Activity activity) {
		this.cultivo = activity;

		txtEditIdCultivos.setText(Integer.toString(activity.getId()));
		txtEditNombreCultivos.setText(activity.getNombre());
		txtEditProduccionCultivos.setText(String.valueOf(activity.getProduccion()));
		txtEditCantidadCultivos.setText(String.valueOf(activity.getCantidad()));
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
