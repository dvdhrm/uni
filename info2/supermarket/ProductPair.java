// David Herrmann
// Product list entry
public class ProductPair implements IProductList {
    // List entry
    IProduct product;
    // List tail
    IProductList tail;

    public ProductPair(IProduct product, IProductList tail)
    {
        this.product = product;
        this.tail = tail;
    }

    // Returns the number of elements in the list.
    public int countElements()
    {
        return 1 + this.tail.countElements();
    }

    // Return product with highest PPG. If there are
    // multiple products with the same highest PPG, then
    // the first product such product in the list is returned.
    // If the list is empty, InvalidProduct is returned.
    public IProduct getProductWithHighestPPG()
    {
        if (this.product.isOtherPPGHigher(this.tail.getProductWithHighestPPG()))
            return this.tail.getProductWithHighestPPG();
        else
            return this.product;
    }

    // Returns the list of the names of all products.
    // The list does not have duplicate entries.
    public IStringList getElementNames()
    {
        if (this.tail.getElementNames().contains(this.product.getName()))
            return this.tail.getElementNames();
        else
            return new StringPair(this.product.getName(), this.tail.getElementNames());
    }
}
