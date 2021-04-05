package com.acn.google.workspacereservation.service;

import java.util.List;

import com.acn.google.workspacereservation.entity.Account;

public interface AccountService {

	public List<Account> findAll();
	
	public Account findById(int theId);
	
	public void save(Account account);
	
	public void savePersonnel(Account account);
	
	public void deleteById(int theId);

}
