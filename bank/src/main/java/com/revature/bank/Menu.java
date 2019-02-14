package com.revature.bank;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

public class Menu {

	//Below are three helper methods to be called whenever the user needs to be asked for input
	//This is meant to significantly reduce code clutter
	
	//Inputs
	@SuppressWarnings("resource")
	static int getInput() {
		boolean check = false;
		int i = 0;
		while (check != true) {
			try {
				check = true;
				Scanner user = new Scanner(System.in);
				i = user.nextInt();	
			} catch(Exception e) {
				System.out.println("Please input a number between 0-9.");
				check = false;

			}
		}
	return i;
	}
	
	@SuppressWarnings("resource")
	static String getStringInput() {
		String s = new String();
		Scanner string = new Scanner(System.in);
		s = string.nextLine();
		return s;
	}
	
	@SuppressWarnings("resource")
	static double getBalance() {
		boolean check = false;
		double i = 0;
		while (check != true) {
			try {
				check = true;
				Scanner user = new Scanner(System.in);
				i = user.nextDouble();	
			} catch(Exception e) {
				System.out.println("Please input a value in the format of xx.xx");
				check = false;
			}
		}
	return i;
	}
	
	private static Scanner sc = new Scanner(System.in);
	Connection con;
	
	
	static void createMenu() {
		boolean loopState = true;
		User currentUser;
		int input = 0;
		
		//If the input number isn't the value that is meant to exit the loop...
		
		while(input != 3) { 			
		System.out.println("Welcome. Please input a number to confirm an option. If not registered, you will need to create an account.");
		System.out.println("1) Login");
		System.out.println("2) Sign Up");
		System.out.println("3) Exit");
		input = getInput();
		switch(input) {
		case 1: 
			loginMenu();
			break;
		case 2:
			createUserMenu();
			break;
		case 3:
			break;
		default:
			System.out.print("Please select a valid option");
			break;

		}
		
		
		}
	}
	
	static void loginMenu() {
		
		System.out.println("Which kind of user are you?");
		System.out.println("1) General User");
		System.out.println("2) Moderator");
		System.out.println("0) Return");
		int input = getInput();
		
		//Similar deal with the createMenu(), as well as all future instances
		while(input != 0) {
		switch(input) {
		
		case 1:
		System.out.println("Enter your credentials.");
		
		System.out.println("Username:");
		String userLogin = getStringInput();
		System.out.println("Password:");
		String userPassword = getStringInput();
		
		try {
			//parse for login to the DAO
			Optional<Boolean> login = UserService.getService().login(userLogin, userPassword, 0);
			if(login.get()) {
				servicesMenu(userLogin);
			}
		}
		catch (Exception e) {
			System.out.println("Login failed");
		}
		break;
		
		
		//Moderator login Same deal, but now checks for id code for moderator
		//The reason a boolean isn't used is because normal SQL doesn't support them
		
		case 2:
			System.out.println("Enter your credentials.");
			
			System.out.println("Username:");
			userLogin = getStringInput();
			System.out.println("Password:");
			userPassword = getStringInput();
			
			try {
				Optional<Boolean> login = UserService.getService().login(userLogin, userPassword, 1);
				if(login.get()) {
					servicesMenu(userLogin);
				}
			}
			catch (Exception e) {
				System.out.println("Login failed");
			}
			
			break;
			default:
				break;
		}
		}
	}
	
	static void createUserMenu() {
		Scanner sc = new Scanner(System.in);
		
		
			System.out.println("Enter the username you would like to use.");
			String username = getStringInput();
			System.out.println("Enter a password");
			String password = getStringInput();
			System.out.println("Enter first name: ");
			String firstname = getStringInput();
			System.out.println("Enter last name: ");
			String lastname = getStringInput();
			
			try {
				UserService.getService().createUser(username, password, firstname, lastname, 0);
			}  catch (NoSuchElementException e) {
				System.out.println("Error.");
			}
			
			/*
			System.out.println("Confirm your password by typing it again");
			
			if(sc.nextLine().equals(password)) {
				//create new entry in table
				
				System.out.println("Successfully created an account");
				createMenu();
			}
			else {
				System.out.println("Your password did not match.");
				}
				
			}
			*/
	}
	
