package jpa_hibernate_relationship;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "CREDITCARD")
public class CreditCard {
	@javax.persistence.Id
	@javax.persistence.Column(nullable = false)
	private long creditCardId;

	@Basic
	private long cardNumber;

	// orphaned(deleted) entities should be removed
	@OneToOne(optional = false, orphanRemoval = true)
	@JoinColumn(name = "CUSTOMER_ID", unique = true, nullable = false, updatable = false)
	private Customer customer;

	public CreditCard(long cid, long cnumber) {
		this.cardNumber = cnumber;
		this.creditCardId = cid;
	}

	public CreditCard() {

	}

	public CreditCard(int i) {
		// TODO Auto-generated constructor stub
	}

	public long getCreditCardId() {
		return creditCardId;
	}

	public void setCreditCardId(long creditCardId) {
		this.creditCardId = creditCardId;
	}

	public long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "CreditCard [creditCardId=" + creditCardId + ", cardNumber=" + cardNumber + ", customer=" + customer
				+ "]";
	}

}
