package jpa_hibernate_relationship;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity(name = "CREDITCARD")
public class CreditCard {
	
	@Id
	@Column(name="CARDID", nullable = false)
	private long creditCardNumber;
	
	//user of the card
	@ManyToOne
	@JoinColumn(name="CUSTOMID", nullable=false)
	private Customer customer;
	
	public CreditCard(long cid) {
		this.creditCardNumber = cid;
	}

	public long getCardNumber() {
		return creditCardNumber;
	}

	public void setCardNumber(long cardNumber) {
		this.creditCardNumber = cardNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "CreditCard [creditCardId=" + creditCardNumber + "]";
	}

}
