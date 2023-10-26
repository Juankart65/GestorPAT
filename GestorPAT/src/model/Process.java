package model;

import java.util.Objects;

import dataStructures.ListaSimple;

public class Process {

	private ListaSimple<Activity> activities = new ListaSimple<Activity>();
	private String name;
	private String description;
	private int id;

	/**
	 * 
	 * Class constructor
	 *
	 * @param activities
	 * @param name
	 * @param description
	 * @param id
	 */
	public Process(ListaSimple<Activity> activities, String name, String description, int id) {
		super();
		this.activities = activities;
		this.name = name;
		this.description = description;
		this.id = id;
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
	 * Getter of name
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter of name
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter of description
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter of description
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter of id
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter of id
	 *
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
