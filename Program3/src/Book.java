public class Book extends Item
{
    private String authors;
    private String edition;
    private String publisher;
    private int pubYear;

    public Book(String title, double price, String authors, String edition, String publisher, int pubYear)
    {
        super(title, price);
        this.authors = authors;
        this.edition = edition;
        this.publisher = publisher;
        this.pubYear = pubYear;
    }

    public String getAuthors()
    {
        return authors;
    }

    public void setAuthors(String authors)
    {
        this.authors = authors;
    }

    public String getEdition()
    {
        return edition;
    }

    public void setEdition(String edition)
    {
        this.edition = edition;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    public int getPubYear()
    {
        return pubYear;
    }

    public void setPubYear(int pubYear)
    {
        this.pubYear = pubYear;
    }
}
