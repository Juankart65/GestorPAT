package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.Aplicacion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;

public class VistaPrincipalController {

	private Aplicacion aplicacion;
	private Finca finca = new Finca(null, null, null, 0, null);

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TableColumn<Cultivo, EstadoCultivo> estadoColumna;

	@FXML
	private TableColumn<Cultivo, Double> produccionColumna;

	@FXML
	private TableView<Cultivo> cultivosTabla;

	@FXML
	private TableColumn<Cultivo, String> nombreColumna;

	@FXML
	private TableColumn<Cultivo, Integer> idColumna;

	@FXML
	private TableColumn<Cultivo, Double> cantidadColumna;

	@FXML
	private TableColumn<Persona, Integer> edadColumn;

	@FXML
	private TableColumn<Persona, Double> sueldoColumn;

	@FXML
	private TableView<Persona> personalTabla;

	@FXML
	private TableColumn<Persona, String> idPersonColumn;

	@FXML
	private TableColumn<Persona, CargoPersona> cargoColumn;

	@FXML
	private TableColumn<Persona, String> nombrePersonColumn;

	@FXML
	private Button btnCrearTrabajador;

	@FXML
	private Button btnCrearCultivo;

	@FXML
	private Button btnActualizarTrabajador;

	@FXML
	private Button btnActualizarCultivo;

	@FXML
	private Button btnEliminarTrabajador;

	@FXML
	private Button btnEliminarCultivo;

	@FXML
	private Label textNombreAdministrador;

	@FXML
	private Label textNombreFinca;

	@FXML
	private Label textDireccion;

	@FXML
	private Label textIdAdmin;

	@FXML
	private Label textIdFinca;

	@FXML
	private ImageView imgFinca;

	@FXML
	private Button btnCerrarSesion;

	@FXML
	private Button btnActualizarLabor;

	@FXML
	private Button btnEliminarLabor;

	@FXML
	private Button btnRefrescar;

	@FXML
	private Button btnVolver;

	@FXML
	private TableView<Labor> laboresTabla;

	@FXML
	private Button btnCrearLabor;

	@FXML
	private Button btnAgregarImagen;

	@FXML
	private Button btnPagoNomina;

	@FXML
	private TableColumn<Labor, String> nombreLaborColumn;

	@FXML
	private TableColumn<Labor, EstadoLabor> estadoLaborColumn;

	@FXML
	private TableColumn<Labor, String> descripcionLaborColumn;

	@FXML
	private TableColumn<Labor, String> idLaborColumn;

	public ObservableList<EstadoCultivo> estadoCultivos = FXCollections.observableArrayList();

	private boolean okClicked = false;

	public void mostrarDetallesFinca(Finca finca) {
		if (finca != null) {
			textNombreFinca.setText(finca.getNombre());
			textDireccion.setText(finca.getDireccion());
			textNombreAdministrador.setText(finca.getAdministrador().getNombre());
			textIdFinca.setText(String.valueOf(finca.getId()));
			textIdAdmin.setText(finca.getAdministrador().getIdentificacion());
			imgFinca.setImage(finca.getImagenFinca());
		}

	}

	@FXML
	void initialize() throws FincaExceptions {

		// Tabla de cultivos
		idColumna.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
		nombreColumna.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		cantidadColumna.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty().asObject());
		estadoColumna.setCellValueFactory(cellData -> cellData.getValue().estadoCultivoProperty());
		produccionColumna.setCellValueFactory(cellData -> cellData.getValue().produccionProperty().asObject());

		// Tabla de personas
		idPersonColumn.setCellValueFactory(cellData -> cellData.getValue().idPersonProperty());
		nombrePersonColumn.setCellValueFactory(cellData -> cellData.getValue().nombrePersonProperty());
		edadColumn.setCellValueFactory(cellData -> cellData.getValue().edadProperty().asObject());
		cargoColumn.setCellValueFactory(cellData -> cellData.getValue().cargoProperty());
		sueldoColumn.setCellValueFactory(cellData -> cellData.getValue().sueldoProperty().asObject());

