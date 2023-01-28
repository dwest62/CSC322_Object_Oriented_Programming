package Item;

/**
 * Represents Item.Software
 */
public class Software extends Item
{
    private String version;

    public Software(String title, double price, String version)
    {
        super(title, price);
        this.version = version;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }
}
