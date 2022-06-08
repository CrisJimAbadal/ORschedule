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

			String sql = "INSERT INTO schedule (date,startTime, finishTime,id) VALUES (?,?,?,?)";
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);
			pr.setDate(1, s.getDate());
			pr.setTime(2, s.getStartTime());
			pr.setTime(3, s.getFinishTime());
			pr.setInt(4,s.getId());
			pr.executeUpdate();
			pr.close();
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
			
			String sql = "SELECT * FROM schedule JOIN surgery ON schedule.id= surgery.scheduleid JOIN surgeonSurgery ON surgeonSurgery.surgeryId= surgery.id WHERE surgeonSurgery.surgeonId = " + id;
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				date = rs.getDate("date");
				startTime = rs.getTime ("startTime");
				finishTime = rs.getTime ("finishTime");
				
			}
			s = new Schedule(id, date, startTime, finishTime);
			rs.close();
			prep.close();


		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	public int getIdSchedule() {
		int id = 0;
		try {
			String sql = "SELECT MAX(id) FROM schedule";
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			ResultSet rs = prep.executeQuery();
			
			while(rs.next()) {
				id = rs.getInt(1);
			}
			
			
			rs.close();
			prep.close();
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		id++;
		return id;
	}
	
	
}
