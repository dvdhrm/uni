// Visitor for generic lists
public interface IListVisitor<T, S>
{
	// Visit element
	public S visitPairList(PairList<T> list);

	// Visit empty list
	public S visitEmptyList(EmptyList<T> list);
}
