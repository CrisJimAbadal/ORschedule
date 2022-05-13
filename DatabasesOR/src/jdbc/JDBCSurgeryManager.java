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
import pojos.Schedule;
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
		public void unassign (int surgeonId, int patientId) {
			try {
			String sql = "DELETE * FROM Surgery WHERE surgeonId=? AND patientId=?";
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);
			pr.setInt(1,surgeonId);
			pr.setInt(2, patientId);
			// TODO revisar
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
			//TODO finish
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

}
