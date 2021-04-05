package com.acn.google.workspacereservation.repo;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acn.google.workspacereservation.entity.Account;

public interface AccountsRepo extends JpaRepository<Account, Integer>{

	Optional<Account> findByUsername(String username);

	boolean existsByUsername(String username);

	


	
}
