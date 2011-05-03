// David Herrmann
// Coffee product
public class Coffee extends AProduct {
    // normal or decoffeinated?
    boolean normal;

    public Coffee(String name, int weight, int price, boolean normal)
    {
        super(name, weight, price);
        this.normal = normal;
    }
}
