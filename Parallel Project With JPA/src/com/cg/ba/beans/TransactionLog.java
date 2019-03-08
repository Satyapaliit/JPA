package com.cg.ba.beans;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="logs")
public class TransactionLog
{
	
	@Id
	@GeneratedValue
	private String accountNumber;
	
	private String description;
	private String transferedTo; 
	private String transferedFrom; 
	private double amount;
	private double availableBalance;
	
	
	public TransactionLog() {
		super();
	}

	public TransactionLog(String description,String transferedTo,String transferedFrom,double amount,
			double availableBalance)
	{
		super();
		
		this.description = description;
		this.transferedTo = transferedTo;
		this.transferedFrom = transferedFrom;
		this.amount=amount;
		this.availableBalance = availableBalance;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTransferedTo() {
		return transferedTo;
	}
	public void setTransferedTo(String transferedTo) {
		this.transferedTo = transferedTo;
	}
	
	
	public String getTransferedFrom() {
		return transferedFrom;
	}
	public void setTransferedFrom(String transferedFrom) {
		this.transferedFrom = transferedFrom;
	}
	
	
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}
	@Override
	public String toString() {
		return accountNumber+": [ Description:" + description +
				", Transfered To:" + transferedTo + ", Transfered From:" + transferedFrom
				+ ", Amount:"+amount+", Available Balance:" + availableBalance + "]";
	}

	
	
	

}
