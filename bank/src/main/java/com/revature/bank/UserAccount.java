package com.revature.bank;

import java.io.Serializable;

public class UserAccount implements Serializable {

	private Integer account_id;
	private String name;
	private double balance;
	
	public UserAccount() {
		super();
	}
	
	public UserAccount(Integer id, String aName, double initBalance) {
		super();
		account_id = id;
		name = aName;
		balance = initBalance;
	}
	
	public Integer getAccount_id() {
		return account_id;
	}
	//TODO: Add methods later
}

