package Menu;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import pojos.Patient;
import pojos.Surgeon;

public class surgeonManager {
	
	public static List<Surgeon> surgeonMenu() {
		
		List<Surgeon> surgeons = new ArrayList<Surgeon>();
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM surgeons";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String name = rs.getString("name");
				String medstat = rs.getString("medstat");
				Integer pagerNumber = rs.getInt("pagerNumber");
				Integer telephone = rs.getInt("telephone"); 
				Surgeon p = new Surgeon(id, name, medstat, pagerNumber, telephone);
				surgeons.add(p);
			}
			rs.close();
				
				stmt.close();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return surgeons;
	}

}
