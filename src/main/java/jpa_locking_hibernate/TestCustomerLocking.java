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
import javax.persistence.TypedQuery;

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
			Query delete = em.createNativeQuery("DELETE FROM CUSTOMLOCK");
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
	  public void noLockResultsProblem() {
		DBaccessor a = new DBaccessor();
		DBaccessor b = new DBaccessor();
		Customer cus = null;
		TypedQuery<Customer> tpq = em.createQuery("FROM CUSTOMLOCK WHERE FIRSTNAME = 'Janelocking'", Customer.class);
		for(Customer c: tpq.getResultList())cus=c;
		a.changeCstmrName(cus, "Dieter");
		b.changeCstmrName(cus, "Wolfgang");
		 a.start();
		 b.start();
	 }
	 
//	 @Test
	 public void lockResolvesProblem() {
		 
	 }
	
	 
}
