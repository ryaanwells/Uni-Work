import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class AppointmentTest 
{
	private Appointment nextAppointment;
	private Date appDate;
	private String appDr;
	private Patient appPatient;
	
	@Before
	public void setUp() throws Exception 
	{
		 appDate=new Date(23012013);
		 appDr="Dr Nick";
		 appPatient=new Patient();
		nextAppointment=new Appointment(appDate,appDr,appPatient);
	
		 
	}

	@After
	public void tearDown() throws Exception 
	{
		nextAppointment=null;
		 appDate=null;
		 appDr=null;
		 appPatient=null;
	}

	@Test
	public void testEqualsAppointment() 
	{
		//test equals for same appointment
		assertEquals("",nextAppointment,nextAppointment);
		//create new appointment with same patient, different doctor and date
		//a date with doctor who, isn't that a nice thought?
		Appointment otherAppointment=new Appointment(new Date(1022013),"Dr Who",appPatient);
		assertFalse(nextAppointment.equals(otherAppointment));
		
	}

	@Test
	public void testGetAppointmentDate() 
	{
		//check the appointment date for nextAppointment is as expected
		assertEquals(appDate,nextAppointment.getAppointmentDate());
	}

}
