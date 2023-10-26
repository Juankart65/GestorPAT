package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrarUsuarioController {

	@FXML
    private PasswordField txtContrase�a;

	@FXML
	private Button btnAceptarUsuario;

	@FXML
	private TextField txtCrearUsuario;

	@FXML
	private Button btnCancelarUsuario;

	private Stage dialogStage;
	private User user = new User("", "");
	private boolean okClicked = false;

	@FXML
    void aceptarUsuarioEvent(ActionEvent event) {
    	if(isInputValid()) {
    		user.setUsuario(txtCrearUsuario.getText());
    		user.setPw(txtContrase�a.getText());
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.initOwner(dialogStage);
    		alert.setTitle("Creado Correctamente");
    		alert.setHeaderText("Usuario creado correctamente");
    		alert.setContentText("El usuario fue creado con exito, inicie sesion nuevamente");
    		
    		alert.showAndWait();
    		
    		
    		okClicked = true;
    		dialogStage.close();
    	}
    }

	@FXML
	void cancelarUsuarioEvent(ActionEvent event) {
		dialogStage.close();
	}

	@FXML
	private void initialize() {
		// TODO Auto-generated method stub
	}

	private boolean isInputValid() {
    	String errorMensaje = "";
    	
    	if(txtCrearUsuario == null || txtCrearUsuario.getText().length() == 0) {
    			errorMensaje += "No es valido el usuario!\n";
    	}
    	
    	if(txtContrase�a == null || txtContrase�a.getText().length() == 0) {
			errorMensaje += "No es valida la contrase�a!\n";
    	}
    	
    	if(errorMensaje.length() == 0) {
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

	public void mostrarDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void mostrarUser(User user) {
    	this.user = user;
    	
    	txtCrearUsuario.setText(user.getUsuario());
    	txtContrase�a.setText(user.getPw());
    }

	public boolean isOkClicked() {
		return okClicked;
	}
}
