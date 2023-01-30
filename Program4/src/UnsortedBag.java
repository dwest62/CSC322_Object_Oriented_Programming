import java.util.ArrayList;

public class UnsortedBag<E> implements IBag<E>
{
	private final ArrayList<E> data = new ArrayList<>();
	UnsortedBag(){}
	@Override
	public void add(E item)
	{
		data.add(item);
	}
	
	@Override
	public E remove()
	{
		return data.remove(0);
	}
	
	@Override
	public boolean contains(E item)
	{
		return data.contains(item);
	}
	
	@Override
	public boolean empty()
	{
		return data.isEmpty();
	}
}
