package jpa_hibernate_xml;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TestCustomer {

	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static Long[] ids;
	private static String[] firstNames;
	private static String[] lastNames;
	private static List<Customer> customers;
	
   @BeforeAll	
	public static void getConnection() {
	emf = Persistence.createEntityManagerFactory("persistenceUnit");
	em = emf.createEntityManager();
		assertTrue(em != null);
		customers = new ArrayList<Customer>();
		ids = new Long[] { 111L, 222L, 333L, 444L, 555L, 666L, 777L, 888L, 999L, 000L };

		firstNames = new String[] { "Jane", "Ana", "Christian", "Tara", "Paul", "Jana", "Robert", "Mila", "Lana",
				"Jovan" };

		lastNames = new String[] { "Milbradt", "Kurnikova", "Bild", "Schmidt", "Hietel", "Clarsen", "Katzenberger",
				"Fonda", "Jovanov", "Angeleski" };
		;

		for (int i = 0; i < firstNames.length; i++) {
			Customer newCustomer = new Customer(ids[i], firstNames[i], lastNames[i],
					new Date(System.currentTimeMillis()));
			customers.add(newCustomer);
		}
		em.getTransaction().begin();
	    System.out.println("I DELETE ");
		Query delete = em.createNativeQuery("DELETE FROM CUSTOMER");
		delete.executeUpdate();
		em.getTransaction().commit();
		em.getTransaction().begin();
		//add all customers to the db
		System.out.println("I REWRITE ");
		for (Customer c : customers) {
			em.persist(c);
		}
		em.getTransaction().commit();
		System.out.println("########################################################");
		System.out.println("WRITE END");

	}

	// write Data into the Database
  
//	public void writeData() {
//	   System.out.println("########################################################");
//		System.out.println("DOING WRITE");
//		assertTrue(em != null);
//		// checking if there is ongoing active transaction and closing it
//		if(em.getTransaction().isActive()) {
//			em.getTransaction().rollback();
//		}
		//removing anything left in the DB
		
//		em.getTransaction().begin();
//		System.out.println("I DELETE ");
//		Query delete = em.createNativeQuery("DELETE FROM CUSTOMER");
//		delete.executeUpdate();
//		em.getTransaction().commit();
		
		
//		em.getTransaction().begin();
//		//add all customers to the db
//		System.out.println("I REWRITE ");
//		for (Customer c : customers) {
//			em.persist(c);
//		}
//		em.getTransaction().commit();
//		System.out.println("########################################################");
//		System.out.println("WRITE END");
//	}

	@Test
	 public void checkCustomerData() {
		System.out.println("########################################################");
		System.out.println("DOING CHECK");
	
	 assertTrue(em != null);
	 assertTrue(customers != null);
	 em.getTransaction().begin();
	Query select = em.createNativeQuery("SELECT * FROM CUSTOMER", Customer.class);
	 @SuppressWarnings("unchecked")
	 List<Customer> result = select.getResultList();	 
	 for(Customer c: result) {
		 System.out.println((c));
		 }
	 for(Customer c: customers) {
		 assertTrue(result.contains(c));
		 }
	 
		System.out.println("All in it!");
	 
	 em.getTransaction().commit();
	 System.out.println("########################################################");
		System.out.println("CHECK END");
	 }

	@Test
	 public void changeCustomerData(){
		System.out.println("########################################################");
		System.out.println("DOING CHANGE");
	 assertTrue(em != null);
	 //Transparent Update
	 em.getTransaction().begin();
	 Customer jane = em.find(Customer.class, 111L);
	 System.out.println(jane);
	 assertTrue(jane != null);
	 em.detach(jane);
	 jane.setFirstName("SchmutzigerHarrald");
	  em.merge(jane);
	  em.getTransaction().commit();
	  jane =em.find(Customer.class, 111L );
		 System.out.println(jane);
	  assertTrue(jane.getFirstName().equals("SchmutzigerHarrald"));
	  System.out.println("########################################################");
		System.out.println(" CHANGE END");
	 }
	
	@Test
	public void delete(){
		System.out.println("########################################################");
		System.out.println("DOING DELETETEST");
		em.getTransaction().begin();
		
		Query select = em.createNativeQuery("SELECT * FROM CUSTOMER", Customer.class);
		
		 @SuppressWarnings("unchecked")
		 List<Customer> result = select.getResultList();	
		 
	
		for(Customer c: result) {
			 System.out.println((c));
			 }
		 for(Customer c: customers) {
			 assertTrue(result.contains(c));
			 }
		
		Query myQuery = em.createNativeQuery("DELETE FROM CUSTOMER WHERE FIRSTNAME='Paul'");
		myQuery.executeUpdate();
		// should still have SOMETHING in the DB
		 @SuppressWarnings("unchecked")
		 List<Customer> rs = select.getResultList();	
	     assertFalse(rs.isEmpty());
	
	//cardinality of Customers in DB
		Query number = em.createNativeQuery("SELECT Count(CID) FROM CUSTOMER");
		
		BigDecimal size = (BigDecimal) number.getSingleResult();
		
		System.out.println(size);
		assertEquals(size.intValue(),  9);
		
		em.getTransaction().commit();
		System.out.println("########################################################");
		System.out.println(" DELETETEST END");
	}
	
	@AfterAll
	public static void shutdown(){
		em.close();
		emf.close();
	}
}
