package a2;

import java.sql.*;
import java.util.Scanner;

public class Main {
	private static boolean idInput = false;

	public static void main(String[] argv) {

		DBC con = new DBC(idInput);
		try {

			deleteDbTables(con);
		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}
	}

	

	private static void deleteDbTables(DBC con) throws SQLException {

		String delTbl = "DROP TABLE AGGFD";
		String delTbl2 = "DROP TABLE DbUser";
		String delTbl3 = "DROP TABLE dbUser2";
		Connection dbConnection = null;
		Statement statement = null;
		try {
			dbConnection = con.getDBConnection();
			statement = dbConnection.createStatement();

			statement.execute(delTbl);
			statement.execute(delTbl2);
			statement.execute(delTbl3);

			System.out.println("Deletion ended");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	private static void createDbUserTable(DBC con) throws SQLException {

		Connection dbConnection = null;
		Statement statement = null;

		String createTableSQL = "CREATE TABLE AGGFD(" + "USER_ID NUMBER(5) NOT NULL, "
				+ "USERNAME VARCHAR(20) NOT NULL, " + "CREATED_BY VARCHAR(20) NOT NULL, "
				+ "CREATED_DATE DATE NOT NULL, " + "PRIMARY KEY (USER_ID) " + ")";

		try {
			dbConnection = con.getDBConnection();
			statement = dbConnection.createStatement();

			System.out.println(createTableSQL);
			// execute the SQL stetement
			statement.execute(createTableSQL);

			System.out.println("Table \"dbuser\" is created!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

}