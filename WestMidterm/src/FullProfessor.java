import java.util.InputMismatchException;

public class FullProfessor extends FacultyMember {
	private final static SalaryRange SALARY_RANGE = new SalaryRange(75000, 120000);
	private double salary = 75000;
	FullProfessor(String firstName, String lastName, double salary) {
		super(firstName, lastName);
		this.setSalary(salary);
	}
	FullProfessor(String firstName, String lastName) {
		super(firstName, lastName);
	}
	
	public double getSalary() {
		return salary;
	}
	
	@Override
	public String getTypeName() {
		return "full";
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
