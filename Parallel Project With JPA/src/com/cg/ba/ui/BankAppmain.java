package com.cg.ba.ui;


import java.util.HashMap;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cg.ba.beans.Account;
import com.cg.ba.beans.TransactionLog;
import com.cg.ba.exception.BankException;
import com.cg.ba.service.IServices;
import com.cg.ba.service.ServicesImpl;

public class BankAppmain
{
	private static Scanner scanner=new Scanner(System.in);
	public static void showMenu()
	{
		System.out.println("1) Create Account");
		System.out.println("2) Show Balance");
		System.out.println("3) Deposite");
		System.out.println("4) Withdraw");
		System.out.println("5) Fund Transfer");
		System.out.println("6) View Transactions");
		System.out.println("7) Exit");
		
	}
	public static boolean isValidOption(int option) throws InputMismatchException
	{
		Pattern optionPattern=Pattern.compile("[1-9]{1,}");
		Matcher optionMatch=optionPattern.matcher(Integer.toString(option));
		if(optionMatch.matches())
		{
			return true;
		}
		else
		{	
			return false;
		}
	}

	public static void main(String[] args)
	{
		IServices iServices=new ServicesImpl();
		System.out.println("\n------Welcome To Citi Bank------\n");
		System.out.print("Enter Your Choice:");
		int option=0;
		while(true)
		{
			System.out.println("\n\n");
			showMenu();
		
			try
			{
				System.out.print("Your Option?:");
				option=scanner.nextInt();
				scanner.nextLine();
				switch(option)
				{
				case 1:
				{
					System.out.print("Enter Account Holder Name:");
					String accHolName=scanner.nextLine();
					
					System.out.print("Enter Account Holder Phone Number:");
					String accHolPhoneNo=scanner.nextLine();
					
					System.out.print("Enter Account Initial balance:");
					double accHolIniBal=scanner.nextDouble();
					
					
					Map<Integer,TransactionLog> tempMap=new HashMap<Integer,TransactionLog>();
					Account acc=new Account(accHolName,accHolIniBal,accHolPhoneNo,tempMap);
					
					
					try
					{
						Account ac=iServices.createAccount(accHolPhoneNo,acc);
						System.out.println("-------------------------------------------");
						System.out.println("Account created:");
						System.out.println("Account No.:"+ac.getAccountNumber());
						System.out.println("Account Holder:"+ac.getCustomerName());
						System.out.println("Account Balance:"+ac.getAccountBalance()+"\n");
					}
					catch(BankException be)
					{
						System.err.println(be.getMessage());
					}
				}
				break;
				case 2:
				{
					System.out.print("Enter Account Number:");
					String accNo=scanner.nextLine();
					try
					{
						double amount=iServices.showBalance(accNo);
						System.out.println("-------------------------------------------");
						System.out.println("Account Number:"+accNo);
						System.out.println("Available Balance:"+amount);
					} catch (BankException e)
					{
						System.err.println(e.getMessage());
					}
				}
				break;
				case 3:
				{
					System.out.print("Enter Account Number:");
					String acc=scanner.nextLine();
					System.out.print("Enter Amount to Deposite:");
					double amount=scanner.nextDouble();
					try
					{
						if(iServices.deposite(acc, amount))
						{
							System.out.println("-------------------------------------------");
							System.out.println("Amount Deposited.");
							System.out.println("Account Number:"+acc);
							System.out.println("Available Balance:"+iServices.showBalance(acc));
						}
					}
					catch (BankException e)
					{
						System.err.println(e.getMessage());
					}
				}
				break;
				case 4:
				{
					System.out.print("Enter Account Number:");
					String acc=scanner.nextLine();
					System.out.print("Enter Amount too Withdraw:");
					double amount=scanner.nextDouble();
					try
					{
						if(iServices.withdraw(acc, amount))
						{
							System.out.println("-------------------------------------------");
							System.out.println("WithDrawl Succeed.");
							System.out.println("Available Balance:"+iServices.showBalance(acc));
						}						
					}
					catch (BankException e)
					{
						System.err.println(e.getMessage());
					}
				}
				break;
				case 5:
				{
					System.out.print("Enter Account Number to transfer From:");
					String acc=scanner.nextLine();
					
					System.out.print("Enter Receiver Account Number:");
					String recAcc=scanner.nextLine();
					
					System.out.print("Enter Amount to Transfer:");
					double amount=scanner.nextDouble();
					
					try
					{
						if(iServices.transfer(acc, recAcc, amount))
						{
							System.out.println("Transaction Successfull.");
							System.out.println("Available Balance:"+iServices.showBalance(acc));
							
						}
						
					}
					catch (BankException e)
					{
						System.err.print(e.getMessage());
					}
					
				}
				break;
				case 6:
				{
					System.out.print("Enter Account Number:");
					String accNo=scanner.nextLine();
					try
					{
						Map<Integer,TransactionLog> txnLog=iServices.viewLogs(accNo);
						System.out.println("-----Transactions Details-----");
						System.out.println("---------------------------------------------------------");
						
						 for (Map.Entry<Integer,TransactionLog> entry : txnLog.entrySet()) 
						 {
							 System.out.println(entry.getValue());
						
						 }
						
					}
					catch (BankException e)
					{
						System.err.println(e.getMessage());
					}
					
					
				}
				break;
				case 7:
				{
					System.exit(0);
				}
				break;
				default:
				{
					System.out.println("Invalid Choice!\n");
				}
				}//switch ends
				
			}//while ends
			
			catch(InputMismatchException e)
			{
				System.err.println("Pleae Enter Numerical Option!");
				break;
			}
						
			
		
		
	}

}
}
