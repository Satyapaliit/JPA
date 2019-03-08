package com.cg.ba.beans;

import java.time.LocalDate;

public class TransactionLog
{
	static int i=0;
	private LocalDate date;
	private String description;
	private String transferedTo; 
	private String transferedFrom; 
	private double amount;
	private double availableBalance;
	
	
	public TransactionLog(String description,String transferedTo,String transferedFrom,double amount,
			double availableBalance)
	{
		super();
		this.date = LocalDate.now();
		this.description = description;
		this.transferedTo = transferedTo;
		this.transferedFrom = transferedFrom;
		this.amount=amount;
		this.availableBalance = availableBalance;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
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
		return ++i+": [Date:" + date + ", Description:" + description +
				", Transfered To:" + transferedTo + ", Transfered From:" + transferedFrom
				+ ", Amount:"+amount+", Available Balance:" + availableBalance + "]";
	}

	
	
	

}
