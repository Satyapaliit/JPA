package com.capgemini.payment.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.capgemini.payment.beans.Customer;

public class DatabaseUtil {
	public boolean insertCustomer(Customer customer) {
		EntityManagerFactory factory = ConnectionUtil.getFactory();
		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(customer);
		entityManager.getTransaction().commit();
		entityManager.close();
		return true;
	}

	public boolean updateWalletBalance(Customer customer) {
		EntityManagerFactory factory = ConnectionUtil.getFactory();
		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin();
		Customer cust = entityManager.find(Customer.class, customer.getMobileNumber());
		cust.setWallet(customer.getWallet());
		entityManager.getTransaction().commit();
		entityManager.close();
		return true;
	}

	public Customer selectCustomer(String mobileNumber) {
		EntityManagerFactory factory = ConnectionUtil.getFactory();
		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin();
		Customer customer = entityManager.find(Customer.class, mobileNumber);
		entityManager.close();
		return customer;
	}

}
