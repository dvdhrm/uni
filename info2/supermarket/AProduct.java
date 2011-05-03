// David Herrmann
// Product with general properties
public abstract class AProduct implements IProduct
{
    // Name of product
    String name;
    // Weight of product in gram
    int weight;
    // Price of product in cents
    int price;

    public AProduct(String name, int weight, int price)
    {
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    // Returns the price per gram in cents
    // This calculates in cent and returns cents. The price is not rounded
    // and odd cent-prices are not returned, instead the price is clamped.
    // This seems appropriate here instead of using a double to be consistent.
    public int pricePerGram()
    {
        return this.price / this.weight;
    }

    // Returns true if the pricePerGram is lower than the given amount of money
    public boolean isPPGLowerThan(int price)
    {
        return this.pricePerGram() < price;
    }

    // Returns true if the pricePerGram of the other product is higher than ours
    // This function would make much more sense implemented as isPPGHigher() but the
    // task explicitely wants to compare this type of function.
    // Maybe this could be renamed to isPPGLowerOrEqualThan().
    public boolean isOtherPPGHigher(IProduct other)
    {
        return other.pricePerGram() > this.pricePerGram();
    }

    // Returns the name of the product
    public String getName()
    {
        return this.name;
    }
}
