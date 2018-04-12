package jpa_hibernate_xml;

import java.sql.Date;

import javax.persistence.Id;



 
public class Customer {
	
	@Id
	private Long cid;
	private String firstName;
	private String lastName;
	private Date entryDate;
	
	public Customer (Long id, String firstName, String lastName, Date entryDate ) {
		this.cid = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.entryDate = entryDate;
	}//constructor()

	public Customer() {
	
	}
	
	public Long getCId() {
		return cid;
	}

	public void setId(Long id) {
		this.cid = id;
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
		return "Customer [id=" + cid + ", firstName=" + firstName + ", lastName=" + lastName + ", entryDate=" + entryDate
				+ "]";
	}

}
