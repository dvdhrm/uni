// The holder one amount of money if due
public class One extends AContract
{
	public Position simplify(Time date)
	{
		return new Position(new Zero(), date, 1);
	}
}
