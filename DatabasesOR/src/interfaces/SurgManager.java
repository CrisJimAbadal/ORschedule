package interfaces;

import java.util.List;

import pojos.Patient;
import pojos.Schedule;
import pojos.Surgeon;
import pojos.Surgery;

public interface SurgManager {


	// assign Surgery
	public void addSurgery (Surgery s);
	
	//unassign/delete Surgery
	public void unassign (int surgeryId);
	
	//list assigned surgeries to the patient
	public List<Surgery> listSurgeries(int id);
	
	//list assigned surgeries so the doctor can delete one
	public List<Surgery> listSurgeries();
	
	//choose Surgery
	public Surgery chooseSurgery (int id);
	
	//to check if it's available at a specific schedule
	public boolean checkOPR (Schedule s, int oprId);
	public boolean checkpatient (Schedule s, Patient p);
	public boolean checksurgeon (Schedule s, Surgeon surg);
	
}
