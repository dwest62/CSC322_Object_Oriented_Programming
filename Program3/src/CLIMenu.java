import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents a CLI Menu
 */
public class CLIMenu {
	
	protected final CLIOption[] OPTIONS;
	protected CLIOption DefaultOption =
		new CLIOption(0, "Default", () -> System.out.println("Invalid choice. Please try again."));
	
	/**
	 * @param OPTIONS Array containing the options the menu will display
	 */
	protected CLIMenu (CLIOption[] OPTIONS) {
		this.OPTIONS = OPTIONS;
	}
	
	/**
	 * Run CLI option command based on user choice
	 * @param choice String containing user's choice. If choice is not valid, DefaultOption is executed.
	 */
	public void runOption(String choice)
	{
		IntStream.range(0, OPTIONS.length)
			.boxed()
			.collect(Collectors.toMap(i -> String.valueOf(OPTIONS[i].getN()), i -> OPTIONS[i]))
			.getOrDefault(choice, DefaultOption)
			.getPROCEDURE()
			.execute();
	}
	
	/**
	 * Build a printable, formatted CLI menu from this menu
	 * @param welcome Welcome message added as line before menu options
	 * @param prompt Prompt added as line after options
	 * @return String containing a formatted CLI menu
	 */
	public String buildMenu (String welcome, String prompt) {
		StringBuilder sb = new StringBuilder(welcome).append("\n");
		Arrays.stream(OPTIONS).forEach(e -> sb.append("\t").append(e).append("\n"));
		return sb.append(prompt).toString();
	}
	
	/**
	 * @param defaultOption new CLIMenu.CLIOption to be executed upon invalid input
	 */
	public void setDefaultOption (CLIOption defaultOption) {
		DefaultOption = defaultOption;
	}
	
	/**
	 * Represents an option for a CLI menu
	 */
	public static class CLIOption {
	//	These could be changed from final for more flexibility
		private final String DESCRIPTION;
		private final Procedure PROCEDURE;
		private final int N;
		
		/**
		 * @param N Number of option
		 * @param DESCRIPTION Descriptive text
		 * @param PROCEDURE Executable procedure to be run when option is chosen
		 */
		CLIOption (int N, String DESCRIPTION, Procedure PROCEDURE) {
			this.N = N;
			this.DESCRIPTION = DESCRIPTION;
			this.PROCEDURE = PROCEDURE;
		}
		
		public Procedure getPROCEDURE () {
			return PROCEDURE;
		}
		public String getDESCRIPTION () {
			return DESCRIPTION;
		}
		public int getN()
		{
			return N;
		}
		@Override
		public String toString () {
			return N + ") " + DESCRIPTION;
		}
		
		/**
		 * Functional Interface with no parameters and no return value.
		 */
		@FunctionalInterface
		public interface Procedure {
			void execute ();
		}
	}
	
}
