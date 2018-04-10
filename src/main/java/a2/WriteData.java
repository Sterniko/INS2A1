package a2;

import java.sql.*;
import java.util.Scanner;

public class WriteData {
	private static final boolean idInput = false;
	private DBC con = new DBC(idInput);

	public void starter() {
		addCustomer();
		ShowCustomer();
		endConnection();

	}

	public void addCustomer() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Customer´s ID");
		long customerId = sc.nextInt();
		System.out.println("Enter Customer´s first name");
		String firstName = sc.next();
		System.out.println("Enter Customer´s last name");
		String lastName = sc.next();
		System.out.println("Enter Date");
		String date = sc.next();

		try {
			Statement statement = con.getDBConnection().createStatement();
			String befehl = "INSERT INTO ace516.Customer VALUES (" + customerId + ", '" + firstName + "', '" + lastName
					+ "', '" + date + "')";
			int anzahl = statement.executeUpdate(befehl);
			statement.close();
		} catch (SQLException e) {
			System.out.println("Beim einfügen des Kundens ist ein Fehler aufgetreten " + e.getMessage());
		}
		System.out.println("Kunde wurde erfolgreich eingefügt");

	}

	public void ShowCustomer() {
		try {
			Statement statement = con.getDBConnection().createStatement();
			String anfrage = "SELECT * FROM ace516.Customer";
			ResultSet ergebnis = statement.executeQuery(anfrage);
			/* Verarbeite das Ergebnis */
			while (ergebnis.next()) {
				long customerId = ergebnis.getLong(1);
				String firstName = ergebnis.getString(2);
				String lastName = ergebnis.getString(3);
				String date = ergebnis.getString(4);

				System.out.println("Tupel:" + customerId + " " + firstName + " " + lastName + "  " + date);
			}
			ergebnis.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println("Fehler beim ausgeben der Kunden " + e.getMessage());
		}
	}

	public void endConnection() {
		try {

			con.getDBConnection().close();
		} catch (SQLException e) {
			System.out.println("Verbindung konnte nicht getrennt werden " + e.getMessage());
		}
		System.out.println("Verbindung wurde getrennt");
	}
}