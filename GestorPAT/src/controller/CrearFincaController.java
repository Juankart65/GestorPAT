package controller;

import java.io.File;

import application.Aplicacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 * Este es el controlador de la ventana para crear o editar cultivos
 * 
 * @author USER
 *
 */
public class CrearFincaController {

	  @FXML
	    private TextField txtEditDireccionFinca;

	    @FXML
	    private Button btnAceptarFinca;

	    @FXML
	    private Button btnCancelarFinca;
	    
	    @FXML
	    private Button btnAgregarImagen; 

	    @FXML
	    private TextField txtEditNombreFinca;

	    @FXML
	    private TextField txtEditIdFinca;

	    @FXML
	    private ImageView imgPreCreacion;
	    
	    private Aplicacion aplicacion = new Aplicacion();

    
	/**
	 * 
	 */
	private Stage dialogStage;
	private Finca finca;
	private Persona admin = new Persona("", "", 0, CargoPersona.Administrador, 0);
	
	public Persona getAdmin() {
		return admin;
	}

	private boolean okClicked = false;




    @FXML
    void aceptarFincaEvent(ActionEvent event) {
    	if (isInputValid()) {
    		finca.setId(Integer.parseInt(txtEditIdFinca.getText()));
    		finca.setNombre(txtEditNombreFinca.getText());
			finca.setDireccion(txtEditDireccionFinca.getText());
			finca.setImagenFinca(imgPreCreacion.getImage());

			okClicked = true;
			dialogStage.close();
		}
    }

    @FXML
    void cancelarFincaEvent(ActionEvent event) {
    	dialogStage.close();
    }

	/**
	 * Metodo initialize predefinidp
	 */
	@FXML
	private void initialize() {
		// TODO Auto-generated method stub
	}

	/**
	 * Metodo para comprobar que los campos de textos sean correctos
	 * 
	 * @return
	 */
	private boolean isInputValid() {
		String errorMensaje = "";

		if (txtEditIdFinca == null || txtEditIdFinca.getText().length() == 0) {
			errorMensaje += "No es valido el id!\n";
		}

		if (txtEditNombreFinca == null || txtEditNombreFinca.getText().length() == 0) {
			errorMensaje += "No es valido el nombre!\n";
		}

		if (txtEditDireccionFinca == null || txtEditDireccionFinca.getText().length() == 0) {
			errorMensaje += "No es valida la direccion!\n";
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
	 * @param cultivo
	 */
	public void mostrarFinca(Finca finca) {
		this.finca = finca;

		txtEditIdFinca.setText(Integer.toString(finca.getId()));
		txtEditNombreFinca.setText(finca.getNombre());
		txtEditDireccionFinca.setText(finca.getDireccion());
		imgPreCreacion.setImage(finca.getImagenFinca());
	}
	

	/**
	 * Metodo que dice si el boton aceptar fue pulsado
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}
	

    @FXML
    void agregarImagenEvent(ActionEvent event) {

    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar Imagen");

        // Agregar filtros para facilitar la busqueda
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        // Obtener la imagen seleccionada
        File imgFile = fileChooser.showOpenDialog(aplicacion.getPrimaryStage());

        // Mostar la imagen
        if (imgFile != null) {
            Image image = new Image("file:" + imgFile.getAbsolutePath());
            imgPreCreacion.setImage(image);
        }
    }
}
