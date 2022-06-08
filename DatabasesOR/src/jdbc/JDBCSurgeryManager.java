package jdbc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


import interfaces.OPRManager;
import interfaces.PManager;
import interfaces.SManager;
import interfaces.ScheduleManager;
import interfaces.SurgManager;
import pojos.OPR;
import pojos.Patient;
import pojos.Schedule;
import pojos.Surgeon;
import pojos.Surgery;

public class JDBCSurgeryManager implements SurgManager {

	private JDBCManager manager;
	private static PManager patientManager;
	private static SManager surgeonManager;
	private static OPRManager oprManager;
	private static ScheduleManager scheduleManager;

	public JDBCSurgeryManager(JDBCManager m) {
		this.manager = m;
	}

	// ADD SURGERY TO THE DATABASE
	@Override
	public void addSurgery(Surgery s) {
		Patient p = s.getPatient();
		OPR o = s.getOpr();
		List<Surgeon> surgeons = s.getSurgeons();
		Schedule sc=s.getSchedule();
		
			System.out.println(surgeons);
		

		try {
			

			String sql = "INSERT INTO surgery (patientId, oprId, type, scheduleId) VALUES (?,?,?,?)";
			// use preparedStmt so nothing damages the database
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			prep.setObject(1, p.getId());
			prep.setObject(2, o.getId());
			prep.setString(3, s.getType());
			prep.setInt(4, sc.getId());
			prep.executeUpdate();
			for (Surgeon surgeon : surgeons) {

				String sql2 = "INSERT INTO surgeonSurgery (surgeryId, surgeonId) VALUES (?,?)";
				// use preparedStmt so nothing damages the database
				PreparedStatement prep2 = manager.getConnection().prepareStatement(sql2);
				prep2 = manager.getConnection().prepareStatement(sql2);
				prep2.setObject(1, s.getId());
				prep2.setObject(2, surgeon.getId());
				prep2.executeUpdate();
				prep2.close();
					
			}
			
			prep.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// DELETE SURGERY (done by the doctor)
	@Override
	public void unassign(int surgeryId) {
		try {
			String sql = "DELETE  FROM surgery WHERE id =?";
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);
			pr.setInt(1, surgeryId);
			pr.executeUpdate();
			pr.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// LIST ASSIGNED SURGERIES TO THE PATIENT
	public List<Surgery> listSurgeries(int id) {
		List<Surgery> surgeries = new ArrayList<Surgery>();
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT  surgery.type, schedule.date, schedule.startTime,schedule.finishTime FROM surgery JOIN schedule ON surgery.id= schedule.id WHERE patientId= "
					+ id;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// when using a join put the number of the column instead of the name. Start
				// with 1 instead of 0

				String type = rs.getString(1);
				Date date = rs.getDate(2);

				Time startTime = rs.getTime(3);
				Time finishTime = rs.getTime(4);

				Schedule schedule = new Schedule(date, startTime, finishTime);
				Surgery s = new Surgery(type, schedule);

				surgeries.add(s);
			}
			rs.close();
			stmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return surgeries;

	}

	// LIST SURGERIES SO THE DOCTOR CAN DELETE ONE
	@Override
	public List<Surgery> listSurgeries() {
		List<Surgery> surgeries = new ArrayList<Surgery>();
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT surgery.id, surgery.type, schedule.date, schedule.id, startTime FROM surgery JOIN schedule ON surgery.id = schedule.id ";
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
			pr.setTime(3, s.getStartTime());
			pr.setTime(4, s.getFinishTime());
			pr.setTime(5, s.getStartTime());
			pr.setTime(6, s.getFinishTime());
			pr.setTime(7, s.getStartTime());
			pr.setTime(8, s.getStartTime());
			ResultSet rs = pr.executeQuery();
			int id=0;
			while(rs.next()) {
			 id = rs.getInt(1);
			}

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
		Patient patient = p;

		try {
			String sql = "SELECT patientId FROM surgery JOIN schedule ON surgery.scheduleId= schedule.id "
					+ "WHERE schedule.Date= ? AND surgery.patientId=?"
					+ " AND ((schedule.startTime<= ? AND schedule.finishTime>= ?) " + // ?1 startTime ?2 finishTime
					"OR (schedule.startTime>= ? AND schedule.finishTime<= ?) " + // ?1 startTime ?2 finishTime
					"OR (schedule.startTime>= ? AND schedule.startTime<= ?) " + // ? = startTime ?2finishTIme
					"OR (schedule.startTime<= ? AND schedule.finishTime>=?))" // ? =starttime
			;
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);
			pr.setDate(1, s.getDate());
			pr.setInt(2, patient.getId());
			pr.setTime(3, s.getStartTime());
			pr.setTime(4, s.getFinishTime());
			pr.setTime(5, s.getStartTime());
			pr.setTime(6, s.getFinishTime());
			pr.setTime(7, s.getStartTime());
			pr.setTime(8, s.getStartTime());
			ResultSet rs = pr.executeQuery();
			int id=0;
			while(rs.next()) {
			 id = rs.getInt(1);
			}
			if (id == 0) {
				return false;
				// patient is not occupied at that schedule

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	public boolean checksurgeon(Schedule s, Surgeon surg) {

		Surgeon surgeon = surg;

		try {
			String sql = "SELECT * FROM surgery JOIN schedule ON surgery.scheduleId= schedule.id JOIN surgeonSurgery ON surgery.id= surgeonSurgery.surgeryId JOIN surgeon ON surgeon.id=surgeonSurgery.surgeonId "
					+ "WHERE schedule.Date= ? AND surgeonSurgery.surgeonId=?"
					+ " AND ((schedule.startTime<= ? AND schedule.finishTime>= ?) " + // ?1 startTime ?2 finishTime
					"OR (schedule.startTime>= ? AND schedule.finishTime<= ?) " + // ?1 startTime ?2 finishTime
					"OR (schedule.startTime>= ? AND schedule.startTime<= ?) " + // ? = startTime ?2finishTIme
					"OR (schedule.startTime<= ? AND schedule.finishTime>=?))" // ? =starttime
			;
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);
			pr.setDate(1, s.getDate());
			pr.setInt(2, surgeon.getId());
			pr.setTime(3, s.getStartTime());
			pr.setTime(4, s.getFinishTime());
			pr.setTime(5, s.getStartTime());
			pr.setTime(6, s.getFinishTime());
			pr.setTime(7, s.getStartTime());
			pr.setTime(8, s.getStartTime());
			ResultSet rs = pr.executeQuery();

			int id=0;
			while(rs.next()) {
			 id = rs.getInt(1);
			}

			if (id == 0) {
				return false;
				// surgeon is not occupied at that schedule

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public Surgery chooseSurgery(int id)  {
		Surgery s = null;
		List<Surgeon> surgeons = new ArrayList<Surgeon>();
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT surgery.*, surgeon.id FROM surgery JOIN surgeonSurgery ON surgeonSurgery.surgeryId= surgery.id JOIN surgeon ON surgeonSurgery.surgeonId=surgeon.id WHERE surgery.id = " + id;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String type = rs.getString("type");
				Integer pId = rs.getInt("patientId");

				Integer sId = rs.getInt("surgeonId");
				Integer oprId = rs.getInt("oprId");
				Integer scheduleId = rs.getInt("scheduleId");

				Patient patient = patientManager.searchPatientbyId(pId);

				Surgeon surgeon = surgeonManager.chooseSurgeon(sId);
				surgeons.add(surgeon);

				OPR opr = oprManager.searchOPR(oprId);
				
				Schedule schedule = scheduleManager.showSchedule(surgeon.getId());
		

				s = new Surgery(id, type, patient, opr, surgeons, schedule);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return s;
	}
	public int getIdSurgery() {
		int id = 0;
		try {
			String sql = "SELECT MAX(id) FROM surgery";
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
