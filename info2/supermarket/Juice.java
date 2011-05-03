// David Herrmann
// Kurzbeschreibung
public class Juice extends AFlavoredProduct {
    // bottle type
    enum Type { GLAS, PLASTIC, TETRAPACK }
    Type type;

    public Juice(String name, int weight, int price, String flavor, Type type)
    {
        super(name, weight, price, flavor);
        this.type = type;
    }
}