		// Tabla de labores
		idLaborColumn.setCellValueFactory(cellData -> cellData.getValue().idLaborProperty());
		nombreLaborColumn.setCellValueFactory(cellData -> cellData.getValue().nombreLaborProperty());
		descripcionLaborColumn.setCellValueFactory(cellData -> cellData.getValue().descripcionLaborProperty());
		estadoLaborColumn.setCellValueFactory(cellData -> cellData.getValue().estadoLaborProperty());

	}

	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;

		cultivosTabla.setItems(User.getFincaActual().getListaCultivos());
		personalTabla.setItems(User.getFincaActual().getListaTrabajadores());
		laboresTabla.setItems(User.getFincaActual().getListaLabores());
	}

	@FXML
	void botonRefrescarEvent(ActionEvent event) {
		aplicacion.mostrarVentanaPrincipal(User.getFincaActual());
	}

	@FXML
	void pagoNominaEvent(ActionEvent event) {
		double nomina = 0;
		for (Persona trabajador : User.getFincaActual().getListaTrabajadores()) {
			nomina += trabajador.getSueldo();
		}

		JOptionPane.showMessageDialog(null, "El pago de n�mina total es: " + nomina);
	}

	@FXML
	void crearLaborEvent(ActionEvent event) {

		Labor tempLabor = new Labor(null, null, null, EstadoLabor.Otro);
		boolean okClicked = aplicacion.mostrarVentanaEditarLabor(tempLabor);

		if (okClicked) {
			User.getFincaActual().getListaLabores().add(tempLabor);
		}
	}

	@FXML
	void actualizarLaborEvent(ActionEvent event) {
		Labor laborSeleccionada = laboresTabla.getSelectionModel().getSelectedItem();
		if (laborSeleccionada != null) {
			boolean okClicked = aplicacion.mostrarVentanaEditarLabor(laborSeleccionada);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(aplicacion.getPrimaryStage());
			alert.setTitle("Sin seleccion");
			alert.setHeaderText("No seleccion� una labor");
			alert.setContentText("Por favor selecciona una labor en la tabla");

			alert.showAndWait();
		}
	}

	@FXML
	void eliminarLaborEvent(ActionEvent event) {

		int selectedIndex = laboresTabla.getSelectionModel().getSelectedIndex();

		if (selectedIndex >= 0) {
			laboresTabla.getItems().remove(selectedIndex);
		} else {
			// Nada seleccionado

			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(aplicacion.getPrimaryStage());
			alert.setTitle("Sin selecci�n");
			alert.setHeaderText("No hay nada seleccionado");
			alert.setContentText("Por favor seleccione una labor en la tabla");

			alert.showAndWait();
		}
	}

	@FXML
	void botonVolverEvent(ActionEvent event) {
		aplicacion.mostrarVentanaMisFincas(Aplicacion.getUserActual());
	}

	@FXML
	void CerrarSesionEvent(ActionEvent event) {
		aplicacion.mostrarVentanaInicioSesion();
	}

	@FXML
	void crearCultivoEvent(ActionEvent event) {

		Cultivo tempCultivo = new Cultivo(null, 0, EstadoCultivo.OTRO, 0, 0);
		boolean okClicked = aplicacion.mostrarVentanaEditarCultivos(tempCultivo);

		if (okClicked) {
			User.getFincaActual().getListaCultivos().add(tempCultivo);
		}

	}

	@FXML
	void actualizarCultivoEvent(ActionEvent event) {

		Cultivo cultivoSeleccionado = cultivosTabla.getSelectionModel().getSelectedItem();
		if (cultivoSeleccionado != null) {
			boolean okClicked = aplicacion.mostrarVentanaEditarCultivos(cultivoSeleccionado);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(aplicacion.getPrimaryStage());
			alert.setTitle("Sin seleccion");
			alert.setHeaderText("No seleccion� un cultivo");
			alert.setContentText("Por favor selecciona un cultivo en la tabla");

			alert.showAndWait();
		}
	}

	@FXML
	void eliminarCultivoEvent(ActionEvent event) {
		int selectedIndex = cultivosTabla.getSelectionModel().getSelectedIndex();

		if (selectedIndex >= 0) {
			cultivosTabla.getItems().remove(selectedIndex);
		} else {
			// Nada seleccionado

			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(aplicacion.getPrimaryStage());
			alert.setTitle("Sin selecci�n");
			alert.setHeaderText("No hay nada seleccionado");
			alert.setContentText("Por favor seleccione un cultivo en la tabla");

			alert.showAndWait();
		}
	}

	@FXML
	void crearTrabajadorEvent(ActionEvent event) {
		Persona tempPersona = new Persona(null, null, 0, CargoPersona.Otro, 0);
		boolean okClicked = aplicacion.mostrarVentanaEditarPersonas(tempPersona);

		if (okClicked) {

			if (tempPersona.getCargo().equals(CargoPersona.Administrador)) {
				User.getFincaActual().setAdministrador(tempPersona);
			}
			User.getFincaActual().getListaTrabajadores().add(tempPersona);
		}
	}

	@FXML
	void actualizarTrabajadorEvent(ActionEvent event) {
		Persona personaSeleccionada = personalTabla.getSelectionModel().getSelectedItem();
		if (personaSeleccionada != null) {
			boolean okClicked = aplicacion.mostrarVentanaEditarPersonas(personaSeleccionada);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(aplicacion.getPrimaryStage());
			alert.setTitle("Sin seleccion");
			alert.setHeaderText("No seleccion� un cultivo");
			alert.setContentText("Por favor selecciona un cultivo en la tabla");

			alert.showAndWait();
		}
	}

	@FXML
	void eliminarTrabajadorEvent(ActionEvent event) {
		int selectedIndex = personalTabla.getSelectionModel().getSelectedIndex();

		if (selectedIndex >= 0) {
			personalTabla.getItems().remove(selectedIndex);
		} else {
			// Nada seleccionado

			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(aplicacion.getPrimaryStage());
			alert.setTitle("Sin selecci�n");
			alert.setHeaderText("No hay nada seleccionado");
			alert.setContentText("Por favor seleccione una persona en la tabla");

			alert.showAndWait();
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
