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
				System.out.println("3. XML");
				System.out.println("0. Exit");

				int choice = Integer.parseInt(read.readLine());

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

		//CHECK THAT THE PATIENT DOESN'T EXIST ALREADY (do the same in surgeon)
		Patient patient = new Patient(name, medstat, email, Date.valueOf(dobDate), sex);
		if (patientManager.searchPatient(email) == null) {  //TODO pojos.Patient.getId()??
		// CREATE PATIENT AND ADD TO JPA
		User u = new User(email, digest);
		Role role = userManager.getRole("patient");
		u.setRole(role);
		role.addUser(u);
		userManager.newUser(u);

		// CREATE PATIENT AND ADD TO JDBC
		//Patient patient = new Patient(name, medstat, email, Date.valueOf(dobDate), sex);
		patientManager.addPatient(patient);	
			
			
		}else {
			patientMenu();
		}
		
		
	}

	public static void createSurgeon() throws Exception {

		System.out.println("Input information: ");
		System.out.println("Name: ");
		String name = read.readLine();
		System.out.println("Email: ");
		String email = read.readLine();
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

		// [depending on the type of user we open a different menu]
		if (user != null && user.getRole().getName().equals("patient")) {
			String emailu=user.getEmail();
			Patient p = patientManager.searchPatient(emailu);
			PMenu(p.getId());

		}

		if (user != null && user.getRole().getName().equals("surgeon")) {
			SMenu(user.getId()); // TODO does not go to SMenu();
		}

	}

	// UPDATE PATIEN'S INFO
	private static void updatePatientInfo(int id) throws IOException {

		// List patient info
		Patient patient = patientManager.showPatient(id);
		System.out.println(patient.toString());

		Patient p = patientManager.searchPatient(patient.getEmail());

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
	}

	// UPDATE SURGEON'S INFO
	private static void updateSurgeonInfo(int id) throws Exception {

		Surgeon surgeon = surgeonManager.showSurgeon(id);
		System.out.println(surgeon.getId());

		Surgeon s = surgeonManager.searchSurgeon(id);

		System.out.println("Update your information: ");
		System.out.println("New pager Number: ");
		Integer pagerNumber = Integer.parseInt(read.readLine());
		if (!pagerNumber.equals("")) {
			s.setPagerNumber(pagerNumber);
		}
		System.out.println("New phone number: ");
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
				s = chooseSchedule(); // TODO there is an error here

			}

			// 3) choose OPR
			int oprId = chooseOPR(); // Cannot invoke "interfaces.OPRManager.listOprs()" because
										// "Menu.MenuORschedule.oprManager" is null

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
				while (surgeryManager.checksurgeon(s, surg)) {
					// TRUE = not available
					System.out.println("The surgeon is not available at this schedule.");
					System.out.println("Please choose another one:");
					surg = chooseSurgeon(specialty);
				}
				surgeons.add(surg);
			}

			// 6) TYPE of surgery (ex: transplant)
			System.out.println("Input the type of surgery:");
			String type = read.readLine();

			// CREATE THE SURGERY and add to the database
			Surgery surg = new Surgery(p, surgeons, opr, type, s);

			scheduleManager.addSchedule(s);
			surgeryManager.addSurgery(surg);
			java2Xmlsurgury(surg);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// CHOOSE SCHEDULE FOR THE SURGERY

	public static Schedule chooseSchedule() {

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
			s = new Schedule(date, startTime, finishTime);

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
		Patient p = patientManager.searchPatient(patient.getEmail());
	

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

	private static EntityManager em;

	public static void java2Xmlsurgury(Surgery surgery) throws Exception {

		em = Persistence.createEntityManagerFactory("DatabasesOR").createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();

		// Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Surgery.class);
		// Get the marshaller
		Marshaller marshaller = jaxbContext.createMarshaller();

		// Pretty formatting
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		File file = new File("./xmls/Sample-Surgery.xml");
		marshaller.marshal(surgery, file);
	}

	// TODO do not know in which moment we need unmarshaller, maybye it is not
	// necessary
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
	public void toxml() throws NumberFormatException, IOException {
		System.out.println("Select a surgery to pass to xml");
		surgeryManager.listSurgeries();
		Integer id= Integer.parseInt(read.readLine());
		Surgery s = surgeryManager.chooseSurgery(id);
		
	}

}
