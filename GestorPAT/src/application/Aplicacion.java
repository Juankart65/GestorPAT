package application;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.User;

import java.io.IOException;

import controller.LoginController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * This class represents to
 *
 * @author Juanes Cardona
 */
public class Aplicacion extends Application {

	private Stage primaryStage;
	public static User userActual;

	private ObservableList<User> listaUsuarios = FXCollections.observableArrayList();

	public ObservableList<User> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(ObservableList<User> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	@Override
	public String toString() {
		return "Aplicacion [listaUsuarios=" + listaUsuarios + "]";
	}

//	public boolean verificarUsuario(String user, String pw) {
//		for (User usuario : listaUsuarios) {
//			if (usuario.getUsuario().equals(user) && usuario.getPw().equals(pw)) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public User getUsuario(String user, String pw) {
//		for (User usuario : listaUsuarios) {
//			if (usuario.getUsuario().equals(user) && usuario.getPw().equals(pw)) {
//				return usuario;
//			}
//		}
//		return null;
//	}

	public static User getUserActual() {
		return userActual;
	}

	public static void setUserActual(User userActual) {
		Aplicacion.userActual = userActual;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.initStyle(StageStyle.TRANSPARENT);
		this.primaryStage.centerOnScreen();
		this.primaryStage.setTitle("CAMP");
		mostrarVentanaInicioSesion();
//		getListaUsuarios();

	}

	/**
	 * Obtiene la escenario principal
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Cambia el escenario principal
	 * 
	 * @param primaryStage
	 */
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	/**
	 * Muestra la ventana para que el usuario inicie sesion
	 */
	public void mostrarVentanaInicioSesion() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Aplicacion.class.getResource("../view/LoginView.fxml"));

			AnchorPane rootLayout = (AnchorPane) loader.load();

			LoginController inicioSesionController = loader.getController();
			inicioSesionController.setAplicacion(this);

			Scene scene = new Scene(rootLayout);

