/**
 * This class belongs to the package co.edu.uniquindio.finalproject.controllers
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Aplicacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * This class represents to
 *
 * @author Juanes Cardona
 */
public class LoginController {

	@FXML
	private ResourceBundle resources;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignUp;

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPassword;

	private Aplicacion aplicacion;
	private Stage dialogStage;
	int robin = 0;

	@FXML
	void backEvent(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	void loginEvent(ActionEvent event) {
		loginAction();
	}

	@FXML
	void signUpEvent(ActionEvent event) {
		signUpAction();
	}

	/**
	 * 
	 */
	private void signUpAction() {
		User tempUser = new User("", "");
		boolean okClicked = aplicacion.showSignUp(tempUser);

		if (okClicked) {
			aplicacion.getUserList().add(tempUser);
			btnLogin.setDisable(false);
		}
	}

	/**
	 * 
	 */
	private void loginAction() {
		robin++;
		String user = txtUser.getText();
		String password = txtPassword.getText();

		if (robin >= 3) {
			btnLogin.setDisable(true);
		}

		if (isInputValid()) {
			boolean validUser = aplicacion.verifyUser(user, password);

			if (validUser) {
				User currentUser = aplicacion.getUser(user, password);
				Aplicacion.setCurrentUser(currentUser);
//				Aplicacion.userActual.getListaFincas();
//				aplicacion.mostrarVentanaMisFincas(Aplicacion.getUserActual());
			} else {
				txtUser.setText("");
				txtPassword.setText("");

				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(dialogStage);
				alert.setTitle("User does not exist");
				alert.setHeaderText("Please enter a valid username and password");
				alert.setContentText("After 3 incorrect attempts the user will be blocked");

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

		if (txtUser == null || txtUser.getText().length() == 0) {
			errorMensaje += "The user is not valid!\n";
		}

		if (txtPassword == null || txtPassword.getText().length() == 0) {
			errorMensaje += "The password is not valid!\n";
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
