import java.util.InputMismatchException;

public class AssistantProfessor extends FacultyMember {
	
	private final static SalaryRange SALARY_RANGE = new SalaryRange(50000, 70000);
	private double salary = 50000;
	AssistantProfessor(String firstName, String lastName, double salary) {
		super(firstName, lastName);
		this.setSalary(salary);
	}
	AssistantProfessor(String firstName, String lastName) {
		super(firstName, lastName);
	}
	public double getSalary() {
		return salary;
	}
	
	@Override
	public String getTypeName() {
		return "assistant";
	}
	
	public void setSalary(double salary) {
		if(salary < SALARY_RANGE.getLowerBound() || salary > SALARY_RANGE.getUpperBound())
			throw new InputMismatchException();
		this.salary = salary;
	}
	@Override
	public SalaryRange getSalaryRange() {
		return SALARY_RANGE;
	}
}
