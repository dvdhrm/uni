// Observable which is modifies another observable by an IFunction1
public class ModObservable<T> implements IObservable<T>
{
	// The observable we are based on
	IObservable<T> ob;
	// The modifier
	IFunction1<T> mod;

	public ModObservable(IObservable<T> ob, IFunction1<T> mod)
	{
		this.ob = ob;
		this.mod = mod;
	}

	// Return value at given date
	public T at(Time date)
	{
		return this.mod.apply(this.ob.at(date));
	}

	public Time next(Time date)
	{
		return this.mod.next(date);
	}
}
