package model;

import java.util.Objects;

import dataStructures.DoubleCircularList;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Activity {

	private DoubleCircularList<Task> tasks = new DoubleCircularList<Task>();
	private StringProperty name;
	private StringProperty description;
	private StringProperty id;
	private ObjectProperty<State> state;
	private User owner;

	/**
	 * 
	 * Class constructor
	 *
	 * @param tasks
	 * @param name
	 * @param description
	 * @param id
	 */
	public Activity(DoubleCircularList<Task> tasks, String name, String description, String id, User owner, State state) {
		super();
		this.tasks = tasks;
		this.name = new SimpleStringProperty(name);
		this.description = new SimpleStringProperty(description);
		this.id = new SimpleStringProperty(id);
		this.owner = owner;
		this.state = new SimpleObjectProperty<State>(state);
	}
	
	/**
	 * 
	 * Class constructor
	 *
	 * @param tasks
	 * @param name
	 * @param description
	 * @param id
	 */
	public Activity(String name, String description, String id, User owner, State state) {
		super();
		this.name = new SimpleStringProperty(name);
		this.description = new SimpleStringProperty(description);
		this.id = new SimpleStringProperty(id);
		this.owner = owner;
		this.state = new SimpleObjectProperty<State>(state);
	}
	
	public ObjectProperty<State> stateProperty(){
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
	 * @param object the state to set
	 */
	public void setState(Object object) {
		this.state.set((State) object);
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
	 * Getter of tasks
	 *
	 * @return the tasks
	 */
	public DoubleCircularList<Task> getTasks() {
	    if (tasks == null) {
	        tasks = new DoubleCircularList<Task>();
	    }
	    return tasks;
	}

	/**
	 * Setter of tasks
	 *
	 * @param tasks the tasks to set
	 */
	public void setTasks(DoubleCircularList<Task> tasks) {
		this.tasks = tasks;
	}

	/**
	 * 
	 * Method that
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
		;
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
		;
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
		;
	}

	/**
	 * @return
	 **/
	@Override
	public int hashCode() {
		return Objects.hash(id);
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
		Activity other = (Activity) obj;
		return id == other.id;
	}

	/**
	 * @return
	 **/
	@Override
	public String toString() {
		return "Activity [tasks=" + tasks + ", name=" + name + ", description=" + description + ", id=" + id + "]";
	}

}
