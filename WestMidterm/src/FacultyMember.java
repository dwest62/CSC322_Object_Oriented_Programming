/**
* Represents a generic FacultyMember
*/
public abstract class FacultyMember {
//	Will increment each time a faculty member is initialized.
	public static int n = 1;
	private String firstName;
	private String lastName;
	
	FacultyMember(String firstName, String lastName) {
		n++;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
//	Abstract methods to be implemented
	public abstract SalaryRange getSalaryRange();
	
	public abstract void setSalary(double salary);
	
	public abstract double getSalary();
	public abstract String getTypeName();
	
	@Override
	public String toString() {
		return String.format("%s %s %s %.2f", this.getFirstName(), this.getLastName(), this.getTypeName(),
			this.getSalary());
	}
}
