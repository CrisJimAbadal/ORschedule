package Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.Date;
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
		
		// initialize database
		patientManager = new JDBCPatientManager(jdbcManager);
		surgeonManager = new JDBCSurgeonManager(jdbcManager);
		surgeryManager = new JDBCSurgeryManager(jdbcManager);
		userManager= new JPAUserManager();
		scheduleManager= new JDBCScheduleManager(jdbcManager);
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

					logIn();
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

					logIn();
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

					logIn();
					DMenu();

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

	private static void DMenu() {

		try {
			do {
				System.out.println("CHOOSE AN OPTION: ");
				System.out.println("1. Create a surgery");
				System.out.println("0. Exit");

				int choice = Integer.parseInt(read.readLine());

				switch (choice) {

				case 1:

					createSurgery();

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

					checksurgeries();

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
				System.out.println("1. Check information and update");
				System.out.println("2. Check schedule");
				System.out.println("0. Exit");

				int choice = Integer.parseInt(read.readLine());

				switch (choice) {

				case 1:
					updateSurgeonInfo();

					break;

				case 2:
					// TODO show schedule
					showshedule();  // id + time + type
					// TODO accept / deny surgery
					System.out.println ("Do you want to deny a surgery? (1 if yes)");
					int yes = Integer.parseInt(read.readLine());
					if (yes ==1) {
						System.out.println ("Input surgery id: ");
						int id= Integer.parseInt (read.readLine());
						surgeryManager.unassign(id);;
					}else{
						SMenu();
					}
					
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

	private static void showshedule() {
		
		// TODO tipo y hora   JOIN
		
		
		
		
		

	}

	private static void createSurgery() {
		try {
			//1) choose schedule
			Schedule s=chooseSchedule();
			
			//2)choose a patient
			Patient p = choosePatient();
			//check if patient available at that schedule
			while(surgeryManager.checkpatient(s, p)) {
				// TRUE = not available
				System.out.println("The patient is not available at that time and date");
				// if patient not available-> choose another schedule 
				System.out.println("Choose another schedule for that patient");
				s=chooseSchedule();
			}
			
			//3) choose OPR
			int oprId = chooseOPR();
			
			while(surgeryManager.checkOPR(s,oprId)) {
				// TRUE = not available
				System.out.println("The OPR is not available at this schedule.");
				System.out.println("Please choose another one:");
				oprId = chooseOPR();
			}  
			OPR opr = oprManager.searchOPR(oprId);
			
			// 4)show surgeons with same specialty as patient
			String specialty=p.getMedstat();
			
			//5) choose surgeon
			System.out.println("How many surgeons are going to participate? ");
			Integer numSurg = Integer.parseInt(read.readLine());
			List<Surgeon> surgeons = new ArrayList<Surgeon>();
		
			
			

			for (int i = 0; i < numSurg; i++) {
				Surgeon surg = chooseSurgeon(specialty);
				surgeons.add(surg);
			}

			// input type of surgery (ex: transplant)
			System.out.println("Input the type of surgery:");
			String type = read.readLine();
			

			Surgery surg = new Surgery(p, surgeons, opr, type, s);
			surg.setAcceptSurgery(false);
			scheduleManager.addSchedule(s);
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
		System.out.println("Password: ");
		String password = read.readLine();
		// try-catch
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();

		System.out.println("Date of birth (yyyy-mm-dd): ");
		String dob = read.readLine();
		LocalDate dobDate = LocalDate.parse(dob, formatter);
		
		System.out.println("Sex: ");
		String sex = read.readLine();

		Patient patient = new Patient(name, medstat, digest, email, Date.valueOf(dobDate), sex);
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

	public static Surgeon chooseSurgeon(String specialty) throws Exception {

		System.out.println("choose a surgeon by its id: ");
		
		surgeonManager.listSurgeons(specialty);
		Integer surgeonId = Integer.parseInt(read.readLine());
		Surgeon s = surgeonManager.searchSurgeon(surgeonId);

		return s;
	}

	public static int chooseOPR() throws Exception {
		System.out.println("choose an opr by its id: ");
		// list oprs
		oprManager.listOprs();
		Integer oprsId = Integer.parseInt(read.readLine());
		return oprsId;

	}

	private static void updatePatientInfo() throws Exception {

		System.out.println("input your email: ");
		String email = read.readLine();

		Integer id = patientManager.listPatientsId(email);

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

	private static void updateSurgeonInfo() throws Exception {

		System.out.println("Input your pagerNumber: ");

		Integer surgeonPagerNum = Integer.parseInt(read.readLine());
		
		Surgeon s = surgeonManager.showSurgeon(surgeonPagerNum);
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

	public static void logIn()throws IOException {

		System.out.println("Insert your email: ");
		String email = read.readLine();

		System.out.println("Input your password: ");
		String passwrd = read.readLine();

		// returns a user, if null, user is not found
		User user = userManager.checkPassword(email, passwrd); 

		if (user == null) {
			System.out.println("User not found");
			
			principalMenu(); // returns to principal menu if the user does not exist

		}
		//TODO LOGIN
		/*
		if (user!=null && user.getRole().getName().equals("patient")) {
			System.out.println("Login successful");
			PMenu(user.getId());
		}*/
	}

	public static void checksurgeries() throws IOException {
		System.out.println("Insert your email: ");
		String email = read.readLine();
		patientManager.listPatientsId(email);

		System.out.println("Input your id: ");
		Integer patientId = Integer.parseInt(read.readLine());

		if (surgeryManager.listSurgeries(patientId).isEmpty()) {

			System.out.println("There are no surgeries scheduled yet ");
		}

	}
	
	public static Schedule chooseSchedule() {
		Schedule s;
		Date date = null;
		Time time = null;
		try {
		System.out.println ("Insert a date: ");
		date = Date.valueOf(read.readLine());
		System.out.println ("Insert a date: ");
		time = Time.valueOf(read.readLine());
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		s= new Schedule (date, time);
		return s;
	}
}
