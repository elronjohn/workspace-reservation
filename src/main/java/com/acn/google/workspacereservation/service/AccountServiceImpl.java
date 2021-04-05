package com.acn.google.workspacereservation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acn.google.workspacereservation.entity.Account;
import com.acn.google.workspacereservation.repo.AccountsRepo;

@Service
public class AccountServiceImpl implements AccountService {
	
	private AccountsRepo accountsRepo;
	
	@Autowired
	public AccountServiceImpl(AccountsRepo theAccountsRepo) {
		accountsRepo = theAccountsRepo;
	}
	
	@Override
	public List<Account> findAll() {
		return accountsRepo.findAll();
	}

	@Override
	public Account findById(int theId) {
		Optional<Account> result = accountsRepo.findById(theId);
		
		Account theclients = null;
		
		if (result.isPresent()) {
			theclients = result.get();
		}
		else {
			throw new RuntimeException("Did not find client id - " + theId);
		}
		
		return theclients;
	}

	@Override
	public void save(Account account) {
		account.setAccount_type("guest");
		accountsRepo.save(account);
	}

	@Override
	public void deleteById(int theId) {
		accountsRepo.deleteById(theId);
	}

	@Override
	public void savePersonnel(Account account) {
		
		accountsRepo.save(account);
		
	}


}
