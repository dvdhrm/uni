// Observable which is modifies two observable by an IFunction2
public class Mod2Observable<T> implements IObservable<T>
{
	// The observables we are based on
	IObservable<T> ob1;
	IObservable<T> ob2;
	// The modifier
	IFunction2<T> mod;

	public Mod2Observable(IObservable<T> ob1, IObservable<T> ob2, IFunction2<T> mod)
	{
		this.ob1 = ob1;
		this.ob2 = ob2;
		this.mod = mod;
	}

	// Return value at given date
	public T at(Time date)
	{
		return this.mod.apply(this.ob1.at(date), this.ob2.at(date));
	}

	public Time next(Time date)
	{
		return this.mod.next(date);
	}
}
