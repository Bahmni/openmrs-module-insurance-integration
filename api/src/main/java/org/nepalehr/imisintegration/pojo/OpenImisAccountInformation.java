package org.nepalehr.imisintegration.pojo;

import java.util.Date;

public class OpenImisAccountInformation {

	private String nhisNumber;
	private String firstName;
	private String middleName;
	private String lastName;
	private Date validFrom;
	private Date validTill;
	private Double remainingAmount;
	private String cardType;

	public String getNhisNumber() {
		return nhisNumber;
	}

	public void setNhisNumber(String nhisNumber) {
		this.nhisNumber = nhisNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTill() {
		return validTill;
	}

	public void setValidTill(Date validTill) {
		this.validTill = validTill;
	}

	public Double getRemainingAmount() {
		return remainingAmount;
	}

	public void setRemainingAmount(Double remainingAmount) {
		this.remainingAmount = remainingAmount;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	@Override
	public String toString() {
		return "OpenImisAccountInformation [nhisNumber=" + nhisNumber + ", firstName=" + firstName + ", middleName="
				+ middleName + ", lastName=" + lastName + ", validFrom=" + validFrom + ", validTill=" + validTill
				+ ", remainingAmount=" + remainingAmount + ", cardType=" + cardType + "]";
	}

}
