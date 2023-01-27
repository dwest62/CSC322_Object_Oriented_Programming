import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OnlineStore {
    private static ItemInventory INVENTORY;

    public OnlineStore(String file) throws FileNotFoundException {
        INVENTORY = ItemInventory.load(file);
    }

    public void start() {
        Scanner stdin = new Scanner(System.in);
        Menu.runMenu(stdin);
    }

    private static class Menu {
        private interface Command {boolean execute();}

        public record MenuOption(String DESCRIPTION, Command COMMAND) {
        }
        
        private static final Map<String, MenuOption> OPTIONS = Stream.of
            (new Object[][]{
                {"1", new MenuOption("Show all " + "items",
                    () -> printEntries(INVENTORY.getEntries().toArray(ItemEntry[]::new)))},
                {"2", new MenuOption("Show only music CD", () -> printEntries(filterEntries(MusicCD.class)))},
                {"3", new MenuOption("Show only books", () -> printEntries(filterEntries(Book.class)))},
                {"4", new MenuOption("Show only software", () -> printEntries(filterEntries(Software.class)))},
                {"5", new MenuOption("Exit Program", () -> {
                    System.out.println("Thanks for shopping!");
                    return false;
                })},
            }
            ).collect(Collectors.toMap(data -> (String) data[0], data -> (MenuOption) data[1]));
    
        private static final MenuOption DEFAULT_OPTION = new MenuOption(
            "Default Option", () ->
        {
            System.out.println("Invalid menu selection. Please try again.");
            return true;
        });

        private static ItemEntry[] filterEntries(Class<?> t) {
            System.out.println(t);
            return INVENTORY.getEntries()
                    .stream()
                    .filter(data -> data.getItem().getClass() == t)
                    .toArray(ItemEntry[]::new);
        }

        private static boolean printEntries(ItemEntry[] entries) {
            printHeader();
            for (ItemEntry entry : entries) {
                printItemEntry(entry);
            }
            printBar();
            System.out.println();
            return true;
        }

        private static void printItemEntry(ItemEntry entry) {
            Item item = entry.getItem();
            System.out.printf("%-30s %10s %10.2f %10s\n", item.getTitle(),
                ItemFromFileFactory.CLASSNAME_TYPENAME_MAP.get(item.getClass().getName()), item.getPrice(),
                entry.getQuantity());
        }

        private static void printHeader() {
            printBar();
            System.out.printf("%-30s %10s %10s %10s\n", "Title", "Type", "Price", "Quantity");
            printBar();
        }

        private static void printBar() {
            System.out.println("-".repeat(63));
        }
        
        private static void printMenu() {
            for(Map.Entry<String, MenuOption> entry: OPTIONS.entrySet())
            {
                System.out.println("\t" + entry.getKey() + ") " + entry.getValue().DESCRIPTION);
            }
        }
        
        private static void runMenu(Scanner stdin) {
            String input;
            do
            {
                System.out.println("Welcome to eMart");
                Menu.printMenu();
                System.out.print("Your Choice: ");
                input = stdin.next();
                System.out.println();
            } while (Menu.OPTIONS.getOrDefault(input, Menu.DEFAULT_OPTION).COMMAND.execute());
        }

    }
}
