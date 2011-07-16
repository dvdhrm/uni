// Iterator for generic lists
public class ListIterator<T> implements java.util.Iterator<T>
{
	// Current position
	private IList<T> pos;

	public ListIterator(IList<T> p)
	{
		this.pos = p;
	}

	// Is there a next element in the list
	public boolean hasNext()
	{
		return this.pos instanceof PairList;
	}

	// Return element and increment the iterator
	public T next()
	{
		if (this.hasNext()) {
			T t = ((PairList<T>)this.pos).head;
			this.pos = ((PairList<T>)this.pos).tail;
			return t;
		} else {
			throw new java.util.NoSuchElementException();
		}
	}

	// Remove current element
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
}
