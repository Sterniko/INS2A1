package jpa_hibernate_xml;

import java.sql.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class XML {
	public static void main(String[] args) {
		// for getting a connection to the database we need a manager
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit");

		// EntityManager is an instance of the EntityManagerFactory
		EntityManager em = emf.createEntityManager();

		// start the transaction
		em.getTransaction().begin();

		Customer customer = new Customer(731L, "nn", "aas", new Date(System.currentTimeMillis()));

		// call the EntityManager so that it can store the student
		em.persist(customer);

		// save the data and close the transaction
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

}
