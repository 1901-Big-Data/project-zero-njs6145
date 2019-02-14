package com.revature.bank;

import java.util.List;
import java.util.Optional;

public interface UserServiceDAO {
	
	//User refers to the user themselves. UserAccount refers to a User's ACCOUNT
	Optional<Boolean> login(String username, String password, int mod) throws Exception;
	Optional<UserAccount> deposit(int id, double amount, String username);
	Optional<UserAccount> withdraw(int id, double amount, String username); 
	Optional<UserAccount> deleteAccount(int id, String username);
	Optional<UserAccount> createNewAccount(String name, String username);
	Optional<List<UserAccount>> getUserAccounts(String username);
	Optional<User> createUser(String userName, String firstName, String lastName, String password, int isSuper);
	
	//A superuser can view, update, and delete all users.
	Optional<User> updateUser(int id, String firstName, String lastName, int isSuper);
	Optional<User> deleteUser(int id);
	Optional<List<User>> getUsers();
	
	
}
