package controller;

import java.io.IOException;
import java.time.Duration;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

import application.App;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.Activity;
import model.Handler;
import model.Process;
import model.Rol;
import model.State;
import model.Task;
import persistence.Persistencia;

public class TasksViewController {

	// Attribute declaration

	@FXML
	private Label txtDescriptionActivity;

	@FXML
	private Button btnBack;

	@FXML
	private Label txtIdActivity;

	@FXML
	private TextField txtSearchTask;

	@FXML
	private Button btnVisualizeTask;

	@FXML
	private Button btnCreateTask;

	@FXML
	private Button btnDeleteTask;

	@FXML
	private Label txtOwnerActivity;

	@FXML
	private TableView<Task> taskTable;

	@FXML
	private Button btnUpdateTask;

	@FXML
	private Button btnRefresh;

	@FXML
	private Button btnMoveUp;

	@FXML
	private Button btnMoveDown;

	@FXML
	private Button btnSearchTask;

	@FXML
	private TableColumn<Task, String> idCol;

	@FXML
	private TableColumn<Task, String> nameCol;

	@FXML
	private TableColumn<Task, State> stateCol;

	@FXML
	private TableColumn<Task, String> durationCol;

	@FXML
	private TableColumn<Task, Boolean> mandatoryCol;

	@FXML
	private Label txtNameActivity;

	private ObservableList<Task> allTask;

	private App app;
	private boolean okClicked = false;

	@FXML
	void refreshEvent(ActionEvent event) {
		app.showActivitiesView(Process.getCurrentActivity());
	}

	@FXML
	void backEvent(ActionEvent event) {
		app.showProcessView(App.getCurrentProcess());
	}

	@FXML
	void createTaskEvent(ActionEvent event) {
		Task tempTask = new Task(Handler.generateRandomIdAsString(), null, null, okClicked, null, null, null);
		boolean okClicked = app.showCreateTask(tempTask);

		if (okClicked) {
			ModelFactoryController.getInstance().createTask(tempTask);
			Process.getCurrentActivity().getTasks().addEnd(tempTask);
			taskTable.getItems().add(tempTask);
		}
	}

