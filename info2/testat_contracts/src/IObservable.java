// An Observable is an opaque type that generates a static value for a
// specific date.
public interface IObservable<T>
{
	// Return value for given date
	public T at(Time date);

	// Returns the first time after \date when the overservable changes next
	public Time next(Time date);
}
