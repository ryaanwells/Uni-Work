import java.util.Date;

/**
 * 
 * @author rose
 *
 */

public class Patient 
{
	private String firstName;
	private String surname;
	private int dob;
	private String address;
	private String illness;
	private int patientNum;
	private int appointmentsAttended;//the number of appointments this patient has had since time immemorial
	
	/*default constructor, intialise class variables*/
	public Patient()
	{
		firstName="Jane";
		surname="Doe";
		dob=10170;
		address="31 Spooner Street, Quahog, Rhode Island";
		illness="stupidity";
		patientNum=0;
		appointmentsAttended=0;
	}
	
	public Patient(String fn, String ln, int birthday, String add, String malady)
	{
		firstName=fn;
		surname=ln;
		dob=birthday;
		address=add;
		illness=malady;
		appointmentsAttended=0;
	}
	

	/*
	 * patient number set method to be used by collection when a new patient is added
	 */
	public void setPatientNumber(int number)
	{
		patientNum=number;
	}
	
	/*increment appointments*/

	public void incrementAppointments()
	{
		appointmentsAttended++;
	}
	
	public String toString()
	{
		return " " + firstName + " " + surname + " " + dob ;
	}
	
	/**
	 * compares one patient to another to see if equal, returns true if the names and dob are the same
	 * @param other
	 * @return true or false, true if patients are the same
	 */
	public boolean equals(Patient other)
	{
		boolean equalFname=this.firstName.equalsIgnoreCase(other.firstName);
		boolean equalLName=this.surname.equalsIgnoreCase(other.surname);
		boolean equalDOB=false;
		if(this.dob==other.dob)
			{
			equalDOB=true;
			}
		
		
		//System.out.println(" fname " + equalFname + " lname " + equalLName + " dob " + equalDOB); 
		
		return(equalFname && equalLName  && equalDOB );
	}

	/******SETTERS AND GETTERS********/
	public String getFirstName() 
	{
		return firstName;
	}

	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}

	public String getSurname() 
	{
		return surname;
	}

	public void setSurname(String surname) 
	{
		this.surname = surname;
	}

	public int getDob() 
	{
		return dob;
	}

	public void setDob(int dob) 
	{
		this.dob = dob;
	}

	public String getAddress() 
	{
		return address;
	}

	public void setAddress(String address) 
	{
		this.address = address;
	}

	public String getIllness() 
	{
		return illness;
	}

	public void setIllness(String illness) 
	{
		this.illness = illness;
	}

	public int getPatientNum() 
	{
		return patientNum;
	}
	
	public int getAppointmentsAttended() 
	{
		return appointmentsAttended;
	}

	public void setAppointmentsAttended(int appointmentsAttended) 
	{
		this.appointmentsAttended = appointmentsAttended;
	}


}
