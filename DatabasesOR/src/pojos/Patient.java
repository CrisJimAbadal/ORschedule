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
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import utils.SQLDateAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Patient")
@XmlType(propOrder = { "name", "medstat", "email"})
public class Patient implements Serializable {


	
	private static final long serialVersionUID = 7926972104136754563L;
	
	@XmlTransient
	private Integer id;
	@XmlAttribute
	private String name;
	@XmlAttribute
	private String medstat;
	@XmlAttribute
	private String email;
	@XmlTransient
	private byte[] digest;
	public byte[] getDigest() {
		return digest;
	}


	@XmlJavaTypeAdapter(SQLDateAdapter.class)
	private Date dob;
	@XmlAttribute
	private String sex;
	
	@XmlElement(name = "Surgery")
	@XmlElementWrapper(name= "Surgeries")
	private List <Surgery> surgeries;
	
	

	public Patient() {
		super();
		surgeries = new ArrayList <Surgery>();
	}
	

	public Patient(String name,  String medstat, String email, Date dob, String sex) {
		super();
		this.name = name;
		this.medstat = medstat;
		this.email = email;
	//	this.digest= digest;
		this.dob = dob;
		this.sex = sex;
		this.surgeries= new ArrayList<Surgery>();
	}
	
	
	public Patient(Integer id, String name, String medstat, Date dob, String sex,String email) {
		super();
		this.id = id;
		this.name = name;
		this.medstat = medstat;
		this.dob = dob;
		this.sex = sex;
		this.email=email;
		this.surgeries= new ArrayList<Surgery>();
	}
	
	

	public Patient(Integer id, String name, String medstat) {
		super();
		this.id = id;
		this.name = name;
		this.medstat = medstat;
		this.surgeries= new ArrayList<Surgery>();

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
		Patient other = (Patient) obj;
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

	public String getMedstat() {
		return medstat;
	}

	public void setMedstat(String medstat) {
		this.medstat = medstat;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public List<Surgery> getSurgeries() {
		return surgeries;
	}



	public void setSurgeries(List<Surgery> surgeries) {
		this.surgeries = surgeries;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", name=" + name + ", medstat=" + medstat + ", email=" + email + ", dob=" + dob
				+ ", sex=" + sex + "]";
	}

	
	
}
