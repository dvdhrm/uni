// David Herrmann
// Coffee Variant Representation
public class CoffeeVariant
{
    // What kind of coffee?
    String kind;
    // Price per pound
    double pricePerPound;

    public CoffeeVariant(String kind, double price)
    {
        this.kind = kind;
        this.pricePerPound = price;
    }

    // returns the price per pound
    public double getPrice()
    {
        return this.pricePerPound;
    }
}
