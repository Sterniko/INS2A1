package jpa_hibernate_relationship;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;


@Entity(name="BANK")
public class Bank {
	
	@Id
	@Column(nullable = false)
	private Long bankId;
	
	@Basic
	private String location;
	
	@javax.persistence.ManyToMany
	@javax.persistence.JoinTable(name ="BANK_CUST",
				joinColumns=
				@JoinColumn(name="BANKID",
							referencedColumnName="bankId"),
				inverseJoinColumns=
				@JoinColumn(name="CUSTID",
							referencedColumnName="customerId")
				)
	private List<Customer> customerList;
	
	public Bank(long id, String location) {
		this.bankId =id;
		this.location = location;
	}
	
	public Bank(){
		
	}

	public void addCustomer(Customer customer){
		customerList.add(customer);
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}

	@Override
	public String toString() {
		return "Bank [bankId=" + bankId + ", location=" + location + ", customerList=" + customerList + "]";
	}



}
