import java.util.ArrayList;


public class SortedBag<E extends Comparable<? super E>> implements IBag<E>
{
	private final ArrayList<E> data = new ArrayList<>();
	
	SortedBag(){}
	@Override
	public void add(E item)
	{
		data.add(binarySearch(item).index(), item);
	}
	@Override
	public E remove()
	{
		return data.remove(0);
	}
	
	@Override
	public boolean contains(E item)
	{
		return binarySearch(item).isFound();
	}
	
	@Override
	public boolean empty()
	{
		return data.isEmpty();
	}
	private SearchResult binarySearch(E item, int low, int high)
	{
		if(low > high) 	return new SearchResult(false, low);
		else {
			int mid = (low + high) / 2;
			if(item.equals(data.get(mid))) return new SearchResult(true, mid);
			else if(item.compareTo(data.get(mid)) < 0) return binarySearch(item, low, mid - 1);
			else return binarySearch(item, mid + 1, high);
		}
	}
	private SearchResult binarySearch(E item) {
		if(data.isEmpty()) return new SearchResult(false, 0);
		else return binarySearch(item, 0, data.size() - 1);
	}
	private record SearchResult (boolean isFound, int index){}
}
