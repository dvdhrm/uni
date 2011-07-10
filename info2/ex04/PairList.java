// List entry
public class PairList<T> implements IList<T> {
	// Entry
	T head;
	// rest of list
	IList<T> tail;

	public PairList(T head, IList<T> tail)
	{
		this.head = head;
		this.tail = tail;
	}

	// Visitor
	public <Q> IList<Q> visit(IVisitor<Q> visitor)
	{
		return visitor.visitPrimOp(this);
	}

	// Return element at pos n
	public T at(int n)
	{
		if (n <= 0)
			return this.head;
		else
			return this.tail.at(n - 1);
	}

	// Returns list length
	public int length()
	{
		return 1 + tail.length();
	}
}
