package com.revature.bank;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserOracle implements UserServiceDAO {

	private static UserOracle userOracle;
	
	public static UserServiceDAO getDao() {
		if(userOracle == null) {
			userOracle = new UserOracle();
		}
		
		return userOracle;
	}

	public Optional<Boolean> login(String username, String password, Integer mod) throws Exception {
Connection connect = ConnectionUtil.getConnection();
		
		if (connect == null) {
			return Optional.empty();
		}
		try {
			String sql = "select * from users where username = ? and pass_word = ? and moderator = ?";
			PreparedStatement ps = connect.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setInt(3, mod);
			ResultSet login = ps.executeQuery();
			List<Integer> foundUser = new ArrayList<Integer>();
			while (login.next()) {
				foundUser.add(new Integer(login.getInt("moderator")));
			}
			if (foundUser.isEmpty()) {
				System.out.println("Incorrect username/password. Returning to main...");
				return Optional.of(false);
			} else {
				return Optional.of(true);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return Optional.of(false);
		}
	}

	public Optional<UserAccount> deposit(Integer id, double amount, String username) {
Connection connect = ConnectionUtil.getConnection();
		
		if (connect == null) {
			return Optional.empty();
		}
		try {
			String sql = "update accounts set balance = ? + (select balance from accounts where account_id = ?) where account_id = ? and account_owner = ?";
			PreparedStatement ps = connect.prepareStatement(sql);
			ps.setDouble(1, amount);
			ps.setInt(2, id);
			ps.setInt(3, id);
			ps.setString(4, username);
			ps.executeQuery();
			String sql2 = "select balance from accounts where account_id = ? and account_owner = ?";
			ps = connect.prepareStatement(sql2);
			ps.setInt(1, id);
			ps.setString(2, username);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Double newBalance = rs.getDouble(1);
			System.out.println("New account balance: $" + newBalance);
			String commit = "commit";
			ps = connect.prepareStatement(commit);
			ps.executeQuery();
			return Optional.empty();
		} catch (Exception e) {
			System.out.println("Access denied. You do not own this account.");
			return Optional.empty();
		}
	}

	public Optional<UserAccount> withdraw(Integer id, double amount, String username) {
Connection connect = ConnectionUtil.getConnection();
		
		if (connect == null) {
			return Optional.empty();
		}
		try {
			String checkFunds = "select balance from accounts where account_id = ? and account_owner = ?";
			PreparedStatement ps = connect.prepareStatement(checkFunds);
			ps.setInt(1, id);
			ps.setString(2, username);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Double checkBalance = rs.getDouble(1);
			if (checkBalance < amount) {
				throw new Exception("Overdraft Error");
			}
			String sql = "update accounts set balance = -? + (select balance from accounts where account_id = ?) where account_id = ? and account_owner = ?";
			ps = connect.prepareStatement(sql);
			ps.setDouble(1, amount);
			ps.setInt(2, id);
			ps.setInt(3, id);
			ps.setString(4, username);
			ps.executeQuery();
			String sql2 = "select balance from accounts where account_id = ? and account_owner = ?";
			ps = connect.prepareStatement(sql2);
			ps.setInt(1, id);
			ps.setString(2, username);
			rs = ps.executeQuery();
			rs.next();
			Double newBalance = rs.getDouble(1);
			System.out.println("New account balance: $" + newBalance);
			String commit = "commit";
			ps = connect.prepareStatement(commit);
			ps.executeQuery();
			return Optional.empty();
		} catch (SQLException e) {
			System.out.println("You do not own the selected account. Access denied.");
			return Optional.empty();
		} catch (Exception e) {
			System.out.println("Overdraft Error: Account has insufficient funds.");
			return Optional.empty();
		}
	}

	public Optional<UserAccount> createNewAccount(String name, String username) {
Connection connect = ConnectionUtil.getConnection();
		
		if (connect == null) {
			return Optional.empty();
		}
		try {
			String sql = "call createNewAccount(?, ?, ?, ?)";
			CallableStatement cs = connect.prepareCall(sql);
			cs.setString(1, name);
			cs.setString(2, username);
			cs.setInt(3, 0);
			cs.registerOutParameter(4, Types.INTEGER);
			cs.execute();
			Integer id = cs.getInt(4);
			UserAccount user = new UserAccount(id, name, 0.0);
			System.out.println("New account " + name + " has been created!");
			return Optional.of(user);
		} catch (Exception e) {
			System.out.println("Could not create new account. Please try again.");
			return Optional.empty();
		}
	}

	public Optional<User> createUser(String userName, String firstName, String lastName, String password,
			int isSuper) {
Connection connect = ConnectionUtil.getConnection();
		
		if (connect == null) {
			return Optional.empty();
		}
		try {
			String sql = "call createNewUser(?, ?, ?, ?, ?, ?)";
			CallableStatement cs = connect.prepareCall(sql);
			cs.setString(1, userName);
			cs.setString(2, password);
			cs.setString(3, firstName);
			cs.setString(4, lastName);
			cs.setInt(5, 0);
			cs.registerOutParameter(6, Types.INTEGER);
			cs.execute();
			Integer id = cs.getInt(6);
			User user = new User(id, userName, "*****", firstName, lastName, 0);
			System.out.println("Welcome " + userName + " to JDBC Online Banking.");
			return Optional.of(user);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Username already registered. If this is what you normally use, try logging in");
			return Optional.empty();
		}
	}

	public Optional<User> updateUser(Integer id, String firstName, String lastName, int isSuper) {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<User> deleteUser(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<List<User>> getUsers() {
Connection connect = ConnectionUtil.getConnection();
		
		if (connect == null) {
			return Optional.empty();
		}
		try {
			String sql = "select * from users where moderator = 0 order by username";
			PreparedStatement ps = connect.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<User> allUsers = new ArrayList<User>();
			while (rs.next()) {
				allUsers.add(new User(rs.getInt("login_id"), rs.getString("username"), 
						"**********", rs.getString("firstname"), rs.getString("lastname"), rs.getInt("moderator")));
			}
			return Optional.of(allUsers);
		} catch (Exception e) {
			System.out.println("Error from within database.");
			return Optional.empty();
		}
	}

	public Optional<List<UserAccount>> getUserAccounts(String username) {
		Connection connect = ConnectionUtil.getConnection();
		
		if (connect == null) {
			return Optional.empty();
		}
		try {
			String sql = "select * from accounts where account_owner = ? order by account_id";
			PreparedStatement ps = connect.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			List<UserAccount> allAccounts = new ArrayList<UserAccount>();
			while (rs.next()) {
				allAccounts.add(new UserAccount(rs.getInt("account_id"), rs.getString("account_name"), 
						rs.getDouble("balance")));
			}
			return Optional.of(allAccounts);
		} catch (Exception e) {
			System.out.println("Error inside  database.");
			return Optional.empty();
		}
	}


	@Override
	public Optional<UserAccount> deleteAccount(Integer id, String username) {
Connection connect = ConnectionUtil.getConnection();
		
		if (connect == null) {
			return Optional.empty();
		}
		try {
			String checkFunds = "select balance from accounts where account_id = ? and account_owner = ?";
			PreparedStatement ps = connect.prepareStatement(checkFunds);
			ps.setInt(1, id);
			ps.setString(2, username);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Double checkBalance = rs.getDouble(1);
			if (checkBalance != 0.0) {
				throw new Exception("Leftover Funds Error");
			}
			String sql = "delete from accounts where account_id = ? and account_owner = ?";
			ps = connect.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, username);
			ps.executeQuery();
			System.out.println("Account " + id + " has been successfully closed.");
			String commit = "commit";
			ps = connect.prepareStatement(commit);
			ps.executeQuery();
			return Optional.empty();
		} catch (SQLException e) {
			System.out.println("You do not own the selected account. Access denied.");
			return Optional.empty();
		} catch (Exception e) {
			System.out.println("Account still has funds available. Please use the withdraw feature to empty account before closure.");
			return Optional.empty();
		}
	}

	

}
