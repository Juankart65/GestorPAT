package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

public class SignUpController {

    @FXML
    private Button btnCancelUser;

    @FXML
    private TextField txtCreateUser;

    @FXML
    private Button btnAcceptUser;

    @FXML
    private PasswordField txtPassword;


	private Stage dialogStage;
	private User user = new User("", "");
	private boolean okClicked = false;

	@FXML
    void acceptUserEvent(ActionEvent event) {
    	if(isInputValid()) {
    		user.setName(txtCreateUser.getText());
    		user.setPassword(txtPassword.getText());
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.initOwner(dialogStage);
    		alert.setTitle("Created Successfully");
    		alert.setHeaderText("Successfully created user");
    		alert.setContentText("The user was created successfully, log in again");
    		
    		alert.showAndWait();
    		
    		
    		okClicked = true;
    		dialogStage.close();
    	}
    }

	@FXML
	void cancelUserEvent(ActionEvent event) {
		dialogStage.close();
	}

	@FXML
	private void initialize() {
		// TODO Auto-generated method stub
	}

	private boolean isInputValid() {
    	String errorMensaje = "";
    	
    	if(txtCreateUser == null || txtCreateUser.getText().length() == 0) {
    			errorMensaje += "The user is not valid!\n";
    	}
    	
    	if(txtPassword == null || txtPassword.getText().length() == 0) {
			errorMensaje += "The password is not valid!\n";
    	}
    	
    	if(errorMensaje.length() == 0) {
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

	public void showDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void showUser(User user) {
    	this.user = user;
    	
    	txtCreateUser.setText(user.getName());
    	txtPassword.setText(user.getPassword());
    }

	public boolean isOkClicked() {
		return okClicked;
	}
}
