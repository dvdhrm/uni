// David Herrmann
// Kurzbeschreibung
public abstract class AFlavoredProduct extends AProduct {
    // Product flavor
    String flavor;

    public AFlavoredProduct(String name, int weight, int price, String flavor)
    {
        super(name, weight, price);
        this.flavor = flavor;
    }
}
