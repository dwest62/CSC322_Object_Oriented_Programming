/**
 * Author: James West Date: 01.27.2023 Class: Spring 100 CSC322 Intro Programming with Java Assignment: Program3
 * Description: Online Store. Represents a CLI which filters and displays various types of "Item"s for an Online Store.
 *
 * I certify, that this computer program submitted by me is all of my own work. Signed: James West
 **/

import java.io.IOException;

// Main class initializes OnlineStore and checks for errors
public class Main {
	public static void main (String[] args) {
//		Check if file path provided
		if(args.length != 1) {
			System.out.println("Invalid number of arguments (" + args.length + "). Example usage: java Main " +
				"[filepath]");
			System.exit(0);
		} else {
//			Try to load store with file path
			try {
				new OnlineStore(args[0]).start();
//			Catch IOException error and display useful message to user.
			} catch (IOException err) {
				System.out.println("Error loading file: " + args[0]);
				System.out.println(err.getMessage());
			}
		}
	}
}