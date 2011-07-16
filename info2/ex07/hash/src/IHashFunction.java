// hash functions
public interface IHashFunction<T> extends IEquality<T>
{
	// return hash code for Ts
	public int hashCode(T v);
}
