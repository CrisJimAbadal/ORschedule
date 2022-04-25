package interfaces;

import java.util.List;

import pojos.Patient;

public interface PManager {

	// addPatient
	public void addPatient(Patient p);

	// listPatients
	public List<Patient> listPatients();
	
	// listPatients name and id
		public List<Patient> listPatientsId();
	
	//searchPatients
	public Patient searchPatient(int id);
	
	//list patient info
	public Patient showPatient(int id);

	//update patient
	public void updatePatient(Patient p);
	
	public void updatePatient (String name, String medstat, Integer age, String sex);
	
	public void deletePatient(int patientid);
	
	
}
