/*
 * NOTE: Program uses assert to test Composition and Aggregation. Assert validation is disabled by default. Use -ea or
 *      -enableassertions if assert validation is desired.
 */

/*
 * Author: James West Date: 01.12.2023
 * Class: Spring 100 CSC322 Intro Programming with Java
 * Assignment: Program1
 * Description: Program contains questions and answers from assignment, two classes and a test class demonstrating
 *      Aggregation, two classes and a test class demonstrating Composition, and a Main class which runs the tests.
 *
 * I certify, that this computer program submitted by me is all of my own work. Signed: James West
 */

/*
 * Question Answers:
 * 1. Relationships between classes are an important part of Object-Oriented Programming because they allow for the
 *      modeling of complex systems which contain many interconnected components at varying levels of abstraction and
 *      specificity within an application.
 *
 * 2. An Association is a relationship between classes which involves an activity between the classes.
 *
 * 3. List the four types of Associations between two classes.
 *      In reading the text, it was hard to find 4 clear types of Association. There was Aggregation, Composition,
 *      and Inheritance was mentioned. According to
 *      <a href="https://www.javatpoint.com/uml-relationship">javapoint.com</a>, the 4 types are bidirectional,
 *      unidirectional, aggregation (composition aggregation), and reflexive. I researched a few other sites, but these
 *      seemed to be the clearest to me given the prompt.
 *
 * 4. Illustrate each of these relationships by writing a sentence for each one between an Employee class and a Company
 * class.
 *      a) unidirectional: A Company hires an Employee.
 *      b) bidirectional: A Company hires an Employee and an Employee
 *          works for a Company(s).
 *      c) aggregation: A Company has an Employee.
 *      d) reflexive: A Company has an Employee (e.g., the manager) who hires Employees.
 *
 * 5. These Associations mean that objects from one class can communicate with objects of another class, enabling them
 * to access data. Describe how a HAS-a relationship is created in OOP. In OOP, a HAS-a relationship is created when a
 * reference to a subject object is included within an owner object.
 *
 * 6. The two forms of Association are Aggregation and Composition. Describe both, highlighting the differences.
 *      Aggregation is a form of association which the "HAS-a" relationship. Composition is a special form of
 *      aggregation in which the existence of the subject object is dependent on the existence of its owner object. In
 *      other words, it represents a "stronger" connection between the owner object and the subject object in which
 *      the existence of the subject object cannot meaningfully exist in a vacuum or more particularly, the lifetime of
 *      the subject is tied to the lifetime of the object.
 */

// Program1

import java.util.Objects;

// Run composition and aggregation tests.
public class Main
{
	public static void main(String[] args)
	{
		DogToyAggregationTest.run();
		PersonNameCompositionTest.run();
	}
}

// Aggregation demonstration section. Dog is aggregated with Toy.
class Dog
{
	private String name;
	private Toy favoriteToy;

	Dog(String name, Toy favoriteToy)
	{
		this.name = name;
		this.favoriteToy = favoriteToy;
	}

	public Toy getFavoriteToy()
	{
		return favoriteToy;
	}

	public void setFavoriteToy(Toy favoriteToy)
	{
		this.favoriteToy = favoriteToy;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}

class Toy
{
	private String name;
	private double cost;

	Toy(String name, double cost)
	{
		this.name = name;
		this.cost = cost;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public double getCost()
	{
		return cost;
	}

	public void setCost(int cost)
	{
		this.cost = cost;
	}
}

class DogToyAggregationTest
{
	public static void run()
	{
		Toy testToy = new Toy("Squeaky Bone", 18.12);
		Dog testDog = new Dog("Buster", testToy);

		// Note: assert only triggers if -ea is passed as a parameter.
		assert Objects.equals(testDog.getFavoriteToy(), testToy) : "Given testDog initialized as a Dog type with " +
				"testToy passed as the favoriteToy parameter, getFavoriteToy() should return a reference to testToy.";
		System.out.println("Aggregation test passed!");
	}
}

// Composition demonstration section. Person is composed with Name.

class Person
{
	private Name name;
	private double height;
	private double weight;

	public Person(Name name, double height, double weight)
	{
		this.name = name;
		this.height = height;
		this.weight = weight;
	}

	public Name getName()
	{
		return name;
	}

	public void setName(Name name)
	{
		this.name = name;
	}

	public double getHeight()
	{
		return height;
	}

	public void setHeight(double height)
	{
		this.height = height;
	}

	public double getWeight()
	{
		return weight;
	}

	public void setWeight(double weight)
	{
		this.weight = weight;
	}

}

class Name
{
	private String first;
	private String middle;
	private String last;

	public Name(String first, String middle, String last)
	{
		this.first = first;
		this.middle = middle;
		this.last = last;
	}

	public String getFirst()
	{
		return first;
	}

	public void setFirst(String first)
	{
		this.first = first;
	}

	public String getMiddle()
	{
		return middle;
	}

	public void setMiddle(String middle)
	{
		this.middle = middle;
	}

	public String getLast()
	{
		return last;
	}

	public void setLast(String last)
	{
		this.last = last;
	}
}

class PersonNameCompositionTest
{
	public static void run()
	{
		Name testName = new Name("John", "Adam", "Doe");
		Person testPerson = new Person(testName, 172, 190);
		Name testPersonName = testPerson.getName();
		assert Objects.equals(testPersonName, testName) : "The testPerson's Name should be the testName object";
		System.out.println("Composition test passed!");
	}
}