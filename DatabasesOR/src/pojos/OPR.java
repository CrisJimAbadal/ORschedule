package pojos;

import java.io.Serializable;
import java.util.Objects;

public class OPR implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3851196959970978337L;
	
	
	private Integer id;
	private Integer floor;
	private Integer number;
	
	public OPR() {
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
	
	@Override
	public String toString() {
		return "OPR [id=" + id + ", floor=" + floor + ", number=" + number + "]";
	}

}
