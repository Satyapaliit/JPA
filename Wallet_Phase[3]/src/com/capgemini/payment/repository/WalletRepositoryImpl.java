package com.capgemini.payment.repository;

import com.capgemini.payment.beans.Customer;
import com.capgemini.payment.util.ConnectionUtil;
import com.capgemini.payment.util.DatabaseUtil;

public class WalletRepositoryImpl implements WalletRepository {

	private DatabaseUtil databaseUtil = new DatabaseUtil();

	public WalletRepositoryImpl() {
		super();
		new ConnectionUtil();
	}

	@Override
	public boolean save(Customer customer) {
		Customer cust = findCustomer(customer.getMobileNumber());
		if (cust == null) {
			return databaseUtil.insertCustomer(customer);
		} else {
			return databaseUtil.updateWalletBalance(customer);
		}
	}

	@Override
	public Customer findCustomer(String mobileNumber) {
		Customer customer = databaseUtil.selectCustomer(mobileNumber);
		return customer;
	}
}
