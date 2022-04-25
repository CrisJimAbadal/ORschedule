package Menu;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import pojos.Patient;

public class patientManager {

	public static List<Patient> patientMenu() {
		
		List<Patient> patients = new ArrayList<Patient>();
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM patients";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Integer id = rs.getInt("id");
				Integer age = rs.getInt("age");
				String sex = rs.getString("sex");
				String name = rs.getString("name");
				String medstat = rs.getString("medstat");
				Patient p = new Patient(id, age, sex, name, medstat);
				patients.add(p);
			}
			rs.close();
				
				stmt.close();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return patients;
	}
		
		
		
	
	
	

}
