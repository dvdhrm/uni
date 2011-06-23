// A contract without any effect
public class Zero extends AContract
{
	// Is zero?
	public boolean isZero()
	{
		return true;
	}

	// Invert contract
	public IContract invert()
	{
		return this;
	}

	// scale contract
	public IContract scale(IObservable<Double> scale)
	{
		return this;
	}

	public Position simplify(Time date)
	{
		return new Position(this, date, 0);
	}
}
