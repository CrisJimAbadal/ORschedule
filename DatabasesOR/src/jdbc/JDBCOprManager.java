package jdbc;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import interfaces.OPRManager;
import pojos.OPR;
import pojos.Surgeon;

public class JDBCOprManager implements OPRManager {

	private JDBCManager manager;

	public JDBCOprManager(JDBCManager m) {
		this.manager = m;
	}

	//LIST ALL THE OPRs
	@Override
	public List<OPR> listOprs() {
		List<OPR> oprs = new ArrayList<OPR>();
		try {

			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM opr";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Integer id = rs.getInt("id");
				Integer floor = rs.getInt("floor");
				Integer number = rs.getInt("number");

				OPR opr = new OPR(id, floor, number);
				oprs.add(opr);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oprs;
	}
	
	//TODO can we delete this?
/*	//SHOW OPR
	@Override
	public OPR showOPR(int id) {
		OPR opr = null;
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM opr WHERE id = " + id;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {

				Integer floor = rs.getInt("floor");
				Integer number = rs.getInt("number");

				opr = new OPR(id, floor, number);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return opr;
	}*/
	
	//SEARCH and return OPR by id
	@Override
	public OPR searchOPR(int id) {
		OPR opr = null;
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM opr WHERE id = " + id;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Integer floor = rs.getInt("floor");
				Integer number = rs.getInt("number");

				opr = new OPR(id, floor, number);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return opr;
	}
   
	

}
