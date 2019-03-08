package com.cg.ba.dao;


import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.Transaction;

import com.cg.ba.beans.Account;
import com.cg.ba.beans.TransactionLog;
import com.cg.ba.exception.BankException;
import com.cg.ba.util.JPAUtil;



public class ServicesDaoImpl implements IServicesDao
{
	private EntityManager em=JPAUtil.getEntityManager();
	
	static int txnNo=1;
	public ServicesDaoImpl()
	{
		em=JPAUtil.getEntityManager();
	}
	
	
	
	
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}




	@Override
	public Account createAccount(String accNo, Account acc) throws BankException
	{
		EntityTransaction tran=em.getTransaction();
		tran.begin();
		em.persist(acc.getLogs());
		em.persist(acc);
		Account temp= em.find(Account.class,accNo);
		tran.commit();
		return temp;
	}

	@Override
	public double showBalance(String accNo) throws BankException
	{
		em.getTransaction().begin();
		return em.find(Account.class,accNo).getAccountBalance();
	}

	@Override
	public boolean deposite(String accNo,double amount) throws BankException
	{
		em.getTransaction().begin();
		double accountBalance=em.find(Account.class,accNo).getAccountBalance();
		Account accTemp=em.find(Account.class,accNo);
		accTemp.setAccountBalance(accountBalance+amount);
		accTemp.getLogs().put(++txnNo, new TransactionLog("Deposited",
				null,null,amount,accTemp.getAccountBalance()));
		
		em.merge(accTemp);
		
		em.getTransaction().commit();
		
		return true;
	}

	@Override
	public boolean withdraw(String accNo,double amount) throws BankException
	{
		em.getTransaction().begin();
		double accountBalance=em.find(Account.class,accNo).getAccountBalance();
		if(accountBalance<amount)
		{
			throw new BankException("Insufficient Balance!");
		}
		else
		{
			Account accTemp=em.find(Account.class,accNo);
			accTemp.setAccountBalance(accountBalance-amount);
			accTemp.getLogs().put(++txnNo, new TransactionLog("Withdrawl",
					null,null,amount,accTemp.getAccountBalance()));
			
			em.merge(accTemp);
		}
		
		em.getTransaction().commit();
		return true;
	}

	@Override
	public boolean transfer(String accNo,String recAcc,double amount) throws BankException
	{
		
		em.getTransaction().begin();
		double accountBalance=em.find(Account.class,accNo).getAccountBalance();
		
		Account accTemp=em.find(Account.class,accNo);
		accTemp.setAccountBalance(accountBalance-amount);
		accTemp.getLogs().put(++txnNo, new TransactionLog("Transfered",
				recAcc,accNo,amount,accTemp.getAccountBalance()));
		em.merge(accTemp);
		
		Account recTemp=em.find(Account.class,accNo);
		recTemp.setAccountBalance(accountBalance+amount);
		accTemp.getLogs().put(++txnNo, new TransactionLog("Received",
				recAcc,accNo,amount,accTemp.getAccountBalance()));
		
		em.merge(accTemp);
			
		em.getTransaction().commit();
		
		return true;
		
	}

	@Override
	public Map<Integer, TransactionLog> viewLogs(String accNo) throws BankException
	{
		em.getTransaction().begin();
		Map<Integer,TransactionLog> temp=em.find(Account.class,accNo).getLogs();
		em.getTransaction().commit();
		return temp;
	}

}
