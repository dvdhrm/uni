// total order
public interface IOrder<T> {
	// Is element v1 smaller than or equal to v2
	public boolean lessThanOrEqual(T v1, T v2);
}
