package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCManager {

	private static Connection c;

	public JDBCManager() {

		try {
			// OPEN DATABASE CONNECTION
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/Hospital.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("database connection opened");

			this.createTables();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			System.out.println("class not found");
		}

	}

	// CLOSE DATABASE CONNECTION
	public static void disconnect() {
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// ACCESS TO THE CONNECTION FOR OTHER CLASSES
	public Connection getConnection() {
		return c;
	}

	// CREATES TABLES
	private void createTables() {

		try {
			// TABLE SCHEDULE
			Statement stm = c.createStatement();
			String sql = "CREATE TABLE schedule" + "(id INTEGER PRIMARY KEY AUTOINCREMENT, date DATE ,"
					+ " startTime TIME, finishTime TIME )";
			stm.executeUpdate(sql);

			// TABLE OPR
			sql = "CREATE TABLE opr" + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + "floor INTEGER NOT NULL ,"
					+ " number INTEGER NOT NULL )";
			stm.executeUpdate(sql);

			// TABLE PATIENT
			sql = "CREATE TABLE patient(id INTEGER PRIMARY KEY AUTOINCREMENT,  name TEXT NOT NULL ,"
					+ "email TEXT NOT NULL UNIQUE,medstat TEXT NOT NULL,dob DATE,sex TEXT )";
			stm.executeUpdate(sql);

			// TABLE SURGEON
			sql = "CREATE TABLE surgeon (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL ,"
					+ "email TEXT NOT NULL, medstat TEXT NOT NULL, pagerNumber INTEGER NOT NULL, tlfNumber INTEGER )";
			stm.executeUpdate(sql);

			// TABLE SURGERY
			sql = "CREATE TABLE surgery(id INTEGER PRIMARY KEY AUTOINCREMENT, type TEXT NOT NULL,"
					+ "patientId INTEGER REFERENCES patient(id) ON DELETE SET NULL,"
					+ "oprId INTEGER REFERENCES opr(id) ON DELETE SET NULL,"
					+ "scheduleId INTEGER REFERENCES schedule(id)ON DELETE SET NULL)";
			stm.executeUpdate(sql);

			// TABLE SURGERY-SURGEONS
			sql = "CREATE TABLE surgeonSurgery ("
					+ "surgeonId INTEGER NOT NULL,"
					+ "surgeryId INTEGER NOT NULL,"
					+ "PRIMARY KEY (surgeonId,surgeryId),"
					+ "FOREIGN KEY (surgeonId) REFERENCES surgeon(id) ON DELETE CASCADE,"
					+ "FOREIGN KEY (surgeryId) REFERENCES surgery(id) ON DELETE CASCADE"
					+ ")";
			stm.executeUpdate(sql);

			// WE ASSUME THAT OUR HOSPITALS HAS 6 OPR
			sql = "INSERT INTO opr (floor, number) VALUES (1, 1)";
			stm.executeUpdate(sql);
			sql = "INSERT INTO opr (floor, number) VALUES (1, 2)";
			stm.executeUpdate(sql);
			sql = "INSERT INTO opr (floor, number) VALUES (1, 3)";
			stm.executeUpdate(sql);
			sql = "INSERT INTO opr (floor, number) VALUES (2, 1)";
			stm.executeUpdate(sql);
			sql = "INSERT INTO opr (floor, number) VALUES (2, 2)";
			stm.executeUpdate(sql);
			sql = "INSERT INTO opr (floor, number) VALUES (2, 3)";
			stm.executeUpdate(sql);
			
			//SURGEONS
			sql = "INSERT INTO surgeon (name, email, medstat, pagerNumber, tlfNumber) VALUES ('tomas', 'tomasalvarez', 'cardiology', 123, 6789)";
			stm.executeUpdate(sql);
			sql = "INSERT INTO surgeon (name, email, medstat, pagerNumber, tlfNumber) VALUES ('cristina', 'cristinaj', 'neurology', 234, 6391)";
			stm.executeUpdate(sql);
			sql = "INSERT INTO surgeon (name, email, medstat, pagerNumber, tlfNumber) VALUES ('paola', 'paolad', 'trauma', 345, 6493)";
			stm.executeUpdate(sql);
			sql = "INSERT INTO surgeon (name, email, medstat, pagerNumber, tlfNumber) VALUES ('jaime', 'jaimesol', 'oncology', 456, 6895)";
			stm.executeUpdate(sql);
			sql = "INSERT INTO surgeon (name, email, medstat, pagerNumber, tlfNumber) VALUES ('eduardo', 'eduardog', 'pediatrics', 567, 6673)";
			stm.executeUpdate(sql);
			
			

		} catch (Exception e) {

			if (!e.getMessage().contains("already exists")) {
				e.printStackTrace();
			}

		}

	}

}
