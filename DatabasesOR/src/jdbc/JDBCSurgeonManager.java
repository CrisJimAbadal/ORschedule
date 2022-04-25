package jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import interfaces.SManager;
import pojos.Patient;
import pojos.Surgeon;

public class JDBCSurgeonManager implements SManager {

	private JDBCManager manager;

	public JDBCSurgeonManager(JDBCManager m) {
		this.manager = m;
	}

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

	@Override
	public List<Surgeon> listSurgeons() {
		List<Surgeon> surgeons = new ArrayList<Surgeon>();
		try {

			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM surgeon";
			ResultSet rs = stmt.executeQuery(sql);
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
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return surgeons;

	}

	@Override
	public Surgeon searchSurgeon(int id) {
		Surgeon s = null;
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELCT * FROM surgeon WHERE id = " + id;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String name = rs.getString("name");
				String medstat = rs.getString("medstat");
				// availability???????????????????????????????????

				s = new Surgeon(id, name, medstat);

			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return s;
	}

	@Override
	public Surgeon showSurgeon(int num) {
		Surgeon s= null;
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM surgeon WHERE pagerNumber = " + num;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {

				String name = rs.getString("name");
				String medstat = rs.getString("medstat");
				Integer pagerNumber = rs.getInt("pagerNumber");
				Integer tlfNumber = rs.getInt("tlfNumber");

				s = new Surgeon(name, medstat, pagerNumber, tlfNumber);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	@Override
	public void updateSurgeon(String name, String medstat, Integer pagerNumber, Integer tlfNumber) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSurgeon(Surgeon s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void acceptSurgery(String s) {
		// TODO Auto-generated method stub

	}

}
