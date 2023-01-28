package Menu;

/**
 * Represents an option for a CLI menu
 */
public class CLIOption {
	//	These could be changed from final for more flexibility
	private final String DESCRIPTION;
	private final Procedure PROCEDURE;
	private final int N;
	
	/**
	 * @param N           Number of option
	 * @param DESCRIPTION Descriptive text
	 * @param PROCEDURE   Executable procedure to be run when option is chosen
	 */
	public CLIOption (int N, String DESCRIPTION, Procedure PROCEDURE) {
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
	
	public int getN () {
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
