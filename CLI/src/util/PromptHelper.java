package util;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class PromptHelper {
    public static final Scanner INPUT = new Scanner(System.in);

    /**
     * Generic method used to sanitize prompts. Will catch any InputMismatch and NumberFormat Exceptions and print the
     * exception's message, looping until input is valid.
     *
     * @param supplier Lambda function of type Supplier designed to return a value after parsing user input. A good
     *                 candidate should throw an exception on invalid input.
     * @param <R>      Type returned by supplier. Also, the return type of this method.
     * @return Sanitized parsed result of type T
     */

    public static <R> R sanitizePrompt(Supplier<R> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static int getSanitizedInt(String prompt, String errMsg, Predicate<Integer> test) {
        Supplier<Integer> promptInt =
            () -> {
                System.out.print(prompt);
                String input = INPUT.nextLine().trim();
                int result;
                if (!input.matches("^[-+]?[0-9]+$") || !test.test((result = Integer.parseInt(input))))
                    throw new IllegalArgumentException(errMsg);
                else return result;
            };
        return sanitizePrompt(promptInt);
    }

    public static boolean getSanitizedBoolean(String prompt) {
        Supplier<Boolean> promptString =
            () -> {
                System.out.print(prompt);
                String input = INPUT.nextLine().trim();
                if (!input.matches("(^[Tt][Rr][Uu][Ee]$)|(^[Ff][Aa][Ll][Ss][Ee]$)"))
                    throw new IllegalArgumentException("Invalid input. Please try again.");
                else return Boolean.parseBoolean(input.toLowerCase());
            };
        return sanitizePrompt(promptString);
    }

    public static String getString(String prompt) {
        System.out.print(prompt);
        return INPUT.nextLine().trim();
    }

    public static String getString(String prompt, String errMsg, Predicate<String> predicate) {
        return sanitizePrompt(
            () -> {
                String input = getString(prompt);
                if (!predicate.test(input)) throw new IllegalArgumentException(errMsg);
                return input;
            }
        );
    }

    public static int getInt(String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(INPUT.nextLine().trim());
    }
}
