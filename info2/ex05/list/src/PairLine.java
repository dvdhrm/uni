// a single station entry in a line
public class PairLine implements ILine
{
	// the station
	private Station station;
	// the rest of the line
	private ILine rest;

	public PairLine(Station station, ILine rest)
	{
		this.station = station;
		this.rest = rest;
	}

	// Refresh helper
	public ILine refresh(ILine passed)
	{
		this.station.passed = passed;
		ILine ahead = this.rest.refresh(new PairLine(this.station, passed));
		this.station.ahead = ahead;
		return new PairLine(this.station, ahead);
	}

	// Refresh line
	public void refresh()
	{
		this.refresh(new EndOfLine());
	}

	// Getter
	public Station getStation() { return this.station; }
	public ILine getRest() { return this.rest; }
}
