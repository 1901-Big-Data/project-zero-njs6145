package com.revature.bank;

import java.util.ArrayList;
import java.util.List;

public class RegisteredUser{

	
	private String username;
	private String password;
	private double balance;
	private boolean activeSession = false;
	private List<String> history = new ArrayList<String>();
	
	public RegisteredUser(String name, String pWord, int initFunds) {
		username = name;
		password = pWord;
		balance = initFunds;
		
	}
	
	public void deposit(double amount) {
		
		//TODO Handle an invalid format
		
		balance += amount;
		history.add("$" + amount + "has been deposited into the account.");
		
	}
	
	public void withdraw(double amount) {
		
		//TODO Handle an invalid format
		if(amount < balance) {
		balance -= amount;
		history.add("$" + amount + "has been withdrawn from the account.");
		}
		else {
			System.out.print("You cannot withdraw the desired amount due to insufficient funds");
		}
	}
	
	public void setActiveSession(boolean status) {
		activeSession = status;
	}
	
}
