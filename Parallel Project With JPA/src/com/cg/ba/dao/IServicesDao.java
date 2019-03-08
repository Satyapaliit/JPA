package com.cg.ba.dao;

import java.util.Map;

import com.cg.ba.beans.Account;
import com.cg.ba.beans.TransactionLog;
import com.cg.ba.exception.BankException;

public interface IServicesDao
{
	Account createAccount(String accNo,Account acc) throws BankException;
	double showBalance(String accNo) throws BankException;
	boolean deposite(String accNo,double amount) throws BankException;
	boolean withdraw(String accNo,double amount) throws BankException;
	boolean transfer(String accNo,String transferTo,double amount) throws BankException;
	Map<Integer, TransactionLog> viewLogs(String accNo) throws BankException;
}
