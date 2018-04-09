package jpa_hibernate_relationship;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;



@NamedQuery(
		name="findCustomersByName",
		query="FROM CUSTOMERREL c WHERE c.firstName LIKE :custName"
		) 
@Entity(name="CUSTOMERREL")
public class Customer {
	
	@Id
	@Column(name="CUSTOMID")
	private Long customerId;
	@Basic
	@Column(name="FIRSTNAME")
	private String firstName;
	@Basic
	@Column(name="SURNAME")
	private String lastName;
	@Basic
	@Column(name="ENTRYDATE")
	private Date entryDate;

	// address-ID
	@ManyToOne
	@JoinColumn(name="ADDRESSID", nullable=false)
	private Address address;

	
	// banks the customer visits
	@ManyToMany
	@JoinTable(name="VISITS",
	joinColumns=@JoinColumn(name="CUSTOMID",
	referencedColumnName="CUSTOMID"),
	inverseJoinColumns=@JoinColumn(name="BANKID",
	referencedColumnName="BANKID")
	)
	private List<Bank> banks;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="customer")
	private List<CreditCard> cards;
	
	public Customer (Long customerId, String firstName, String lastName, Date entryDate, Address address) {
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.entryDate = entryDate;
		this.address = address;
		this.banks = new ArrayList<Bank>();
		this.cards = new ArrayList<CreditCard>();
	}//constructor()
	
	public Customer (Long customerId, String firstName, String lastName, Date entryDate ) {
		this(customerId, firstName, lastName, entryDate, null);
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

	public List<Bank> getBanks() {
		return banks;
	}

	public void addBank(Bank bank) {
		this.banks.add(bank);
	}

	public List<CreditCard> getCards(){
		return cards;
	}
	
	public void addCards(CreditCard c) {
		cards.add(c);
	}


	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", entryDate=" + entryDate + ", address=" + address + "]";
	}
	


}