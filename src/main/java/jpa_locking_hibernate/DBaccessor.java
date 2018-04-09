package jpa_locking_hibernate;



import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



public class DBaccessor {
	private  EntityManagerFactory emf;
	private  EntityManager em;
	private Customer myC;
	
	public DBaccessor() {
		emf = Persistence.createEntityManagerFactory("persistenceUnit");
		em = emf.createEntityManager();
		myC = null;
	}

	public void doAction() {
		em.getTransaction().begin();
		em.merge(myC);
		em.getTransaction().commit();
	}

	public void changeCstmrName(Customer c, String newname) {
		c.setFirstName(newname);
		myC = c;
	}
}
