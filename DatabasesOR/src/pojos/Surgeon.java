package pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Surgeon")
@XmlType(propOrder = { "name", "medstat", "pagerNumber", "tlfNumber" })
public class Surgeon implements Serializable {

	private static final long serialVersionUID = 7391492954500022065L;

	@XmlTransient
	private Integer id;
	@XmlAttribute
	private String email;
	@XmlAttribute
	private String name;

	@XmlAttribute
	private String medstat;
	@XmlElement
	private Integer pagerNumber;
	@XmlAttribute
	private Integer tlfNumber;
	@XmlTransient
	private List<Surgery> surgeries;

	public Surgeon() {
		super();
		surgeries = new ArrayList<Surgery>();

	}

	public Surgeon(String name, String email, String medstat, Integer pagerNumber, Integer tlfNumber) {
		super();
		this.name = name;
		this.email = email;
		this.medstat = medstat;
		this.pagerNumber = pagerNumber;
		this.tlfNumber = tlfNumber;
		surgeries = new ArrayList<Surgery>();

	}

	public Surgeon(Integer id, String name, String email,String medstat, Integer pagerNumber, Integer tlfNumber) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.medstat = medstat;
		this.pagerNumber = pagerNumber;
		this.tlfNumber = tlfNumber;
		surgeries = new ArrayList<Surgery>();

	}

	public Surgeon(Integer id, String email, String name, String medstat) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.medstat = medstat;
		surgeries = new ArrayList<Surgery>();

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
		Surgeon other = (Surgeon) obj;
		return Objects.equals(id, other.id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMedstat() {
		return medstat;
	}

	public void setMedstat(String medstat) {
		this.medstat = medstat;
	}

	public Integer getPagerNumber() {
		return pagerNumber;
	}

	public void setPagerNumber(Integer pagernumber) {
		this.pagerNumber = pagernumber;
	}

	public Integer getTlfNumber() {
		return tlfNumber;
	}

	public void setTlfNumber(Integer tlfnumber) {
		this.tlfNumber = tlfnumber;
	}

	public List<Surgery> getSurgeries() {
		return surgeries;
	}

	public void setSurgeries(List<Surgery> surgeries) {
		this.surgeries = surgeries;
	}

	@Override
	public String toString() {
		return "Surgeon [id=" + id + ", email=" + email + ", name=" + name + ", medstat=" + medstat + ", pagerNumber="
				+ pagerNumber + ", tlfNumber=" + tlfNumber + "]";
	}

	

}
