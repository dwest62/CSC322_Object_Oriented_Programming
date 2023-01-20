/**
 * Represents a Student
 */
public class Student extends Person
{
	// Requirements requested Status be a constant
	private final Status STATUS;
	public Student(String name, String address, String phoneNumber, String emailAddress, Status status)
	{
		super(name, address, phoneNumber, emailAddress);
		STATUS = status;
	}

	public Status getSTATUS()
	{
		return STATUS;
	}
	
	public static enum Status
	{
		FRESHMAN,
		SOPHOMORE,
		JUNIOR,
		SENIOR
	}
}
