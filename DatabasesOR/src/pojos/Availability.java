package pojos;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Availability implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4417970591986345560L;
	
	private Integer id;
	private Date date;
	private Time time;
	
	private List <Availability> avs;
	private List <Surgeon> surgeons;
	
	
	
	public Availability() {
		super();
		avs = new ArrayList <Availability>();
		surgeons = new ArrayList <Surgeon>();
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
		Availability other = (Availability) obj;
		return Objects.equals(id, other.id);
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public List<Availability> getAvs() {
		return avs;
	}
	public void setAvs(List<Availability> avs) {
		this.avs = avs;
	}
	public List<Surgeon> getSurgeons() {
		return surgeons;
	}
	public void setSurgeons(List<Surgeon> surgeons) {
		this.surgeons = surgeons;
	}
	@Override
	public String toString() {
		//añadir avs y surgeons
		return "Availability [id=" + id + ", date=" + date + ", time=" + time + "]";
	}
	
	
	
	

	
	
	
}