	@FXML
	void updateTaskEvent(ActionEvent event) {
		Task selectTask = taskTable.getSelectionModel().getSelectedItem();
		if (selectTask != null) {
			@SuppressWarnings("unused")
			boolean okClicked = app.showCreateTask(selectTask);
			try {
				Persistencia.saveProcess(ModelFactoryController.getInstance().getHandler().getProcessList());
				Persistencia.guardaRegistroLog("The task was updated", 1, "updateTask");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(app.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("You did not select a process");
			alert.setContentText("Please select a process in the table");

			alert.showAndWait();
		}
	}

	@FXML
	void deleteTaskEvent(ActionEvent event) {
		int selectedIndex = taskTable.getSelectionModel().getSelectedIndex();

		if (selectedIndex >= 0) {
			taskTable.getItems().remove(selectedIndex);
			try {
				Process.getCurrentActivity().getTasks().deleteNode(selectedIndex);
				Persistencia.saveProcess(ModelFactoryController.getInstance().getHandler().getProcessList());
				Persistencia.guardaRegistroLog("The task was deleted", 1, "deleteTask");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// Nada seleccionado

			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(app.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("You did not select a process");
			alert.setContentText("Please select a process in the table");

			alert.showAndWait();
		}
	}

	@FXML
	void moveTaskUpEvent(ActionEvent event) {
		Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
		if (selectedTask != null) {
			int selectedIndex = taskTable.getSelectionModel().getSelectedIndex();
			if (selectedIndex > 0) {
				// Obtén la tarea que está arriba de la tarea seleccionada.
				Task taskAbove = taskTable.getItems().get(selectedIndex - 1);

				// Intercambia las tareas en la lista.
				taskTable.getItems().set(selectedIndex - 1, selectedTask);
				taskTable.getItems().set(selectedIndex, taskAbove);

				// Intercambia las tareas en la lista de tareas de la actividad.
				Process.getCurrentActivity().getTasks().moveUp(selectedIndex);
				try {
					Persistencia.saveProcess(ModelFactoryController.getInstance().getHandler().getProcessList());
					Persistencia.guardaRegistroLog("Task " + Process.getCurrentActivity().getTasks()
							.getNode(selectedIndex - 1).getValorNodo().getName() + " changed position", 1, "moveUp");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@FXML
	void moveTaskDownEvent(ActionEvent event) {
		Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
		if (selectedTask != null) {
			int selectedIndex = taskTable.getSelectionModel().getSelectedIndex();
			if (selectedIndex < taskTable.getItems().size() - 1) {
				// Obtén la tarea que está abajo de la tarea seleccionada.
				Task taskBelow = taskTable.getItems().get(selectedIndex + 1);

				// Intercambia las tareas en la lista.
				taskTable.getItems().set(selectedIndex + 1, selectedTask);
				taskTable.getItems().set(selectedIndex, taskBelow);

				System.out.println(Process.getCurrentActivity().getTasks().iterator());
				// Intercambia las tareas en la lista de tareas de la actividad.
				Process.getCurrentActivity().getTasks().moveDown(selectedIndex);
				System.out.println(Process.getCurrentActivity().getTasks().iterator());
				try {
					Persistencia.saveProcess(ModelFactoryController.getInstance().getHandler().getProcessList());
					Persistencia.guardaRegistroLog("Task "
							+ Process.getCurrentActivity().getTasks().getNode(selectedIndex).getValorNodo().getName()
							+ " changed position", 1, "moveDown");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Method that
	 *
	 * @param app
	 */
	public void setAplicacion(App app) {
		this.app = app;
	}

	@FXML
	void initialize() {

		// Activity table
		idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		stateCol.setCellValueFactory(cellData -> cellData.getValue().stateProperty());
		durationCol.setCellValueFactory(cellData -> cellData.getValue().formattedDurationProperty());
		mandatoryCol.setCellValueFactory(cellData -> cellData.getValue().mandatoryProperty());

		for (Task task : ModelFactoryController.getInstance().tasks()) {
			if (task != null) {
				taskTable.getItems().add(task);
			}
		}

		// Obtener la lista de actividades completa y almacenarla en la variable
		allTask = FXCollections.observableArrayList(
				Process.getCurrentActivity().getTasks().convertArraylist(Process.getCurrentActivity().getTasks()));

		// Configurar la tabla con la lista completa de actividades
		taskTable.setItems(allTask);

		if (App.getCurrentUser().getRol().equals(Rol.User)) {
			btnCreateTask.setDisable(true);
			btnUpdateTask.setDisable(true);
			btnDeleteTask.setDisable(true);
		}

	}

	/**
	 * Method that
	 *
	 * @param currentProcess
	 */
	public void showDetailActivity(Activity activity) {
		txtIdActivity.setText(activity.getId());
		txtNameActivity.setText(activity.getName());
		txtDescriptionActivity.setText(activity.getDescription());
		txtOwnerActivity.setText(activity.getOwner().getName());
	}

	/**
	 * 
	 * Method that tells if the accept button was pressed
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	void searchTask(ActionEvent event) {
		// Obtener el texto de búsqueda
		String searchText = txtSearchTask.getText();

		// Filtrar la lista de actividades
		ObservableList<Task> fliteredTask;

		if (searchText.isEmpty()) {
			// Si el campo de búsqueda está vacío, mostrar todas las actividades
			fliteredTask = allTask;
		} else {
			// Filtrar las actividades por nombre
			fliteredTask = allTask.filtered(task -> task.getName().contains(searchText));
		}

		// Actualizar la tabla con las actividades filtradas o todas las actividades
		taskTable.setItems(fliteredTask);
	}

}
