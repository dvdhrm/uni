// David Herrmann
// Tests
public class Tests extends de.tuebingen.informatik.Test {
    // Test 1
    @org.junit.Test
    public void testCoffee()
    {
        CoffeeVariant coffee = new CoffeeVariant("someVariant", 10.0);
        CoffeePurchase purchase = new CoffeePurchase(coffee, 10);
        checkExpect(coffee.pricePerPound, 10.0);
        checkExpect(purchase.getBill(), 10.0 * 10);
        purchase = new CoffeePurchase(coffee, 10000);
        checkExpect(purchase.getDiscountBill(), 10 * 10000 * 0.9);
        purchase = new CoffeePurchase(coffee, 20000);
        checkExpect(purchase.getDiscountBill(), 10 * 20000 * 0.75);
    }
}
