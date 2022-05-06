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

	// añade un paciente a la base de datos
	@Override
	public void addPatient(Patient p) {
		try {
			String sql = "INSERT INTO patient (name, medstat, dob, sex) VALUES (?,?,?,?)";
			// use preparedStmt so nothing damages the database
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			prep.setString(1, p.getName());
			prep.setString(2, p.getMedstat());
			prep.setDate(3, (Date) p.getDob());
			prep.setString(4, p.getSex());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// muestra la info de todos los pacientes
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

				Patient p = new Patient(id, name, medstat, dob, sex);
				patients.add(p);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return patients;
	}

	// muestra nombre y id de todos los pacientes
	@Override
	public Integer listPatientsId(String email) {
		Integer id= null;
		try {

			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT id FROM patient WHERE email = " +email;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {

				 id = rs.getInt("id");
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	// busca un paciente por id
	@Override
	public Patient searchPatient(int id) {
		Patient p = null;
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELCT * FROM patient WHERE id = " + id;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String name = rs.getString("name");
				String medstat = rs.getString("medstat");

				p = new Patient(id, name, medstat);

			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return p;
	}

	// info that the patient can see
	@Override
	public Patient showPatient(int id) {

		Patient p = null;

		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM patient WHERE id = " + id;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {

				String name = rs.getString("name");
				String medstat = rs.getString("medstat");
				String email = rs.getString ("email");
				Date dob = rs.getDate("Dob");
				String sex = rs.getString("sex");
				//TODO surgeries????????????????????

				p = new Patient( name, medstat, email, dob, sex);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	@Override
	public void updatePatient(Patient p) {
		try {
			String sql = "UPDATE patient " + "SET name = ?" + " medstat = ?" + "email = ?" +
					" dob = ?" + " sex = ?";
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);
			
			pr.setString(1, p.getName());
			pr.setString(2, p.getMedstat());
			pr.setString(3, p.getEmail());
			pr.setDate(4, (LocalDate) p.getDob());
			pr.setString(5, p.getSex());
			
			pr.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@Override
	public void deletePatient(int patientId) {
		try {
			String sql = "DELETE FROM Patients WHERE id=?";
			PreparedStatement pr = manager.getConnection().prepareStatement(sql);
			pr.setInt(1, patientId);
			pr.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
