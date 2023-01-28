import Item.ItemEntry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents an Item.Item Inventory. Contains ArrayList of entries and methods to build Item.Item Entries from file data.
 */
public class ItemInventory {
	private final ArrayList<ItemEntry> entries;
	
	/**
	 * Item.Item Inventory is initialized through the load factory method
	 * @param entries Inventory entries
	 */
	private ItemInventory (ArrayList<ItemEntry> entries) {
		this.entries = entries;
	}
	
	/**
	 * Load data from file and return an Item.Item Inventory object initialized with entry data.
	 * @param file file path
	 * @return Item.Item Inventory Object initialized with file data
	 */
	public static ItemInventory load (String file) throws IOException {
		ArrayList<ItemEntry> entries = new ArrayList<>();
		final AtomicInteger index = new AtomicInteger();
		Arrays.stream(
			Arrays
				.stream(Files.readString(Path.of(file)).split("\n"))
				.map(e -> e.trim().split("\\|"))
				.toArray()
		).forEach(e -> addEntry((String[]) e, index.getAndIncrement(), entries));

		
		return new ItemInventory(entries);
	}
	
	/**
	 * Add Item.ItemEntry to ArrayList
	 * @param itemData data to be added
	 * @param i index of file line being processed
	 * @param entries ArrayList to update
	 */
	private static void addEntry (String[] itemData, int i, ArrayList<ItemEntry> entries) {
		try {
			entries.add(
				new ItemEntry(ItemFromFileFactory.build(itemData),
				Integer.parseInt(itemData[itemData.length -1]))
			);
		} catch (IllegalArgumentException exception) {
			System.out.println("Warning: Unable to parse file entry on line " + i + ". " + exception.getMessage()
				+ "\n" + Arrays.toString(itemData) + "\n");
		}
	}
	
	/**
	 * @return ArrayList containing Item.Item Entries in inventory
	 */
	public ArrayList<ItemEntry> getEntries () {
		return entries;
	}
}
