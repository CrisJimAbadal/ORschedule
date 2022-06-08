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
			String sql = "INSERT INTO surgeon (name, email, medstat, pagerNumber, tlfNumber) VALUES (?,?,?,?,?)";
			// use preparedStmt so nothing damages the database
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			prep.setString(1, s.getName());
			prep.setString(2, s.getEmail());
			prep.setString(3, s.getMedstat());
			prep.setInt(4, s.getPagerNumber());
			prep.setInt(5, s.getTlfNumber());
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// LIST SURGEONS by specialty (to match the patient's medStat)
	@Override
	public List<Surgeon> listSurgeons(String medstat) {
		List<Surgeon> surgeons = new ArrayList<Surgeon>();
		try {
			
			String sql = "SELECT * FROM surgeon WHERE medstat = ?";
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);
			pr.setString(1,medstat);
			ResultSet rs = pr.executeQuery();

			while (rs.next()) {
				Integer id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Integer pagerNumber = rs.getInt("pagerNumber");
				Integer tlfNumber = rs.getInt("tlfNumber");

				Surgeon s = new Surgeon(id, email, name, medstat, pagerNumber, tlfNumber);
				surgeons.add(s);
				
			}
			rs.close();
			pr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return surgeons;
	}

	// get surgeonid from user id -> for login
	public int searchSurgeonIdfromUId(int id) {
		int sid = 0;
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT surgeon.id FROM surgeon JOIN users ON surgeon.email=users.email WHERE users.id= " + id;
			ResultSet rs = stmt.executeQuery(sql);

			sid = rs.getInt("id");

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sid;

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
				String email = rs.getString("email");
				String medstat = rs.getString("medstat");

				s = new Surgeon(id, email, name, medstat);

			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	// CHOOSE SURGEON
	@Override
	public Surgeon chooseSurgeon(int id) {
		Surgeon s = null;
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM surgeon WHERE id = " + id;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String medstat = rs.getString("medstat");
				Integer pagerNumber = rs.getInt("pagerNumber");
				Integer tlfNumber = rs.getInt("tlfNumber");

				s = new Surgeon(id, email, name, medstat, pagerNumber, tlfNumber);

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
			String sql = "SELECT * FROM surgeon WHERE id= " + id;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String medstat = rs.getString("medstat");
				Integer pagerNumber = rs.getInt("pagerNumber");
				Integer tlfNumber = rs.getInt("tlfNumber");

				s = new Surgeon(id, name, email, medstat, pagerNumber, tlfNumber);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	// UPDATE SURGEON
	@Override
	public void updateSurgeon(Surgeon s) {
		try {
			String sql = "UPDATE surgeon SET  pagernumber = ?, tlfnumber = ? WHERE id = ?";
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);

			if (s.getPagerNumber() != null) {
				pr.setInt(1, s.getPagerNumber());
			}
			if (s.getTlfNumber() != null) {
				pr.setInt(2, s.getTlfNumber());
			}
			pr.setInt (3, s.getId());
			pr.executeUpdate();
			pr.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public List<Surgery> listSurgeries(int surgeonId) {
		List<Surgery> surgeries = new ArrayList<Surgery>();
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT surgery.id, surgery.type, schedule.date, schedule.id, startTime FROM surgery JOIN schedule ON surgery.id = schedule.id JOIN surgeonSurgery ON surgery.id=surgeonSurgery.surgeryId WHERE surgeonSurgery.surgeonId = "
					+ surgeonId;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {

				Integer surgeryId = rs.getInt(1);
				String type = rs.getString(2);
				Date date = rs.getDate(3);
				Integer scheduleId = rs.getInt(4);
				Time startTime = rs.getTime(5);

				Schedule schedule = new Schedule(scheduleId, date, startTime);
				Surgery s = new Surgery(surgeryId, type, schedule);

				surgeries.add(s);
			}
			rs.close();
			stmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return surgeries;
	}

	@Override
	public int countSurgeons(String medstat) {
		int surgeons = 0;
		
		try {
			
			String sql = "SELECT COUNT(id) FROM surgeon WHERE medstat LIKE ?" ;
			
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);
			pr.setString(1,medstat);
			ResultSet rs = pr.executeQuery();
			while (rs.next()) {
				surgeons = (rs.getInt("COUNT(id)"));
			}
			
			pr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return surgeons;
	}
}
