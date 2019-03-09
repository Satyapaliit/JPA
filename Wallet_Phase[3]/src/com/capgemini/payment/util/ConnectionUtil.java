package com.capgemini.payment.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionUtil {

	private static EntityManagerFactory factory;
	
	public ConnectionUtil() {
		
		factory = Persistence.createEntityManagerFactory("Wallet-Service");
	}

	public static EntityManagerFactory getFactory() {
		return factory;
	}
	
}
