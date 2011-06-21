// a single station entry in a line
public class PairLine
{
	// the station
	String station;
	// the rest of the line
	ILine passed;
	ILine ahead;

	public PairLine(String s, ILine passed, ILine ahead)
	{
		this.station = s;
		this.passed = passed;
		this.ahead = ahead;
	}

	// add new station to tail of line
	public PairLine addTail(String name)
	{
		return this.addTailHelper(name, new EmptyLine());
	}

	public PairLine addTailHelper(String s, ILine passed)
	{
		return new PairLine(this.station, this.passed, new PairLine(s
		return new PairLine(new Station(s.name, new PairLine(name, s.ahead), s.passed), this.tail.newStation(name, new PairLine(this.s, passed)));
	}
}
