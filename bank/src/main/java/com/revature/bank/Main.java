package com.revature.bank;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Scanner;


public class Main extends Menu {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
		Connection connect = ConnectionUtil.getConnection();
		if (connect == null) {
			System.out.println("Connection is null.");
		}
		else {
			System.out.println("Connection established: " + connect.toString());
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		finally {
			createMenu();
			System.out.println("*Session Ended*");
		}
		
		
	}

}
