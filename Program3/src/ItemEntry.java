/**
 * Represents an Item Entry
 */
public class ItemEntry
{
    private Item item;
    private int quantity;
    
    public ItemEntry(Item item, int quantity)
    {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem()
    {
        return item;
    }

    public void setItem(Item item)
    {
        this.item = item;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
}
