package interfaces;

import java.util.List;

import jdbc.Availability;
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

	// updateSurgeon
	public void updateSurgeon(String name, String medstat, Integer pagerNumber, Integer tlfNumber);

	public void updateSurgeon(Surgeon s);

	// acceptSurgery
	public void acceptSurgery(String s);
	
	public void deleteSurgeon(int Surgeonid);

	// availability
	public void updateAvailability (Availability a);
	// TODO availability
	//TODO check availability method
	//TODO do an ORManager
}
