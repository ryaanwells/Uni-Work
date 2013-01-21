import java.util.Date;

/**
 * This class represents an appointment which belongs to a patient
 * @author rose
 *
 */
public class Appointment 
{
	private Date appointmentDate;/*date and time of the appointment*/
	private String doctor;/*a string to hold the name of the doctor taking the appointment*/
	private Patient patient;/*the patient associated with this appointment*/
	
	public Appointment()
	{
		/*initialise class variables*/
		appointmentDate= new Date();//current day and time
		doctor="Dr Default";
		patient= new Patient();
	}
	
	public Appointment(Date d, String dr, Patient p)
	{
		appointmentDate=d;
		doctor=dr;
		patient=p;
	}
	
	/**
	 * 
	 * @param other - a different appointment object
	 * @return a boolean representing whether the appointment is the same
	 */
	public boolean equals(Appointment other)
	{
		/*
		 * An appointment is the same if the dates, doctor and patient is the same*/
		return (this.appointmentDate.equals(other.appointmentDate) && this.doctor.equals(other.doctor) 
		   && this.patient.equals(other.patient));
		
		
	}

	/* SETTERS AND GETTERS**/
	public Date getAppointmentDate() 
	{
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) 
	{
		this.appointmentDate = appointmentDate;
	}

	public String getDoctor() 
	{
		return doctor;
	}

	public void setDoctor(String doctor) 
	{
		this.doctor = doctor;
	}

	public Patient getPatient() 
	{
		return patient;
	}

	public void setPatient(Patient patient) 
	{
		this.patient = patient;
	}
	
	

}
