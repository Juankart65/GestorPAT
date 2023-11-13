package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import model.Activity;
import model.State;
import model.User;

public class ActivityTest {

    private Activity activity1;
    private Activity activity2;
    private User owner;

    @Before
    public void setUp() {
        owner = new User("John Doe", "23456");
        activity1 = new Activity("Activity 1", "Description 1", "1", owner, State.Blocked);
        activity2 = new Activity("Activity 2", "Description 2", "2", owner, State.Exit);
    }

    @Test
    public void testGetters() {
        assertEquals("Activity 1", activity1.getName());
        assertEquals("Description 1", activity1.getDescription());
        assertEquals("1", activity1.getId());
        assertEquals(owner, activity1.getOwner());
        assertEquals(State.Blocked, activity1.getState());
        assertNotNull(activity1.getTasks());
    }

    @Test
    public void testSetters() {
        activity2.setName("New Name");
        activity2.setDescription("New Description");
        activity2.setId("3");
        activity2.setOwner(new User("Jane Doe", "23456"));
        activity2.setState(State.Ready);

        assertEquals("New Name", activity2.getName());
        assertEquals("New Description", activity2.getDescription());
        assertEquals("3", activity2.getId());
        assertEquals(owner, activity2.getOwner());
        assertEquals(State.Ready, activity2.getState());
    }

}