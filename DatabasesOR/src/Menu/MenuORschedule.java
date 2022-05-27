package Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.List;

import interfaces.OPRManager;
import interfaces.PManager;
import interfaces.SManager;
import interfaces.ScheduleManager;
import interfaces.SurgManager;
import interfaces.UserManager;
import jdbc.JDBCManager;
import jdbc.JDBCPatientManager;
import jdbc.JDBCScheduleManager;
import jdbc.JDBCSurgeonManager;
import jdbc.JDBCSurgeryManager;
import pojos.OPR;
import pojos.Patient;
import pojos.Role;
import pojos.Schedule;
import pojos.Surgeon;
import pojos.Surgery;
import pojos.User;
import jpa.JPAUserManager; //Is this the right package??

public class MenuORschedule {

	private static BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
	private static PManager patientManager;
	private static SManager surgeonManager;
	private static SurgManager surgeryManager;
	private static OPRManager oprManager;
	private static UserManager userManager;
	private static ScheduleManager scheduleManager;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static void main(String[] args) {

		System.out.println("WELCOME TO THE OR SHCEDULE");
		JDBCManager jdbcManager = new JDBCManager();

		// initialize database JDBC
		patientManager = new JDBCPatientManager(jdbcManager);
		surgeonManager = new JDBCSurgeonManager(jdbcManager);
		surgeryManager = new JDBCSurgeryManager(jdbcManager);
		scheduleManager = new JDBCScheduleManager(jdbcManager);
		scheduleManager = new JDBCScheduleManager(jdbcManager);
		// initialize database JPA
		userManager = new JPAUserManager();

		principalMenu();
	}

