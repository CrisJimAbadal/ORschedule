package interfaces;

import java.util.List;

import pojos.Schedule;
import pojos.Surgeon;
import pojos.Surgery;

public interface SManager {

	// addSurgeon
	public void addSurgeon(Surgeon p);

	// list surgeons by specialty
	public List<Surgeon> listSurgeons(String specialty);

	// searchSurgeon
	public Surgeon searchSurgeon(int id);
	
	//chooseSurgeon
	public Surgeon chooseSurgeon(int id);

	// search by PagerNumber
	public Surgeon showSurgeon(int id);

	//update surgeon
	public void updateSurgeon(Surgeon s);


	//get surgeonid from userid
	public int searchSurgeonIdfromUId (int id);

	//to show the schedule
	public List<Surgery> listSurgeries(int surgeonId);
	
	//countSurgeons
	public int countSurgeons(String specialty);
}
