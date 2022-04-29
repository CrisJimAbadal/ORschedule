package interfaces;

import java.util.List;

import pojos.Patient;

public interface PManager {

	// addPatient
	public void addPatient(Patient p);

	// listPatients
	public List<Patient> listPatients();
	
	// listPatients name and id
		public Integer listPatientsId(String email);
	
	//searchPatients
	public Patient searchPatient(int id);
	
	//list patient info
	public Patient showPatient(int id);

	//update patient
	public void updatePatient(Patient p);
	
	
	public void deletePatient(int patientid);
	
	
}
