package jdbc;

import java.sql.PreparedStatement;

import interfaces.SurgManager;
import pojos.Surgery;

public class JDBCSurgeryManager implements SurgManager {

	private JDBCManager manager;

	public JDBCSurgeryManager(JDBCManager m) {
		this.manager = m;
	}

	@Override
	public void addSurgery(Surgery s) {

		try {
			String sql = "INSERT INTO surgery (patient, surgeon, opr, type) VALUES (?,?,?,?)";
			// use preparedStmt so nothing damages the database
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			prep.setObject(1, s.getPatient());
			prep.setObject(2, s.getSurgeon());
			prep.setObject(3, s.getOpr());
			prep.setString(4, s.getType());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
