// empty rail line
public class EmptyLine
{
	// add station to tail of line
	public ILine addTail(String name)
	{
		return this.addTailHelper(name, new EmptyLine());
	}

	public ILine addTailHelper(String name, ILine passed)
	{
		return new PairLine(new Station(name, this, passed), this);
	}
}
