package jpa_locking_hibernate;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestCustomerLocking {
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
			String anno = "locking";
			for(int i = 0; i<10;i++) {
				firstNames[i]= firstNames[i]+anno;
				
			}
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
	 
	 @Test
	 public void changeNOLock() {
	try {	 
		 Customer jane;
		 Customer bruno;
		 em.getTransaction().begin();
		 DBaccessor a1= new DBaccessor();
		 DBaccessor a2= new DBaccessor();
		 jane = em.find(Customer.class, 111L);
	     a1.changeCstmrName(jane, "Bruno1");
	     bruno = em.find(Customer.class, 111L);
	     a2.changeCstmrName(bruno, "bruno2");
	  a2.doAction();
	a1.doAction();
		 em.getTransaction().commit();
		 fail("no exception thrown");
	}catch(javax.persistence.OptimisticLockException win) {
		assertTrue(true);
	}
	 }
		@Test
	public void changeLock() {
		try {
			Customer jane;
			Customer bruno;
			em.getTransaction().begin();
			DBaccessor a1 = new DBaccessor();
			DBaccessor a2 = new DBaccessor();
			jane = em.find(Customer.class, 111L);
			em.lock(jane, LockModeType.OPTIMISTIC);
			a1.changeCstmrName(jane, "Bruno1");

			bruno = em.find(Customer.class, 111L);
			a2.changeCstmrName(bruno, "bruno2");
			a2.doAction();
			jane = em.find(Customer.class, 111L);
			a1.changeCstmrName(jane, "Bruno1");
			a1.doAction();
			em.getTransaction().commit();
		} catch (IllegalStateException e) {
			assertTrue(e.toString().equals("Transaction alreadz active"));
			em.getTransaction().commit();
		}
	}
}
