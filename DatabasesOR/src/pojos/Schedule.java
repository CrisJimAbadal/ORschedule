package pojos;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
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
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


import utils.SQLDateAdapter;
import utils.SQLTimeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Schedule")
@XmlType(propOrder = { "date", "startTime", "finishTime"})
public class Schedule implements Serializable{
	
	private static final long serialVersionUID = 4417970591986345560L;
	
	@XmlTransient
	private Integer id;
	@XmlJavaTypeAdapter(SQLDateAdapter.class)
	private Date date;
	@XmlJavaTypeAdapter(SQLTimeAdapter.class)
	private Time startTime;
	@XmlJavaTypeAdapter(SQLTimeAdapter.class)
	private Time finishTime;
	
	@XmlTransient
	private Surgery surgery; //TODO  adapter?
	

	public Schedule(Integer id, Date date, Time startTime, Time finishTime) {
		super();
		this.id = id;
		this.date = date;
		this.startTime = startTime;
		this.finishTime = finishTime;
	}
	
	public Schedule(Date date, Time startTime, Time finishTime) {
		super();
		this.date = date;
		this.startTime = startTime;
		this.finishTime = finishTime;
	}

	public Schedule(Integer id, Date date, Time startTime) {
		super();
		this.id = id;
		this.date = date;
		this.startTime = startTime;
	}

	public Schedule() {
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
		Schedule other = (Schedule) obj;
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
	
	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Time finishTime) {
		this.finishTime = finishTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Schedule [id=" + id + ", date=" + date + ", startTime=" + startTime + ", finishTime=" + finishTime
				+ ", surgery=" + surgery + "]";
	}
	
	
	
	
	
	

	
	
	
}
