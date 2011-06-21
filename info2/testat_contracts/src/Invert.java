// This inverts a given contract
public class Invert extends AContract
{
	// The contract that shall be inverted
	private IContract contract;

	public Invert(IContract contract)
	{
		this.contract = contract;
	}

	// inverts a contract
	public IContract invert()
	{
		return this.contract;
	}
}
