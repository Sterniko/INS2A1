package jpa_hibernate_relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
@Entity(name="ADDRESS")
public class Address {

	@Id 
    @Column(nullable = false)
	private Long addressId;

	@Basic
	private String street;
	@Basic
	private int nummer;
	@Basic
	private int zip;
	
	@OneToMany(mappedBy="address")	
	private List<Customer> customers;
	
	public Address(Long adressId, String street, int nummer, int zip) {
		this.addressId = adressId;
		this.street = street;
		this.nummer = nummer;
		this.zip = zip;
		customers = new ArrayList<Customer>();
	}	


	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNummer() {
		return nummer;
	}

	public void setNummer(int nummer) {
		this.nummer = nummer;
	}

	public int getZip() {
		return zip;
	}

	public void setPlz(int zip) {
		this.zip = zip;
	}
	
	
	public List<Customer> getCustomers() {
		return customers;
	}

public void addCustomer(Customer c) {
	customers.add(c);
}

	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", street=" + street + ", nummer=" + nummer + ", plz=" + zip
				+ "]";
	}
	

}
