package model;

import java.util.Objects;

import dataStructures.ListaSimple;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Process {

	private ListaSimple<Activity> activities = new ListaSimple<Activity>();
	private StringProperty name;
	private StringProperty description;
	private StringProperty id;
	private State state;
	private User owner;

	/**
	 * 
	 * Class constructor
	 *
	 * @param activities
	 * @param name
	 * @param description
	 * @param id
	 */
	public Process(ListaSimple<Activity> activities, String name, String description, String id) {
		super();
		this.activities = activities;
		this.name = new SimpleStringProperty(name);
		this.description = new SimpleStringProperty(description);
		this.id = new SimpleStringProperty(id);
	}

	/**
	 * Getter of activities
	 *
	 * @return the activities
	 */
	public ListaSimple<Activity> getActivities() {
		return activities;
	}

	/**
	 * Setter of activities
	 *
	 * @param activities the activities to set
	 */
	public void setActivities(ListaSimple<Activity> activities) {
		this.activities = activities;
	}

	/**
	 * Getter of state
	 *
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * Setter of state
	 *
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
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
		Process other = (Process) obj;
		return id == other.id;
	}

	/**
	 * @return
	 **/
	@Override
	public String toString() {
		return "Process [activities=" + activities + ", name=" + name + ", description=" + description + ", id=" + id
				+ "]";
	}

}
