// David Herrmann
// Tests
public class Tests extends de.tuebingen.informatik.Test {
    IProduct juice = new Juice("Sprudel", 10, 500, "medium", Juice.Type.GLAS);
    IProduct coffee = new Coffee("Lecker", 10, 100, true);
    IProduct ice = new IceCream("Lecker", 10, 100, "vanilla");
    IProductList list1 = new ProductPair(juice, new ProductPair(coffee, new ProductPair(ice, new EmptyProductPair())));
    IProductList list2 = new EmptyProductPair();
    IProductList list3 = new ProductPair(juice,
        new ProductPair(coffee,
        new ProductPair(coffee,
        new ProductPair(coffee,
        new ProductPair(coffee,
        new ProductPair(ice,
        new EmptyProductPair()))))));
    // Test 1
    @org.junit.Test
    public void test1()
    {
        checkExpect(juice.pricePerGram(), 500 / 10);
        checkExpect(juice.isPPGLowerThan(1000), true);
        checkExpect(juice.isPPGLowerThan(0), false);
        checkExpect(juice.isOtherPPGHigher(coffee), false);
        checkExpect(juice.isOtherPPGHigher(juice), false);
        checkExpect(coffee.isOtherPPGHigher(juice), true);
        checkExpect(coffee.isOtherPPGHigher(ice), false);
    }
    // Test 2
    @org.junit.Test
    public void test2()
    {
        checkExpect(list1.countElements(), 3);
        checkExpect(list1.getProductWithHighestPPG(), juice);
        checkExpect(list2.countElements(), 0);
        checkExpect(list2.getProductWithHighestPPG(), new InvalidProduct());
        checkExpect(list3.countElements(), 6);
        checkExpect(list3.getProductWithHighestPPG(), juice);
        checkExpect(list2.getElementNames(), new EmptyStringPair());
        checkExpect(list3.getElementNames().contains("asdf"), false);
        checkExpect(list3.getElementNames().contains("Sprudel"), true);
    }
}
