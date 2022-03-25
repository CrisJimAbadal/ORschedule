package pojos;

import java.io.Serializable;
import java.util.Objects;

public class Surgeon implements Serializable {

	
	private static final long serialVersionUID = 7391492954500022065L;
	
	private Integer id;
	private String name;
	private String medstat;
	private Integer pagernumber;
	private Integer tlfnumber;
	
	public Surgeon() {
		super();
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

	public String getMedstat() {
		return medstat;
	}

	public void setMedstat(String medstat) {
		this.medstat = medstat;
	}

	public Integer getPagernumber() {
		return pagernumber;
	}

	public void setPagernumber(Integer pagernumber) {
		this.pagernumber = pagernumber;
	}

	public Integer getTlfnumber() {
		return tlfnumber;
	}

	public void setTlfnumber(Integer tlfnumber) {
		this.tlfnumber = tlfnumber;
	}
	
	
	
	@Override
	public String toString() {
		return "Surgeon [id=" + id + ", name=" + name + ", medstat=" + medstat + ", pagernumber=" + pagernumber
				+ ", tlfnumber=" + tlfnumber + "]";
	}

	
	
}
