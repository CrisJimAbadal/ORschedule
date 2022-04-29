package jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import interfaces.SManager;
import pojos.Availability;
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
		Surgeon s = null;
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
	public void updateSurgeon(Surgeon s) {
		try {
			String sql = "UPDATE surgeon" + "SET name = ?" + " medstat = ?" + " pagernumber = ?" + " tlfnumber = ?";
			// TODO same as the other
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);
			pr.setString(1, s.getName());
			pr.setString(2, s.getMedstat());
			pr.setInt(3, s.getPagerNumber());
			pr.setInt(4, s.getTlfNumber());
			pr.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@Override
	public void deleteSurgeon(int surgeonId) {
		try {
			String sql = "DELETE FROM Surgeons WHERE id=?";
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);
			pr.setInt(1, surgeonId);
			pr.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

//TODO check if this method is correct
	@Override
	public void acceptSurgery(String s) {
		try {
			String sql = "UPDATE surgery" + "SET conductSurgery = TRUE";
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);
			pr.setBoolean(3, true);
			pr.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@Override
	public void updateAvailability(Availability a) {
		try {
			String sql = "UPDATE surgAvailability" + "SET date = ?" + "time=?";
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);
			pr.setDate(1, a.getDate());
			pr.setTime(2, a.getTime());
			pr.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
