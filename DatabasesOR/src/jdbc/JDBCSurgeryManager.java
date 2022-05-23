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

	@Override
	public void addSurgery(Surgery s) {

		try {
			String sql = "INSERT INTO surgery (patient, surgeons, opr, type) VALUES (?,?,?,?)";
			// use preparedStmt so nothing damages the database
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			prep.setObject(1, s.getPatient());
			prep.setObject(2, s.getSurgeons());
			prep.setObject(3, s.getOpr());
			prep.setString(4, s.getType());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	//TODO method of surgeries that will be accepted

		@Override
		public void unassign ( int surgeryId) {
			try {
			String sql = "DELETE * FROM Surgery WHERE id =?";
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);
			pr.setInt(1, surgeryId);
		
			}catch (SQLException e) {
				e.printStackTrace();
			}
						
			
		}

public List<Surgery> listSurgeries (int id){
	List <Surgery> surgeries = new ArrayList<Surgery>();
	try {
		Statement stmt= manager.getConnection().createStatement();
		String sql= "SELECT surgery.id,type,date,schedule.id time FROM surgery JOIN schedule ON surgery.id= schedule.id WHERE id="+ id;
		ResultSet rs=stmt.executeQuery(sql);
		while(rs.next()) {
			//when using a join put the number of the column instead of the name. Start with 1 instead of 0
			Integer id2=rs.getInt(1);
			String type = rs.getString(2);
			Date date=rs.getDate(4);
			Time time=rs.getTime(5);
			Integer id3=rs.getInt(6);
			Schedule schedule=new Schedule(id3,date,time);
			Surgery s = new Surgery (id2,type,schedule);
			
			surgeries.add(s);
			
		}
		rs.close();
		stmt.close();
	}
	catch(Exception ex){
		ex.printStackTrace();
		
	}
	return surgeries;
	
}

//COMPROBACION select lo q sea de surgery where time= ? if null... nueva surgery

	public boolean checkOPR (Schedule s, int oprId) {
		//lista que guarde todos los oprid que estan ocupados en ese schedule
		//luego recorro lista y voy comprobando si coincide con el oprId que le he pasado a la funcion
		//si coincide devuelvo true y el doctor elige otro oprId desde el menu
		//si no coincide devuelvo false y me deja seleccionar esta oprid en el menu
		
		ArrayList <Integer> oprsOccupied= new ArrayList <Integer>();
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT oprId FROM surgery JOIN schedule ON surgery.scheduleId= schedule.id WHERE schedule.Date= ? AND schedule.TIME = ?";
			//TODO add date and time to this query
			ResultSet rs= stmt.executeQuery(sql);
			while(rs.next()) {
				//TODO check if this is correct
				Integer oprOccupied=rs.getInt(4);
				oprsOccupied.add(oprOccupied);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
return true;
		
	}
	public boolean checkpatient (Schedule s, Patient p) {
		ArrayList <Integer> patientsOccupied= new ArrayList <Integer>();
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT patientId FROM surgery JOIN schedule ON surgery.scheduleId= schedule.id WHERE schedule.Date= ? AND schedule.TIME = ?";
			ResultSet rs= stmt.executeQuery(sql);
			//TODO finish and check if correct
			while(rs.next()) {
				Integer oprOccupied=rs.getInt(2);
				patientsOccupied.add(oprOccupied);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
return true;
		
	}
	public boolean checksurgeon (Schedule s, Surgeon surg) {
		ArrayList <Integer> surgeonsOccupied= new ArrayList <Integer>();
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT surgeonId FROM surgery JOIN schedule ON surgery.scheduleId= schedule.id WHERE schedule.Date= ? AND schedule.TIME = ?";
			ResultSet rs= stmt.executeQuery(sql);
			//TODO finish and check if correct
			while(rs.next()) {
			Integer oprOccupied=rs.getInt(3);
			surgeonsOccupied.add(oprOccupied);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
return true;
		
	}
}

