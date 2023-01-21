import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Represents a date with a day, month, and year following the Gregorian Calendar.
 */
public class MyDate
{
	private int year;
	private int month;
	private int day;
	
	/**
	 * Construct a default Date using the current local time.
	 */
	MyDate()
	{
		this(new GregorianCalendar().getTimeInMillis());
	}
	
	/**
	 * Constructs a date using the number of milliseconds January 1, 1970 00:00:00.000 GMT (Gregorian).
	 * @param elapsedTime number of elapsed milliseconds from January 1, 1970 00:00:00.000 GMT (Gregorian).
	 */
	MyDate(long elapsedTime)
	{
		this.setDate(elapsedTime);
	}
	
	/**
	 * Construct a date from day, month, and year, parameters.
	 * @param day Day of date
	 * @param month Month of date. Indexed starting with 0.
	 * @param year Year of date
	 */
	MyDate(int day, int month, int year)
	{
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public void setYear(int year)
	{
		this.year = year;
	}
	
	public int getMonth()
	{
		return month;
	}
	
	public void setMonth(int month)
	{
		this.month = month;
	}
	
	public int getDay()
	{
		return day;
	}
	
	public void setDay(int day)
	{
		this.day = day;
	}
	public void setDate(long elapsedTime)
	{
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(elapsedTime);
		this.day = calendar.get(Calendar.DAY_OF_MONTH);
		this.month = calendar.get(Calendar.MONTH);
		this.year = calendar.get(Calendar.YEAR);
	}
}
