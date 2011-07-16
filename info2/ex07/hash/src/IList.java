// a generic list
// Normal functions are applicative, suffix 2 is imperative with while and
// suffix 3 is imperative with foreach
public interface IList<T> extends Iterable<T>
{
	// Return this list sorted
	public IList<T> sort(IOrder<T> order);
	public IList<T> sort2(IOrder<T> order);
	public IList<T> sort3(IOrder<T> order);
	// Return list with \value inserted into sorted list
	public IList<T> insert(IOrder<T> order, T value);
	public IList<T> insert2(IOrder<T> order, T value);
	public IList<T> insert3(IOrder<T> order, T value);
	// Return list of elements that apply to this filter
	public IList<T> filter(IFunction<T, Boolean> filter);
	public IList<T> filter2(IFunction<T, Boolean> filter);
	public IList<T> filter3(IFunction<T, Boolean> filter);
	// Return list of all elements mapped to another value
	public <S> IList<S> map(IFunction<T, S> function);
	public <S> IList<S> map2(IFunction<T, S> function);
	public <S> IList<S> map3(IFunction<T, S> function);
	// Return true if any element in the list applies to the filter
	public boolean any(IFunction<T, Boolean> filter);
	public boolean any2(IFunction<T, Boolean> filter);
	public boolean any3(IFunction<T, Boolean> filter);
	// Fold the whole list to a single value
	public <S> S fold(IFolder<T, S> folder);
	public <S> S fold2(IFolder<T, S> folder);
	public <S> S fold3(IFolder<T, S> folder);
	// Visitor pattern
	public <S> S visit(IListVisitor<T, S> visitor);
//	public <S> S visit2(IListVisitor<T, S> visitor);
//	public <S> S visit3(IListVisitor<T, S> visitor);
	// Reverse list (imperative)
	public IList<T> reverse();
}
