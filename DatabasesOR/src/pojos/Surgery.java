package pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Surgery implements Serializable{

	
	private static final long serialVersionUID = 7569041371565811550L;
	private Integer id;
	private String type;
	private Boolean conductSurgery;
	//TODO set false
	
	private Patient patient;
	private OPR opr;
	private Surgeon surgeon;


	

	public Surgery() {
		super();
		
	}
	
	
	
	public Surgery(Patient patient, Surgeon surgeon, OPR opr,String type ) {
		super();
		this.patient = patient;
		this.surgeon = surgeon;
		this.opr = opr;
		this.type = type;
		//initialice the others???????????????????????????????????????????????????????
		
	}



	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Surgery other = (Surgery) obj;
		return Objects.equals(id, other.id);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getConductSurgery() {
		return conductSurgery;
	}
	public void setConductSurgery(Boolean conductSurgery) {
		this.conductSurgery = conductSurgery;
	}
	
		public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public OPR getOpr() {
		return opr;
	}

	public void setOpr(OPR opr) {
		this.opr = opr;
	}
	
	public Surgeon getSurgeon() {
		return surgeon;
	}

	public void setSurgeon(Surgeon surgeon) {
		this.surgeon = surgeon;
	}
	
	@Override
	public String toString() {
		//añadir patient, opr, surgeon
		return "Surgery [id=" + id + ", type=" + type + ", conductSurgery=" + conductSurgery + "]";
	}
	
}
