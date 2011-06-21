// a single rail station
public class Station
{
	// name of the line
	String name;
	// the line ahead
	ILine ahead;
	// the passted stations (reversed)
	ILine passed;

	public Station(String name, ILine ahead, ILine passed)
	{
		this.name = name;
		this.ahead = ahead;
		this.passed = passed;
	}
}
