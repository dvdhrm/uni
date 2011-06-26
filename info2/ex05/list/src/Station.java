// a single rail station
public class Station
{
	// name of the line
	public String name;
	// the line ahead
	public ILine ahead;
	// the passed stations (reversed)
	public ILine passed;

	public Station(String name)
	{
		this.name = name;
		this.ahead = new EndOfLine();
		this.passed = new EndOfLine();
	}
}
