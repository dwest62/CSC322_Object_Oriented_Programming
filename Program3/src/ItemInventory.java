import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ItemInventory
{
    private final ArrayList<ItemEntry> entries;

    private ItemInventory(ArrayList<ItemEntry> entries)
    {
        this.entries = entries;
    }

    public static ItemInventory load(String file) throws FileNotFoundException
    {
        Scanner fileIn = new Scanner(new File(file));
        ArrayList<ItemEntry> entries = new ArrayList<>();

        while (fileIn.hasNextLine())
        {
            String[] itemData = fileIn.nextLine().split("\\|");
            String quantity = itemData[itemData.length - 1];

            entries.add(new ItemEntry(ItemFromFileFactory.build(itemData), Integer.parseInt(quantity)));
        }
        return new ItemInventory(entries);
    }

    public ArrayList<ItemEntry> getEntries()
    {
        return entries;
    }
}
