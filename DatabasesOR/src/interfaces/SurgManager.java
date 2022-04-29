package interfaces;

import pojos.Surgery;

public interface SurgManager {


	// assign Surgery
	public void addSurgery (Surgery s);
	
	//unassign Surgery
	public void unassign (int surgeonId, int patientId);
}
