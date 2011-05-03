// David Herrmann
// Coffee Purchase
public class CoffeePurchase
{
    // What kind of coffee?
    CoffeeVariant kind;
    // amount of coffee in pounds
    double pounds;

    public CoffeePurchase(CoffeeVariant kind, double pounds)
    {
        this.kind = kind;
        this.pounds = pounds;
    }

    // returns the cost of the purchase
    public double getBill()
    {
        return this.pounds * this.kind.getPrice();
    }

    // returns the cost of the purchase with bulk discount in mind
    public double getDiscountBill()
    {
        if (this.pounds >= 20000.0)
            return this.getBill() * 0.75;
        else if (this.pounds >= 5000.0)
            return this.getBill() * 0.9;
        else
            return this.getBill();
    }
}
