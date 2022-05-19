package Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import interfaces.OPRManager;
import interfaces.PManager;
import interfaces.SManager;
import interfaces.SurgManager;
import interfaces.UserManager;
import jdbc.JDBCManager;
import jdbc.JDBCPatientManager;
import jdbc.JDBCSurgeonManager;
import jdbc.JDBCSurgeryManager;
import pojos.OPR;
import pojos.Patient;
import pojos.Schedule;
import pojos.Surgeon;
import pojos.Surgery;
import pojos.User;
import jpa.JPAUserManager;  //Is this the right package??


public class MenuORschedule {

	private static BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
	private static PManager patientManager;
	private static SManager surgeonManager;
	private static SurgManager surgeryManager;
	private static OPRManager oprManager;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");

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
					
					logInPatient();
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
					
					logInSurgeon();
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
					
					logInDoctor();
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
				System.out.println("2. Check schedule");// check and update?
				System.out.println("0. Exit");

				int choice = Integer.parseInt(read.readLine());

				switch (choice) {

				case 1:
					updateSurgeonInfo();

					break;

				case 2:
					//TODO show schedule
					showshedule();
					//TODO accept / deny surgery
					//System.out.println("Input id of the surgery you accept");
					//update acceptance from surgery to TRUE
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
		// TODO Auto-generated method stub
		
	}

	private static void createSchedule() {
		try {
			//TODO everything about speciality
		
			// show medical specialties
			//select speciality FROM surgeons
			
			
			// choose an specialty

			// choose a patient
			Patient p = choosePatient();

			// TODO list schedule
			// choose schedule

			 
			OPR opr = chooseOPR(); 

			// choose surgeon
			System.out.println("How many surgeons are going to participate? ");
			Integer numSurg = Integer.parseInt(read.readLine());
			List<Surgeon> surgeons = new ArrayList<Surgeon>();
			//TODO if a surgeon accepts but the rest no what happens
			//all surgeons must accept for the surgery to take place
			
			for (int i = 0; i < numSurg; i++) {
				Surgeon s = chooseSurgeon();
				surgeons.add(s);
			}
			
			// input type of surgery (ex: transplant)
			System.out.println("Input the type of surgery: ");
			String type = read.readLine();
		    Schedule schedule =null; //TODO set schedule for the surgery

			Surgery surg = new Surgery(p, surgeons, opr, type, schedule);
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
		System.out.println("Password: ");
		String password = read.readLine();
		//try-catch
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();
		
		System.out.println("Date of birth (yyyy-mm-dd): ");
		String dob= read.readLine();
		LocalDate dobDate =LocalDate.parse(dob,formatter);
		//TODO DATE ask rodrigo
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

	public static Surgeon chooseSurgeon() throws Exception {

		System.out.println("choose a surgeon by its id: ");
		// list surgeons
		surgeonManager.listSurgeons();
		Integer surgeonId = Integer.parseInt(read.readLine());
		Surgeon s = surgeonManager.searchSurgeon(surgeonId);

		return s;
	}
	public static OPR chooseOPR() throws Exception{
		System.out.println("choose an opr by its id: ");
		// list oprs
		oprManager.listOprs();
		Integer oprsId = Integer.parseInt(read.readLine());
		OPR opr = oprManager.searchOPR(oprsId);
		return opr;
		
	}

	private static void updatePatientInfo() throws Exception {
		
		System.out.println("Insert your email: ");
		String email = read.readLine();
		patientManager.listPatientsId(email);

		System.out.println("Input your password: ");
		String password =read.readLine();
		
		//returns a user, if null, user is not found
		User user = checkPassword(email, password); //no me acepta la funcion??
		
		if(user == null) {
			System.out.println("User not found");
			PMenu(); //returns to patient menu if the user does not exist
			
		}
		
		// List patient info
		patientManager.showPatient(user.getId());
		Patient p = patientManager.searchPatient(user.getId());
		
		p.setDigest(user.getPassword()); //mete el digest del password en el paciente
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
		System.out.println("Date of birth (yyyy-mm-dd): ");
		String dob= read.readLine();
		LocalDate dobDate =LocalDate.parse(dob,formatter);
		
		if(!dob.equals("")) {
			p.setDob(Date.valueOf(dobDate));
		}

		String sex = read.readLine();
		if (!sex.equals("")) {
			p.setSex(sex);
		}
		patientManager.updatePatient(p);

	}

	private static void updateSurgeonInfo() throws Exception {
	
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
	
	public static void logInDoctor() {
		
		System.out.println("Insert your email: ");
		String email = read.readLine();
		

		System.out.println("Input your password: ");
		String passwrd =read.readLine();
		
		//returns a user, if null, user is not found
		User user = checkPassword(email, passwrd); //does not work??
		
		if(user == null) {
			System.out.println("User not found");
			doctorMenu(); //returns to patient menu if the user does not exist
			
		}
	}
		public static void logInPatient() {
			
			System.out.println("Insert your email: ");
			String email = read.readLine();
			

			System.out.println("Input your password: ");
			String passwrd =read.readLine();
			
			//returns a user, if null, user is not found
			User user = checkPassword(email, passwrd); //doesnt work??
			
			if(user == null) {
				System.out.println("User not found");
				patientMenu(); //returns to patient menu if the user does not exist
				
			}
		}
		
			public static void logInSurgeon() {
				
				System.out.println("Insert your email: ");
				String email = read.readLine();
				

				System.out.println("Input your password: ");
				String passwrd =read.readLine();
				
				//returns a user, if null, user is not found
				User user = checkPassword(email, passwrd); //doesnt work??
				
				if(user == null) {
					System.out.println("User not found");
					surgeonMenu(); //returns to patient menu if the user does not exist
					
				}
		
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
}
