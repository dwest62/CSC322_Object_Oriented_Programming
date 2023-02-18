package list_view;

public class Person {
	private String fName;
	private String lName;
	
	public String getfName () {
		return fName;
	}
	Person(String fName, String lName) {
		this.fName = fName;
		this.lName = lName;
	}
	
	public void setfName (String fName) {
		this.fName = fName;
	}
	
	public String getlName () {
		return lName;
	}
	
	public void setlName (String lName) {
		this.lName = lName;
	}
}
