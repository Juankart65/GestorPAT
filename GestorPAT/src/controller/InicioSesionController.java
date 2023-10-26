/**
 * This class belongs to the package co.edu.uniquindio.finalproject.controllers
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * This class represents to
 *
 * @author Juanes Cardona
 */
public class InicioSesionController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnIniciarSesion;

	@FXML
	private TextField txtUsuario;

	@FXML
	private Button btnRegistrarse;

	@FXML
	private PasswordField txtContrasenia;

	@FXML
	private Button btnSalir;

	private Aplicacion aplicacion;
	private Stage dialogStage;
	int cont = 0;

	@FXML
	void salirEvent(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	void iniciarSesionEvent(ActionEvent event) {
		iniciarSesionAction();
	}

	@FXML
	void registrarseEvent(ActionEvent event) {
		registrarseAction();
	}

	/**
	 * 
	 */
	private void registrarseAction() {
		User tempUser = new User("", "");
		boolean okClicked = aplicacion.mostrarVentanaRegistrarUsuario(tempUser);

		if (okClicked) {
			aplicacion.getListaUsuarios().add(tempUser);
			btnIniciarSesion.setDisable(false);
		}
	}

	/**
	 * 
	 */
	private void iniciarSesionAction() {
		cont++;
		String usuario = txtUsuario.getText();
		String contrasenia = txtContrasenia.getText();

		if (cont >= 3) {
			btnIniciarSesion.setDisable(true);
		}

		if (isInputValid()) {
			boolean usuarioValido = aplicacion.verificarUsuario(usuario, contrasenia);

			if (usuarioValido) {
				User usuarioActual = aplicacion.getUsuario(usuario, contrasenia);
				Aplicacion.setUserActual(usuarioActual);
				Aplicacion.userActual.getListaFincas();
				aplicacion.mostrarVentanaMisFincas(Aplicacion.getUserActual());
			} else {
				txtUsuario.setText("");
				txtContrasenia.setText("");

				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(dialogStage);
				alert.setTitle("Usuario no existe");
				alert.setHeaderText("Por favor ingrese un usuario y una contraseña validos");
				alert.setContentText("Despues de 3 intentos incorrectos el usuario se bloqueará");

				alert.showAndWait();
			}
		}

	}

	/**
	 * 
	 * @return
	 */
	private boolean isInputValid() {
		String errorMensaje = "";

		if (txtUsuario == null || txtUsuario.getText().length() == 0) {
			errorMensaje += "No es valido el usuario!\n";
		}

		if (txtContrasenia == null || txtContrasenia.getText().length() == 0) {
			errorMensaje += "No es valida la contraseña!\n";
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

	@FXML
	void initialize() {

	}

	/**
	 * @param aplicacion
	 */
	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;

	}

}
