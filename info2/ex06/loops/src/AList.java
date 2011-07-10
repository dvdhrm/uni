// common functions of generic lists
public abstract class AList<T> implements IList<T>
{
	// Return new iterator at this position
	public java.util.Iterator<T> iterator()
	{
		return new ListIterator<T>(this);
	}

	// Reverse list
	public IList<T> reverse()
	{
		IList<T> ret = new EmptyList<T>();

		for (T v : this)
			ret = new PairList<T>(v, ret);

		return ret;
	}

	// Return this list sorted
	public IList<T> sort2(IOrder<T> order)
	{
		IList<T> sorted = new EmptyList<T>();
		IList<T> iter = this;

		while (iter instanceof PairList) {
			PairList<T> t = (PairList<T>)iter;
			sorted = sorted.insert2(order, t.head);
			iter = t.tail;
		}

		return sorted;
	}

	public IList<T> sort3(IOrder<T> order)
	{
		IList<T> sorted = new EmptyList<T>();

		for (T v : this)
			sorted = sorted.insert3(order, v);

		return sorted;
	}

	// Return list with \value inserted into sorted list
	public IList<T> insert2(IOrder<T> order, T value)
	{
		boolean added = false;
		IList<T> sorted = new EmptyList<T>();
		IList<T> iter = this;

		while (iter instanceof PairList) {
			PairList<T> t = (PairList<T>)iter;

			if (!added && order.lessThanOrEqual(value, t.head)) {
				sorted = new PairList<T>(value, sorted);
				added = true;
			}

			sorted = new PairList<T>(t.head, sorted);
			iter = t.tail;
		}

		if (!added)
			sorted = new PairList<T>(value, sorted);

		return sorted.reverse();
	}

	public IList<T> insert3(IOrder<T> order, T value)
	{
		boolean added = false;
		IList<T> sorted = new EmptyList<T>();

		for (T v : this) {
			if (!added && order.lessThanOrEqual(value, v)) {
				sorted = new PairList<T>(value, sorted);
				added = true;
			}
			sorted = new PairList<T>(v, sorted);
		}

		if (!added)
			sorted = new PairList<T>(value, sorted);

		return sorted.reverse();
	}

	// Return list of elements that apply to this filter
	public IList<T> filter2(IFunction<T, Boolean> filter)
	{
		IList<T> filtered = new EmptyList<T>();
		IList<T> iter = this;

		while (iter instanceof PairList) {
			PairList<T> t = (PairList<T>)iter;

			if (filter.apply(t.head))
				filtered = new PairList<T>(t.head, filtered);

			iter = t.tail;
		}

		return filtered.reverse();
	}

	public IList<T> filter3(IFunction<T, Boolean> filter)
	{
		IList<T> filtered = new EmptyList<T>();

		for (T v : this) {
			if (filter.apply(v))
				filtered = new PairList<T>(v, filtered);
		}

		return filtered.reverse();
	}

	// Return list of all elements mapped to another value
	public <S> IList<S> map2(IFunction<T, S> function)
	{
		IList<S> mapped = new EmptyList<S>();
		IList<T> iter = this;

		while (iter instanceof PairList) {
			PairList<T> t = (PairList<T>)iter;

			mapped = new PairList<S>(function.apply(t.head), mapped);

			iter = t.tail;
		}

		return mapped.reverse();
	}

	public <S> IList<S> map3(IFunction<T, S> function)
	{
		IList<S> mapped = new EmptyList<S>();

		for (T v : this)
			mapped = new PairList<S>(function.apply(v), mapped);

		return mapped.reverse();
	}

	// Return true if any element in the list applies to the filter
	public boolean any2(IFunction<T, Boolean> filter)
	{
		return this.filter2(filter) instanceof PairList;
	}

	public boolean any3(IFunction<T, Boolean> filter)
	{
		return this.filter3(filter) instanceof PairList;
	}

	// Fold the whole list to a single value
	public <S> S fold2(IFolder<T, S> folder)
	{
		S folded = folder.start();
		IList<T> iter = this.reverse();

		while (iter instanceof PairList) {
			PairList<T> t = (PairList<T>)iter;

			folded = folder.apply(t.head, folded);

			iter = t.tail;
		}

		return folded;
	}

	public <S> S fold3(IFolder<T, S> folder)
	{
		S folded = folder.start();

		for (T v : this.reverse())
			folded = folder.apply(v, folded);

		return folded;
	}
}