	static void servicesMenu(String user) {

		System.out.println("Welcome back, " + user);
		
		int input = 0;
		
		while(input != 8) {
			
		
			System.out.println("Welcome back. Please select an option by typing a number.");
			System.out.println("1) Deposit");
			System.out.println("2) Withdraw");
			System.out.println("3) Check accounts");
			System.out.println("ACCOUNT OPTIONS BELOW");
			System.out.println("4) Change User Information");
			//System.out.println("5) Change Password");
			System.out.println("6) Close Account");
			System.out.println("7) Create Account");
			System.out.println("8) Log Out");
		
				input = 9;
			switch(input) {
			
			case 1: 
				depositMenu(user);
				break;
				
			
			case 2: 
				withdrawMenu(user);

				break;
			case 3:
				UserService service = UserService.getService();
				List<UserAccount> userAccounts = new ArrayList<>();
				try {
					if(userAccounts.isEmpty()) {
						System.out.println("You currently have no accounts option.");
					}else {
						//parse for all accounts that the user has. For loops of this structure further down are for the same purpose
						
						for(int i = 0; i < userAccounts.size(); i++) {
							System.out.println(userAccounts.get(i));
						}
					}
				}catch(NoSuchElementException e) {
					System.out.println("Could not fetch accounts.");
				}
				break;
				
			case 4:
				changeUserInfo(1);
				break;
				
			case 5: changeUserInfo(2);
				break;
			case 6: 
				deleteAccount(user);
				break;
			case 7:
				createAccount(user);
			default:
				System.out.println("Invalid option. Choose from displayed options.");
					break;
			}
			
			}
			
		
		}
		
	
	
	static void createAccount(String username) {
		System.out.println("Enter the name of account you will create.");
		String name = getStringInput();
		
		try {
			//Same deal; parse the DAO
			UserService.getService().createNewAccount(name, username);
		}catch(NoSuchElementException e) {
			System.out.println("Error.");
			
		}
		
	}
	
	static void deleteAccount(String username) {
		UserService userService = UserService.getService();
		List<UserAccount> userAccounts = new ArrayList<>();
		try {
			if(userAccounts.isEmpty()) {
				System.out.println("You currently have no accounts option.");
			}else {
				for(int i = 0; i < userAccounts.size(); i++) {
					System.out.println(userAccounts.get(i));
				}
			}
			
			System.out.println("Input the id number of the account you'd like to close");
			System.out.println("Please note that you cannot close an account with funds still in it.");
			int id = getInput();
			
			//Check for a case sensitive Confirm for extra safety
			System.out.println("Warning: Account id #" + id + " will be closed. Type 'Confirm' (case-sensitive) to confirm its deletion.");
			String confirm = getStringInput();

			if (confirm.equals("Confirm")) {
				UserService.getService().deleteAccount(id, username);
			} else {
				System.out.println("Closure has been canceled. Returning to menu.");
			}
						
		}catch(NoSuchElementException e) {
			System.out.println("Could not get accounts.");
		}
	}

	static void changeUserInfo(int code) {
		
	}
	
	static void depositMenu(String username) {
		UserService userService = UserService.getService();
		List<UserAccount> userAccounts = new ArrayList<>();
		
		try {
			userAccounts = userService.getUserAccounts(username).get();
			if(userAccounts.isEmpty()) {
				System.out.println("You currently have no accounts option.");
			}else {
				for(int i = 0; i < userAccounts.size(); i++) {
					System.out.println(userAccounts.get(i));
				}
				
				System.out.println("Type the id# of the account you wish to withdraw from: ");
				int id = getInput();
				System.out.println("Enter amount to deposit (format xx.xx): ");
				double amount = getBalance();
				UserService.getService().deposit(id, amount, username);
				
			}
		}catch(NoSuchElementException e) {
			System.out.println("Could not get accounts");
		}
	}
	
	static void withdrawMenu(String username) {
		
		UserService userService = UserService.getService();
		List<UserAccount> userAccounts = new ArrayList<>();
		
		try {
			userAccounts = userService.getUserAccounts(username).get();
			if(userAccounts.isEmpty()) {
				System.out.println("You currently have no accounts option.");
			}else {
				for(int i = 0; i < userAccounts.size(); i++) {
					System.out.println(userAccounts.get(i));
				}
				
				System.out.println("Type the id# of the account you wish to withdraw from: ");
				int id = getInput();
				System.out.println("Enter amount to withdraw (format xx.xx): ");
				double amount = getBalance();
				UserService.getService().withdraw(id, amount, username);
				
			}
		}catch(NoSuchElementException e) {
			System.out.println("Could not get accounts");
		}
		
	}
	

	
	static void manageUsers() {
		
	}
	
	
}
