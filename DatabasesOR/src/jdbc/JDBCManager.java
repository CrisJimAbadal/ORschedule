package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCManager {

	private static Connection c;

	public JDBCManager() {
		try {
			// open database connection
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager.getConnection("jdbc:sqlite:./db/Hospital.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("database connection opened");
			// create tables each time
			this.createTables();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			System.out.println("class not found");
		}

	}

	public static void disconnect() {
		try {
			// close database connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		// for other classes to access the connection
		return c;
	}

	private void createTables() {

		try {

			// CREATES TABLES
			// TABLE AVAILABILITY
			Statement stm = c.createStatement();
			String sql = "CREATE TABLE schedule" + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + "date DATE ,"
					+ " time TIME )";
			stm.executeUpdate(sql);

			// TABLE OPR
			sql = "CREATE TABLE opr" + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + "floor INTEGER NOT NULL ,"
					+ " number INTEGER NOT NULL )";
			stm.executeUpdate(sql);

			// TABLE OPRAVAILABILITY
			sql = "CREATE TABLE opravailability" + "(orId INTEGER REFERENCES opr(id) ON DELETE CASCADE,"
					+ "avId INTEGER REFERENCES availability(id) ON DELETE CASCADE," + "PRIMARY KEY (orId, avId) )";
			stm.executeUpdate(sql);

			// TABLE PATIENT

			sql = "CREATE TABLE patient" + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT NOT NULL ,"
					+ "email TEXT NOT NULL" + "medstat TEXT NOT NULL," + "dob DATE," + "sex TEXT NOT NULL," + " number INTEGER NOT NULL )";
			stm.executeUpdate(sql);

			// TABLE SURGEON AVAILABILITY
			sql = "CREATE TABLE surgAvailability" + "(surgId INTEGER REFERENCES surgeon(id) ON DELETE CASCADE,"
					+ "avId INTEGER REFERENCES availability (id) ON DELETE CASCADE," + "PRIMARY KEY (surgId, avId) )";
			stm.executeUpdate(sql);

			// TABLE SURGEON
			sql = "CREATE TABLE surgeon" + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT NOT NULL ,"
					+ "medstat TEXT NOT NULL," + "pagerNumber INTEGER UNIQUE NOT NULL," + "tlfNumber INTEGER UNIQUE )";
			stm.executeUpdate(sql);

			// TABLE SURGERY
			sql = "CREATE TABLE surgery" + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + "type TEXT,"
					+ "patientId INTEGER REFERENCES patient(id) ON DELETE SET NULL,"
					+ "surgeonID INTEGER REFERENCES surgeon(id) ON DELETE SET NULL,"
					+ "oprId INTEGER REFERENCES opr(id) ON DELETE SET NULL" + "acceptSurgery BOOLEAN )";
			stm.executeUpdate(sql);

		} catch (Exception e) {

			if (!e.getMessage().contains("already exists")) {
				e.printStackTrace();
			}

		}

	}

}
