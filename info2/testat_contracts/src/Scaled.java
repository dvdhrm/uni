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

	public Position simplify(Time date)
	{
		Position p = this.contract.simplify(date);
		IContract c = p.contract.scale(this.scale);
		return new Position(c, p.date, p.amount * this.scale.at(date));
	}
}
