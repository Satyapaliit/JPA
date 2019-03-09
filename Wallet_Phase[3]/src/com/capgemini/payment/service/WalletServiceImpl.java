package com.capgemini.payment.service;

import java.math.BigDecimal;

import com.capgemini.payment.beans.Customer;
import com.capgemini.payment.beans.Wallet;
import com.capgemini.payment.exception.ConnectionLostException;
import com.capgemini.payment.exception.InsufficientBalanceException;
import com.capgemini.payment.exception.InvalidMobileNumberException;
import com.capgemini.payment.repository.WalletRepository;

public class WalletServiceImpl implements WalletService {

	private WalletRepository walletRepository;

	public WalletServiceImpl(WalletRepository walletRepository) {
		super();
		this.walletRepository = walletRepository;
	}

	@Override
	public Customer createAccount(String customerName, String mobileNumber, BigDecimal amount)
			throws InvalidMobileNumberException {
		Customer findCustomer;
		findCustomer = walletRepository.findCustomer(mobileNumber);
		if (findCustomer == null) {
			Wallet wallet = createWallet(amount);
			Customer customer = createCustomer(customerName, mobileNumber, wallet);
			if (walletRepository.save(customer)) {
				return walletRepository.findCustomer(mobileNumber);
			}
		}
		throw new InvalidMobileNumberException("Mobile number already in use");

	}

	@Override
	public Customer showBalance(String mobileNumber) throws InvalidMobileNumberException {
		Customer customer;
		customer = walletRepository.findCustomer(mobileNumber);
		if (customer == null) {
			throw new InvalidMobileNumberException("This number is not in use for this application");
		}
		return customer;

	}

	@Override
	public Customer fundTransfer(String sourceMobileNumber, String targetMobileNumber, BigDecimal amount)
			throws InvalidMobileNumberException, InsufficientBalanceException, ConnectionLostException {
		Customer sourceCustomer;
		Customer targetCustomer;
		sourceCustomer = walletRepository.findCustomer(sourceMobileNumber);
		targetCustomer = walletRepository.findCustomer(targetMobileNumber);
		
		if (sourceCustomer == null) {
			throw new InvalidMobileNumberException("Your number is not in use for this application");
		}
		if (targetCustomer == null) {
			throw new InvalidMobileNumberException("Payment Receiver's number is not in use for this application");
		}
		if (sourceCustomer.getWallet().getBalance().compareTo(amount) < 0) {
			throw new InsufficientBalanceException();
		}
		Wallet sourceWallet = createWallet(sourceCustomer.getWallet().getBalance().subtract(amount));
		Wallet targetWallet = createWallet(targetCustomer.getWallet().getBalance().add(amount));
		sourceCustomer.setWallet(sourceWallet);
		targetCustomer.setWallet(targetWallet);

		if ((walletRepository.save(sourceCustomer)) && (walletRepository.save(targetCustomer))) {
			return walletRepository.findCustomer(sourceMobileNumber);
		}
		throw new ConnectionLostException();
	}

	@Override
	public Customer depositAmount(String mobileNumber, BigDecimal amount)
			throws InvalidMobileNumberException, ConnectionLostException {
		Customer customer;
		customer = walletRepository.findCustomer(mobileNumber);
		if (customer == null) {
			throw new InvalidMobileNumberException("This number is not in use for this application");
		}
		Wallet wallet = createWallet(customer.getWallet().getBalance().add(amount));
		customer.setWallet(wallet);
		if (walletRepository.save(customer)) {
			return walletRepository.findCustomer(mobileNumber);
		}
		throw new ConnectionLostException();

	}

	@Override
	public Customer withdrawAmount(String mobileNumber, BigDecimal amount)
			throws InvalidMobileNumberException, InsufficientBalanceException, ConnectionLostException {
		Customer customer;
		customer = walletRepository.findCustomer(mobileNumber);
		if (customer == null) {
			throw new InvalidMobileNumberException("This number is not in use for this application");
		}
		if (customer.getWallet().getBalance().compareTo(amount) < 0) {
			throw new InsufficientBalanceException();
		}
		Wallet wallet = createWallet(customer.getWallet().getBalance().subtract(amount));
		customer.setWallet(wallet);
		if (walletRepository.save(customer)) {
			return walletRepository.findCustomer(mobileNumber);
		}
		throw new ConnectionLostException();
	}

	private Customer createCustomer(String customerName, String mobileNumber, Wallet wallet) {
		Customer customer = new Customer();
		customer.setCustomerName(customerName);
		customer.setMobileNumber(mobileNumber);
		customer.setWallet(wallet);
		return customer;
	}

	private Wallet createWallet(BigDecimal amount) {
		Wallet wallet = new Wallet();
		wallet.setBalance(amount);
		return wallet;
	}

}
