package interfaces;

import java.util.List;

import pojos.Patient;
import pojos.Surgeon;

public interface SManager {

	// addSurgeon
	public void addSurgeon(Surgeon p);

	// list surgeons
	public List<Surgeon> listSurgeons();

	// searchSurgeon
	public List<Surgeon> searchSurgeon(int id);

	// updateSurgeon
	public void updateSurgeon(String name, String medstat, Integer pagerNumber, Integer tlfNumber);

	public void updateSurgeon(Surgeon s);

	// acceptSurgery
	public void acceptSurgery(String s);

	// availability
	// TODO availability
}
