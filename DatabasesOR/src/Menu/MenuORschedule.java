package Menu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import interfaces.PManager;
import interfaces.SManager;
import interfaces.SurgManager;
import jdbc.JDBCManager;
import jdbc.JDBCPatientManager;
import jdbc.JDBCSurgeonManager;
import jdbc.JDBCSurgeryManager;
import pojos.OPR;
import pojos.Patient;
import pojos.Surgeon;
import pojos.Surgery;

public class MenuORschedule {

	private static BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
	private static PManager patientManager;
	private static SManager surgeonManager;
	private static SurgManager surgeryManager;

	public static void main(String[] args) {

		System.out.println("WELCOME TO THE OR SHCEDULE");
		JDBCManager jdbcManager = new JDBCManager();
		// initialize database
		patientManager = new JDBCPatientManager(jdbcManager);
		surgeonManager = new JDBCSurgeonManager(jdbcManager);
		surgeryManager = new JDBCSurgeryManager(jdbcManager);
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
					// Call method patient menu
					patientMenu();

					break;
				case 2:
					// Call method surgeon menu
					surgeonMenu();
					break;
				case 3:
					// Call method doctor menu
					doctorMenu();

					break;
				case 0:
					JDBCManager.disconnect();
					System.exit(0);

				default:
					break;
				}
			} while (true);
			// TODO RETRIEVE DATA : END
			// CLOSE DATA BASE CONNECTION
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Menu's methods
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

					break;
				case 2:
					// TODO Call method log in
					// call PMenu();
					PMenu();

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

					break;
				case 2:
					// sugeonManager.logIn();
					// call SMenu
					SMenu();
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
				System.out.println("CHOOSE AN OPTION: ");
				System.out.println("1. LOG IN");
				System.out.println("0. Exit");

				int choice = Integer.parseInt(read.readLine());

				switch (choice) {

				case 1:
					// Call method LOG IN
					createSchedule();

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

	private static void PMenu() {

		try {
			do {
				System.out.println("CHOOSE AN OPTION: ");
				System.out.println("1. Check information");// check and update?
				System.out.println("2. Check surgeries");// check and update?

				System.out.println("0. Exit");

				int choice = Integer.parseInt(read.readLine());

				switch (choice) {

				case 1:

					updatePatientInfo();

					break;

				case 2:
					// if there's a surgery schedule show info
					// else ("no surgeries")

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

	private static void SMenu() {

		try {
			do {
				System.out.println("CHOOSE AN OPTION: ");
				System.out.println("1. Check information"); // check and update?
				System.out.println("2. Check schedule");// check and update?
				System.out.println("0. Exit");

				int choice = Integer.parseInt(read.readLine());

				switch (choice) {

				case 1:
					updateSurgeonInfo();

					break;

				case 2:
					// show schedule
					// ask to accept or cancel a surgery

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

	private static void createSchedule() {
		try {
			// show medical specialties
			// choose an specialty

			// choose a patient
			Patient p = choosePatient();

			// list availability
			// choose availability

			// choose opr
			OPR opr = null; // de momento

			// choose surgeon
			System.out.println("How many surgeons are going to participate? ");
			Integer numSurg = Integer.parseInt(read.readLine());
			List<Surgeon> surgeons = new ArrayList<Surgeon>();

			for (int i = 0; i < numSurg; i++) {
				Surgeon s = chooseSurgeon();
				surgeons.add(s);
			}
			// input type of surgery (ex: transplant)
			System.out.println("Input the type of surgery: ");
			String surgery = read.readLine();

			Surgery surg = new Surgery(p, surgeons, opr, surgery);
			surg.setAcceptSurgery(false);
			surgeryManager.addSurgery(surg);

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
		Integer dob = Integer.parseInt(read.readLine());
		System.out.println("Sex: ");
		String sex = read.readLine();

		Patient patient = new Patient(name, medstat, email, dob, sex);
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

		Surgeon surgeon = new Surgeon(name, medstat, pagerNumber, tlfNumber);
		surgeonManager.addSurgeon(surgeon);

	}

	public static Patient choosePatient() throws Exception {
		System.out.println("choose a patient by its id: ");
		// list patients
		patientManager.listPatients();
		Integer patientId = Integer.parseInt(read.readLine());
		Patient p = patientManager.searchPatient(patientId);

		return p;
	}

	public static Surgeon chooseSurgeon() throws Exception {

		System.out.println("choose a surgeon by its id: ");
		// list surgeons
		surgeonManager.listSurgeons();
		Integer surgeonId = Integer.parseInt(read.readLine());
		Surgeon s = surgeonManager.searchSurgeon(surgeonId);

		return s;
	}

	private static void updatePatientInfo() throws Exception {
		// choose patient
		System.out.println("Insert your email: ");
		String email = read.readLine();
		patientManager.listPatientsId(email);

		System.out.println("Input your id: ");
		Integer patientId = Integer.parseInt(read.readLine());
		// List patient info
		patientManager.showPatient(patientId);
		Patient p = patientManager.searchPatient(patientId);
		System.out.println("Update your information: ");
		// Ask for name, if empty keep existing name
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
		// TODO dob

		String sex = read.readLine();
		if (!sex.equals("")) {
			p.setSex(sex);
		}
		patientManager.updatePatient(p);

	}

	private static void updateSurgeonInfo() throws Exception {
		// choose surgeon

		System.out.println("Input your pagerNumber: ");

		Integer surgeonPagerNum = Integer.parseInt(read.readLine());
		// List patient info
		surgeonManager.showSurgeon(surgeonPagerNum);
		System.out.println("input your id: ");
		Integer id = Integer.parseInt(read.readLine());
		Surgeon s = surgeonManager.searchSurgeon(id);
		System.out.println("Update your information: ");

		String name = read.readLine();
		if (!name.equals("")) {
			s.setName(name);
		}
		String medstat = read.readLine();
		if (!medstat.equals("")) {
			s.setMedstat(medstat);
		}
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
}
