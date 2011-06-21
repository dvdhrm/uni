// Function that takes two arguments to perform an action
public interface IFunction2<T>
{
	// Apply function
	public T apply(T first, T second);
	// Return time when \apply changes next
	public Time next(Time date);
}
