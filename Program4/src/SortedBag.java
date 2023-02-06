import java.util.ArrayList;


/**
 * Represents a generic container of sorted items.
 * @param <E> Item type held in container. Must implement "Comparable".
*/
public class SortedBag<E extends Comparable<? super E>> implements IBag<E> {
	
	private final ArrayList<E> data = new ArrayList<>();
	
	SortedBag(){}
/**
  * Sorts item into container using binary search
  * @param item Item to be added
  */
	@Override
	public void add(E item) {
		data.add(binarySearch(item).INDEX(), item);
	}
/**
  * Removes the smallest item from the container
  * @return item removed
*/
	@Override
	public E remove() { return data.remove(0); }
	
/**
 * Uses binary search to attempt to find item in container
 * @param item Item to check
 * @return true if found, otherwise false
*/
	@Override
	public boolean contains(E item) { return binarySearch(item).IS_FOUND(); }
	
/**
  * Checks if the container is empty
  * @return true if empty, false otherwise
*/
	@Override
	public boolean empty() { return data.isEmpty();	}
/**
 * Searches for an item within this container using binary search and recursion.
 * @param item item to look for
 * @param low lower-bound index of current search
 * @param high upper-bound index of current search
 * @return SearchResult containing a boolean set to true if item is found and an int containing the index of the item
 *      or the index of the closest larger item.
*/
	private SearchResult binarySearch(E item, int low, int high) {
		if(low > high) 	return new SearchResult(false, low);
		else {
			int mid = (low + high) / 2;
			if(item.equals(data.get(mid))) return new SearchResult(true, mid);
			else if(item.compareTo(data.get(mid)) < 0) return binarySearch(item, low, mid - 1);
			else return binarySearch(item, mid + 1, high);
		}
	}
/**
 * Searches for an item within this container using binary search.
 * @param item item to search for
 * @return SearchResult containing a boolean set to true if item is found and an int containing the index of the item
 *      or the index of the closest larger item.
*/
	private SearchResult binarySearch(E item) {
		return this.empty()
			       ? new SearchResult(false, 0)
			       : binarySearch(item, 0, data.size() - 1);
	}
/**
* Represent a search result produced by binary search
 * @param IS_FOUND is set to true when the object is found in the container
 * @param INDEX is set to the index of the object if IS_FOUND, otherwise set to the index of the next larger object.
*/
	private record SearchResult (boolean IS_FOUND, int INDEX){}
}
