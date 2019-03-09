package com.capgemini.payment.repository;

import com.capgemini.payment.beans.Customer;

public interface WalletRepository {

	public boolean save(Customer customer);

	public Customer findCustomer(String mobileNumber);

}
