package interfaces;

import java.util.List;

import pojos.Patient;

public interface PManager {

	// addPatient
	public void addPatient(Patient p);

	// listPatients
	public List<Patient> listPatients();
	
	//searchPatients
	public Patient searchPatient( String email);
	
	//list patient info
	public Patient showPatient(int id);

	//update patient
	public void updatePatient(Patient p);
	
	
	
	
}
