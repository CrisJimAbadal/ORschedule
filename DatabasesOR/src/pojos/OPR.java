package pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "OPR")
@XmlType(propOrder = { "id", "floor", "number"})
public class OPR implements Serializable {

	
	private static final long serialVersionUID = -3851196959970978337L;
	
	@XmlAttribute
	private Integer id;
	@XmlAttribute
	private Integer floor;
	@XmlAttribute
	private Integer number;
	@XmlTransient
	private List <Surgery> surgeries; 
	@XmlTransient
	private List <Schedule> av;
	
	

	public OPR() {
		super();
		surgeries = new ArrayList <Surgery>();
		av = new ArrayList <Schedule>();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public OPR(Integer id, Integer floor, Integer number) {
		super();
		this.id = id;
		this.floor = floor;
		this.number = number;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OPR other = (OPR) obj;
		return Objects.equals(id, other.id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public List<Surgery> getSurgeries() {
		return surgeries;
	}

	public void setSurgeries(List<Surgery> surgeries) {
		this.surgeries = surgeries;
	}

	public List<Schedule> getAv() {
		return av;
	}

	public void setAv(List<Schedule> av) {
		this.av = av;
	}
	@Override
	public String toString() {
		//a?adir surgery y availability
		
		return "OPR [id=" + id + ", floor=" + floor + ", number=" + number + "]";
	}

}
