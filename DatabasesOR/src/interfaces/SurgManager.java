package interfaces;

import pojos.Surgery;

public interface SurgManager {


	// addSurgery
	public void addSurgery (Surgery s);
	
	public void assign (int surgeonId, int patientId);
	
	public void unassign (int surgeonId, int patientId);
}
