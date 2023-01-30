import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Program4
{
	private static IBag<String> bag;
	static final Scanner STDIN = new Scanner(System.in);
	
	public static void main(String[] args) {
		run();
	}
	
	public static void run(){
		bag = sanitizePrompt(Program4::promptBag);
		final int ITEM_COUNT = sanitizePrompt(Program4::promptNumberOfItemsToAdd);
		IntStream.range(0, ITEM_COUNT).forEach(Program4::promptAddItemToBag);
		promptCheckItemsInBag();
		removeItemsFromBag();
	}
	
	private static IBag<String> promptBag() throws InputMismatchException
	{
		System.out.print("Which type of bag do you want? sorted/unsorted: ");
		String input = STDIN.next();
		if(!input.matches("^([uU][nN])?[sS][oO][rR][tT][eE][dD]$"))
			throw new InputMismatchException("Invalid input: " + input +". Input must be 'sorted' or 'unsorted'");
		
		return input.matches("^[sS][oO][rR][tT][eE][dD]$")
			       ? new SortedBag<>() : new UnsortedBag<>();
	}
	
	private static int promptNumberOfItemsToAdd() throws NumberFormatException, InputMismatchException
	{
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
	
	private static void promptAddItemToBag(int n) {
		System.out.print("Enter item " + (n + 1) + ": ");
		bag.add(STDIN.next());
	}
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
	private static <T> T sanitizePrompt(Supplier<T> supplier) {
		while(true) {
			try {return supplier.get();}
			catch (InputMismatchException | NumberFormatException ex) {System.out.println(ex.getMessage());}
		}
	}
	
	private static void removeItemsFromBag() {
		System.out.println("\nLet's remove stuff from the bag.");
		while(!bag.empty()) {
			System.out.println("Removing item: " + bag.remove());
		}
	}
}