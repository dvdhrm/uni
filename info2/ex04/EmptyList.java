// Empty list entry
public class EmptyList<T> implements IList<T> {
	public EmptyList()
	{
	}

	// Visitor
	public <Q> IList<Q> visit(IVisitor<Q> visitor)
	{
		return visitor.visitPrimEnd(this);
	}

	// Return element at pos n
	public T at(int n)
	{
		throw new AssertionError("List overflow");
	}

	// Returns list length
	public int length()
	{
		return 0;
	}
}
