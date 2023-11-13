package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Duration;
import java.util.Objects;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task {

	private transient StringProperty description;
	private transient ObjectProperty<Duration> duration;
	private transient BooleanProperty mandatoryTask;
	private transient StringProperty id;
	private transient StringProperty name;
	private transient ObjectProperty<State> state;
	private User owner;

	/**
	 * Class constructor
	 * 
	 * @param description
	 * @param duration
	 * @param mandatoryTask
	 */
	public Task(String id, String description, Duration duration, boolean mandatoryTask, State state, User owner,
			String name) {
		super();
		this.id = new SimpleStringProperty(id);
		this.description = new SimpleStringProperty(description);
		this.duration = new SimpleObjectProperty<Duration>(duration);
		this.mandatoryTask = new SimpleBooleanProperty(mandatoryTask);
		this.state = new SimpleObjectProperty<State>(state);
		this.owner = owner;
		this.name = new SimpleStringProperty(name);

	}

	public Task() {
		super();
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

	public ObjectProperty<Duration> durationProperty() {
		return duration;
	}

	/**
	 * Getter of duration
	 *
	 * @return the duration
	 */
	public Duration getDuration() {
		return duration.get();
	}

	/**
	 * Setter of duration
	 *
	 * @param duration the duration to set
	 */
	public void setDuration(Duration duration) {
		this.duration.set(duration);
	}

	public BooleanProperty mandatoryProperty() {
		return mandatoryTask;
	}

	/**
	 * Getter of mandatoryTask
	 *
	 * @return the mandatoryTask
	 */
	public boolean isMandatoryTask() {
		return mandatoryTask.get();
	}

	/**
	 * Setter of mandatoryTask
	 *
	 * @param mandatoryTask the mandatoryTask to set
	 */
	public void setMandatoryTask(boolean mandatoryTask) {
		this.mandatoryTask.set(mandatoryTask);
	}

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
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	/**
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		return id == other.id;
	}

	/**
	 * @return
	 **/
	@Override
	public String toString() {
		return "Task [description=" + description + ", duration=" + duration + ", mandatoryTask=" + mandatoryTask
				+ ", id=" + id + "]";
	}

	public StringProperty formattedDurationProperty() {
		StringProperty formattedDurationProperty = new SimpleStringProperty();

		// Vincular la propiedad formateada a la propiedad de duración
		formattedDurationProperty.bind(Bindings.createStringBinding(() -> formatDuration(duration.get()), duration));

		return formattedDurationProperty;
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
