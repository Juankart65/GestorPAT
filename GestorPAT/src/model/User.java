package model;

import java.io.Serializable;
import java.util.Objects;

/**
 * 
 * 
 * @author Juanes Cardona
 * @author Juanes Ramirez
 * @author Jose Taborda
 * 
 */
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String password;
	private Rol rol;

	/**
	 * 
	 * Class constructor
	 *
	 * @param id
	 * @param name
	 * @param password
	 * @param rol
	 */
	public User(String id, String name, String password, Rol rol) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.rol = rol;
	}

	/**
	 * 
	 * Class constructor
	 *
	 * @param name
	 * @param password
	 */
	public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}
	
	

	public User() {
		super();
	}

	/**
	 * Getter of id
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter of id
	 *
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * Getter of user
	 *
	 * @return the user
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter of user
	 *
	 * @param password the user to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter of rol
	 *
	 * @return the rol
	 */
	public Rol getRol() {
		return rol;
	}

	/**
	 * Setter of rol
	 *
	 * @param rol the rol to set
	 */
	public void setRol(Rol rol) {
		this.rol = rol;
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
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}

	/**
	 * @return
	 **/
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", rol=" + rol + "]";
	}

}
