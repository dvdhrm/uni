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
}
