package com.cg.ba.util;

import java.util.HashMap;
import java.util.Map;

import com.cg.ba.beans.Account;

public class UtilClass
{
	private static Map<String,Account> accountEntry=new HashMap<String,Account>();

	public static Map<String, Account> getAccountEntry() {
		return accountEntry;
	}

	public static void setAccountEntry(Map<String, Account> accountEntry) {
		UtilClass.accountEntry = accountEntry;
	}
	
	
}
