// A timed observable that switches from obj1 to obj2 when the given time has
// timed out.
public class TimedObservable<T> implements IObservable<T>
{
	// The two observables we are based on
	IObservable<T> before;
	IObservable<T> after;
	// The date when we switch between both observables
	// At "date" exactly, it already is \after.
	Time date;

	public TimedObservable(IObservable<T> before, IObservable<T> after, Time date)
	{
		this.before = before;
		this.after = after;
		this.date = date;
	}

	// Return value at given date
	public T at(Time date)
	{
		if (this.date.before(date))
			return this.before.at(date);
		else
			return this.after.at(date);
	}

	public Time next(Time date)
	{
		if (date.before(this.date))
			return this.date;
		else
			return new Time(0);
	}
}
