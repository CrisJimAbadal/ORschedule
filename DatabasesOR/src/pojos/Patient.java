package pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Patient implements Serializable {


	
	private static final long serialVersionUID = 7926972104136754563L;
	
	private Integer id;
	private String name;
	private String medstat;
	private String email;
	private byte[] digest;
	public byte[] getDigest() {
		return digest;
	}


	public void setDigest(byte[] digest) {
		this.digest = digest;
	}

	private Date dob;
	private String sex;
	
	private List <Surgery> surgeries;
	
	

	public Patient() {
		super();
		surgeries = new ArrayList <Surgery>();
	}
	

	public Patient(String name,  String medstat, byte[] digest, String email, Date dob, String sex) {
		super();
		this.name = name;
		this.medstat = medstat;
		this.email = email;
		this.digest= digest;
		this.dob = dob;
		this.sex = sex;
		this.surgeries= new ArrayList<Surgery>();
	}
	
	public Patient(Integer id, String name, String medstat, Date dob, String sex) {
		super();
		this.id = id;
		this.name = name;
		this.medstat = medstat;
		this.dob = dob;
		this.sex = sex;
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
		//añadir las surgeries, email
		
		return "Patient [id=" + id + ", name=" + name + ", medstat=" + medstat + ", age=" + dob + ", sex=" + sex + "]";
	}

	
	
}
