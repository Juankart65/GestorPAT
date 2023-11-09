package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Rol;
import model.User;

public class SignUpController {

	// Attribute declaration

    @FXML
    private Button btnCancelUser;

    @FXML
    private TextField txtCreateUser;

    @FXML
    private Button btnAcceptUser;

    @FXML
    private PasswordField txtPassword;
    
    @FXML
    private ComboBox<Rol> cbxRol = new ComboBox<Rol>();


	private Stage dialogStage;
	private User user = new User("", "");
	private boolean okClicked = false;

	/**
	 * 
	 * Method that 
	 *
	 * @param event
	 */
	@FXML
    void acceptUserEvent(ActionEvent event) {
    	if(isInputValid()) {
    		user.setName(txtCreateUser.getText());
    		user.setPassword(txtPassword.getText());
    		
			switch (cbxRol.getValue().name()) {
			case "Admin":
				user.setRol(Rol.Admin);
				break;
			case "User":
				user.setRol(Rol.User);
				break;
			default:
				user.setRol(Rol.User);
				break;
			}
    		
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

	/**
	 * 
	 * Method that 
	 *
	 * @param event
	 */
	@FXML
	void cancelUserEvent(ActionEvent event) {
		dialogStage.close();
	}
	
	/**
	 * LLena el comboBox con los tipos de cuenta disponibles
	 */
	public void llenarComboBox() {
		ObservableList<Rol> rols = FXCollections.observableArrayList();

		for (Rol rol : Rol.values()) {
			rols.add(rol);
		}
		this.cbxRol.setItems(rols);
	}

	/**
	 * 
	 * Method that 
	 *
	 */
	@FXML
	private void initialize() {
		llenarComboBox();
	}

	/**
	 * 
	 * Method that 
	 *
	 * @return
	 */
	private boolean isInputValid() {
    	String errorMensaje = "";
    	
    	if(txtCreateUser == null || txtCreateUser.getText().length() == 0) {
    			errorMensaje += "The user is not valid!\n";
    	}
    	
    	if(txtPassword == null || txtPassword.getText().length() == 0) {
			errorMensaje += "The password is not valid!\n";
    	}
    	
    	if(cbxRol.getValue() == null || cbxRol.getValue().name().length() == 0) {
			errorMensaje += "The rol is not valid!\n";
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

	/**
	 * 
	 * Method that 
	 *
	 * @param dialogStage
	 */
	public void showDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * 
	 * Method that 
	 *
	 * @param user
	 */
	public void showUser(User user) {
    	this.user = user;
    	
    	txtCreateUser.setText(user.getName());
    	txtPassword.setText(user.getPassword());
    }

	/**
	 * 
	 * Method that 
	 *
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}
}
