package com.cg.ba.beans;

import java.util.HashMap;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
@Entity
public class Account
{
	@Id
	private String accountNumber;
	private String customerName;
	private double accountBalance;
	

	@OneToMany
	@ElementCollection(fetch = FetchType.LAZY)
	  @CollectionTable(name = "map", joinColumns =@JoinColumn(name ="account_accountNumber"))
	  @MapKeyColumn(name = "accountNumber", length = 50)
	private Map<Integer,TransactionLog> logs=new HashMap<Integer,TransactionLog>();
	
	
	public Account() {
		super();
	}
	public Account(String customerName, double accountBalance, String accountNumber, Map<Integer, TransactionLog> logs)
	{
		super();
		this.customerName = customerName;
		this.accountBalance = accountBalance;
		this.accountNumber = accountNumber;
		this.logs = logs;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountnumber) {
		this.accountNumber = accountnumber;
	}
	public Map<Integer, TransactionLog> getLogs() {
		return logs;
	}
	public void setLogs(Map<Integer, TransactionLog> logs) {
		this.logs = logs;
	}
	
	
	
}
