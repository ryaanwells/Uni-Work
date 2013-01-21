import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * This class represents the collection of Patient objects
 * @author rose
 *
 */
@SuppressWarnings("serial")
public class AllPatients extends Exception
{
	private ArrayList<Patient> patientList;//a list of patient objects
	private int numPatients;//keeps track of the number of patients on the list

	/*
	 * default constructor initialises class variables
	 */
	public AllPatients()
	{
		patientList=new ArrayList<Patient>();
		numPatients=0;
	}
	
	public void addPatient(Patient p) throws Exception
	{
		if(this.containsPatient(p.getFirstName(),p.getSurname(),p.getDob())>=0){
			throw new Exception("Duplicate Entry");
		}
		patientList.add(p);//add to list
		numPatients++;//increment count of patients
	}
	
	public void addPatient(String fn, String ln, int birthday, String add, String malady) throws Exception
	{
		Patient p= new Patient(fn,ln,birthday,add,malady);//create patient object
		p.setPatientNumber(numPatients);//set the patient number to the current number of patients
		addPatient(p);
	}
	
	
	/*
	 * A method to check if the list contains a patient with the given firstname lastname and dob
	 * returns an integer -1 if not found, or the position in the list
	 */
	public int containsPatient(String fName, String lName, int dob)
	{
		//set up a patient object so we can use equals method of Patient
		Patient other=new Patient();
		other.setFirstName(fName);
		other.setSurname(lName);
		other.setDob(dob);
		
		/*iterate through the list, get each patient, check if equal, return patient object if equal */
		Iterator<Patient> patientIterator=patientList.iterator();
		int currPos=0;//keeps track of the current index/position in the list
		while(patientIterator.hasNext())
		{
			Patient currPatient=patientIterator.next();
			if(currPatient.equals(other))
				return currPos;
			else
				currPos++;//not found, move to next position
			
		}
		//if we get this far, then the patient isn't in the list, return -1
		return -1;
	}

	/*
	 * returns the Patient object at index 'position ' in the patient list
	 */
	public Patient getPatient(int position)
	{
		return patientList.get(position);
	}
	
	/*
	 * deletes the Patient object at index 'position' in the patient list
	 */
	public void removePatient(int position)
	{
		patientList.remove(position);
		numPatients--;//reduce num patients
	}
	
	public int getNumPatients()
	{
		return numPatients;
	}

	public String toString()
	{
		String list="";
		Iterator<Patient> patientIterator=patientList.iterator();
		
		while(patientIterator.hasNext())
		{
			Patient currPatient=patientIterator.next();
			list+=currPatient.toString() + "\n";
			
		}
		return list;
	}
	
}
