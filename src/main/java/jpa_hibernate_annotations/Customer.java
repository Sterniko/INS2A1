package jpa_hibernate_annotations;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="CUSTOMER")

public class Customer {
	
	@Id
	@Column(name="CID", nullable = false)
	private Long id;
	@Basic
	@Column(name="FIRSTNAME")
	private String firstName;
	@Basic
	@Column(name="LASTNAME")
	private String lastName;
	@Basic
	@Column(name="ENTRYDATE")
	private Date entryDate;
	
	
	public Customer (Long id, String firstName, String lastName, Date entryDate ) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.entryDate = entryDate;
	}//constructor()

	public Customer() {
	
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", entryDate=" + entryDate
				+ "]";
	}

}