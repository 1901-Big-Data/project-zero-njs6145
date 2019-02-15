package bank.jdbc_bank;

import java.util.HashMap;
import java.util.Map;

public class User {

	private String username, password, firstname, lastname;
	private Map<String, UserAccount> accounts = new HashMap<String,UserAccount>();
	
	public User(String un, String pw, String fn, String ln) {
		username = un;
		password = pw;
		firstname = fn;
		lastname = ln;
		
		accounts.put("money", new UserAccount("money", 120.50));
		
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getName() {
		return firstname + " " + lastname;
	}
	
	public UserAccount getAccount(String name) {
		return accounts.get(name);
	}
	
	public Map<String, UserAccount> getAllAccounts(){
		return accounts;
	}
	
}
