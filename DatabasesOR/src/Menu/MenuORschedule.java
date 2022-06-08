package Menu;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import Exceptions.InputException;
import interfaces.OPRManager;
import interfaces.PManager;
import interfaces.SManager;
import interfaces.ScheduleManager;
import interfaces.SurgManager;
import interfaces.UserManager;
import jdbc.JDBCManager;
import jdbc.JDBCOprManager;
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
import jpa.JPAUserManager;

public class MenuORschedule {

	private static BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
	private static PManager patientManager;
	private static SManager surgeonManager;
	private static SurgManager surgeryManager;
	private static OPRManager oprManager;
	private static UserManager userManager;
	private static ScheduleManager scheduleManager;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static DateTimeFormatter formattert = DateTimeFormatter.ofPattern("HH:mm");

	public static void main(String[] args) {

		System.out.println("WELCOME TO THE OR SHCEDULE");
		JDBCManager jdbcManager = new JDBCManager();

		// initialize database JDBC
		patientManager = new JDBCPatientManager(jdbcManager);
		surgeonManager = new JDBCSurgeonManager(jdbcManager);
		surgeryManager = new JDBCSurgeryManager(jdbcManager);
		scheduleManager = new JDBCScheduleManager(jdbcManager);
		scheduleManager = new JDBCScheduleManager(jdbcManager);
		oprManager = new JDBCOprManager(jdbcManager);
		// initialize database JPA
		userManager = new JPAUserManager();

		principalMenu();
	}

