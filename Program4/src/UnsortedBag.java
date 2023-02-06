import java.util.ArrayList;

/**
 * Represents a generic container of unsorted items
 * @param <E> Item type held in container
*/
public class UnsortedBag<E> implements IBag<E> {
	private final ArrayList<E> data = new ArrayList<>();
	UnsortedBag(){}
	/**
	 * Adds item into container
	 * @param item Item to be added
	 */
	@Override
	public void add(E item) { data.add(item); }
	
	/**
	 * Removes the first item added to the container from the container
	 * @return item removed
	 */
	@Override
	public E remove() {	return data.remove(0); }
	
/**
 * Checks if item is in bag
 * @param item Item to check
 * @return true if found, otherwise false
*/
	@Override
	public boolean contains(E item) { return data.contains(item); }
	
/**
  * Checks if bag is empty
  * @return true if bag is empty, otherwise false
*/
	@Override
	public boolean empty() { return data.isEmpty(); }
}
