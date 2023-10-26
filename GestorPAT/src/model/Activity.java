package model;

import java.util.Objects;

import dataStructures.ListaSimple;

public class Activity {

	private ListaSimple<Task> tasks = new ListaSimple<Task>();
	private String name;
	private String description;
	private int id;

	/**
	 * 
	 * Class constructor
	 *
	 * @param tasks
	 * @param name
	 * @param description
	 * @param id
	 */
	public Activity(ListaSimple<Task> tasks, String name, String description, int id) {
		super();
		this.tasks = tasks;
		this.name = name;
		this.description = description;
		this.id = id;
	}

	/**
	 * Getter of tasks
	 *
	 * @return the tasks
	 */
	public ListaSimple<Task> getTasks() {
		return tasks;
	}

	/**
	 * Setter of tasks
	 *
	 * @param tasks the tasks to set
	 */
	public void setTasks(ListaSimple<Task> tasks) {
		this.tasks = tasks;
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
