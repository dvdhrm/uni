// End of a line
public class EndOfLine implements ILine
{
	// Refresh helper
	public ILine refresh(ILine passed)
	{
		return this;
	}

	// Refresh line
	public void refresh()
	{
	}

	// Getter
	public Station getStation() { throw new AssertionError("End Of Line has no related station"); }
	public ILine getRest() { throw new AssertionError("End of Line has no rest line"); }
}
