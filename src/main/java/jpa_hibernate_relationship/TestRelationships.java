package jpa_hibernate_relationship;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestRelationships {
	
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static String[] streets;
	private static int[] numbers;
	private static int[] plz;
	private static List<Address> addresses;
	private static int[] creditCardNumbers;
	private static List<CreditCard> creditCards;
	private static String[] locations;
	private static List<Bank> banks;
	private static String[] firstNames;
	private static String[] lastNames;
	private static List<Customer> customers;
	private static Long[] ids;
	@BeforeAll
	public static void connectDatabase(){
		emf = Persistence.createEntityManagerFactory("persistenceUnit");
		em = emf.createEntityManager();
	}
	
	@BeforeEach
	public void setUp(){
		em.getTransaction().begin();
		Query deleteBankCust = em.createNativeQuery("DELETE FROM BANK_CUST");
		deleteBankCust.executeUpdate();
		Query deleteBank = em.createNativeQuery("DELETE FROM BANK");
		deleteBank.executeUpdate();
		Query deleteCreditCard = em.createNativeQuery("DELETE FROM CREDITCARD");
		deleteCreditCard.executeUpdate();
		Query deleteCust = em.createNativeQuery("DELETE FROM CUSTOMERREL");
		deleteCust.executeUpdate();
		Query deleteAddress = em.createNativeQuery("DELETE FROM ADDRESS");
		deleteAddress.executeUpdate();
		em.getTransaction().commit();
		
		createIds();
		createAddresses();
		createCreditCards();
		createBanks();
		createCustomers();
		setCustomersInCreditCards();
		setCustomersInAddresses();
		setCustomersInBanks();
		
		em.getTransaction().begin();
		
		for(Address a : addresses){
			em.persist(a);
		}
		
		for(Customer c: customers){
			em.persist(c);
		}
		for(CreditCard c : creditCards){
			em.persist(c);
		}
		for(Bank b : banks){
			em.persist(b);
		}

		em.getTransaction().commit();
	}
	
	private void createIds() {
		ids = new Long[] { 111L, 222L, 333L, 444L, 555L, 666L, 777L, 888L, 999L, 000L };
		
	}

	@Test
	public void checkCorrectUser() {
		em.getTransaction().begin();
		TypedQuery<Customer> myQuery = em.createQuery("FROM CUSTOMERREL c ORDER by c.id" , Customer.class);
		List<Customer> result = myQuery.getResultList();
		assertEquals(customers, result);
		em.getTransaction().commit();
	}
	
	@Test
	public void changeUser(){
		em.getTransaction().begin();
		customers.get(1).setFirstName("Peter");
		em.merge(customers.get(1));
		
		Query check = em.createNativeQuery("SELECT firstName from CUSTOMERREL WHERE familyName ='Schreier'");
		List result = check.getResultList();
		String name = (String)result.get(0);
		assertEquals(name, "Peter");
		
		em.getTransaction().commit();
	}
	
	@Test
	public void deleteAll(){
		clear();
		em.getTransaction().begin();
		Query number = em.createNativeQuery("SELECT Count(*) FROM CUSTOMERREL");
		
		BigDecimal size = (BigDecimal) number.getSingleResult();
		assertEquals(new BigDecimal(0), size);
		
		em.getTransaction().commit();
	}
	
	
	
	@Test
	public void workWithRelationships(){
		Query query = em.createQuery("FROM CUSTOMERREL c order by c.id");
		List<Customer> list= (List<Customer>)query.getResultList();
		int i = 0;
		String[] checkStreets = new String[list.size()];
		for(Customer customer:list){
		       checkStreets[i] = customer.getAddress().getStreet();
		       i++;
		}
		assertArrayEquals(streets, checkStreets);
	}

	private static void setCustomersInBanks() {
		for(int i = 0; i < customers.size(); i++){
			banks.get(i).setCustomerList(customers);
		}
		
	}

	private static void setCustomersInAddresses() {
		for(int i = 0; i < customers.size(); i++){
			addresses.get(i).setCustomer(customers.get(i));
		}
		
	}

	private static void setCustomersInCreditCards() {
		for(int i = 0; i < customers.size(); i++){
			creditCards.get(i).setCustomer(customers.get(i));
		}
	}

	private static void createCustomers() {
		firstNames = new String[] { "Jane", "Ana", "Christian", "Tara", "Paul", "Jana", "Robert", "Mila", "Lana",
				"Jovan" };

		lastNames = new String[] { "Milbradt", "Kurnikova", "Bild", "Schmidt", "Hietel", "Clarsen", "Katzenberger",
				"Fonda", "Jovanov", "Angeleski" };
		
		customers = new ArrayList<Customer>();
		
		for(int i = 0; i < ids.length; i++){
			Customer newCustomer = new Customer(ids[i], firstNames[i], lastNames[i],
					new Date(System.currentTimeMillis()), addresses.get(i), 
					creditCards.get(i), banks);
			customers.add(newCustomer);
		}
		
	}

	private static void createBanks() {
		locations = new String[]{"Volksdorf", "Hoisbüttel", "Rahlstedt", "Wandsbek", "Bergstedt","Billstedt", "Reppenstedt", "Rettmer", "Pinneberg", "Mechtersen", "Bardowick"};
		banks = new ArrayList<Bank>();
		for(int i = 0; i < locations.length; i++){
			Bank bank = new Bank(ids[i], locations[i]);
			banks.add(bank);
		}
		
	}

	private static void createAddresses() {
		streets = new String[]{"Baumstraße", "Holzstraße", "Eisenstraße", "Goldstraße", "Marmorstraße", "Holstenstrasse", "Stephanstrasse","Baseler PLatz", "Rheinhardstrasse", "Dernauerstrasse"};
		numbers = new int[]{10,20,30,40,50, 60, 70, 80, 90, 100};
		plz = new int[]{22949, 22450, 22303, 22405, 22395, 22047, 22095, 31509, 27456, 11111};
		addresses = new ArrayList<Address>();
		for(int i = 0; i < streets.length; i++){
			Address addr = new Address(ids[i], streets[i], numbers[i], plz[i]);
			addresses.add(addr);
		}
		
	}

	private static void createCreditCards() {
		creditCardNumbers = new int[]{10,20,30,40,50, 60, 70, 80, 90, 100};
		creditCards = new ArrayList<CreditCard>();
		for(int i = 0; i < creditCardNumbers.length; i++){
			CreditCard card = new CreditCard(ids[i], creditCardNumbers[i]);
			creditCards.add(card);
		}
		
	}
	
	private void clear() {
		if(customers != null){
			em.getTransaction().begin();
			for(Bank b : banks){
				if(b != null){
					em.remove(b);
					}
			}
			for(CreditCard c : creditCards){
				if(c != null){
					em.remove(c);
					}
			}
			for(Customer c: customers){
				if(c != null){
					em.remove(c);
					}
			}
			for(Address a : addresses){
				em.remove(a);
			}
			em.getTransaction().commit();
		} //else no customers in database
	}
	
	@AfterAll
	public static void shutdown(){
		em.close();
		emf.close();
	}


	
}
