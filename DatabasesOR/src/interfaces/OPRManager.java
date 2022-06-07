package interfaces;

import java.util.List;

import pojos.OPR;
import pojos.Surgeon;

public interface OPRManager {

	public List<OPR> listOprs();

	// searchOPR
	public OPR searchOPR(int id) ;


}
