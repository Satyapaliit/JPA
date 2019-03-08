package com.cg.ba.dao;

import java.util.Map;

import com.cg.ba.beans.Account;
import com.cg.ba.beans.TransactionLog;
import com.cg.ba.exception.BankException;
import com.cg.ba.util.UtilClass;

public class ServicesDaoImpl implements IServicesDao
{
	static int txnNo=1;
	public ServicesDaoImpl()
	{
		
	}

	@Override
	public Account createAccount(String accNo, Account acc) throws BankException
	{
		
		UtilClass.getAccountEntry().put(accNo, acc);
		return UtilClass.getAccountEntry().get(accNo);
	}

	@Override
	public double showBalance(String accNo) throws BankException
	{
		return UtilClass.getAccountEntry().get(accNo).getAccountBalance();
	}

	@Override
	public boolean deposite(String accNo,double amount) throws BankException
	{
		double amtTemp=UtilClass.getAccountEntry().get(accNo).getAccountBalance();
		UtilClass.getAccountEntry().get(accNo).setAccountBalance(amount+amtTemp);
		UtilClass.getAccountEntry().get(accNo).getLogs().put(++txnNo, new TransactionLog("Deposited",
				null,null,amount,UtilClass.getAccountEntry().get(accNo).getAccountBalance()));
		
		return true;
	}

	@Override
	public boolean withdraw(String accNo,double amount) throws BankException
	{
		double balance=UtilClass.getAccountEntry().get(accNo).getAccountBalance();
		UtilClass.getAccountEntry().get(accNo).setAccountBalance(balance-amount);
		UtilClass.getAccountEntry().get(accNo).getLogs().put(++txnNo, new TransactionLog("Withdrawl",
				null,null,amount,UtilClass.getAccountEntry().get(accNo).getAccountBalance()));
		
		return true;
	}

	@Override
	public boolean transfer(String accNo,String recAcc,double amount) throws BankException
	{
		double senderAccountBalance=UtilClass.getAccountEntry().get(accNo).getAccountBalance();
		double receiverAccountBalance=UtilClass.getAccountEntry().get(recAcc).getAccountBalance();
		
		UtilClass.getAccountEntry().get(accNo).setAccountBalance(senderAccountBalance-amount);
		UtilClass.getAccountEntry().get(accNo).getLogs().put(++txnNo, new TransactionLog("Transfered",
				recAcc,accNo,amount,UtilClass.getAccountEntry().get(accNo).getAccountBalance()));
		
		
		
		UtilClass.getAccountEntry().get(recAcc).setAccountBalance(receiverAccountBalance+amount);		
		UtilClass.getAccountEntry().get(recAcc).getLogs().put(++txnNo,
				new TransactionLog("Received",recAcc,accNo,amount,
						UtilClass.getAccountEntry().get(recAcc).getAccountBalance()));
		
		return true;
	}

	@Override
	public Map<Integer, TransactionLog> viewLogs(String accNo) throws BankException
	{
		return UtilClass.getAccountEntry().get(accNo).getLogs();
	}

}
