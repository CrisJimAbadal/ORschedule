package interfaces;

import java.util.List;

import pojos.Schedule;
//import jdbc.Availability;
import pojos.Patient;
import pojos.Surgeon;

public interface SManager {

	// addSurgeon
	public void addSurgeon(Surgeon p);

	// list surgeons
	public List<Surgeon> listSurgeons();

	// searchSurgeon
	public Surgeon searchSurgeon(int id);
	
	//search by PagerNumber
	public Surgeon showSurgeon (int num);

	public void updateSurgeon(Surgeon s);

	// acceptSurgery
	public void acceptSurgery(String s);
	
	public void deleteSurgeon(int Surgeonid);

	// availability
	public void updateAvailability (Schedule a);
	// TODO availability
	
}
