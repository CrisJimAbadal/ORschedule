package pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Patient implements Serializable {


	
	private static final long serialVersionUID = 7926972104136754563L;
	
	private Integer id;
	private String name;
	private String medstat;
	private Integer age;
	private String sex;
	
	private List <Surgery> surgeries;
	
	

	public Patient(String name, String medstat, Integer age, String sex) {
		super();
		this.name = name;
		this.medstat = medstat;
		this.age = age;
		this.sex = sex;
		this.surgeries= new ArrayList<Surgery>();
	}



	public Patient() {
		super();
		surgeries = new ArrayList <Surgery>();
	}
	
	
	public Patient(Integer id, String name, String medstat, Integer age, String sex) {
		super();
		this.id = id;
		this.name = name;
		this.medstat = medstat;
		this.age = age;
		this.sex = sex;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
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

	@Override
	public String toString() {
		//a�adir las surgeries
		
		return "Patient [id=" + id + ", name=" + name + ", medstat=" + medstat + ", age=" + age + ", sex=" + sex + "]";
	}

	
}
