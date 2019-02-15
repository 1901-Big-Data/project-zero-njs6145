package bank.jdbc_bank;

import java.util.Scanner;
import java.util.*;

/**
 * Hello world!
 *
 */
public class Main
{
	static Map<String, User> users = new HashMap<String, User>();
	
	
    public static void main( String[] args )
    {
       startMenu();
       System.out.println("*Session has ended. Thank you for banking with us.*");
    }
    

	static int getNumInput() {
		
		Scanner sc = new Scanner(System.in);
		int input = sc.nextInt();
		
		return input;
		
	}
	
	static double getDoubleInput() {
		Scanner sc = new Scanner(System.in);
		double input = sc.nextDouble();
		
		return input;
	}
	
	static String getStringInput() {
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		
		return input;
	}
	
	static void startMenu() {
		System.out.println("Welcome. Log into an existing account, or create one if you do not have one.");
		System.out.println("1) Login");
		System.out.println("2) Sign Up");
		System.out.println("3) Exit");
	
		int input = getNumInput();
		
		switch(input) {
		
		case 1:
			loginMenu();
			break;
		case 2: 
			signupMenu();
			break;
		case 3:
			break;
		default:
			System.out.println("Not a valid input. Pick a number between 1-3");
		}		
	}
	
	static void loginMenu() {
		System.out.println("Enter your credentials (Enter * to head back to the main menu)");
		System.out.println("Username:");
		String username = getStringInput();
		if(username.equals("*")) {
			System.out.println("Returning to main menu.");
			System.out.println("--------------------------");
			
			startMenu();
			}
		System.out.println("Password:");
		String password = getStringInput();
		if(username.equals("*")) {
			System.out.println("Returning to main menu.");
			System.out.println("--------------------------");
			startMenu();
			}
		
		if(users.containsKey(username) && users.get(username).getPassword().equals(password)) {
		serviceMenu(username);
		}
		else {
			System.out.println("Your username and/or password is incorrect. Please try again.");
			loginMenu();
		}
		
	}
	
	static void signupMenu() {
		System.out.println("Create a username and password. (Enter * to head back to the main menu)");
		System.out.println("Username:");
		String username = getStringInput();
		if(username.equals("*")) {
			System.out.println("Returning to main menu.");
			System.out.println("--------------------------");
			
			startMenu();
			}
		System.out.println("Password:");
		String password = getStringInput();
		if(username.equals("*")) {
			System.out.println("Returning to main menu.");
			System.out.println("--------------------------");
			startMenu();
			}
		
		System.out.println("Enter your first and last name. (Enter * to head back to the main menu) ");
		System.out.println("First Name:");
		String firstName = getStringInput();
		if(username.equals("*")) {
			System.out.println("Returning to main menu.");
			System.out.println("--------------------------");
			
			startMenu();
			}
		System.out.println("Last Name:");
		String lastName = getStringInput();
		if(username.equals("*")) {
			System.out.println("Returning to main menu.");
			System.out.println("--------------------------");
			startMenu();
			}
	
		users.put(username, new User(username,password,firstName,lastName));
		startMenu();
	}
	
	static void serviceMenu(String username) {
		
		System.out.println("Welcome back. Please select an option by typing a number.");
		System.out.println("1) Deposit");
		System.out.println("2) Withdraw");
		System.out.println("3) Check accounts");
		
		System.out.println("ACCOUNT OPTIONS BELOW");
		System.out.println("4) Create Account");
		System.out.println("5) Close Account");
		System.out.println("6) Log out");
		
		int input = getNumInput();
		int exitNum = 6;
		
		while (input != exitNum) {
			switch(input) {
			
			case 1:
				depositMenu(users.get(username));
				break;
			case 2: 
				withdrawMenu(users.get(username));
				break;
			
			case 3: 
				displayAccounts(users.get(username));
				break;
			case 4:
				createAccountMenu(users.get(username));
				break;
			case 5:
				closeAccountMenu(users.get(username));
				break;
			case 6:
				startMenu();
				break;
			default:
				System.out.println("Not a valid option. Please pick among the displayed options");
				serviceMenu(username);
			break;
			}
		}
		
	}
	
	static void depositMenu(User user) {
	
		try {
			System.out.println("Type the name of the account you would like to deposit into. (Type * to head to previous menu.)");
			String accountName = getStringInput();
			
			if(accountName.equals("*")) {
				System.out.println("Returning to service menu.");
				System.out.println("--------------------------");
				
				serviceMenu(user.getName());
				}
			
			UserAccount account =  user.getAccount(accountName);
			System.out.println("Enter an amount in the format of xx.xx (Type 0.0 to go back to the previous prompt.)");
			double amount = getDoubleInput();
			if(amount == 0.0) {
			depositMenu(user);
			}
			account.deposit(amount);
			
		}
		catch(Exception e) {
			System.out.println("Account does not exist. " + e);
			depositMenu(user);
		}
			
	}
	
	static void withdrawMenu(User user) {
		try {
			System.out.println("Type the name of the account you would like to withdraw from. (Type * to head to previous menu.)");
			String accountName = getStringInput();
			if(accountName.equals("*")) {
				System.out.println("Returning to service menu.");
				System.out.println("--------------------------");
				
				serviceMenu(user.getName());
				}
			
			UserAccount account =  user.getAccount(accountName);
			System.out.println("Enter an amount to withdraw in the format of xx.xx (Type 0.0 to go back to the previous prompt.)");
			double amount = getDoubleInput();
			
				if(amount == 0.0) {
				withdrawMenu(user);
				}

			while(true) {
			if(account.getBalance() > amount) {
				account.withdraw(amount);
				break;
			}
			else {
				System.out.println("Overdraft warning. Please enter a smaller amount.");
				System.out.println("Enter an amount to withdraw in the format of xx.xx (Type 0.0 to go back to the previous prompt.)");
				amount = getDoubleInput();
				if(amount == 0.0) {
				withdrawMenu(user);
				}

				}
			}
			
		}
		catch(Exception e) {
			System.out.println("Account does not exist.");
			withdrawMenu(user);
		}
		
	}
	
	private static void displayAccounts(User user) {
		// TODO Auto-generated method stub
		
		try {
		if(!user.getAllAccounts().isEmpty()) {
		System.out.println("Here are the accounts currently open.");
		
		for(String u: user.getAllAccounts().keySet()) {
			UserAccount account = user.getAccount(u);
			account.displayAccount();	
			}
		
		}
		
		else {
			System.out.println("You do not currently have any open accounts.");
			}
		serviceMenu(user.getName());
		}
		catch(Exception e) {
			System.out.println("You do not currently have any open accounts.");
		}
		
	}
		
	private static void createAccountMenu(User user) {
		
		System.out.println("Enter the name of the account you would like to create");
		String accountName = getStringInput();
		System.out.println("Enter the inital balance");
		double initAmount = getDoubleInput();
		user.getAllAccounts().put(accountName, new UserAccount(accountName,initAmount));
		serviceMenu(user.getName());
		
	}
	
	private static void closeAccountMenu(User user) {
		System.out.println("Enter the name of the account you would like to close. This is case-sensitive.");
		
	}








	
    
}
