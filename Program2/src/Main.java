/*
 * Author: James West Date: 01.20.2023
 * Class: Spring 100 CSC322 Intro Programming with Java
 * Assignment: Program2
 * Description: This program demonstrates the use of inheritance through the Person, Student, Employee, Faculty, and
 *      Staff classes.
 *
 * I certify, that this computer program submitted by me is all of my own work. Signed: James West
 */

/**
 * Main class tests the program by initiating each class and printing their toString method (which is overridden to
 * return the name and class name of the object) to the console.
 */
public class Main
{
	public static void main(String[] args)
	{
		// Initialize all classes as objects for testing
		
		Person person = new Person(
				"Paul Anderson",
				"123 Address Rd., Saint Paul, MN",
				"1231231234",
				"panderson@gmail.com"
		);
		
		Student student = new Student(
				"Paul Anderson",
				"123 Address Rd., Saint Paul, MN",
				"1231231234",
				"panderson@gmail.com",
				Student.Status.SOPHOMORE
		);
		
		Employee employee = new Employee(
				"Paul Anderson",
				"123 Address Rd., Saint Paul, MN",
				"1231231234",
				"panderson@gmail.com",
				"12F",
				65000.00,
				new MyDate()
		);
		
		Faculty faculty = new Faculty(
				"Paul Anderson",
				"123 Address Rd., Saint Paul, MN",
				"1231231234",
				"panderson@gmail.com",
				"12F",
				65000.00,
				new MyDate(),
				"MWF 1300 to 1500",
				"Associate Professor"
		);
		
		Staff staff = new Staff(
				"Paul Anderson",
				"123 Address Rd., Saint Paul, MN",
				"1231231234",
				"panderson@gmail.com",
				"12F",
				65000.00,
				new MyDate(),
				"Human Resources"
		);
		
		// Print toString output for each object
		
		System.out.println(person);
		System.out.println(student);
		System.out.println(employee);
		System.out.println(faculty);
		System.out.println(staff);
		
		// Assert toString method of each object returns a String consisting of the Objects name field and Class name
		//      with a space in between
		
		assert person.toString().equals("Paul Anderson Person");
		assert student.toString().equals("Paul Anderson Student");
		assert employee.toString().equals("Paul Anderson Employee");
		assert faculty.toString().equals("Paul Anderson Faculty");
		assert staff.toString().equals("Paul Anderson Staff");
		
		// Print Class test success to console
		
		System.out.println("Tests Passed!");
	}
}
