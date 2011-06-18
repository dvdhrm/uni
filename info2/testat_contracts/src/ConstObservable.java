// Constant observable at any time
public class ConstObservable<T> implements IObservable<T>
{
	// The constant value
	T value;

	public ConstObservable(T value)
	{
		this.value = value;
	}

	// Get value at given date
	public T at(Time date)
	{
		return this.value;
	}
}
