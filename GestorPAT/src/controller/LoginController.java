/**
 * This class belongs to the package co.edu.uniquindio.finalproject.controllers
 */
package controller;

import java.util.ResourceBundle;

import application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * This class represents to
 *
 * @author Juanes Cardona
 * @author Juanes Ramirez
 * @author Jose Taborda
 * 
 */
public class LoginController {

	// Attribute declaration

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
	private ComboBox<String> cbxRol;

	@FXML
	private PasswordField txtPassword;

	private App aplicacion;
	private Stage dialogStage;
	int robin = 0;

	/**
	 * 
	 * Method that
	 *
	 * @param event
	 */
	@FXML
	void backEvent(ActionEvent event) {
		System.exit(0);
	}

	/**
	 * 
	 * Method that
	 *
	 * @param event
	 */
	@FXML
	void loginEvent(ActionEvent event) {
		loginAction();
	}

	/**
	 * 
	 * Method that
	 *
	 * @param event
	 */
	@FXML
	void signUpEvent(ActionEvent event) {
		signUpAction();
	}

	/**
	 * 
	 * This method runs the registration window
	 *
	 */
	private void signUpAction() {
		User tempUser = new User("", "");
		boolean okClicked = aplicacion.showSignUp(tempUser);

		if (okClicked) {
			ModelFactoryController.getInstance().createUser(tempUser);
			ModelFactoryController.getInstance().getHandler().getUserList().addEnd(tempUser);
			txtUser.setText(tempUser.getName());
			btnLogin.setDisable(false);
		}
	}

	/**
	 * 
	 * Method that performs the login action
	 *
	 */
	private void loginAction() {
		robin++;
		int n = 3 - robin;
		String user = txtUser.getText();
		String password = txtPassword.getText();

		if (robin >= 3) {
			btnLogin.setDisable(true);
		}

		if (isInputValid()) {
			boolean validUser = ModelFactoryController.getInstance().getHandler().verifyUser(user, password);

			if (validUser) {
				User currentUser = ModelFactoryController.getInstance().getHandler().getUser(user, password);
				App.setCurrentUser(currentUser);
				aplicacion.showMainView(App.getCurrentUser());
			} else {
				txtUser.setText("");
				txtPassword.setText("");

				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(dialogStage);
				alert.setTitle("User does not exist");
				alert.setHeaderText("Please enter a valid username and password");
				alert.setContentText("After " + n + " incorrect attempts the user will be blocked");

				alert.showAndWait();
			}
		}

	}

	/**
	 * This method checks if the textField entries are valid.
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

	/**
	 * 
	 * Method that
	 *
	 */
	@FXML
	void initialize() {

	}

	/**
	 * @param aplicacion
	 */
	public void setAplicacion(App aplicacion) {
		this.aplicacion = aplicacion;

	}

}
