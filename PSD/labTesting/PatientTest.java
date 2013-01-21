import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class PatientTest 
{
	private Patient p;
	private int patientBday;
	private String fname,sname,address,malady;

	@Before
	public void setUp() throws Exception 
	{
		patientBday=290267;
		fname="Stuart";
		sname="Bridge";
		address="6 Salcoats Lane";
		malady="yips";
		p=new Patient(fname,sname,patientBday,address,malady);
		
	}

	@After
	public void tearDown() throws Exception 
	{
		p=null;
		patientBday=0;
		fname=null;
		sname=null;
		address=null;
		malady=null;
	}

	@Test
	public void testIncrementAppointments() 
	{
		//check appointments attended is 0 to start
		assertEquals(0,p.getAppointmentsAttended());
		//now increment and check it's 1
		p.incrementAppointments();
		assertEquals(1,p.getAppointmentsAttended());
	}

	@Test
	public void testEqualsPatient() 
	{
		//patients are equal if fname, surname and dob are equal
		//create a different patient
		Patient other=new Patient("Frank","Crawford",31011972,"somewhere place","cold");
		//check not equal
		//assertFalse(p.equals(other));
		//check when same name and dob the patient is equal
		Patient same=new Patient(fname,sname,patientBday,address,"sore back");
		//System.out.print(p + "\n" + same);
		assertTrue(p.equals(same));
	}

}
