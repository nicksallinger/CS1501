import java.util.*;
import java.lang.System;
import java.util.Scanner;
import java.util.Random;
import java.util.Scanner;
import java.text.Format;
import java.text.NumberFormat;
import java.text.DecimalFormat;

public class AptTracker {
	static Scanner sc = new Scanner(System.in);
	static AptPQ apts = new AptPQ();

	public static void main(String args[]) {
		System.out.println("WELCOME TO THE APARTMENT TRACKER");
		mainMenu();
	}
	/*
	 * Main menu to handle user input
	 */
	public static void mainMenu(){
		int choice;
		System.out.println("----------Main Menu----------");
		System.out.println("Please make a choice:");
		System.out.println("1: Add a new apartment");
		System.out.println("2: Update an existing apartment");
		System.out.println("3: Remove an apartment");
		System.out.println("4: Display lowest price apartment");
		System.out.println("5: Display highest square footage apartment");
		System.out.println("6: Display lowest price apartment by city");
		System.out.println("7: Display highest square footage apartment by city");
		System.out.println("0: Exit");
		try{
		choice = Integer.parseInt(sc.nextLine());
		}
		catch(Exception e){
			choice = -1;
		}
		if (choice > 7 || choice < 0){
			System.out.println("Not a valid choice");
			System.out.println("Please try again");
			choice = Integer.parseInt(sc.nextLine());
		}
		switch(choice){
		case(0):System.exit(0);
		case(1):addApt(); break;
		case(2):updateApt(); break;
		case(3):removeApt(); break;
		case(4):lowestPrice(); break;
		case(5):highestFootage(); break;
		case(6):lowestPriceByCity(); break;
		case(7):highestFootageByCity(); break;
		
		}
	}		
	
	/*
	 * Displays the highest square footage apartment by city specified by the user
	 */
	private static void highestFootageByCity() {
		
		String city = null;
		System.out.println("------------Highest footage by City----------");
		System.out.println("Enter city");
		try{
			city = sc.nextLine();
		}
		catch(Exception e){
			System.out.println("Invalid input");
			mainMenu();
		}
		apts.cityHighestFootage(city);
		mainMenu();
		
		
	}
	
	/*
	 * Displays the lowest priced apartment by city specified by the user
	 */
	private static void lowestPriceByCity() {
			System.out.println("------------lowest price by City----------");
			System.out.println("Enter city:");
			try{
			String city = sc.nextLine();
			apts.cityLowestPrice(city);
			}
			catch(Exception e){
				System.out.println("Invalid input");
			}
			
			mainMenu();
		
	}
		
	/*
	 * Displays the apartment with the greatest square footage of all 
	 */
	private static void highestFootage() {
			System.out.println("Highest Footage:");
			apts.getHighestFootage();	
			mainMenu();
	}
	/*
	 * Displays the apartment with the lowest price of all
	 */
	private static void lowestPrice() {
			System.out.println("Lowest price:");
			apts.getLowestPrice();
			mainMenu();
	}
		
	/*
	 * Handles user input of an apartment to remove
	 */
	private static void removeApt() {
			System.out.println("----------Removing an apartment----------");
			try{
			System.out.println("address");
			String address = sc.nextLine();
			System.out.println("number");
			String number = sc.nextLine();
			System.out.println("zip");
			String zip = sc.nextLine();
			apts.removeApt(address,number,zip);
			}
			catch(Exception e){
				System.out.println("Invalid input");
			}
			mainMenu();
		
	}
	
	/*
	 * Handles user input in updating an apartment they specify
	 */
	private static void updateApt() {
			
			try{
				System.out.println("----------Updating an apartment----------");
			System.out.println("address to update: ");
			String address = sc.nextLine();
			System.out.println("number to update: ");
			String number = sc.nextLine();
			System.out.println("zip: ");
			String zip = sc.nextLine();
			Apt toUpdate = apts.get(address, number, zip);
			if(toUpdate == null){
				System.out.println("Apartment does not exist");
				mainMenu();
			}
			else{
				System.out.println(toUpdate);
				System.out.println("Would you like to update the price?");
				String choice = sc.nextLine();
				if(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y")){
					System.out.println("new price of apartment: ");
				
				
					int newPrice = Integer.parseInt(sc.nextLine());
					apts.updatePrice(address,number,zip,newPrice);
					toUpdate = apts.get(address, number, zip);
					System.out.println("Apartment update: ");
					System.out.println(toUpdate);
					mainMenu();
				
				
				}
			else mainMenu();
			}
			}
			catch(Exception e){
				System.out.println("Invalid input");
			}
	}
			
			
		
		
	/*
	 * Handles user input for an apartment to be added to PQ
	 */
	private static void addApt() {
		
		System.out.println("-----------Adding apartment----------");
		try{
			System.out.println("Address to add:");
			String address = sc.nextLine();
			System.out.println("number to add:");
			String number = sc.nextLine();
			System.out.println("city to add:");
			String city = sc.nextLine();
			System.out.println("zip to add:");
			String zip = sc.nextLine();
			System.out.println("price to add:");
			double price = Double.parseDouble(sc.nextLine());
			System.out.println("footage to add:");
			int footage = Integer.parseInt(sc.nextLine());
			Apt newApt = new Apt(address, number, city, zip, price, footage);
			apts.insert(newApt);
			System.out.println(newApt);
		}
		catch(Exception e){
			System.out.println("Invalid Input");
		}
			
		mainMenu();
	}
}