	private static void principalMenu() {
		try {

			do {
				System.out.println("PLEASE CHOOSE AN OPTION: ");
				System.out.println("1. PATIENT");
				System.out.println("2. SURGEON");
				System.out.println("3. DOCTOR");
				System.out.println("O. EXIT");
				int choice = Integer.parseInt(read.readLine());

				switch (choice) {

				case 1:

					patientMenu();
					break;
				case 2:

					surgeonMenu();
					break;
				case 3:

					doctorMenu();
					break;
				case 0:

					JDBCManager.disconnect();
					System.exit(0);

				default:
					break;
				}
			} while (true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void patientMenu() {
		try {
			do {
				System.out.println("CHOOSE AN OPTION: ");
				System.out.println("1. REGISTER");
				System.out.println("2. LOG IN");
				System.out.println("O. BACK");
				int choice = Integer.parseInt(read.readLine());

				switch (choice) {

				case 1:
					// REGISTER (add patient)
					createPatient();
					// TODO if already exists, don't add
					break;
				case 2:
					// LOG IN as patient
					logIn();

					break;
				case 0:

					principalMenu();
				default:
					break;
				}
			} while (true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void surgeonMenu() {
		try {
			do {
				System.out.println("CHOOSE AN OPTION: ");
				System.out.println("1. REGISTER");
				System.out.println("2. LOG IN");
				System.out.println("0. Exit");

				int choice = Integer.parseInt(read.readLine());

				switch (choice) {

				case 1:
					// Call method REGISTER
					createSurgeon();
					//TODO if already exists don't add
					break;
				case 2:
					// LOG IN as surgeon
					logIn();

					break;

				case 0:
					// EXIT
					principalMenu();
				default:
					break;
				}
			} while (true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void doctorMenu() {

		try {
			do {
				System.out.println("CHOOSE AN OPTION: "); //
				System.out.println("1. LOG IN");
				System.out.println("0. Exit");

				int choice = Integer.parseInt(read.readLine());

				switch (choice) {

				case 1:
					String originalPassword = "g4nd4lf";
					System.out.println("Introduce the most famous password in the world:");
					String password = read.readLine();
					if (password.equals("g4nd4lf") || password.equals("G4ND4LF")) {
						DMenu();
					} else {
						System.out.println("error");
						principalMenu();
					}

					break;
				case 0:

					// EXIT
					principalMenu();
				default:
					break;
				}
			} while (true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void PMenu(int id) {

		try {
			do {
				System.out.println("CHOOSE AN OPTION: ");
				System.out.println("1. Check information and update");
				System.out.println("2. Check surgeries");

				System.out.println("0. Exit");

				int choice = Integer.parseInt(read.readLine());

				switch (choice) {

				case 1:

					updatePatientInfo(id);
					break;
				case 2:

					checksurgeries(id);
					break;
				case 0:

					// EXIT
					patientMenu();
				default:
					break;
				}
			} while (true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void SMenu(int sId) {

		try {
			do {
				System.out.println("CHOOSE AN OPTION: ");
				System.out.println("1. Check information and update");
				System.out.println("2. Check schedule");
				System.out.println("0. Exit");

				int choice = Integer.parseInt(read.readLine());

				switch (choice) {

				case 1:

					updateSurgeonInfo(sId);
					break;

				case 2:

					surgeonManager.showSchedules(sId);
					break;

				case 0:
					// EXIT
					surgeonMenu();
				default:
					break;
				}
			} while (true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void DMenu() {

		try {
			do {
				System.out.println("CHOOSE AN OPTION: ");
				System.out.println("1. Create a surgery");
				System.out.println("2. Delete a surgery");
				System.out.println("0. Exit");

				int choice = Integer.parseInt(read.readLine());

				switch (choice) {

				case 1:

					createSurgery();
					break;

				case 2:

					deleteSurgery();
					break;

				case 0:
					// EXIT
					doctorMenu();
				default:
					break;
				}
			} while (true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createPatient() throws Exception {

		System.out.println("Input patient's information: ");
		System.out.println("Name: ");
		String name = read.readLine();
		System.out.println("Medstat: ");
		String medstat = read.readLine();
		System.out.println("Email: ");
		String email = read.readLine();
		System.out.println("Date of birth (yyyy-mm-dd): ");
		String dob = read.readLine();
		LocalDate dobDate = LocalDate.parse(dob, formatter);
		System.out.println("Sex: ");
		String sex = read.readLine();

		System.out.println("Password: ");
		String password = read.readLine();
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();

		// CREATE PATIENT AND ADD TO JPA
		User u = new User(email, digest);
		Role role = userManager.getRole("patient");
		u.setRole(role);
		role.addUser(u);
		userManager.newUser(u);

		// CREATE PATIENT AND ADD TO JDBC
		Patient patient = new Patient(name, medstat, email, Date.valueOf(dobDate), sex);
		patientManager.addPatient(patient);
	}

	public static void createSurgeon() throws Exception {

		System.out.println("Input information: ");
		System.out.println("Name: ");
		String name = read.readLine();
		System.out.println("Medstat: ");
		String medstat = read.readLine();
		System.out.println("Pager number: ");
		Integer pagerNumber = Integer.parseInt(read.readLine());
		System.out.println("Telephone number: ");
		Integer tlfNumber = Integer.parseInt(read.readLine());

		System.out.println("Password: ");
		String password = read.readLine();
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();

		// CREATE SURGEON AND ADD TO JPA
		User u = new User(name, digest);
		Role role = userManager.getRole("surgeon");
		u.setRole(role);
		role.addUser(u);
		userManager.newUser(u);

		// CREATE SURGEON AND ADD TO JDBD
		Surgeon surgeon = new Surgeon(name, medstat, pagerNumber, tlfNumber);
		surgeonManager.addSurgeon(surgeon);
	}

	// LOG IN METHOD
	public static void logIn() throws IOException {

		System.out.println("Insert your email: ");
		String email = read.readLine();

		System.out.println("Input your password: ");
		String passwrd = read.readLine();

		// returns a user, if null = user is not found
		User user = userManager.checkPassword(email, passwrd);

		if (user == null) {
			System.out.println("User not found");
			principalMenu(); // returns to principal menu if the user does not exist
		}

		// check [depending on the type of user we open a different menu]
		if (user != null && user.getRole().equals("patient")) {
			PMenu(user.getId());
		}
		if (user != null && user.getRole().equals("surgeon")) {
			SMenu(user.getId());
		}

	}

	// UPDATE PATIEN'S INFO
	private static void updatePatientInfo(int id) throws Exception {

		// List patient info
		patientManager.showPatient(id);
		Patient p = patientManager.searchPatient(id);
		System.out.println("Update your information: ");
		// Ask for info, if empty keeps the one existing before
		String name = read.readLine();
		if (!name.equals("")) {
			p.setName(name);
		}
		String medstat = read.readLine();
		if (!medstat.equals("")) {
			p.setMedstat(medstat);
		}
		String newEmail = read.readLine();
		if (!newEmail.equals("")) {
			p.setEmail(newEmail);
		}
		patientManager.updatePatient(p);
	}

	// UPDATE SURGEON'S INFO
	private static void updateSurgeonInfo(int id) throws Exception {

		Surgeon s = surgeonManager.showSurgeon(id);
		System.out.println("Update your information: ");

		Integer pagerNumber = Integer.parseInt(read.readLine());
		if (!pagerNumber.equals("")) {
			s.setPagerNumber(pagerNumber);
		}
		Integer tlfNumber = Integer.parseInt(read.readLine());
		if (!tlfNumber.equals("")) {
			s.setTlfNumber(tlfNumber);
		}
		surgeonManager.updateSurgeon(s);
	}

	// CHECK ASSIGNED SURGERIES (patient)
	public static void checksurgeries(int patientId) throws IOException {

		if (surgeryManager.listSurgeries(patientId).isEmpty()) {

			System.out.println("There are no surgeries scheduled yet ");
		}
	}

	// CREATE SURGERY
	private static void createSurgery() {
		try {
			// 1) choose SCHEDULE
			Schedule s = chooseSchedule();

			// 2)choose PATIENT
			Patient p = choosePatient();
			// check if patient available at that schedule
			while (surgeryManager.checkpatient(s, p)) {
				// TRUE = not available
				System.out.println("The patient is not available at that time and date");
				// if patient not available-> choose another schedule
				System.out.println("Choose another schedule for that patient");
				s = chooseSchedule();
			}

			// 3) choose OPR
			int oprId = chooseOPR();

			while (surgeryManager.checkOPR(s, oprId)) {
				// TRUE = not available
				System.out.println("The OPR is not available at this schedule.");
				System.out.println("Please choose another one:");
				oprId = chooseOPR();
			}
			OPR opr = oprManager.searchOPR(oprId);

			// 4) SPECIALITY
			String specialty = p.getMedstat();

			// 5) choose SURGEONS specialized on the patient's medStat
			System.out.println("How many surgeons are going to participate? ");
			Integer numSurg = Integer.parseInt(read.readLine());
			List<Surgeon> surgeons = new ArrayList<Surgeon>();

			for (int i = 0; i < numSurg; i++) {
				Surgeon surg = chooseSurgeon(specialty);
				surgeons.add(surg);
			}

			// 6) TYPE of surgery (ex: transplant)
			System.out.println("Input the type of surgery:");
			String type = read.readLine();

			// CREATE THE SURGERY and add to the database
			Surgery surg = new Surgery(p, surgeons, opr, type, s);

			scheduleManager.addSchedule(s);
			surgeryManager.addSurgery(surg);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// CHOOSE SCHEDULE FOR THE SURGERY
	
	public static Schedule chooseSchedule() {

		Date date = null;
		Time startTime = null;
		Time finishTime = null;
		try {
			System.out.println("\nInsert a date (yyyy-MM-dd): ");
			date = Date.valueOf(read.readLine());
			System.out.println("Insert a start time: ");
			startTime = Time.valueOf(read.readLine());
			System.out.println("Insert a finish time: ");
			finishTime = Time.valueOf(read.readLine());
			if (finishTime.compareTo(startTime) > 0) {
				// finishtime occurs later-> correct
				Schedule s = new Schedule(date, startTime, finishTime);

			} else {

				System.out.println("The start and finish time are incorrect");
				System.out.println("Repeat the process please:");
				Schedule s=chooseSchedule();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		Schedule s = new Schedule(date, startTime, finishTime);
		return s;
	}

	// CHOOSE PATIENT FOR SURGERY
	public static Patient choosePatient() throws Exception {
		// TODO this doesn�t show anything, shows []-> check if now correct
		
		List<Patient> patients=patientManager.listPatients();
		
		//TODO why it doesn't get here?
		for(Patient patient: patients) {
			System.out.println(patient.toString());
			
		}
		
		System.out.println("choose a patient by its id: ");
		Integer patientId = Integer.parseInt(read.readLine());
		Patient p = patientManager.searchPatient(patientId);

		return p;
	}

	// CHOOSE SURGEON FOR SURGERY
	public static Surgeon chooseSurgeon(String specialty) throws Exception {

		System.out.println("choose a surgeon by its id: ");

		surgeonManager.listSurgeons(specialty);
		Integer surgeonId = Integer.parseInt(read.readLine());
		Surgeon s = surgeonManager.searchSurgeon(surgeonId);

		return s;
	}

	// CHOOSE OPR FOR SURGERY
	public static int chooseOPR() throws Exception {
		System.out.println("choose an opr by its id: ");
		oprManager.listOprs();
		Integer oprsId = Integer.parseInt(read.readLine());
		return oprsId;

	}

	// DELETE SURGERY
	private static void deleteSurgery() throws IOException {
		// 1)LIST SURGERIES
		surgeryManager.listSurgeries();
		System.out.println("Is there any surgery you want to delete? [1= Yes / 0= No]");
		int answer = Integer.parseInt(read.readLine());

		if (answer != 1 || answer != 0) {
			System.out.println("Please input the answer again.");
		}

		System.out.println("Input the id of the surgery you want to delete.");
		int surgeryId = Integer.parseInt(read.readLine());
		// 2)DELETE SURGERY
		surgeryManager.unassign(surgeryId);
	}

}
