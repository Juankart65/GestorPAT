package persistence;

import java.io.IOException;

import dataStructures.SimpleList;
import model.Handler;
import model.Process;
import model.User;

public class Persistencia {

	public static final String PROCESS_FILE_PATH = "src/td/fileProcess.xlsx";
	public static final String USERS_FILE_PATH = "src/td/users.xlsx";
	public static final String LOG_FILE_PATH = "src/td/log.txt";

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


}