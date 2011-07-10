// single element in a list
public class PairList<T> extends AList<T>
{
	// Value of this element
	T head;
	// Rest of list
	IList<T> tail;

	public PairList(T h, IList<T> t)
	{
		this.head = h;
		this.tail = t;
	}

	// Return this list sorted
	public IList<T> sort(IOrder<T> order)
	{
		return this.tail.sort(order).insert(order, this.head);
	}

	// Return list with \value inserted into sorted list
	public IList<T> insert(IOrder<T> order, T value)
	{
		if (order.lessThanOrEqual(value, this.head))
			return new PairList<T>(value, this);
		else
			return new PairList<T>(this.head, this.tail.insert(order, value));
	}

	// Return list of elements that apply to this filter
	public IList<T> filter(IFunction<T, Boolean> filter)
	{
		if (filter.apply(this.head))
			return new PairList<T>(this.head, this.tail.filter(filter));
		else
			return this.tail.filter(filter);
	}

	// Return list of all elements mapped to another value
	public <S> IList<S> map(IFunction<T, S> function)
	{
		return new PairList<S>(function.apply(this.head), this.tail.map(function));
	}

	// Return true if any element in the list applies to the filter
	public boolean any(IFunction<T, Boolean> filter)
	{
		return filter.apply(this.head) || this.tail.any(filter);
	}

	// Fold the whole list to a single value
	public <S> S fold(IFolder<T, S> folder)
	{
		return folder.apply(this.head, this.tail.fold(folder));
	}

	// Visitor pattern
	public <S> S visit(IListVisitor<T, S> visitor)
	{
		return visitor.visitPairList(this);
	}
}
