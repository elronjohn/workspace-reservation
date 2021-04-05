package com.acn.google.workspacereservation.service;



import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acn.google.workspacereservation.entity.Account;
import com.acn.google.workspacereservation.repo.AccountsRepo;

@Service
public class LoginServiceImpl implements LoginService {

	Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	private AccountsRepo accountsRepo;
	
	@Autowired
	public LoginServiceImpl(AccountsRepo theAccountsRepo) {
		accountsRepo = theAccountsRepo;
	}



	
	
	@Override
	public boolean findByUsername(String username) {
		return accountsRepo.existsByUsername(username);
	}





	@Override
	public Account getByUsername(String username) {
		Optional<Account> result = accountsRepo.findByUsername(username);
		
		Account account = null;
		
		if(result.isPresent()) {
				account = result.get();
		}
		return account;
	}
	
	

}
