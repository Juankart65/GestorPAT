package model;

import java.time.Duration;
import java.util.Objects;

public class Task {

	private String description;
	private Duration duration;
	private boolean mandatoryTask;
	private int id;

	/**
	 * Class constructor
	 * 
	 * @param description
	 * @param duration
	 * @param mandatoryTask
	 */
	public Task(int id, String description, Duration duration, boolean mandatoryTask) {
		super();
		this.id = id;
		this.description = description;
		this.duration = duration;
		this.mandatoryTask = mandatoryTask;
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
	 * Getter of duration
	 *
	 * @return the duration
	 */
	public Duration getDuration() {
		return duration;
	}

	/**
	 * Setter of duration
	 *
	 * @param duration the duration to set
	 */
	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	/**
	 * Getter of mandatoryTask
	 *
	 * @return the mandatoryTask
	 */
	public boolean isMandatoryTask() {
		return mandatoryTask;
	}

	/**
	 * Setter of mandatoryTask
	 *
	 * @param mandatoryTask the mandatoryTask to set
	 */
	public void setMandatoryTask(boolean mandatoryTask) {
		this.mandatoryTask = mandatoryTask;
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

}
