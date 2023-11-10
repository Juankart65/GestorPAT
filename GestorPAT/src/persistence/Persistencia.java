package persistence;

import java.io.IOException;

import dataStructures.SimpleList;
import model.Handler;
import model.Process;
import model.User;

public class Persistencia {

	public static final String PROCESS_FILE_PATH = "src/resources/fileProcess.xlsx";
	public static final String USERS_FILE_PATH = "src/resources/users.xlsx";
	public static final String LOG_FILE_PATH = "src/resources/log.txt";
	public static final String HANDLER_BINARY_FILE_PATH = "src/resources/handler.dat";

	public static void saveProcess(SimpleList<Process> process) throws IOException {

		ArchivoUtil.saveProcessesInExcel(PROCESS_FILE_PATH, process);
	}

	public static void saveUsers(SimpleList<User> users) throws IOException {

		ArchivoUtil.saveUsersInExcel(USERS_FILE_PATH, users);
	}

//    ----------------------LOADS------------------------

	public static SimpleList<Process> loadProcess(Handler handler) throws IOException {
		return ArchivoUtil.leerProcesosDesdeExcel(PROCESS_FILE_PATH, handler);
	}

	public static SimpleList<User> loadUsers() throws IOException {
		return ArchivoUtil.readUsersFromExcel(USERS_FILE_PATH);
	}

	public static void guardaRegistroLog(String mensajeLog, int nivel, String accion) {

		ArchivoUtil.saveLogRegister(mensajeLog, nivel, accion, LOG_FILE_PATH);
	}

//    ----------------------SAVES------------------------

	public static Handler loadResourceHandlerBinary() {

		Handler handler = null;

		try {
			handler = (Handler) ArchivoUtil.cargarRecursoSerializado(HANDLER_BINARY_FILE_PATH);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return handler;
	}

	public static void saveHandlerBinary(Handler handler) {

		try {
			ArchivoUtil.salvarRecursoSerializado(HANDLER_BINARY_FILE_PATH, handler);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}