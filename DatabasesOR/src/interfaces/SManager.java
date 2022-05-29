package interfaces;

import java.util.List;

import pojos.Schedule;
import pojos.Surgeon;

public interface SManager {

	// addSurgeon
	public void addSurgeon(Surgeon p);

	// list surgeons by specialty
	public List<Surgeon> listSurgeons(String specialty);

	// searchSurgeon
	public Surgeon searchSurgeon(int id);

	// search by PagerNumber
	public Surgeon showSurgeon(int id);

	public void updateSurgeon(Surgeon s);

    //show assigned surgeries
	public List<Schedule> showSchedules(int surgeonId);

}
