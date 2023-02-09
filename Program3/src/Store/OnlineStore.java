package Store;
import menu.CLIMenu;
import Item.*;
import menu.CLIOption;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Represents a CLI which displays a filtered table of item entries based on the users choice.
 * @author jwest
 */
public class OnlineStore {
//	Inventory loaded from file.
	private static ItemInventory itemInventory;
	
//	Menu initialized with desired options.
	private static final CLIMenu MENU = new CLIMenu(
		new CLIOption[] {
			new CLIOption(1, "Show all items", () -> printTable(itemInventory.getEntries())),
			new CLIOption(2, "Show only music CD", () -> printTable(getFilteredEntries(MusicCD.class))),
			new CLIOption(3, "Show only books", () -> printTable(getFilteredEntries(Book.class))),
			new CLIOption(4, "Show only software", () -> printTable(getFilteredEntries(Software.class))),
			new CLIOption(5, "Exit program", () -> System.out.println("Thanks " + "for visiting!"))
		}
	);

//	Table layout constants
	private static final Map<Class<?>, String> ITEM_CLASS_NAME_MAP = Map.of(
		Book.class, "book",
		MusicCD.class, "music",
		Software.class, "software"
	);
	private static final String[] TABLE_CATEGORIES = {"Title", "Type", "Price", "Quality"};
	private static final String TABLE_CATEGORY_FORMAT = "%-30s %10s %10s %10s";
	private static final String TABLE_ITEM_ENTRY_FORMAT = "%-30s %10s %10s %10s\n";
	private static final String HEADER = String.format(TABLE_CATEGORY_FORMAT, (Object[]) TABLE_CATEGORIES);
	
	
	/**
	 * Initialize new store
	 * @param file file path to load item entry data from.
	 * @throws FileNotFoundException if file not found.
	 */
	public OnlineStore (String file) throws IOException {
		itemInventory = ItemInventory.load(file);
	}
	
	/**
	 * Initiate CLI (Run Menu)
	 */
	public void start () {
		Scanner stdin = new Scanner(System.in);
		String input;
		do {
			System.out.print(MENU.buildMenu("Welcome to eMart", "Your Choice: "));
			MENU.runOption((input = stdin.next()));
		} while (!input.equals("5"));
	}
	
	/**
	 * @param t Item.Item subclass
	 * @return List of item entries from inventory filtered by subclass
	 */
	private static List<ItemEntry> getFilteredEntries (Class<?> t) {
		return itemInventory.getEntries().stream().filter(data -> data.getItem().getClass().isInstance(t)).toList();
	}
	
	/**
	 * Prints a table outlining various Item.Item Entry properties
	 * @param entries Collection of type Item.Item Entry
	 */
	private static void printTable (Collection<ItemEntry> entries) {
		System.out.println();
		final String BAR = "-".repeat(HEADER.length());
		System.out.println(BAR);
		System.out.println(HEADER);
		System.out.println(BAR);
		entries.forEach(OnlineStore::printEntry);
		System.out.println(BAR);
		System.out.println();
	}
	
	/**
	 * Prints formatted table row containing info regarding an item entry
	 * @param entry Item.ItemEntry to print
	 */
	private static void printEntry(ItemEntry entry) {
		Item item = entry.getItem();
		System.out.printf(
			TABLE_ITEM_ENTRY_FORMAT,
			item.getTitle(),
			ITEM_CLASS_NAME_MAP.get(entry.getItem().getClass()),
			item.getPrice(),
			entry.getQuantity()
		);
	}
}
