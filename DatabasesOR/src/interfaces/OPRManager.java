package interfaces;

import java.util.List;

import pojos.OPR;
import pojos.Surgeon;

public interface OPRManager {

	public List<OPR> listOprs();

	// searchOPR
	public OPR searchOPR(int id);

	// show if it´s scheduled for surgery or not
	//public OPR showOPR(int id);

}
