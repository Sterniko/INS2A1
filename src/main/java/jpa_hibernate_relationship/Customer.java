package jpa_hibernate_relationship;

import java.sql.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


@Entity(name="CUSTOMERREL")

public class Customer {
	
	@Id
	//@Column(name="ID")
	private Long customerId;
	@Basic
	//@Column(name="FIRSTNAME")
	private String firstName;
	@Basic
	//@Column(name="LASTNAME")
	private String lastName;
	@Basic
	//@Column(name="ENTRYDATE")
	private Date entryDate;
	
	@javax.persistence.OneToOne(optional=false, orphanRemoval = true)
    @javax.persistence.JoinColumn(name = "ADDRESS_ID", unique = true, nullable = false, updatable = false )
	private Address address;
	
	@javax.persistence.OneToOne(optional=false,cascade=CascadeType.ALL, 
		       mappedBy="customer",targetEntity=CreditCard.class, orphanRemoval = true)
	private CreditCard creditCard;
	
	@ManyToMany(mappedBy="customerList")
	private List<Bank> bankList;
	
	public Customer (Long customerId, String firstName, String lastName, Date entryDate, Address address, CreditCard creditCard, List<Bank> bankList) {
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.entryDate = entryDate;
		this.address = address;
		this.creditCard = creditCard;
		this.bankList = bankList;
	}//constructor()


	
	public Long getcustomerId() {
		return customerId;
	}

	public void setcustomerId(Long customerId) {
		this.customerId = customerId;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public List<Bank> getBankList() {
		return bankList;
	}

	public void setBankList(List<Bank> bankList) {
		this.bankList = bankList;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", entryDate=" + entryDate + ", address=" + address + ", creditCard=" + creditCard + ", bankList="
				+ bankList + "]";
	}
	


}