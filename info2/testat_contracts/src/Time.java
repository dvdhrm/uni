// Current time based on UNIX timestamp
public class Time
{
	// Seconds since unix epoch
	private long secs;

	public Time()
	{
		this.secs = System.currentTimeMillis()/1000;
	}

	public Time(long date)
	{
		this.secs = date;
	}

	// Returns the date as seconds since unix epoch
	public long getTimestamp()
	{
		return this.secs;
	}

	// Returns true if our date is before \date
	public boolean before(Time date)
	{
		return this.getTimestamp() < date.getTimestamp();
	}

	// Returns true if the time is "never"
	public boolean isNever()
	{
		return this.secs == 0;
	}

	// Return increased time
	public Time inc()
	{
		return new Time(this.secs + 1);
	}
}
