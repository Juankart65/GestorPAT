package persistence;

import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import dataStructures.SimpleList;
import model.Handler;
import model.Process;
import model.User;




public class Persistencia {

    public static final String PROCESS_FILE_PATH = "src/resources/fileProcess.xlsx";
    public static final String USERS_FILE_PATH = "src/resources/users.xlsx";
    public static final String LOG_FILE_PATH = "src/resources/log.xml";


   
   
   
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
   
   
    public static void guardaRegistroLog(String mensajeLog, int nivel, String accion)
    {
       
        ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, LOG_FILE_PATH);
    }


   

//    ----------------------SAVES------------------------

//	public static Empresa cargarRecursoEmpresaBinario() {
//		
//		Empresa empresa = null;
//		
//		try {
//			empresa = (Empresa)ArchivoUtil.cargarRecursoSerializado(RUTA_ARCHIVO_MODELO_BANCO_BINARIO);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return empresa;
//	}
//	
//	public static void guardarRecursoEmpresaBinario(Empresa empresa) {
//		
//		try {
//			ArchivoUtil.salvarRecursoSerializado(RUTA_ARCHIVO_MODELO_BANCO_BINARIO, empresa);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}