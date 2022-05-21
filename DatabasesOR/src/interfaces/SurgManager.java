package interfaces;

import java.util.List;

import pojos.Patient;
import pojos.Schedule;
import pojos.Surgeon;
import pojos.Surgery;

public interface SurgManager {


	// assign Surgery
	public void addSurgery (Surgery s);
	
	//unassign Surgery
	public void unassign (int surgeryId);
	
	public List<Surgery> listSurgeries(int id);
	
	//to check if it's available at a specific schedule
	public boolean checkOPR (Schedule s, int oprId);
	public boolean checkpatient (Schedule s, Patient p);
	public boolean checksurgeon (Schedule s, Surgeon surg);
}
