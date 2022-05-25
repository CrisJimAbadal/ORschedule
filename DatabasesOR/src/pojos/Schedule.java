package pojos;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Schedule implements Serializable{
	
	private static final long serialVersionUID = 4417970591986345560L;
	
	private Integer id;
	private Date date;
	private Time startTime;
	private Time finishTime;
	

	private Surgery surgery;
	

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
