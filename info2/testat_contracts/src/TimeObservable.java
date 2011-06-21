// Observable with current time as value
public class TimeObservable implements IObservable<Time>
{
	public TimeObservable()
	{
	}

	// Get value at given date
	public Time at(Time date)
	{
		return date;
	}

	public Time next(Time date)
	{
		return date.inc();
	}
}
