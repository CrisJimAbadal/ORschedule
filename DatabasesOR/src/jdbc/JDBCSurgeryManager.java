package jdbc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import interfaces.SurgManager;
import pojos.OPR;
import pojos.Patient;
import pojos.Schedule;
import pojos.Surgeon;
import pojos.Surgery;

public class JDBCSurgeryManager implements SurgManager {

	private JDBCManager manager;

	public JDBCSurgeryManager(JDBCManager m) {
		this.manager = m;
	}

	// ADD SURGERY TO THE DATABASE
	@Override
	public void addSurgery(Surgery s) {
		Patient p= s.getPatient();
		OPR o=s.getOpr();
		List <Surgeon> surgeons=s.getSurgeons();

		try {
			//TODO finish query 
			
			String sql = "INSERT INTO surgery (patientId, , oprId, type VALUES (?,?,?)";
			// use preparedStmt so nothing damages the database
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			prep.setObject(1, p.getId());
			prep.setObject(2,o.getId() );
			prep.setString(3, s.getType());
			
			for(Surgeon surgeon: surgeons) {
				
				String sql2 = "INSERT INTO surgeonSurgery (surgeryId, surgeonId VALUES (?,?)";
				// use preparedStmt so nothing damages the database
				PreparedStatement prep2 = manager.getConnection().prepareStatement(sql2);
				prep2.setObject(1, s.getId());
				prep2.setObject(1, surgeon.getId());
				//TODO execute but in all of the queries of the project
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// DELETE SURGERY (done by the doctor)
	@Override
	public void unassign(int surgeryId) {
		try {
			String sql = "DELETE * FROM Surgery WHERE id =?";
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);
			pr.setInt(1, surgeryId);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// LIST ASSIGNED SURGERIES TO THE PATIENT
	public List<Surgery> listSurgeries(int id) {
		List<Surgery> surgeries = new ArrayList<Surgery>();
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT surgery.id,type,date,schedule.id, startTime, finishTime FROM surgery JOIN schedule ON surgery.id= schedule.id WHERE id= "
					+ id;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// when using a join put the number of the column instead of the name. Start
				// with 1 instead of 0
				Integer surgeryId = rs.getInt(1);
				String type = rs.getString(2);
				Date date = rs.getDate(3);
				Integer scheduleId = rs.getInt(4);
				Time startTime = rs.getTime(5);
				Time finishTime = rs.getTime(6);

				Schedule schedule = new Schedule(scheduleId, date, startTime, finishTime);
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

	// TODO check this method
	// LIST SURGERIES SO THE DOCTOR CAN DELETE ONE
	@Override
	public List<Surgery> listSurgeries() {
		List<Surgery> surgeries = new ArrayList<Surgery>();
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT surgery.id,type,date,schedule.id, startTime FROM surgery JOIN schedule ON surgery.id= schedule.id ";
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

	// TODO COMPROBACION select lo q sea de surgery where time= ? if null... nueva
	// surgery
	// CHECKS TO SEE IF IT'S CORRECT TO KEEP CREATING
	public boolean checkOPR(Schedule s, int oprId) {

		try {
			String sql = "SELECT oprId FROM surgery JOIN schedule ON surgery.scheduleId= schedule.id "
					+ "WHERE schedule.Date= ? AND surgery.oprId=?"
					+ " AND ((schedule.startTime<= ? AND schedule.finishTime>= ?) " + // ?1 startTime ?2 finishTime
					"OR (schedule.startTime>= ? AND schedule.finishTime<= ?) " + // ?1 startTime ?2 finishTime
					"OR (schedule.startTime>= ? AND schedule.startTime<= ?) " + // ? = startTime ?2finishTIme
					"OR (schedule.startTime<= ? AND schedule.finishTime>=?))" // ? =starttime
			;
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);
			pr.setDate(1, s.getDate());
			pr.setInt(2, oprId);
			ResultSet rs = pr.executeQuery(sql);
			int id = rs.getInt(1);

			if (id == 0) {
				return false;
				// no OPR occupied at that schedule
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean checkpatient(Schedule s, Patient p) {

		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT patientId FROM surgery JOIN schedule ON surgery.scheduleId= schedule.id WHERE schedule.Date= ? AND schedule.TIME = ? AND surgery.patientId=?";
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);
			pr.setDate(1, s.getDate());
			pr.setTime(2, s.getTime());
			pr.setInt(3, p.getId());
			ResultSet rs = pr.executeQuery(sql);
			int id = rs.getInt("patientId");
			if (id == 0) {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean checksurgeon(Schedule s, Surgeon surg) {

		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT surgeonId FROM surgery JOIN schedule ON surgery.scheduleId= schedule.id WHERE schedule.Date= ? AND schedule.TIME = ? AND surgeon.oprId=?";
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);
			pr.setDate(1, s.getDate());
			pr.setTime(2, s.getTime());
			pr.setInt(3, s.getId());
			ResultSet rs = pr.executeQuery(sql);
			int id = rs.getInt("surgeonId");
			if (id == 0) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
