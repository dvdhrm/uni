// David Herrmann
// Single product which is available in supermarket
public interface IProduct {
    // Returns the price per gram in cents (this is zero for invalid products)
    public int pricePerGram();

    // Returns true if the pricePerGram is lower than the given amount of money
    public boolean isPPGLowerThan(int price);

    // Returns true if the pricePerGram of the other product is higher than ours
    public boolean isOtherPPGHigher(IProduct other);

    // Returns the name of the product
    public String getName();
}
