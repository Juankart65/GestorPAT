package test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Activity;
import model.State;
import model.User;
import model.Process;

class ProcessTest {

	private Process process;
	private Activity activity1;
	private Activity activity2;

	@BeforeEach
	void setUp() {
		// Crear un nuevo proceso y actividades para cada prueba.
		process = new Process("ProcessName", "ProcessDescription", "ProcessId", State.Ready, new User("OwnerName"));

		activity1 = new Activity("Activity1", "ActivityDescription1", "ActivityId1", new User("User1"), State.Ready);
		activity2 = new Activity("Activity2", "ActivityDescription2", "ActivityId2", new User("User2"), State.Blocked);
	}

	@Test
	void testCreateActivity() {
		// Asegurarse de que inicialmente no hay actividades en el proceso.
		assertEquals(0, process.getActivities().getSize());

		// Crear una actividad y verificar que se agregue al proceso.
		process.createActivity(activity1);

		assertEquals(1, process.getActivities().getSize());
		assertTrue(process.getActivities().contains(activity1));
	}
	


	@Test
	void testSettersAndGetters() {
		// Verificar los métodos setters y getters de Process.
		assertEquals("ProcessName", process.getName());
		assertEquals("ProcessDescription", process.getDescription());
		assertEquals("ProcessId", process.getId());
		assertEquals(State.Ready, process.getState());
		assertEquals(new User("OwnerName"), process.getOwner());

		// Modificar valores y verificar los cambios.
		process.setName("NewName");
		process.setDescription("NewDescription");
		process.setId("NewId");
		process.setState(State.Blocked);
		process.setOwner(new User("NewOwner"));

		assertEquals("NewName", process.getName());
		assertEquals("NewDescription", process.getDescription());
		assertEquals("NewId", process.getId());
		assertEquals(State.Blocked, process.getState());
		assertEquals(new User("NewOwner"), process.getOwner());
	}



}
