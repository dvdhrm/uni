// Function that takes a single argument
public interface IFunction1<T>
{
	// Apply the function
	public T apply(T arg);
	// Return time when \arg would change next
	public Time next(Time date);
}
