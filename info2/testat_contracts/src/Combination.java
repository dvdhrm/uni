// combination of two contracts
public class Combination extends AContract
{
	// The two contracts
	IContract c1;
	IContract c2;

	public Combination(IContract c1, IContract c2)
	{
		this.c1 = c1;
		this.c2 = c2;
	}

	public Position simplify(Time date)
	{
		Position p1 = this.c1.simplify(date);
		Position p2 = this.c2.simplify(date);

		if (p1.date.before(p2.date))
			return new Position(p1.contract.combine(this.c2), p1.date, p1.amount);
		else
			return new Position(p2.contract.combine(this.c1), p2.date, p2.amount);
	}
}
