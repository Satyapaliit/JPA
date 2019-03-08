package com.cg.ba.service;

import java.util.Map;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;

import com.cg.ba.beans.Account;
import com.cg.ba.beans.TransactionLog;
import com.cg.ba.dao.IServicesDao;
import com.cg.ba.dao.ServicesDaoImpl;
import com.cg.ba.exception.BankException;


public class ServicesImpl implements IServices
{
	IServicesDao servicesDao=new ServicesDaoImpl();
	ServicesDaoImpl dao=new ServicesDaoImpl();
	private EntityManager em=dao.getEm();
	@Override
	public Account createAccount(String accNo, Account acc) throws BankException
	{
		if(!isValidName(acc.getCustomerName()))
		{
			throw new BankException("Invalid Name!(Name must be atleast 3 character long and should start with  capital letter!)");
		}
		else if(!isValidAccNo(accNo))
		{
			throw new BankException("Invalid Phone Number!");
		}
		else if(acc.getAccountBalance()<500)
		{
			throw new BankException("Minimum Account balance must be >=500 !");
		}
		else
		{
			TransactionLog firstTxn=new TransactionLog("Account Created","--","--",acc.getAccountBalance(),acc.getAccountBalance());
			acc.getLogs().put(1, firstTxn);
			return servicesDao.createAccount(accNo, acc);
		}
	}

	@Override
	public double showBalance(String accNo) throws BankException
	{
		if(!isValidAccNo(accNo))
		{	
			throw new BankException("Invalid Account Number!");
		}
		else if(!isRegisteredAccount(accNo))
		{
			throw new BankException("Account does not Exists!");
		}
		else
		{
			return servicesDao.showBalance(accNo);
		}
	}

	@Override
	public boolean deposite(String accNo,double amount) throws BankException
	{
		if(!isValidAccNo(accNo))
		{	
			throw new BankException("Invalid Account Number!");
		}
		else if(!isRegisteredAccount(accNo))
		{
			throw new BankException("Account does not Exists!");
		}
		else
		{
			return servicesDao.deposite(accNo, amount);
		}
	}

	@Override
	public boolean withdraw(String accNo,double amount) throws BankException
	{
		if(!isValidAccNo(accNo))
		{	
			throw new BankException("Invalid Account Number!");
		}
		else if(!isRegisteredAccount(accNo))
		{
			throw new BankException("Account does not Exists!");
		}
		else if((amount>=servicesDao.showBalance(accNo)))
		{
			throw new BankException("Sorry!, Insufficient Balance.\n\n");
		}
		else
		{
			return servicesDao.withdraw(accNo, amount);
		}
	}

	@Override
	public boolean transfer(String accNo,String transferTo, double amount) throws BankException
	{
		if(!isValidAccNo(accNo))
		{	
			throw new BankException("Invalid Sender Account Number!");
		}
		else if(!isValidAccNo(transferTo))
		{
			throw new BankException("Invalid Receiver Account Number!");
		}
		else if(!isRegisteredAccount(accNo))
		{
			throw new BankException("Sender Account does not Exists!");
		}
		else if(!isRegisteredAccount(transferTo))
		{
			throw new BankException("Receiver Account does not Exists!");
		}				
		else if((amount>=servicesDao.showBalance(accNo)))
		{
			throw new BankException("Sorry!, Insufficient Balance.\n\n");
		}
		else	
		{
			return servicesDao.transfer(accNo, transferTo, amount);
		}
	}

	@Override
	public Map<Integer, TransactionLog> viewLogs(String accNo) throws BankException
	{
		return servicesDao.viewLogs(accNo);
	}
	
	
	public boolean isValidAccNo(String accNo)
	{
		Pattern mobPattern=Pattern.compile("^[6-9]{1}[0-9]{9}$");
		Matcher mobMatch=mobPattern.matcher(accNo);
		if(mobMatch.matches())
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	public boolean isValidName(String name)
	{
		Pattern namePattern=Pattern.compile("^[A-Z]{1}[a-zA-Z]{2,}$");
		Matcher nameMatch=namePattern.matcher(name);
		if(nameMatch.matches())
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	public boolean isRegisteredAccount(String acc)
	{	
		em.getTransaction().begin();
		
		if(em.contains(acc))
		{
			return true;
		}
		else
		{
			return false;
		}
		
		
	}

}
