// A scaled contract
public class Scaled extends AContract
{
	// The contract that is scaled
	IContract contract;
	// The scale observable
	IObservable<Double> scale;

	public Scaled(IContract c, IObservable<Double> s)
	{
		this.contract = c;
		this.scale = s;
	}
}
