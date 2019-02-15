package bank.jdbc_bank;

public class UserAccount {
	
	String accountName;
	double balance;
	
	public UserAccount(String ac, double initBalance) {
		accountName = ac;
		balance = initBalance;
	}
	
	public String getAccountName() {
		return accountName;
	}
	
	public double getBalance() {
		return balance;
	};
	
	public void deposit(double amount) {
		balance += amount;
	}
	
	public void withdraw(double amount) {
		balance -= amount;
	}
	
	public void displayAccount() {
		System.out.println( "Name: " + accountName + " Amount: $" + balance + "\n");
	}
	

}
