package pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Surgery implements Serializable{

	
	private static final long serialVersionUID = 7569041371565811550L;
	private Integer id;
	private String type;
	private Boolean acceptSurgery;
	
	private Patient patient;
	private OPR opr;
	private List <Surgeon> surgeons;
	private Schedule schedule;


	

	public Surgery() {
		super();
		
	}
	
	
	
	public Surgery(Patient patient, List <Surgeon> surgeons, OPR opr,String type, Schedule schedule ) {
		super();
		this.patient = patient;
		setSurgeons(new ArrayList<Surgeon>());
		this.opr = opr;
		this.type = type;
		this.schedule=schedule;
		
	}



	public Surgery(String type, Schedule schedule) {
		this.type=type;
		this.setSchedule(schedule);
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
	public Boolean getAcceptSurgery() {
		return acceptSurgery;
	}
	public void setAcceptSurgery(Boolean acceptSurgery) {
		this.acceptSurgery = acceptSurgery;
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
	
		public List <Surgeon> getSurgeons() {
		return surgeons;
	}
	public void setSurgeons(List <Surgeon> surgeons) {
		this.surgeons = surgeons;
	}
	
	@Override
	public String toString() {
		//añadir patient, opr, surgeons
		return "Surgery [id=" + id + ", type=" + type + ", acceptSurgery=" + acceptSurgery + "]";
	}



	public Schedule getSchedule() {
		return schedule;
	}



	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}




	
}
