// Single entry in a list
public class PairList<T> implements IList<T>
{
	// Current element value
	private T head;
	// Rest of list
	private IList<T> tail;

	public PairList(T h, IList<T> t)
	{
		this.head = h;
		this.tail = t;
	}
}
