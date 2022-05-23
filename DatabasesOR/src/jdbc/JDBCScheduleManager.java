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

	@Override
	public void addSchedule(Schedule s) {
		try {

			String sql = "INSERT INTO schedule (date,time) VALUES (?,?)";
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);
			pr.setDate(1, s.getDate());
			pr.setTime(2, s.getTime());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Schedule showSchedule(int id) {
		Schedule s = null;
		Date date = null;
		Time time = null;
		try {
			String sql = "SELECT * FROM schedule WHERE surgeonId = " + id;
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			ResultSet rs = prep.executeQuery(sql);
			while (rs.next()) {
				date = rs.getDate("date");
				time = rs.getTime("time");

			}
			s = new Schedule(id, date, time);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return s;
	}
}