			// Establecer el color de relleno del Scene a transparente
			scene.setFill(Color.TRANSPARENT);
			// Agregar el archivo de estilos style.css
			scene.getStylesheets().add(getClass().getResource("../resources/Styles.css").toString());

			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public boolean mostrarVentanaMisFincas(User usuarios) {
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(Aplicacion.class.getResource("../views/MisFincas.fxml"));
//
//			AnchorPane rootLayout = (AnchorPane) loader.load();
//
//			ProcessViewController misFincasController = loader.getController();
//			misFincasController.setAplicacion(this);
//
//			Scene scene = new Scene(rootLayout);
//			
//			// Establecer el color de relleno del Scene a transparente
//	        scene.setFill(Color.TRANSPARENT);
//	        // Agregar el archivo de estilos style.css
//	        scene.getStylesheets().add(getClass().getResource("../resource/Styles.css").toString());
//	        
//			primaryStage.setScene(scene);
//			primaryStage.centerOnScreen();
//			primaryStage.show();
//
//			return misFincasController.isOkClicked();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 * Muestra la ventana principal
//	 * 
//	 * @return
//	 */
//	public boolean mostrarVentanaPrincipal(Finca finca) {
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(Aplicacion.class.getResource("../views/VistaPrincipal.fxml"));
//
//			AnchorPane rootLayout = (AnchorPane) loader.load();
//
//			VistaPrincipalController vistaPrincipalController = loader.getController();
//			vistaPrincipalController.setAplicacion(this);
//			vistaPrincipalController.mostrarDetallesFinca(User.getFincaActual());
//
//			Scene scene = new Scene(rootLayout);
//			
//			// Establecer el color de relleno del Scene a transparente
//	        scene.setFill(Color.TRANSPARENT);
//	        // Agregar el archivo de estilos Styles.css
//	        scene.getStylesheets().add(getClass().getResource("../resource/Styles.css").toString());
//			primaryStage.setScene(scene);
//			primaryStage.centerOnScreen();
//			primaryStage.show();
//
//			return vistaPrincipalController.isOkClicked();
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 * Muestra la ventana para crear o editar cultivos
//	 */
//	public boolean mostrarVentanaEditarCultivos(Cultivo cultivo) {
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(Aplicacion.class.getResource("../views/EditarCrearCultivos.fxml"));
//
//			AnchorPane page = (AnchorPane) loader.load();
//
//			Stage dialogStage = new Stage();
//			dialogStage.setTitle("Editar o Crear Cultivo");
//			dialogStage.initModality(Modality.WINDOW_MODAL);
//			dialogStage.initOwner(primaryStage);
//			dialogStage.initStyle(StageStyle.TRANSPARENT);
//			dialogStage.centerOnScreen();
//			
//			Scene scene = new Scene(page);
//			// Establecer el color de relleno del Scene a transparente
//	        scene.setFill(Color.TRANSPARENT);
//	        // Agregar el archivo de estilos style.css
//	        scene.getStylesheets().add(getClass().getResource("../resource/Styles.css").toString());
//	        
//			dialogStage.setScene(scene);
//
//			ActualizarCultivosController actualizarCultivosController = loader.getController();
//			actualizarCultivosController.mostrarDialogStage(dialogStage);
//			actualizarCultivosController.mostrarCultivo(cultivo);
//
//			dialogStage.showAndWait();
//
//			return actualizarCultivosController.isOkClicked();
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 * Obtiene la ultima ruta que visit� el usuario
//	 * 
//	 * @return
//	 */
//	public File getUserFilePath() {
//		Preferences prefs = Preferences.userNodeForPackage(Aplicacion.class);
//		String filePath = prefs.get("filePath", null);
//		if (filePath != null) {
//			return new File(filePath);
//		} else {
//			return null;
//		}
//	}
//
//	/**
//	 * 
//	 * @param file
//	 */
//	public void setUserFilePath(File file) {
//		Preferences prefs = Preferences.userNodeForPackage(Aplicacion.class);
//		if (file != null) {
//			prefs.put("filePath", file.getPath());
//
//			primaryStage.setTitle("CampApp - " + file.getName());
//		} else {
//			prefs.remove("filePath");
//
//			primaryStage.setTitle("CamApp");
//		}
//	}

	/**
	 * Metodo principal del proyecto
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Aplicacion.launch(new String[0]);
	}

//	/**
//	 * Muestra la ventana para crear o editar personas
//	 * 
//	 * @param persona
//	 * @return
//	 */
//	public boolean mostrarVentanaEditarPersonas(Persona persona) {
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(Aplicacion.class.getResource("../views/EditarCrearTrabajadores.fxml"));
//
//			AnchorPane page = (AnchorPane) loader.load();
//
//			Stage dialogStage = new Stage();
//			dialogStage.setTitle("Editar o Crear Personal");
//			dialogStage.initModality(Modality.WINDOW_MODAL);
//			dialogStage.initOwner(primaryStage);
//			dialogStage.initStyle(StageStyle.TRANSPARENT);
//			dialogStage.centerOnScreen();
//
//			Scene scene = new Scene(page);
//			// Establecer el color de relleno del Scene a transparente
//	        scene.setFill(Color.TRANSPARENT);
//	        // Agregar el archivo de estilos style.css
//	        scene.getStylesheets().add(getClass().getResource("../resource/Styles.css").toString());
//			dialogStage.setScene(scene);
//
//			EditarCrearTrabajadoresController editarCrearTrabajadoresController = loader.getController();
//			editarCrearTrabajadoresController.mostrarDialogStage(dialogStage);
//			editarCrearTrabajadoresController.mostrarPersona(persona);
//
//			dialogStage.showAndWait();
//
//			return editarCrearTrabajadoresController.isOkClicked();
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 * Muestra la ventana para registrar un usuario
//	 * 
//	 * @param user
//	 * @return
//	 */
//	public boolean mostrarVentanaRegistrarUsuario(User user) {
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(Aplicacion.class.getResource("../views/RegistrarUsuario.fxml"));
//
//			AnchorPane page = (AnchorPane) loader.load();
//
//			Stage dialogStage = new Stage();
//			dialogStage.setTitle("Registrar Usuario");
//			dialogStage.initModality(Modality.WINDOW_MODAL);
//			dialogStage.initOwner(primaryStage);
//			dialogStage.initStyle(StageStyle.TRANSPARENT);
//			dialogStage.centerOnScreen();
//
//			Scene scene = new Scene(page);
//			// Establecer el color de relleno del Scene a transparente
//	        scene.setFill(Color.TRANSPARENT);
//	        // Agregar el archivo de estilos style.css
//	        scene.getStylesheets().add(getClass().getResource("../resource/Styles.css").toString());
//			dialogStage.setScene(scene);
//
//			RegistrarUsuarioController registrarUsuarioController = loader.getController();
//			registrarUsuarioController.mostrarDialogStage(dialogStage);
//			registrarUsuarioController.mostrarUser(user);
//
//			dialogStage.showAndWait();
//
//			return registrarUsuarioController.isOkClicked();
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	public boolean mostrarVentanaCrearFinca(Finca finca) {
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(Aplicacion.class.getResource("../views/CrearFinca.fxml"));
//
//			AnchorPane page = (AnchorPane) loader.load();
//
//			Stage dialogStage = new Stage();
//			dialogStage.setTitle("Crear Finca");
//			dialogStage.initModality(Modality.WINDOW_MODAL);
//			dialogStage.initOwner(primaryStage);
//			dialogStage.initStyle(StageStyle.TRANSPARENT);
//			dialogStage.centerOnScreen();
//
//			Scene scene = new Scene(page);
//			// Establecer el color de relleno del Scene a transparente
//	        scene.setFill(Color.TRANSPARENT);
//	        // Agregar el archivo de estilos style.css
//	        scene.getStylesheets().add(getClass().getResource("../resource/Styles.css").toString());
//			dialogStage.setScene(scene);
//
//			CrearFincaController crearFincaController = loader.getController();
//			crearFincaController.mostrarDialogStage(dialogStage);
//			crearFincaController.mostrarFinca(finca);
//
//			dialogStage.showAndWait();
//
//			return crearFincaController.isOkClicked();
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	public boolean mostrarVentanaEditarLabor(Labor labor) {
//
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(Aplicacion.class.getResource("../views/EditarCrearLabores.fxml"));
//
//			AnchorPane page = (AnchorPane) loader.load();
//
//			Stage dialogStage = new Stage();
//			dialogStage.setTitle("Registrar Usuario");
//			dialogStage.initModality(Modality.WINDOW_MODAL);
//			dialogStage.initOwner(primaryStage);
//			dialogStage.initStyle(StageStyle.TRANSPARENT);
//			dialogStage.centerOnScreen();
//
//			Scene scene = new Scene(page);
//			// Establecer el color de relleno del Scene a transparente
//	        scene.setFill(Color.TRANSPARENT);
//	        // Agregar el archivo de estilos style.css
//	        scene.getStylesheets().add(getClass().getResource("../resource/Styles.css").toString());
//			dialogStage.setScene(scene);
//
//			EditarCrearLaboresController editarCrearLaboresController = loader.getController();
//			editarCrearLaboresController.mostrarDialogStage(dialogStage);
//			editarCrearLaboresController.mostrarLabor(labor);
//
//			dialogStage.showAndWait();
//
//			return editarCrearLaboresController.isOkClicked();
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
//	}

}
