package Menu;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MenuORschedule {

	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) {

		System.out.println("WELCOME TO THE OR SHCEDULE MENU");
		principalMenu();
	}

	private static void principalMenu() {
		try {
			// TODO DATABASE CONNECTION
			// TODO RETRIEVE DATA: BEGIN
			do {
				System.out.println("PLEASE CHOOSE AN OPTION: ");
				System.out.println("1. PATIENT");
				System.out.println("2. SURGEON");
				System.out.println("3. DOCTOR");
				System.out.println("O. EXIT");
				int choice = Integer.parseInt(reader.readLine());

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
				int choice = Integer.parseInt(reader.readLine());

				switch (choice) {

				case 1:
					// Call method register

					// TODO PatientManager.addPaient();

					break;
				case 2:
					// Call method log in

					// TODO PatientManager.logIn();

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
			
				int choice = Integer.parseInt(reader.readLine());

				switch (choice) {

				case 1:
					// Call method REGISTER

					// surgeonManager.addSurgeon();

					break;
				case 2: 
					// sugeonManager.logIn();
					break;
					
				case 0:
					//EXIT
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
			
				int choice = Integer.parseInt(reader.readLine());

				switch (choice) {

				case 1:
					// Call method LOG IN


					break;
					
				case 0:
					//EXIT
					principalMenu();
				default:
					break;
				}
			} while (true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
