// A conditional contract
public class Conditional extends AContract
{
	// The two contracts
	IContract con1;
	IContract con2;
	// The condition
	IObservable<Boolean> cond;

	public Conditional(IContract con1, IContract con2, IObservable<Boolean> cond)
	{
		this.con1 = con1;
		this.con2 = con2;
		this.cond = cond;
	}

	public Position simplify(Time date)
	{
		if (this.cond.at(date))
			return new Position(this.con1, date, 0);
		else
			return new Position(this.con2, date, 0);
	}
}
