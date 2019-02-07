package com.revature.bank;

import java.io.InputStream;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		while(true) { 
		System.out.println("Welcome. Please input a number to confirm an option. If not registered, you will be asked to create an account.");
		System.out.println("1) Login");
		System.out.println("2) Sign Up");
		Scanner sc = new Scanner(System.in);
		
		try {
			String login, password;
			
			switch(sc.nextInt()) {
			
			case 1: 
				System.out.println("Please Enter your credentials.");
				sc = new Scanner(System.in);
				
			case 2:
				System.out.println("Please enter the username you would like to use.");
				login = sc.nextLine();
				System.out.println("Please enter a password");
				password = sc.nextLine();
				System.out.println("Confirm your password by typing it again");
				/*
				while(true) {
					if()
				}
				*/
			
			}
		}
		catch(Exception e) {
			
		}
	}
		
	}

}
