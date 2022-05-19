package pojos;

import java.io.Serializable;

public class Acceptance implements Serializable {


	private static final long serialVersionUID = -5785232518575439756L;
	private Surgery surgery;
	private Surgeon surgeon;
	private boolean accept;
	
	
	public Acceptance(Surgery surgery, Surgeon surgeon, boolean accept) {
		this.accept= accept;
		this.surgeon = surgeon;
		this.surgery = surgery;
	}
	
	
	
	
	public Surgery getSurgery() {
		return surgery;
	}
	public void setSurgery(Surgery surgery) {
		this.surgery = surgery;
	}
	public Surgeon getSurgeon() {
		return surgeon;
	}
	public void setSurgeon(Surgeon surgeon) {
		this.surgeon = surgeon;
	}
	public boolean isAccept() {
		return accept;
	}
	public void setAccept(boolean accept) {
		this.accept = accept;
	}
	
	
	@Override
	public String toString() {
		
	return "Surgery"+this.surgery+" Surgeon"+this.surgeon+" Accepted: "+this.accept;	
	}
	
}