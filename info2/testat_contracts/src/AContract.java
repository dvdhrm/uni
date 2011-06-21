// common contract functions
public abstract class AContract implements IContract
{
	// is zero contract?
	public boolean isZero()
	{
		return false;
	}

	// inverts a contract
	public IContract invert()
	{
		return new Invert(this);
	}

	// Combine contracts
	public IContract combine(IContract other)
	{
		if (other.isZero())
			return this;
		else if (this.isZero())
			return other;
		else
			return new Combination(this, other);
	}

	// Scale contract
	public IContract scale(IObservable<Double> scale)
	{
		return new Scaled(this, scale);
	}
}
