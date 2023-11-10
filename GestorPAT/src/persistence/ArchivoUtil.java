package persistence;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Duration;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dataStructures.SimpleList;
import model.Activity;
import model.Handler;
import model.Process;
import model.Rol;
import model.State;
import model.Task;
import model.User;

/**
 * Esta clase teine metodo estaticos que permite usarlos sin crear instancias de
 * la clase Lo que se hizo fue crear esta libreria para el manejo de los
 * archivos
 * 
 * @author Admin
 *
 */
public class ArchivoUtil {

	static String fechaSistema = "";

	public static SimpleList<Process> leerProcesosDesdeExcel(String ruta, Handler handler) throws IOException {
		SimpleList<Process> procesos = new SimpleList<Process>();

		FileInputStream inputStream = new FileInputStream(ruta);
		Workbook workbook = new XSSFWorkbook(inputStream);

		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			Sheet sheet = workbook.getSheetAt(i);

			int indexRow = 1;
			Row row;
			while ((row = sheet.getRow(indexRow)) != null) {
				// Leer las celdas de la fila y procesarlas.

				Cell headerCell = row.getCell(0);
				if (headerCell != null && headerCell.getStringCellValue().equals("Activity ID")) {
					break;
				}
				if (row.getPhysicalNumberOfCells() >= 5) {
					// Comprobar si la fila tiene al menos 5 celdas para procesarla como una fila
					// válida.
					String id = row.getCell(0).getStringCellValue();
					String name = row.getCell(1).getStringCellValue();
					String description = row.getCell(2).getStringCellValue();
					State state = null;
					switch (row.getCell(3).getStringCellValue()) {
					case "Ready":
						state = State.Ready;
						break;
					case "Blocked":
						state = State.Blocked;
						break;
					case "Running":
						state = State.Running;
						break;
					case "Exit":
						state = State.Exit;
						break;
					default:
						break;
					}
					String userProcess = row.getCell(4).getStringCellValue();
					User userProcess1 = handler.getUser(userProcess);
					Process proceso = new Process(name, description, id, state, userProcess1);

					// Ahora, verifica si hay actividades y procesa las actividades y tareas.
					int indexRowAct = indexRow + 2;
					Row activityRow;
					while ((activityRow = sheet.getRow(indexRowAct)) != null
							&& activityRow.getPhysicalNumberOfCells() >= 5) {
						String activityId = activityRow.getCell(0).getStringCellValue();
						String activityName = activityRow.getCell(1).getStringCellValue();
						String activityDescription = activityRow.getCell(2).getStringCellValue();
						State activityState = null;
						switch (activityRow.getCell(3).getStringCellValue()) {
						case "Ready":
							activityState = State.Ready;
							break;
						case "Blocked":
							activityState = State.Blocked;
							break;
						case "Running":
							activityState = State.Running;
							break;
						case "Exit":
							activityState = State.Exit;
							break;
						default:
							break;
						}
						String userActivity = activityRow.getCell(4).getStringCellValue();
						User userActivity1 = handler.getUser(userActivity);
						Activity activity = new Activity(activityName, activityDescription, activityId, userActivity1,
								activityState);
						proceso.getActivities().addEnd(activity);

						// Avanzar al siguiente índice de fila.
						indexRowAct++;

						// Verificar y procesar las tareas (si las hay).
						Row taskRow;

						while ((taskRow = sheet.getRow(indexRowAct)) != null) {
							if (taskRow.getPhysicalNumberOfCells() < 7) {
								// La fila no cumple con los requisitos mínimos para ser una fila de tarea, sal
								// del bucle.
								break;
							}
							
						    // Comprueba si esta fila es una tarea y no una actividad.
						    Cell cell0 = taskRow.getCell(0);
						    if (cell0 != null && cell0.getStringCellValue().equals("Task ID")) {
						        // Es un encabezado de tarea, salta al siguiente índice de fila.
						        indexRowAct++;
						        continue;
						    }

							String taskId = taskRow.getCell(0).getStringCellValue();
							String taskName = taskRow.getCell(1).getStringCellValue();
							String taskDescription = taskRow.getCell(2).getStringCellValue();
							State taskState = null;
							switch (taskRow.getCell(3).getStringCellValue()) {
							case "Ready":
								taskState = State.Ready;
								break;
							case "Blocked":
								taskState = State.Blocked;
								break;
							case "Running":
								taskState = State.Running;
								break;
							case "Exit":
								taskState = State.Exit;
								break;
							default:
								break;
							}
							String taskOwner = taskRow.getCell(4).getStringCellValue();
							User taskOwner1 = handler.getUser(taskOwner);
							String taskDuration = taskRow.getCell(5).getStringCellValue();
							Duration duration = Duration.parse(taskDuration);
							boolean isMandatory = "Yes".equalsIgnoreCase(taskRow.getCell(6).getStringCellValue());

							Task task = new Task(taskId, taskDescription, duration, isMandatory, taskState, taskOwner1,
									taskName);
							activity.getTasks().addEnd(task);

							// Avanzar al siguiente índice de fila.
							indexRowAct++;
						}
					}

					procesos.addEnd(proceso);
				}

				// Avanzar al siguiente índice de fila.
				indexRow++;
			}
		}

