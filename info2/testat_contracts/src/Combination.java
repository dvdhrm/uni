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
}
