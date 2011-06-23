// A position is a combination of a contract and a time
public class Position
{
	// the contract
	IContract contract;
	// the date of the contract
	Time date;
	// The amount that is transferred to the holder
	double amount;

	public Position(IContract c, Time d, double a)
	{
		this.contract = c;
		this.date = d;
		this.amount = a;
	}
}
