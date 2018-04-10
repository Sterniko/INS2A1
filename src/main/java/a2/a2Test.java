package a2;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;

class a2Test {
private DBC con = new DBC(false);

@Test
	void connectionTest() {
		
		assertTrue(con.getDBConnection() != null);
		try {
			Statement statement = con.getDBConnection().createStatement();
			String order = "CREATE TABLE TEST(Name int)";
			statement.executeUpdate(order);
			order = "DROP TABLE TEST";
			statement.executeUpdate(order);
			statement.close();
		} catch (SQLException e) {
			fail("connection not succesful"+ e);
		}
		
	}
	@Test
	void testCreateTable() {
		String order = "CREATE TABLE CUSTOMERS(Id INT Not NULL, FirstName VARCHAR(50) Not NULL , LastName VARCHAR(100) Not NULL, EntryDate DATE )";
		try {
		Statement statement = con.getDBConnection().createStatement();
		statement.executeUpdate(order);	
		statement.close();
		}catch(SQLException e) {
			fail(""+e);
		}
	}
	@Test
	void testAddToTable() {
		Date date = new Date(System.currentTimeMillis());

		String c1 ="1, 'Frank', 'Friedrich'";
		String c2 ="2, 'Holger', 'Miller'";
		String c3 ="3, 'Kai', 'Sternke'";
		String c4 ="4, 'Izabela', 'Burevska'";
		String c5 ="5, 'Loki', 'Wieso'";
		String c6 ="6, 'Gebaeck', 'Mischung'";
		String c7 ="7, 'Leberkase', 'Pizza'";
		String c8 ="8, 'Bibi', 'Blocksberg'";
		String c9 ="9, 'Tina', 'Wuddmann'";
		String c10 ="10, 'Maike', 'Katzenbergah'";
		
		String[] customers = {c1,c2,c3,c4,c5,c6,c7,c8,c9,c10};
		try {
		for(String s: customers) {		
			String sDate = s+", "+ "'"+date+"'";
			String order = "INSERT INTO CUSTOMERS VALUES("+sDate+")";
			System.out.println(order);
			Statement statement = con.getDBConnection().createStatement();
			statement.executeUpdate(order);
			statement.close();
		}
		
		}catch(SQLException e) {
		fail(""+e);
		}
		
	}
	
	
	void ReadFromTable() {
		try {
			String order = "SELECT DATE FROM CUSTOMERS";
			Statement statement = con.getDBConnection().createStatement();
			ResultSet rs = statement.executeQuery(order);
			statement.close();
			while (rs.next()) {
				  
				  System.out.println();
				}
		}catch(SQLException e) {
			fail(""+e);
	}
	}
	@Test
	void RemoveFromTable() {
		String order = "DELETE FROM CUSTOMERS WHERE ID<=5";
		try {
		Statement statement = con.getDBConnection().createStatement();
		statement.executeUpdate(order);	
		statement.close();
		}catch(SQLException e) {
			fail(""+e);
		}
	}
	
	@Test
	void DropTable() {
		try {
			String order = "DROP TABLE CUSTOMERS";
			Statement statement = con.getDBConnection().createStatement();
			statement.executeUpdate(order);
			statement.close();
		} catch (SQLException e) {
			fail(""+e);
		}
	}

}
