package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import application.App;
import dataStructures.SimpleList;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Process implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private SimpleList<Activity> activities = new SimpleList<Activity>();
	private transient StringProperty name;
	private transient StringProperty description;
	private transient StringProperty id;
	private transient ObjectProperty<State> state;
	private User owner;
	private static Activity currentActivity;

	/**
	 * 
	 * Class constructor
	 *
	 * @param activities
	 * @param name
	 * @param description
	 * @param id
	 */
	public Process(String name, String description, String id, State state, User owner) {
		super();
		this.name = new SimpleStringProperty(name);
		this.description = new SimpleStringProperty(description);
		this.id = new SimpleStringProperty(id);
		this.state = new SimpleObjectProperty<State>(state);
		this.owner = owner;
		getActivities();
	}

	public Process() {
		super();
	}

	/**
	 * Getter of currentActivity
	 *
	 * @return the currentActivity
	 */
	public static Activity getCurrentActivity() {
		return currentActivity;
	}

	/**
	 * Setter of currentActivity
	 *
	 * @param currentActivity the currentActivity to set
	 */
	public static void setCurrentActivity(Activity currentActivity) {
		Process.currentActivity = currentActivity;
	}

	/**
	 * Getter of activities
	 *
	 * @return the activities
	 */
	public SimpleList<Activity> getActivities() {
		if (activities == null) {
			activities = new SimpleList<Activity>();
		}
		return activities;
	}

	/**
	 * Setter of activities
	 *
	 * @param activities the activities to set
	 */
	public void setActivities(SimpleList<Activity> activities) {
		this.activities = activities;
	}

	public ObjectProperty<State> stateProperty() {
		return state;
	}

	/**
	 * Getter of state
	 *
	 * @return the state
	 */
	public State getState() {
		return state.get();
	}

	/**
	 * Setter of state
	 *
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state.set(state);
	}

	/**
	 * Getter of owner
	 *
	 * @return the owner
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * Setter of owner
	 *
	 * @param owner the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * 
	 * @return
	 */
	public StringProperty nameProperty() {
		return name;
	}

	/**
	 * Getter of name
	 *
	 * @return the name
	 */
	public String getName() {
		return name.get();
	}

	/**
	 * Setter of name
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name.set(name);
	}

	/**
	 * 
	 * @return
	 */
	public StringProperty descriptionProperty() {
		return description;
	}

	/**
	 * Getter of description
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description.get();
	}

	/**
	 * Setter of description
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description.set(description);
	}

	/**
	 * 
	 * @return
	 */
	public StringProperty idProperty() {
		return id;
	}

	/**
	 * Getter of id
	 *
	 * @return the id
	 */
	public String getId() {
		return id.get();
	}

	/**
	 * Setter of id
	 *
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id.set(id);
	}

	/**
	 * @return
	 **/
	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	/**
	 * @param obj
	 * @return
	 **/
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Process other = (Process) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

	/**
	 * @return
	 **/
	@Override
	public String toString() {
		return "Process [activities=" + activities + ", name=" + name + ", description=" + description + ", id=" + id
				+ "]";
	}

	/**
	 * Method that
	 *
	 * @param activity
	 */
	public void createActivity(Activity activity) {
		App.currentProcess.getActivities().addEnd(activity);

	}

	public String getDuration() {

		Duration duration = Duration.ZERO;
		Duration durationSuma = Duration.ZERO;
		for (Activity activity : getActivities()) {
			for (Task task : activity.getTasks()) {
				duration = task.getDuration();
				durationSuma = durationSuma.plus(duration);
			}
		}

		return formatDuration(durationSuma);
	}

	// Método para formatear la duración en horas:minutos:segundos
	private static String formatDuration(Duration duration) {
		long hours = duration.toHours();
		long minutes = duration.minusHours(hours).toMinutes();
		long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();

		// Utilizar String.format para formatear la duración
		return String.format("%d:%02d:%02d", hours, minutes, seconds);
	}
}
