package menu;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents a CLI Menu
 */
public class CLIMenu {

    protected final CLIOption[] OPTIONS;
    protected final Map<String, CLIOption> CHOICE_OPTIONS_MAP;
    protected CLIOption DefaultOption =
            new CLIOption(0, "Default", () -> System.out.println("Invalid choice. Please try again."));

    /**
     * @param OPTIONS Array containing the options the menu will display
     */
    public CLIMenu(CLIOption[] OPTIONS) {
        this.OPTIONS = OPTIONS;
        CHOICE_OPTIONS_MAP =
                IntStream.range(0, OPTIONS.length)
                        .boxed()
                        .collect(Collectors.toMap(i -> String.valueOf(OPTIONS[i].getN()), i -> OPTIONS[i]));
    }

    /**
     * Run CLI option command based on user choice
     *
     * @param choice String containing user's choice. If choice is not valid, DefaultOption is executed.
     */
    public void runOption(String choice) {
        CHOICE_OPTIONS_MAP
                .getOrDefault(choice, DefaultOption)
                .getPROCEDURE()
                .execute();
    }

    /**
     * Build a printable, formatted CLI menu from this menu
     *
     * @param welcome Welcome message added as line before menu options
     * @param prompt  Prompt added as line after options
     * @return String containing a formatted CLI menu
     */
    public String buildMenu(String welcome, String prompt) {
        StringBuilder sb = new StringBuilder(welcome).append("\n");
        Arrays.stream(OPTIONS).forEach(option -> sb.append(String.format("\t%s\n", option)));
        return sb.append(prompt).toString();
    }

    public void runMenu(String welcome, String prompt, int exitOptionNumber) {
        Scanner stdin = new Scanner(System.in);
        String input;
        do {
            System.out.print(buildMenu(welcome, prompt));
            runOption((input = stdin.next()));
        } while (!input.equals(String.valueOf(exitOptionNumber)));
    }

    /**
     * @param defaultOption new Menu.CLIMenu.CLIOption to be executed upon invalid input
     */
    public void setDefaultOption(CLIOption defaultOption) {
        DefaultOption = defaultOption;
    }

}
