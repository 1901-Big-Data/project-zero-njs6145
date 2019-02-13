package com.revature.bank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
	
	//private static final long serialVersionUID
	protected Integer account_id;
	
	private String firstName;
	private String lastName;
	
	protected String username;
	protected String password;
	protected double balance;
	protected boolean activeSession = false;
	private int isMod;
	protected List<String> history = new ArrayList<String>();

	public User(Integer a_id, String name, String fName, String lName, String pWord, int mod) {

		account_id = a_id;
		username = name;
		firstName = fName;
		lastName = lName;
		password = pWord;
		
		isMod = mod;
		
	}
	
	public Integer getUser_id() {
		return account_id;
	}
	
	public void setUser_id(Integer id) {
		account_id = id;
	}
	
	public String getName() {
		return firstName + " " + lastName;
	}
	
	public void setName(String name, int whichName) {
		switch(whichName){
		case 1: 
			firstName = name;
			break;
		case 2:
			lastName = name;
			break;
		default:
			break;
		}
	}
	
}
