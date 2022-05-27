package jdbc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;

import interfaces.ScheduleManager;
import pojos.Schedule;

public class JDBCScheduleManager implements ScheduleManager {
	private JDBCManager manager;

	public JDBCScheduleManager(JDBCManager m) {
		this.manager = m;
	}

	//ADD SCHEDULE TO THE DATABASE
	@Override
	public void addSchedule(Schedule s) {
		try {

			String sql = "INSERT INTO schedule (date,startTime, finishTime) VALUES (?,?,?)";
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);
			pr.setDate(1, s.getDate());
			pr.setTime(2, s.getStartTime());
			pr.setTime(3, s.getFinishTime());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// SCHEDULES OF ASSIGNED SURGERIES
	public Schedule showSchedule(int id) {
		Schedule s = null;
		Date date = null;
		Time startTime = null;
		Time finishTime= null;
		try {
			//TODO check if this query correct
			String sql = "SELECT * FROM schedule JOIN surgeon ON schedule.id= surgeon.scheduleid WHERE surgeonId = " + id;
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			ResultSet rs = prep.executeQuery(sql);
			while (rs.next()) {
				date = rs.getDate("date");
				startTime = rs.getTime ("startTime");
				finishTime = rs.getTime ("finishTime");
				
			}
			s = new Schedule(id, date, startTime, finishTime);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
}
