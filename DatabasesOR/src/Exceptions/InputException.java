package Exceptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputException {
	private static BufferedReader consola = new BufferedReader(new InputStreamReader(System.in));

	public static String getString(String question) {

		while (true) {
			try {
				System.out.println(question);
				String leido = consola.readLine();
				return leido;

			} catch (IOException ex) {
				System.out.println("\n Incorrect value, please input again");
			}
		}
	}

	public static Integer getInt(String question) {
		int x = 0;
		while (true) {
			try {
				System.out.println(question);

				x = Integer.parseInt(consola.readLine());
				return x;

			} catch (NumberFormatException ex) {
				System.out.println("\n Incorrect value, please input a number");
			} catch (IOException ex) {

			}

		}
	}
}
