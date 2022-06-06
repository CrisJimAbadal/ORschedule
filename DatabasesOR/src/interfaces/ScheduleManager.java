package interfaces;

import java.sql.Date;
import java.sql.Time;

import pojos.Schedule;

public interface ScheduleManager {

	public void addSchedule(Schedule s);
	
	public Schedule showSchedule(int pagerNumber);
	public int getIdSchedule();
}
