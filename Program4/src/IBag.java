/**
 * Represents a generic container of items
 * @param <E> Container item type
*/
public interface IBag<E> {
/**
 * Adds item to container
*/
	void add(E item);
/**
 * Removes item from container and returns item
 * @return item removed from container
*/
	E remove();
/**
 * Checks container for item
 * @param item Item to check
 * @return true if item is in container and false otherwise
*/
	boolean contains(E item);
/**
 * Checks if container is empty
 * @return true if container is empty and false otherwise
*/
	boolean empty();
}
