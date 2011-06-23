// A contract between two parties
public interface IContract
{
	// returns true if this contract is a zero contract
	public boolean isZero();

	// Inverts the contract
	public IContract invert();

	// Combine contracts
	public IContract combine(IContract other);

	// Scale contract
	public IContract scale(IObservable<Double> scale);

	// Simplify the contract
	public Position simplify(Time date);
}
