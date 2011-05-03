// David Herrmann
// A pair of two contracts
public class PairZCB implements IContract
{
    IContract first;
    IContract second;

    public PairZCB(IContract first, IContract second)
    {
        this.first = first;
        this.second = second;
    }

    // Return inversed contract
    public IContract inverse()
    {
        return new PairZCB(this.first.inverse(), this.second.inverse());
    }

    // Return new contract that combines this contract with $other
    public IContract combineWith(IContract other)
    {
        return new PairZCB(this, other);
    }

    // Return amount of transfered money until given date
    public double moneyAmountUntil(Date date)
    {
        return this.first.moneyAmountUntil(date) + this.second.moneyAmountUntil(date);
    }
}
