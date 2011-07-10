// Generic list
public interface IList<T> {
	// Visitor
	public <Q> IList<Q> visit(IVisitor<Q> visitor);
	// Return element at pos n
	public T at(int n);
	// Returns list length
	public int length();
}
