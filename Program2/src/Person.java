/**
 * Represents a Person
 */
public class Person
{
	private String name;
	private String address;
	private String phoneNumber;
	private String emailAddress;

	/**
	 * @param name Name of person
	 * @param address Street address of person
	 * @param phoneNumber Phone number of person
	 * @param emailAddress Email of person
	 */
	public Person(String name, String address, String phoneNumber, String emailAddress)
	{
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress()
	{
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	/**
	 * @return String containing the name of the person and the name of the Class with a space in between.
	 */
	@Override
	public String toString()
	{
		// "this.getClass().getName()" returns the object's Class name.
		return this.getName() + " " + this.getClass().getName();
	}
}
