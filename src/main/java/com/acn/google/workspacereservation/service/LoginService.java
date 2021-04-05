package com.acn.google.workspacereservation.service;

import com.acn.google.workspacereservation.entity.Account;

public interface LoginService {


	boolean findByUsername(String username);

	Account getByUsername(String username);




}

