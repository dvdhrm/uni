// David Herrmann
// Empty list tail of product list
public class EmptyProductPair implements IProductList {
    public EmptyProductPair()
    {
    }

    // Returns the number of elements in the list.
    public int countElements()
    {
        return 0;
    }

    // Return product with highest PPG. If there are
    // multiple products with the same highest PPG, then
    // the first product such product in the list is returned.
    // If the list is empty, InvalidProduct is returned.
    public IProduct getProductWithHighestPPG()
    {
        return new InvalidProduct();
    }

    // Returns the list of the names of all products.
    // The list does not have duplicate entries.
    public IStringList getElementNames()
    {
        return new EmptyStringPair();
    }
}
