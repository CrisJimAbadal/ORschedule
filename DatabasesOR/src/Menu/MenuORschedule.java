package Menu;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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

<<<<<<< HEAD
		System.out.println("WELCOME TO THE OR SHCEDULE MENU");
=======
		System.out.println("WELCOME TO THE OR SHCEDULE");
		JDBCManager jdbcManager = new JDBCManager();
		// initialize database
		patientManager = new JDBCPatientManager(jdbcManager);
		surgeonManager = new JDBCSurgeonManager(jdbcManager);
		surgeryManager = new JDBCSurgeryManager(jdbcManager);
>>>>>>> 55400e297c5c9271fde329a54536e95caa3898cd
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
<<<<<<< HEAD
				case 2: 
					// sugeonManager.logIn();
=======
				case 2:
					// log in
					// call SMenu();
					SMenu();
>>>>>>> 55400e297c5c9271fde329a54536e95caa3898cd
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
				System.out.println("1. Check information");
				System.out.println("2. Check surgeries");

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
				System.out.println("1. Check information");
				System.out.println("2. Check schedule");
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
			Surgeon s = chooseSurgeon();

			// input type of surgery (ex: transplant)
			System.out.println("Input the type of surgery: ");
			String surgery = read.readLine();
			Surgery surg = new Surgery(p, s, opr, surgery);
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
		System.out.println("Age: ");
		Integer age = Integer.parseInt(read.readLine());
		System.out.println("Sex: ");
		String sex = read.readLine();

		Patient patient = new Patient(name, medstat, age, sex);
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
		System.out.println("Input your id: ");
		patientManager.listPatientsId();
		Integer patientId = Integer.parseInt(read.readLine());
		// List patient info
		patientManager.showPatient(patientId);

		// TODO ask for changes and update

	}

	private static void updateSurgeonInfo() throws Exception {
		// choose surgeon
		System.out.println("Input your pagerNumber: ");

		Integer surgeonPagerNum = Integer.parseInt(read.readLine());
		// List patient info
		surgeonManager.showSurgeon(surgeonPagerNum);

		// TODO ask for changes and update

	}
}
