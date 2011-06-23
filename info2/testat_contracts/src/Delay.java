// A delayed contract
public class Delay extends AContract
{
	// The contract that is delayed
	IContract contract;
	// The condition
	IObservable<Boolean> cond;

	public Delay(IContract contract, IObservable<Boolean> cond)
	{
		this.contract = contract;
		this.cond = cond;
	}

	public Position simplify(Time date)
	{
		if (this.cond.at(date))
			return new Position(this.contract, date, 0);
		else
			return new Position(this.contract, this.cond.next(date), 0);
	}
}
