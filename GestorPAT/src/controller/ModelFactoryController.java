package controller;

import java.io.IOException;

import dataStructures.SimpleList;
import model.Activity;
import model.Handler;
import model.Process;
import model.Rol;
import model.State;
import model.User;
import persistence.Persistencia;

public class ModelFactoryController {

	Handler handler;

	// ------------------------------ Singleton
	// ------------------------------------------------
	// Clase estatica oculta. Tan solo se instanciara el singleton una vez
	private static class SingletonHolder {
		// El constructor de Singleton puede ser llamado desde aquí al ser protected
		private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
	}

	// Método para obtener la instancia de nuestra clase
	public static ModelFactoryController getInstance() {
		return SingletonHolder.eINSTANCE;
	}

	public ModelFactoryController() {
		
		//1. inicializar datos y luego guardarlo en archivos
//		iniciarSalvarDatosPrueba();

		//2. Cargar los datos de los archivos
		cargarDatosDesdeArchivos();


		//3. Guardar y Cargar el recurso serializable binario
//		guardarResourceBinario();
//		cargarResourceBinario();

		//4. Guardar y Cargar el recurso serializable XML
//		guardarResourceXML();
//		cargarResourceXML();
		
//		 Siempre se debe verificar si la raiz del recurso es null
//		if (handler == null) {
//			System.out.println("es null");
//			inicializarDatos();
//			try {
//				Persistencia.saveProcess(handler.getProcessList());
//				Persistencia.saveUsers(handler.getUserList());
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}
	
	private void cargarDatosDesdeArchivos() {
		
		handler = new Handler();
		try {

			for (User user : Persistencia.loadUsers()) {
				handler.getUserList().addEnd(user);
			}
			for (Process process : Persistencia.loadProcess(handler)) {
				handler.getProcessList().addEnd(process);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void inicializarDatos() {

		handler = new Handler();

		User user = new User("01", "Juan", "123", Rol.Admin);
		handler.getUserList().addEnd(user);
		
		Activity activity = new Activity("Activity 1", "Testing activity 1", "01", user, State.Ready);
		
		Process process1 = new Process("Process 1", "Testing process 1", "01", State.Ready, user);
		process1.getActivities().addEnd(activity);
		handler.getProcessList().addEnd(process1);
		
		
		Process process2 = new Process("Process 2", "Testing process 2", "02", State.Blocked, user);
		handler.getProcessList().addEnd(process2);
		
		Process process3 = new Process("Process 3", "Testing process 3", "03", State.Running, user);
		handler.getProcessList().addEnd(process3);
		
		Process process4 = new Process("Process 4", "Testing process 4", "04", State.Exit, user);
		handler.getProcessList().addEnd(process4);

		System.out.println("Handler started " + handler);
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

//	public void crearEmpleado(String nombre) {
//
//		try {
//			handler.crearEmpleado(nombre);
//			Persistencia.guardarEmpleados(handler.getListaEmpleados());
//			Persistencia.guardaRegistroLog("Se ha creado un empleado", 1, "crearEmpleado");
//			Persistencia.guardarRecursoEmpresaBinario(getHandler());
//		} catch (EmpleadoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

}