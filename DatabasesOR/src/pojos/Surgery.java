package pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Surgery") 
@XmlType(propOrder = { "type", "opr", "patient", "surgeon", "schedule"})
public class Surgery implements Serializable{

	
	private static final long serialVersionUID = 7569041371565811550L;
	
	@XmlTransient
	private Integer id;
	@XmlAttribute
	private String type;
	@XmlElement
	private Patient patient; 
	@XmlElement
	private OPR opr;
	@XmlElement(name = "Surgeon")
	@XmlElementWrapper(name= "Surgeons")
	private List <Surgeon> surgeons;
	@XmlElement
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


	public Surgery(Integer id, String type, Schedule schedule) {
		super();
		this.id = id;
		this.type = type;
		this.schedule = schedule;
	
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
		return "Surgery [id=" + id + ", type=" + type +"]";
	}



	public Schedule getSchedule() {
		return schedule;
	}



	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	
}
