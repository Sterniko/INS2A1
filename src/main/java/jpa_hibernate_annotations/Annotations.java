package jpa_hibernate_annotations;

import java.sql.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import jpa_hibernate_xml.Customer;

public class Annotations {

	public static void main(String[] args) {
		// for getting a connection to the database we need a manager
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit");

		// EntityManager is an instance of the EntityManagerFactory
		EntityManager em = emf.createEntityManager();

		// start the transaction
		em.getTransaction().begin();

		Customer customer = new Customer(765L, "Ann", "Harn", new Date(System.currentTimeMillis()));

		// call the EntityManager and it can store the student
		em.persist(customer);
		
		em.getTransaction().commit();
		
		//update customer 
		customer.setFirstName("Johann");
		em.getTransaction().begin();
		
		em.persist(customer);
		// save the data and close the transaction
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

}
