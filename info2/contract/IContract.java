// David Herrmann
// A contract between the holder and another party which
// consists of one to many zero coupon bonds
//
// A contract is either a single ZCB or a combined contract. A combined
// contract consists of two contracts which both can be again either a single
// ZCB or a combined contract...
// A contract can be modified in the following ways:
//  - inversed: All money transfers are inversed
//  - combined: Creates a new contract that consists of two contracts combined
//  - calculated: Calculates the amount of money that is transfered until a specific date
public interface IContract
{
    // Return inversed contract
    public IContract inverse();
    // Return new contract that combines this contract with $other
    public IContract combineWith(IContract other);
    // Return amount of transfered money until given date
    public double moneyAmountUntil(Date date);
}
