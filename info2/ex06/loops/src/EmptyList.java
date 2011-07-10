// empty list
public class EmptyList<T> extends AList<T>
{
	// Return this list sorted
	public IList<T> sort(IOrder<T> order)
	{
		return this;
	}

	// Return list with \value inserted into sorted list
	public IList<T> insert(IOrder<T> order, T value)
	{
		return new PairList<T>(value, this);
	}

	// Return list of elements that apply to this filter
	public IList<T> filter(IFunction<T, Boolean> filter)
	{
		return this;
	}

	// Return list of all elements mapped to another value
	public <S> IList<S> map(IFunction<T, S> function)
	{
		return new EmptyList<S>();
	}

	// Return true if any element in the list applies to the filter
	public boolean any(IFunction<T, Boolean> filter)
	{
		return false;
	}

	// Fold the whole list to a single value
	public <S> S fold(IFolder<T, S> folder)
	{
		return folder.start();
	}

	// Visitor pattern
	public <S> S visit(IListVisitor<T, S> visitor)
	{
		return visitor.visitEmptyList(this);
	}
}
