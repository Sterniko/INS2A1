package jpa_hibernate_relationship;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="ADDRESS")
public class Address {

	@Id 
    @Column(nullable = false)
	private Long addressId;

	@javax.persistence.Basic
	private String street;
	@javax.persistence.Basic
	private int nummer;
	@javax.persistence.Basic
	private int zip;
	
	@javax.persistence.OneToOne(optional=false,cascade=CascadeType.ALL, 
		       mappedBy="address",targetEntity=Customer.class, orphanRemoval = true)
	private Customer customer;
	
	
	public Address(Long adressId, String street, int nummer, int zip) {
		this.addressId = adressId;
		this.street = street;
		this.nummer = nummer;
		this.zip = zip;
	}
	
	public Address(String streets, int numbers, int zip){
		this(0L,streets, numbers, zip);
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", street=" + street + ", nummer=" + nummer + ", plz=" + zip
				+ ", customer=" + customer + "]";
	}
	

}
