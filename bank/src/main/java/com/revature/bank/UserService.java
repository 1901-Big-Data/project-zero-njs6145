package com.revature.bank;

import java.util.List;
import java.util.Optional;

public class UserService{

	private static UserService userService;
	final static UserServiceDAO userDao = UserOracle.getDao();
	
	public static UserService getService() {
		if(userService == null) {
			userService  = new UserService();
		}
		return userService;
	}


	public Optional<Boolean> login(String username, String password, int mod) throws Exception {
		// TODO Auto-generated method stub
		return userDao.login(username, password, mod);
	}
	public Optional<UserAccount> deposit(int id, double amount, String username) {
		// TODO Auto-generated method stub
		return userDao.deposit(id, amount, username);
	}


	public Optional<UserAccount> withdraw(int id, double amount, String username) {
		// TODO Auto-generated method stub
		return userDao.withdraw(id, amount, username);
	}

	public Optional<UserAccount> deleteAccount(int id, String username) {
		// TODO Auto-generated method stub
		return userDao.deleteAccount(id, username);
	}


	public Optional<UserAccount> createNewAccount(String name, String username) {
		// TODO Auto-generated method stub
		return userDao.createNewAccount(name, username);
	}

	public Optional<List<UserAccount>> getUserAccounts(String username) {
		// TODO Auto-generated method stub
		return userDao.getUserAccounts(username);
	}


	public Optional<User> createUser(String userName, String firstName, String lastName, String password, int isSuper) {
		// TODO Auto-generated method stub
		return userDao.createUser(userName, firstName, lastName, password, isSuper);
	}


	public Optional<User> updateUser(int id, String firstName, String lastName, int isSuper) {
		// TODO Auto-generated method stub
		return userDao.updateUser(id, firstName, lastName, isSuper);
	}

	public Optional<User> deleteUser(int id) {
		// TODO Auto-generated method stub
		return userDao.deleteUser(id);
	}


	public Optional<List<User>> getUsers() {
		// TODO Auto-generated method stub
		return userDao.getUsers();
	}
	
	
	
}