	private static void principalMenu() {
		try {
			int choice;
			do {

				System.out.println("\n1. PATIENT");
				System.out.println("2. SURGEON");
				System.out.println("3. DOCTOR");
				System.out.println("O. EXIT");
				choice = InputException.getInt(" CHOOSE AN OPTION: ");

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
			int choice;
			do {

				System.out.println("\n1. REGISTER");
				System.out.println("2. LOG IN");
				System.out.println("O. BACK");
				choice = InputException.getInt(" CHOOSE AN OPTION: ");

				switch (choice) {

				case 1:
					// REGISTER (add patient)
					createPatient();
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
			int choice;
			do {

				System.out.println("\n1. REGISTER");
				System.out.println("2. LOG IN");
				System.out.println("0. Exit");
				choice = InputException.getInt(" CHOOSE AN OPTION: ");

				switch (choice) {

				case 1:
					// Call method REGISTER
					createSurgeon();

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
			int choice;
			do {

				System.out.println("\n1. LOG IN");
				System.out.println("0. Exit");
				choice = InputException.getInt(" CHOOSE AN OPTION: ");

				switch (choice) {

				case 1:

					System.out.println("Introduce the most famous password in the world:");
					String password = read.readLine();
					if (password.equals("g4nd4lf") || password.equals("G4ND4LF")) {
						DMenu();
					} else {
						System.out.println("wrong password");
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
			int choice;
			do {

				System.out.println("\n1. Check information and update");
				System.out.println("2. Check surgeries");
				System.out.println("0. Exit");

				choice = InputException.getInt(" CHOOSE AN OPTION: ");

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
			int choice;
			do {

				System.out.println("\n1. Check information and update");
				System.out.println("2. Check schedule");
				System.out.println("0. Exit");

				choice = InputException.getInt(" CHOOSE AN OPTION: ");

				switch (choice) {

				case 1:

					updateSurgeonInfo(sId);
					SMenu(sId);
					break;

				case 2:

					List<Surgery> surgeries = new ArrayList<Surgery>();
					surgeries = surgeonManager.listSurgeries(sId);

					System.out.println(surgeries.toString());

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
			int choice;
			do {

				System.out.println("\n1. Create a surgery");
				System.out.println("2. Delete a surgery");
				System.out.println("3. XML");
				System.out.println("0. Exit");

				choice = InputException.getInt(" CHOOSE AN OPTION: ");

				switch (choice) {

				case 1:

					createSurgery();
					break;

				case 2:

					deleteSurgery();
					break;
				case 3:
					toxml();
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

		String name = InputException.getString("\nName: ");
		int choice;
		String medstat = null;
		do {

			System.out.println("\n1.cardiology");
			System.out.println("2.neurology");
			System.out.println("3.trauma");
			System.out.println("4.pedriatics");
			System.out.println("5.oncology");

			choice = InputException.getInt(" CHOOSE MEDSTAT: ");
			switch (choice) {

			case 1:
				medstat = "cardiology";
				break;
			case 2:
				medstat = "neurology";
				break;
			case 3:
				medstat = "trauma";

				break;
			case 4:
				medstat = "pediatrics";

				break;
			case 5:
				medstat = "oncology";

				break;
			default:
				break;
			}
		} while (choice < 1 || choice > 5);

		String email = InputException.getString("Email:");

		System.out.println("Date of birth (yyyy-mm-dd): ");
		String dob = read.readLine();

		LocalDate dobDate = LocalDate.parse(dob, formatter);

		String sex = null;
		do {

			System.out.println("\n1.male");
			System.out.println("2.female");

			choice = InputException.getInt(" CHOOSE SEX: ");
			switch (choice) {

			case 1:
				sex = "male";

				break;

			case 2:
				sex = "female";
				break;

			default:
				break;
			}
		} while (choice < 1 || choice > 2);

		String password = InputException.getString("Password: ");
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();

	
		Patient patient = new Patient(name, medstat, email, Date.valueOf(dobDate), sex);

		// CREATE PATIENT AND ADD TO JPA
		User u = new User(email, digest);
		Role role = userManager.getRole("patient");
		u.setRole(role);
		role.addUser(u);
		userManager.newUser(u);

		patientManager.addPatient(patient);

	}

	public static void createSurgeon() throws Exception {

		System.out.println("Input information: ");

		String name = InputException.getString("Name: ");
		String email = InputException.getString("Email: ");
		System.out.println("Choose medstat: ");
		int choice;
		String medstat = null;
		do {

			System.out.println("\n1.cardiology");
			System.out.println("2.neurology");
			System.out.println("3.trauma");
			System.out.println("4.pedriatics");
			System.out.println("5.oncology");

			choice = InputException.getInt(" CHOOSE MEDSTAT: ");
			switch (choice) {

			case 1:
				medstat = "cardiology";
				break;
			case 2:
				medstat = "neurology";
				break;
			case 3:
				medstat = "trauma";
				;
				break;
			case 4:
				medstat = "pediatrics";
				;
				break;
			case 5:
				medstat = "oncology";
				;
				break;
			default:
				break;
			}
		} while (choice < 1 || choice > 5);

		Integer pagerNumber = InputException.getInt("Pager Number: ");

		Integer tlfNumber = InputException.getInt("Telephone number: ");

		String password = InputException.getString("Password: ");
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();

		// CREATE SURGEON AND ADD TO JPA
		User u = new User(email, digest);
		Role role = userManager.getRole("surgeon");
		u.setRole(role);
		role.addUser(u);
		userManager.newUser(u);

		// CREATE SURGEON AND ADD TO JDBD
		Surgeon surgeon = new Surgeon(name, email, medstat, pagerNumber, tlfNumber);

		surgeonManager.addSurgeon(surgeon);

	}

	// LOG IN METHOD
	public static void logIn() throws IOException {

		String email = InputException.getString("Input your email: ");
		String passwrd = InputException.getString("Input your password: ");

		// returns a user, if null = user is not found
		User user = userManager.checkPassword(email, passwrd);

		if (user == null) {
			System.out.println("User not found");
			principalMenu(); 
		}

		// [depending on the type of user we open a different menu]
		if (user != null && user.getRole().getName().equals("patient")) {
			Integer id = user.getId();

			int patientid = patientManager.searchPatientfromUId(id);
			Patient p = patientManager.searchPatientbyId(patientid);
			System.out.println(p);
			PMenu(p.getId());

		}

		if (user != null && user.getRole().getName().equals("surgeon")) {
			Integer id = user.getId();

			int surgeonid = surgeonManager.searchSurgeonIdfromUId(id);
			Surgeon s = surgeonManager.searchSurgeon(surgeonid);
			System.out.println(s);
			SMenu(s.getId());

		}
	}

	// UPDATE PATIEN'S INFO
	private static void updatePatientInfo(int id) throws IOException {

		// List patient info
		Patient patient = patientManager.showPatient(id);
		System.out.println(patient.toString());

		Patient p = patientManager.searchPatientbyId(id);

		System.out.println("Update your information: ");
		// Ask for info, if empty keeps the one existing before
		System.out.println("New name: ");
		String name = read.readLine();
		if (!name.equals("")) {
			p.setName(name);
		}
		
		System.out.println("New medstat: ");
		String medstat = read.readLine();
		if (!medstat.equals("")) {
			p.setMedstat(medstat);
		}

		patientManager.updatePatient(p);
		PMenu(id);
	}

	// UPDATE SURGEON'S INFO
	private static void updateSurgeonInfo(int id) throws Exception {

		Surgeon surgeon = surgeonManager.showSurgeon(id);
		System.out.println(surgeon.getId());

		Surgeon s = surgeonManager.searchSurgeon(id);

		System.out.println("Update your information: ");
		System.out.println("New pager Number: ");
		Integer pagerNumber = Integer.parseInt(read.readLine());
		s.setPagerNumber(pagerNumber);	
		/*
		if (!(pagerNumber==(Integer.parseInt("\n")))) {
			s.setPagerNumber(pagerNumber);
		}*/
		System.out.println("New phone number: ");
		Integer tlfNumber = Integer.parseInt(read.readLine());
		/*if (!(tlfNumber==(Integer.parseInt("")))) {
			s.setTlfNumber(tlfNumber);
		}
		if (!(tlfNumber==(Integer.parseInt("\n")))) {
			s.setTlfNumber(tlfNumber);
		}*/
		s.setTlfNumber(tlfNumber);
		surgeonManager.updateSurgeon(s);
	}

	// CHECK ASSIGNED SURGERIES (patient)
	public static void checksurgeries(int patientId) throws IOException {
		List<Surgery> surgeries = new ArrayList<Surgery>();
		surgeries = surgeryManager.listSurgeries(patientId);
		if (surgeries.isEmpty()) {
			System.out.println("There are no surgeries scheduled yet ");

		} else {
			for (Surgery s : surgeries) {
				System.out.println(s);

			}
		}
		PMenu(patientId);
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
			
			OPR	opr = oprManager.searchOPR(oprId);
			
			
			// 4) SPECIALITY

			String specialty = p.getMedstat();
			System.out.println(specialty);

			// 5) choose SURGEONS specialized on the patient's medStat

			int numOfSurgeons=0;
			int numSurg=0;

			List<Surgeon> surgeons = new ArrayList<Surgeon>();
			
				
				System.out.println("How many surgeons are going to participate? ");
				numSurg = Integer.parseInt(read.readLine());
				
				numOfSurgeons = surgeonManager.countSurgeons(specialty);
				System.out.println("\nnumber of surgeons compatible with that patient:");
				System.out.println(numOfSurgeons);
				
				if(numOfSurgeons>=numSurg) {
				
				surgeonManager.listSurgeons(specialty);
				
				
				for (int i = 0; i < numSurg; i++) {
					
					Surgeon surg = chooseSurgeon(specialty);
					while (surgeryManager.checksurgeon(s, surg)) {
						// TRUE = not available
						System.out.println("The surgeon is not available at this schedule.");
						System.out.println("Please choose another one:");
						
						surg = chooseSurgeon(specialty);
						
					}
					surgeons.add(surg);
					System.out.println("\nthe surgeons that will participate in this surgery are:");
					for(Surgeon surgeon: surgeons) {
						System.out.println(surgeon);
					}
				}
				}
				else {
					System.out.println("the number of surgeons selected is not available");	
				}
				

			// 6) TYPE of surgery
			String type = p.getMedstat();

			// CREATE THE SURGERY and add to the database
			
			scheduleManager.addSchedule(s);
			int surgeryid=surgeryManager.getIdSurgery();
				
			Surgery surg = new Surgery(surgeryid,type,p,opr,  surgeons,s);
			
			surgeryManager.addSurgery(surg);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// CHOOSE SCHEDULE FOR THE SURGERY

	public static Schedule chooseSchedule() {
		int id= scheduleManager.getIdSchedule();
		Date date = null;
		Time startTime = null;
		Time finishTime = null;
		Schedule s = null;
		try {
			System.out.println("\nInsert a date (yyyy-MM-dd): ");
			LocalDate ld = LocalDate.parse(read.readLine(), formatter);
			date = Date.valueOf(ld);
			System.out.println("Insert a start time (hh:mm): ");
			LocalTime lt = LocalTime.parse(read.readLine(), formattert);
			startTime = Time.valueOf(lt);
			System.out.println("Insert a finish time: ");
			LocalTime ltf = LocalTime.parse(read.readLine(), formattert);
			finishTime = Time.valueOf(ltf);
			while (finishTime.compareTo(startTime) < 0) {
				System.out.println("The start and finish time are incorrect");
				System.out.println("Repeat the process please:");
				s = chooseSchedule();

			} // finishtime occurs later-> correct
			s = new Schedule(id,date, startTime, finishTime);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return s;
	}

	// CHOOSE PATIENT FOR SURGERY
	public static Patient choosePatient() throws Exception {

		List<Patient> patients = patientManager.listPatients();
	

		for (Patient patient : patients) {
			System.out.println(patient.toString());
		}

		System.out.println("choose a patient by its id: ");
		Integer patientId = Integer.parseInt(read.readLine());
		System.out.println("the patient chosen is:");
		Patient patient = patientManager.showPatient(patientId);
		System.out.println(patient.toString());
		Patient p = patientManager.searchPatientbyId(patientId);

		return p;
	}

	// CHOOSE SURGEON FOR SURGERY
	public static Surgeon chooseSurgeon(String specialty) throws Exception {

		System.out.println("choose a surgeon by its id: ");
		List<Surgeon> surgeons = surgeonManager.listSurgeons(specialty);
		for (Surgeon s : surgeons) {
			System.out.println(s);
		}
		Integer surgeonId = Integer.parseInt(read.readLine());
		Surgeon s = surgeonManager.chooseSurgeon(surgeonId);

		return s;
	}

	// CHOOSE OPR FOR SURGERY
	public static int chooseOPR() {

		List<OPR> oprs = oprManager.listOprs();
		for (OPR opr : oprs) {
			System.out.println(opr);
		}
		Integer oprsId = InputException.getInt("Choose an opr by its id: ");
		return oprsId;

	}

	// DELETE SURGERY
	private static void deleteSurgery() throws IOException {
		// 1)LIST SURGERIES
		List<Surgery> surgeries = surgeryManager.listSurgeries();

		for (Surgery s : surgeries) {
			System.out.println(s.toString());
		}

		System.out.println("Is there any surgery you want to delete? [1= Yes / 0= No]");
		int answer = Integer.parseInt(read.readLine());

		if (answer != 1 || answer != 0) {
			System.out.println("Please input the answer again.");
		}

		if (answer == 0) {
			DMenu();
		}

		System.out.println("Input the id of the surgery you want to delete.");
		int surgeryId = Integer.parseInt(read.readLine());
		// 2)DELETE SURGERY
		surgeryManager.unassign(surgeryId);
	}

	

	public static void java2Xmlsurgury(Surgery surgery) throws Exception {
		System.out.println(surgery);

	EntityManager em2 = Persistence.createEntityManagerFactory("DatabasesOR").createEntityManager();
		em2.getTransaction().begin();
		em2.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em2.getTransaction().commit();

		
		// Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Surgery.class);
		// Get the marshaller
		Marshaller marshaller = jaxbContext.createMarshaller();

		// Pretty formatting
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		File file = new File("./xmls/Sample-Surgery.xml");
		
		marshaller.marshal(surgery, file);
	}

	private static final String PERSISTENCE_PROVIDER = "DatabasesOR";
	private static EntityManagerFactory factory;

	public void Xml2JavaSurgery() throws JAXBException {

		// Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Surgery.class);
		// Get the unmarshaller
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		// Use the Unmarshaller to unmarshal the XML document from a file
		File file = new File("./xmls/External-Surgery.xml");
		Surgery surgery = (Surgery) unmarshaller.unmarshal(file);

		surgeryManager.addSurgery(surgery);

	}

	public static void toxml() throws Exception {
		
		System.out.println("Select the type of the surgeries to pass to xml");
		
		surgeryManager.listSurgeries();
		String type = read.readLine();
		System.out.println(type);
		List<Surgery> surgeries = new ArrayList<Surgery>();
		 surgeries= surgeryManager.chooseSurgerybytype(type);
		
		
		 for(Surgery s: surgeries) {
		java2Xmlsurgury(s);}
	}
}
