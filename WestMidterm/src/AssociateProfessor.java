import java.util.InputMismatchException;

public class AssociateProfessor extends FacultyMember {
	private final static SalaryRange SALARY_RANGE = new SalaryRange(60000, 100000);
	private double salary = 60000;
	AssociateProfessor(String firstName, String lastName, double salary) {
		super(firstName, lastName);
		this.setSalary(salary);
	}
	
	AssociateProfessor(String firstName, String lastName) {
		super(firstName, lastName);
	}
	
	public double getSalary() {
		return salary;
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
	
	@Override
	public String getTypeName() {
		return "associate";
	}
}
