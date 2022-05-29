package jdbc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import interfaces.PManager;
import pojos.Patient;

public class JDBCPatientManager implements PManager {

	private JDBCManager manager;

	public JDBCPatientManager(JDBCManager m) {
		this.manager = m;
	}

	// ADD PATIENT TO THE DATABASE
	@Override
	public void addPatient(Patient p) {
		try {
			String sql = "INSERT INTO patient (name, medstat, email, dob, sex) VALUES (?,?,?,?,?)";
			// use preparedStmt so nothing damages the database
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			prep.setString(1, p.getName());
			prep.setString(2, p.getMedstat());
			prep.setString(3, p.getEmail());
			prep.setDate(4, p.getDob());
			prep.setString(5, p.getSex());
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// PATIENTS INFO for the doctor
	@Override
	public List<Patient> listPatients() {
		List<Patient> patients = new ArrayList<Patient>();
		try {

			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM patient";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {

				Integer id = rs.getInt("id");
				String name = rs.getString("name");
				String medstat = rs.getString("medstat");
				Date dob = rs.getDate("dob");
				String sex = rs.getString("sex");
				String email=rs.getString("email");
				
				Patient p = new Patient(id, name, medstat, dob, sex,email);
		
				patients.add(p);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return patients;
	}

	// FINDS A PATIENT BY ITS ID
	@Override
	public Patient searchPatient(int id) {
		Patient p = null;
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM patient WHERE id = " + id;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String name = rs.getString("name");
				String medstat = rs.getString("medstat");

				p = new Patient(id, name, medstat);
				System.out.println("estas en searchPatient");
				
			}
			System.out.println("estas fuera del while");
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("estas antes del return");
		return p;
	}

	// INFO THAT THE PATIENT CAN SEE
	@Override
	public Patient showPatient(int id) {

		Patient p = null;
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM patient WHERE id=" +id;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {

				String name = rs.getString("name");
				String medstat = rs.getString("medstat");
				Date dob = rs.getDate("dob");
				String sex = rs.getString("sex");
				String email=rs.getString("email");
				
				p = new Patient( name,email, medstat, dob, sex);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	// UPDATE PATIENT'S INFORMATION
	@Override
	public void updatePatient(Patient p) {
		try {
			String sql = "UPDATE patient SET name = ?, medstat = ?";
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);

			pr.setString(1, p.getName());
			pr.setString(2, p.getMedstat());
			
			
			pr.executeUpdate();
			pr.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}


}
