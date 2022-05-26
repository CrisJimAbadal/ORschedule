package jdbc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import interfaces.SManager;
import pojos.Schedule;
import pojos.Patient;
import pojos.Surgeon;
import pojos.Surgery;

public class JDBCSurgeonManager implements SManager {

	private JDBCManager manager;

	public JDBCSurgeonManager(JDBCManager m) {
		this.manager = m;
	}

	// ADD PATIENT TO THE DATABASE
	@Override
	public void addSurgeon(Surgeon s) {

		try {
			String sql = "INSERT INTO surgeon (name, medstat, pagerNumber, tlfNumber) VALUES (?,?,?,?)";
			// use preparedStmt so nothing damages the database
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			prep.setString(1, s.getName());
			prep.setString(2, s.getMedstat());
			prep.setInt(3, s.getPagerNumber());
			prep.setInt(4, s.getTlfNumber());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// LIST SURGEONS by specialty (to match the patient's medStat)
	@Override
	public List<Surgeon> listSurgeons(String specialty) {
		List<Surgeon> surgeons = new ArrayList<Surgeon>();
		try {

			String sql = "SELECT * FROM surgeon WHERE specialty LIKE" + specialty;
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			ResultSet rs = prep.executeQuery(sql);
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String name = rs.getString("name");
				String medstat = rs.getString("medstat");
				Integer pagerNumber = rs.getInt("pagerNumber");
				Integer tlfNumber = rs.getInt("tlfNumber");

				Surgeon s = new Surgeon(id, name, medstat, pagerNumber, tlfNumber);
				surgeons.add(s);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return surgeons;
	}

	// SEARCH SURGEON BY ID
	@Override
	public Surgeon searchSurgeon(int id) {
		Surgeon s = null;
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM surgeon WHERE id = " + id;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String name = rs.getString("name");
				String medstat = rs.getString("medstat");

				s = new Surgeon(id, name, medstat);

			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	// SHOW SURGEON'S INFO (before updating the info)
	@Override
	public Surgeon showSurgeon(int id) {
		Surgeon s = null;
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM surgeon WHERE id = " + id;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String name = rs.getString("name");
				String medstat = rs.getString("medstat");
				Integer pagerNumber = rs.getInt("pagerNumber");
				Integer tlfNumber = rs.getInt("tlfNumber");

				s = new Surgeon(id, name, medstat, pagerNumber, tlfNumber);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	// UPDATE SURGEON
	@Override
	public void updateSurgeon(Surgeon s) {
		try {
			String sql = "UPDATE surgeonSET name = ?, medstat = ?, pagernumber = ?, tlfnumber = ?";
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);
			if (s.getName() != "") {
				pr.setString(1, s.getName());
			}
			if (s.getMedstat() != "") {
				pr.setString(2, s.getMedstat());
			}
			if (s.getPagerNumber() != null) {
				pr.setInt(3, s.getPagerNumber());
			}
			if (s.getTlfNumber() != null) {
				pr.setInt(4, s.getTlfNumber());
			}
			pr.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public List<Schedule> showSchedules(int surgeonId) {

		List<Schedule> schedules = new ArrayList<Schedule>();
		try {
			String sql = "SELECT date, startTime, finishTime  FROM schedule JOIN surgeon"
					+ " ON surgeon.scheduleId = schedule.id WHERE surgeonId = " + surgeonId;
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			ResultSet rs = prep.executeQuery(sql);
			while (rs.next()) {

				Date date = rs.getDate(1);
				Time stime = rs.getTime(2);
				Time ftime = rs.getTime(3);
				Schedule schedule = new Schedule(date, stime, ftime);

				schedules.add(schedule);
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return schedules;
	}

}
