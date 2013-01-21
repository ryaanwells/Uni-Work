import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/*
 * some basic non-exhaustive tests for the AllPatients class
 */
public class AllPatientsTest 
{
	private AllPatients patientList;

	@Before
	public void setUp() throws Exception 
	{
		patientList=new AllPatients();
	}

	@After
	public void tearDown() throws Exception 
	{
		patientList=null;
	}

	@Test
	public void testAddPatientPatient() throws Exception 
	{
		//tests happy day add where patient doesn't exist
		Patient myPatient=new Patient("Rosanne","English",1011984,"Somewhere in Glasgow","Pulled muscle");
		int contains=patientList.containsPatient("Rosanne", "English", 1011984);//first check patient
		//isn't in list, should be -1, parameters message, expected then actual
		assertEquals("",-1,contains);
		//now add
		patientList.addPatient(myPatient);
		//check it's in the list now
		contains=patientList.containsPatient("Rosanne", "English", 1011984);//first check patient
		//is in list, should be 0, parameters message, expected then actual
		assertEquals("",0,contains);
	}

	@Test
	public void testAddPatientStringStringDateStringString() throws Exception 
	{
		int contains=patientList.containsPatient("Rosanne", "English", 1011984);//first check patient
		//isn't in list, should be -1, parameters message, expected then actual
		assertEquals("",-1,contains);
		//now add
		patientList.addPatient("Rosanne","English",1011984,"Somewhere in Glasgow","Pulled muscle");
		//check it's in the list now
		contains=patientList.containsPatient("Rosanne", "English", 1011984);//first check patient
		//is in list, should be 0, parameters message, expected then actual
		assertEquals("",0,contains);
		//check list is of length 1
		assertEquals("",1,patientList.getNumPatients());
	}

	@Test
	public void testContainsPaitent() throws Exception 
	{
		int contains=patientList.containsPatient("Rosanne", "English", 1011984);//first check patient
		//isn't in list, should be -1, parameters message, expected then actual
		assertEquals("",-1,contains);
		//now add
		patientList.addPatient("Rosanne","English",1011984,"Somewhere in Glasgow","Pulled muscle");
		//check it's in the list now
		contains=patientList.containsPatient("Rosanne", "English", 1011984);//first check patient
		//is in list, should be 0, parameters message, expected then actual
		assertEquals("",0,contains);
	}

	@Test
	public void testGetPatient() throws Exception 
	{
		Patient lois=new Patient("Lois", "Griffin", 170755, "39 Spooner St", "Flu");
		//add a few patients to the list, then get the patient at position 2
		patientList.addPatient("Peter", "Griffin", 20354, "39 Spooner St", "High blood pressure");
		patientList.addPatient("Meg", "Griffin", 131186, "39 Spooner St", "Short sighted");
		patientList.addPatient(lois);
		patientList.addPatient("Chris", "Griffin", 240983, "39 Spooner St", "Low IQ");
		
		Patient atPos2=patientList.getPatient(2);
		assertEquals("",lois,atPos2);
		
	}

	@Test
	public void testRemovePatient() throws Exception 
	{
		//add a few patients to the list
				patientList.addPatient("Peter", "Griffin", 20354, "39 Spooner St", "High blood pressure");
				patientList.addPatient("Meg", "Griffin", 131186, "39 Spooner St", "Short sighted");
				patientList.addPatient("Lois", "Griffin", 170755, "39 Spooner St", "Flu");
				patientList.addPatient("Chris", "Griffin", 240983, "39 Spooner St", "Low IQ");
		//check num patients in list is 4
				//check now only three in list
				int numPatients=patientList.getNumPatients();
				assertEquals("",4,numPatients);
		//remove lois
				patientList.removePatient(2);
		//check now only three in list
				numPatients=patientList.getNumPatients();
				assertEquals("",3,numPatients);
		//check lois isn't in list, contains should return -1
				assertEquals("",-1,patientList.containsPatient("Lois", "Griffin", 170755));
	}
	
	/*
	 * This test should test the non-functional requirement that adding a new Patient
	 * to the system should take less than 5 milliseconds
	 */
	@Test
	public void testTimeToAdd() throws Exception
	{	
		long start = System.currentTimeMillis();
		Patient myPatient=new Patient("Peter", "Griffin", 20354, "39 Spooner St", "High blood pressure");
		patientList.addPatient(myPatient);
		long elapsed = System.currentTimeMillis()-start;
		assertEquals(elapsed<5,true);
	}
	
	@Test(expected=Exception.class)
	public void testDuplicateEntry() throws Exception
	{
		Patient myPatient=new Patient("Peter", "Griffin", 20354, "39 Spooner St", "High blood pressure");
		patientList.addPatient(myPatient);
		patientList.addPatient(myPatient);
	}
	

}
