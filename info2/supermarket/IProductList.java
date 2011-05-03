// David Herrmann
// List of products
public interface IProductList {
    // Returns the number of elements in the list.
    public int countElements();
    // Return product with highest PPG. If there are
    // multiple products with the same highest PPG, then
    // the first product such product in the list is returned.
    // If the list is empty, InvalidProduct is returned.
    public IProduct getProductWithHighestPPG();
    // Returns the list of the names of all products.
    // The list does not have duplicate entries.
    public IStringList getElementNames();
}
