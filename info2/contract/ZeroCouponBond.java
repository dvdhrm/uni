// David Herrmann
// A single Zero Coupon Bond
public class ZeroCouponBond implements IContract
{
    // Amount of money that is transferred to the holder (may be negative)
    double money;
    // Date of money transfer
    Date date;

    public ZeroCouponBond(double money, Date date)
    {
        this.money = money;
        this.date = date;
    }

    // Return a new ZCB with inversed transfer
    public IContract inverse()
    {
        return new ZeroCouponBond(-this.money, this.date);
    }

    // Combine this contract with \other
    public IContract combineWith(IContract other)
    {
        return new PairZCB(this, other);
    }

    // Returns amount of money that is transfered to the holder
    // until the given date
    public double moneyAmountUntil(Date date)
    {
        if (date.isEarlierThan(this.date))
            return 0.0;
        else
            return this.money;
    }
}