		workbook.close();
		inputStream.close();

		return procesos;
	}

	public static void saveProcessesInExcel(String ruta, SimpleList<Process> listProcess) throws IOException {
		@SuppressWarnings("resource")
		Workbook workbook = new XSSFWorkbook();

		for (Process process : listProcess) {
			Sheet processSheet = workbook.createSheet(process.getName());

			int rowIndex = 0;
			Row processRow = processSheet.createRow(rowIndex++);
			processRow.createCell(0).setCellValue("ID");
			processRow.createCell(1).setCellValue("Name");
			processRow.createCell(2).setCellValue("Description");
			processRow.createCell(3).setCellValue("State");
			processRow.createCell(4).setCellValue("Owner");

			// Agregar los atributos del proceso
			processRow = processSheet.createRow(rowIndex++);
			processRow.createCell(0).setCellValue(process.getId());
			processRow.createCell(1).setCellValue(process.getName());
			processRow.createCell(2).setCellValue(process.getDescription());
			processRow.createCell(3).setCellValue(process.getState().name());
			processRow.createCell(4).setCellValue(process.getOwner().getName());

			// Encabezado para las actividades
			Row activityHeaderRow = processSheet.createRow(rowIndex++);
			activityHeaderRow.createCell(0).setCellValue("Activity ID");
			activityHeaderRow.createCell(1).setCellValue("Activity Name");
			activityHeaderRow.createCell(2).setCellValue("Activity Description");
			activityHeaderRow.createCell(3).setCellValue("Activity State");
			activityHeaderRow.createCell(4).setCellValue("Activity Owner");

			for (Activity activity : process.getActivities()) {
				Row activityRow = processSheet.createRow(rowIndex++);
				activityRow.createCell(0).setCellValue(activity.getId());
				activityRow.createCell(1).setCellValue(activity.getName());
				activityRow.createCell(2).setCellValue(activity.getDescription());
				activityRow.createCell(3).setCellValue(activity.getState().name());
				activityRow.createCell(4).setCellValue(activity.getOwner().getName());

				// Encabezado para las tareas
				Row taskHeaderRow = processSheet.createRow(rowIndex++);
				taskHeaderRow.createCell(0).setCellValue("Task ID");
				taskHeaderRow.createCell(1).setCellValue("Task Name");
				taskHeaderRow.createCell(2).setCellValue("Task Description");
				taskHeaderRow.createCell(3).setCellValue("Task State");
				taskHeaderRow.createCell(4).setCellValue("Task Owner");
				taskHeaderRow.createCell(5).setCellValue("Task Duration");
				taskHeaderRow.createCell(6).setCellValue("Mandatory");

				for (Task task : activity.getTasks()) {
					Row taskRow = processSheet.createRow(rowIndex++);
					taskRow.createCell(0).setCellValue(task.getId());
					taskRow.createCell(1).setCellValue(task.getName());
					taskRow.createCell(2).setCellValue(task.getDescription());
					taskRow.createCell(3).setCellValue(task.getState().name());
					taskRow.createCell(4).setCellValue(task.getOwner().getName());
					taskRow.createCell(5).setCellValue(task.getDuration().toString());
					taskRow.createCell(6).setCellValue(task.isMandatoryTask() ? "Yes" : "No");
				}
			}
		}

		FileOutputStream outputStream = new FileOutputStream(ruta);
		workbook.write(outputStream);
		outputStream.close();
	}

	public static void saveLogRegister(String mensajeLog, int nivel, String accion, String rutaArchivo) {
		@SuppressWarnings("unused")
		String log = "";
		Logger LOGGER = Logger.getLogger(accion);
		FileHandler fileHandler = null;

		cargarFechaSistema();
		try {
			fileHandler = new FileHandler(rutaArchivo, true);
			fileHandler.setFormatter(new SimpleFormatter());
			LOGGER.addHandler(fileHandler);

			switch (nivel) {
			case 1:
				LOGGER.log(Level.INFO, accion + "," + mensajeLog + "," + fechaSistema);
				break;

			case 2:
				LOGGER.log(Level.WARNING, accion + "," + mensajeLog + "," + fechaSistema);
				break;

			case 3:
				LOGGER.log(Level.SEVERE, accion + "," + mensajeLog + "," + fechaSistema);
				break;

			default:
				break;
			}

		} catch (SecurityException e) {

			LOGGER.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} finally {

			fileHandler.close();
		}

	}

	private static void cargarFechaSistema() {

		String diaN = "";
		String mesN = "";
		@SuppressWarnings("unused")
		String anioN = "";

		Calendar cal1 = Calendar.getInstance();

		int dia = cal1.get(Calendar.DATE);
		int mes = cal1.get(Calendar.MONTH) + 1;
		int anio = cal1.get(Calendar.YEAR);
		@SuppressWarnings("unused")
		int hora = cal1.get(Calendar.HOUR);
		@SuppressWarnings("unused")
		int minuto = cal1.get(Calendar.MINUTE);

		if (dia < 10) {
			diaN += "0" + dia;
		} else {
			diaN += "" + dia;
		}
		if (mes < 10) {
			mesN += "0" + mes;
		} else {
			mesN += "" + mes;
		}

		// fecha_Actual+= a�o+"-"+mesN+"-"+ diaN;
		// fechaSistema = a�o+"-"+mesN+"-"+diaN+"-"+hora+"-"+minuto;
		fechaSistema = anio + "-" + mesN + "-" + diaN;
		// horaFechaSistema = hora+"-"+minuto;
	}

	// ------------------------------------SERIALIZACI�N y XML
	/**
	 * Escribe en el fichero que se le pasa el objeto que se le envia
	 *
	 * @param rutaArchivo path del fichero que se quiere escribir
	 * @throws IOException
	 */

	public static Object cargarRecursoSerializado(String rutaArchivo) throws Exception {
		Object aux = null;
//		Empresa empresa = null;
		ObjectInputStream ois = null;
		try {
			// Se crea un ObjectInputStream
			ois = new ObjectInputStream(new FileInputStream(rutaArchivo));

			aux = ois.readObject();

		} catch (Exception e2) {
			throw e2;
		} finally {
			if (ois != null)
				ois.close();
		}
		return aux;
	}

	public static void salvarRecursoSerializado(String rutaArchivo, Object object) throws Exception {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo));
			oos.writeObject(object);
		} catch (Exception e) {
			throw e;
		} finally {
			if (oos != null)
				oos.close();
		}
	}

	public static Object cargarRecursoSerializadoXML(String rutaArchivo) throws IOException {

		XMLDecoder decodificadorXML;
		Object objetoXML;

		decodificadorXML = new XMLDecoder(new FileInputStream(rutaArchivo));
		objetoXML = decodificadorXML.readObject();
		decodificadorXML.close();
		return objetoXML;

	}

	public static void salvarRecursoSerializadoXML(String rutaArchivo, Object objeto) throws IOException {

		XMLEncoder codificadorXML;

		codificadorXML = new XMLEncoder(new FileOutputStream(rutaArchivo));
		codificadorXML.writeObject(objeto);
		codificadorXML.close();

	}

	/**
	 * Method that
	 *
	 * @param usersFilePath
	 * @param users
	 */
	public static void saveUsersInExcel(String usersFilePath, SimpleList<User> users) {
		// Crear un nuevo libro de trabajo XLSX
		@SuppressWarnings("resource")
		Workbook workbook = new XSSFWorkbook();

		// Crear una hoja en el libro
		Sheet sheet = workbook.createSheet("Users");

		// Crear un encabezado para las columnas
		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("ID");
		headerRow.createCell(1).setCellValue("Name");
		headerRow.createCell(2).setCellValue("Password");
		headerRow.createCell(3).setCellValue("Rol");

		// Llenar el contenido de la hoja con los datos de los usuarios
		for (int i = 0; i < users.getSize(); i++) {
			User user = users.getNodeValue(i);
			Row dataRow = sheet.createRow(i + 1);
			dataRow.createCell(0).setCellValue(user.getId());
			dataRow.createCell(1).setCellValue(user.getName());
			dataRow.createCell(2).setCellValue(user.getPassword());
			dataRow.createCell(3).setCellValue(user.getRol().toString());
		}

		try (FileOutputStream outputStream = new FileOutputStream(usersFilePath)) {
			workbook.write(outputStream);
			System.out.println("Usuarios guardados en Excel correctamente.");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error al guardar usuarios en Excel.");
		}
	}

	public static SimpleList<User> readUsersFromExcel(String usersFilePath) {
		SimpleList<User> users = new SimpleList<User>();

		try (FileInputStream inputStream = new FileInputStream(usersFilePath);
				Workbook workbook = new XSSFWorkbook(inputStream)) {

			Sheet sheet = workbook.getSheetAt(0); // Suponiendo que los usuarios están en la primera hoja del archivo

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				String id = row.getCell(0).getStringCellValue();
				String name = row.getCell(1).getStringCellValue();
				String password = row.getCell(2).getStringCellValue();
				String rol = row.getCell(3).getStringCellValue();
				Rol userRol = Rol.valueOf(rol); // Suponiendo que tienes un enum llamado "Rol"

				User user = new User(id, name, password, userRol);
				users.addEnd(user);
			}

			System.out.println("Usuarios leídos de Excel correctamente.");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error al leer usuarios desde Excel.");
		}

		return users;
	}
}
