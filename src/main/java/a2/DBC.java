package a2;

import java.sql.*;
import java.util.Scanner;

public class DBC {
	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@ora14.informatik.haw-hamburg.de:1521:inf14";
	private String id;
	private String pw;

	public DBC(boolean idInput) {

		if (idInput) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Please insert id");
			id = sc.next();

			sc.nextLine();
			System.out.println("Please insert pw");
			pw = sc.next();
			sc.nextLine();
			sc.close();

		} else {
			id = "ace621";
			pw = "5000NEWwin";
		}

	}

	public Connection getDBConnection() {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(
					DB_CONNECTION, id, pw);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}
	
}
