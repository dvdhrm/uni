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
}
