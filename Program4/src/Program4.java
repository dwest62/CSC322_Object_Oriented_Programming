/**
 * Author: James West
 * Date: 01.30.2023
 * Class: Spring 100 CSC322 Intro Programming with Java Assignment: Program4
 * Description: IBag. IBag represents a generic container. The container may be of two subtypes: sorted or unsorted.
 *      Container Items can be added, removed, and searched for. The sorted version uses binary search to assist in
 *      adding and sorting items. Program 4 tests the container bag classes through a CLI.
 *
 * I certify, that this computer program submitted by me is all of my own work. Signed: James West
 **/

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.IntStream;


/**
 * Program 4 provides a CLI intended to test IBag and its subclasses.
*/
public class Program4
{
	private static IBag<String> bag;
	static final Scanner STDIN = new Scanner(System.in);
	
	public static void main(String[] args) { run();	}
	
	public static void run() {
// 		Get sanitized bag type (sorted/unsorted) from user and initialize variable "bag" to new bag of that type
		bag = sanitizePrompt(Program4::promptBag);
//		Get number of items to add to bad from user then prompt user for supplied number of items and add them to bag.
		final int ITEM_COUNT = sanitizePrompt(Program4::promptNumberOfItemsToAdd);
		IntStream.range(0, ITEM_COUNT).forEach(Program4::promptAddItemToBag);
//		Prompt user for item. Check if item is in the bag. Display results. Repeat until user enters "done".
		promptCheckItemsInBag();
//		Remove items from bag, printing them to console 1 by 1.
		removeItemsFromBag();
	}
	
/**
  * Prompts user for bag type (sorted/unsorted) and returns new IBag subclass
  * @return IBag subclass of type SortedBag or UnsortedBag
  * @throws InputMismatchException when user inputs anything other than "sorted" or "unsorted" case-insensitive
*/
	private static IBag<String> promptBag() throws InputMismatchException {
		System.out.print("Which type of bag do you want? sorted/unsorted: ");
		String input = STDIN.next();
		if(!input.matches("^([uU][nN])?[sS][oO][rR][tT][eE][dD]$"))
			throw new InputMismatchException("Invalid input: " + input + ". Input must be 'sorted' or 'unsorted'");
		
		return input.matches("^[sS][oO][rR][tT][eE][dD]$")
			       ? new SortedBag<>() : new UnsortedBag<>();
	}
	
/**
  * Prompts user for the number of item they wish to add to the bag
  * @return positive int containing number of items the user wishes to add to the bag
  * @throws NumberFormatException if String input is not parsable to int
  * @throws InputMismatchException if String input is parsable to int, but not a positive number
*/
	private static int promptNumberOfItemsToAdd() throws NumberFormatException, InputMismatchException {
		System.out.print("How many items to put in the bag? ");
		String input = STDIN.next();
		String errMessage = "Invalid input: " + input + ". Input must be parsable to a positive integer.";
		int n;
		try {
			n = Integer.parseInt(input);
		} catch (NumberFormatException ex) {
			throw new NumberFormatException(errMessage);
		}
		if (n < 0) throw new InputMismatchException(errMessage);
		else return n;
	}
	
/**
  * Prompt user for an item of type String to add to bag
  * @param n int used to enumerate prompt
*/
	private static void promptAddItemToBag(int n) {
		System.out.print("Enter item " + (n + 1) + ": ");
		bag.add(STDIN.next());
	}
/**
  * Prompts user for input and checks if any item in bag matches input. Loops until user enters "done".
*/
	private static void promptCheckItemsInBag() {
		System.out.println("\nYou can check if something is in the bag.");
		
		Supplier<String> prompt = () -> {
			System.out.print("Check for (type done to stop): ");
			return STDIN.next();
		};
		
		String input = prompt.get();
		while (!input.matches("^[Dd][Oo][Nn][Ee]$")){
			System.out.println((bag.contains(input) ? "Yes, it's" : "No, it's not") + " in the bag.");
			input = prompt.get();
		}
	}
	
/**
  * Generic method used to sanitize prompts. Will catch any InputMismatch and NumberFormat Exceptions and print
  *      the exception's message, looping until input is valid.
  * @param supplier Lambda function of type Supplier designed to return a value after parsing user input. A good
  *                 candidate should throw an exception on invalid input.
  * @return Sanitized parsed result of type T
  * @param <R> Type returned by supplier. Also the return type of this method.
*/
	private static <R> R sanitizePrompt(Supplier<R> supplier) {
		while(true) {
			try {return supplier.get();}
			catch (InputMismatchException | NumberFormatException ex) {System.out.println(ex.getMessage());}
		}
	}
	
/**
  * Remove all items from the bag printing them to the console.
*/
	private static void removeItemsFromBag() {
		System.out.println("\nLet's remove stuff from the bag.");
		while(!bag.empty()) {
			System.out.println("Removing item: " + bag.remove());
		}
	}
}