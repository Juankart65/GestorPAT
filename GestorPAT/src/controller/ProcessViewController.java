package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

public class ProcessViewController {

	@FXML
	private TableView<Finca> fincasTabla;

	@FXML
	private TableColumn<Finca, Persona> adminColumn;

	@FXML
	private Button btnEliminarFinca;

	@FXML
	private Button btnVisualizarFinca;

	@FXML
	private TableColumn<Finca, String> nombreColumn;

	@FXML
	private Button btnCrearFinca;

	@FXML
	private Button btnActualizarFinca;

	@FXML
	private TableColumn<Finca, Integer> idColumn;

	@FXML
	private TableColumn<Finca, String> direccionColumn;

	@FXML
	private Button btnVolver;

	private Aplicacion aplicacion;
	private User user = Aplicacion.userActual;
	private CrearFincaController crearFincaController = new CrearFincaController();

	private boolean okClicked = false;

	@FXML
	void crearFincaEvent(ActionEvent event) {
		Persona tempAdmin = new Persona(null, null, 0, null, 0);
		Finca tempFinca = new Finca(null, null, tempAdmin, 0, null);
		boolean okClicked = aplicacion.mostrarVentanaCrearFinca(tempFinca);

		if (okClicked) {
			user.getListaFincas().add(tempFinca);
		}
	}

	@FXML
	void actualizarFincaEvent(ActionEvent event) {
		Finca fincaSeleccionada = fincasTabla.getSelectionModel().getSelectedItem();
		if (fincaSeleccionada != null) {
			boolean okClicked = aplicacion.mostrarVentanaCrearFinca(fincaSeleccionada);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(aplicacion.getPrimaryStage());
			alert.setTitle("Sin seleccion");
			alert.setHeaderText("No seleccion� una finca");
			alert.setContentText("Por favor selecciona una finca en la tabla");

			alert.showAndWait();
		}
	}

	@FXML
	void EliminarFincaEvent(ActionEvent event) {
		int selectedIndex = fincasTabla.getSelectionModel().getSelectedIndex();

		if (selectedIndex >= 0) {
			fincasTabla.getItems().remove(selectedIndex);
		} else {
			// Nada seleccionado

			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(aplicacion.getPrimaryStage());
			alert.setTitle("Sin selecci�n");
			alert.setHeaderText("No hay nada seleccionado");
			alert.setContentText("Por favor seleccione una finca en la tabla");

			alert.showAndWait();
		}
	}

	@FXML
	void visualizarFincaEvent(ActionEvent event) {
		Finca fincaSeleccionada = fincasTabla.getSelectionModel().getSelectedItem();

		if (fincaSeleccionada != null) {

			User.setFincaActual(fincaSeleccionada);

			boolean okClicked = aplicacion.mostrarVentanaPrincipal(User.getFincaActual());
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(aplicacion.getPrimaryStage());
			alert.setTitle("Sin seleccion");
			alert.setHeaderText("No seleccion� una finca");
			alert.setContentText("Por favor selecciona una finca en la tabla");

			alert.showAndWait();
		}
	}

	@FXML
	private Button btnCerrarSesion;

	@FXML
	void cerrarSesionEvent(ActionEvent event) {
		aplicacion.mostrarVentanaInicioSesion();
	}

	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;

		fincasTabla.setItems(user.getListaFincas());
	}

	@FXML
	void initialize() {

		// Tabla de fincas
		idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
		nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		direccionColumn.setCellValueFactory(cellData -> cellData.getValue().direccionProperty());
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
