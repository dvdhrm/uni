// David Herrmann
// Tests
public class Tests extends de.tuebingen.informatik.Test {
    // Datum 11. 10. 2010
    Date date = new Date(2010, 10, 11);
    // Test 1
    @org.junit.Test
    public void test1()
    {
        checkExpect(this.date.isEarlierThan(new Date(2010, 11, 10)), true);
    }
    // Test 2
    @org.junit.Test
    public void test2()
    {
        ZeroCouponBond zcb = new ZeroCouponBond(10.0, this.date);
        checkExpect(zcb.inverse().moneyAmountUntil(this.date), -10.0);
        IContract contract = zcb.inverse().combineWith(new ZeroCouponBond(20.0, this.date));
        checkExpect(contract.moneyAmountUntil(this.date), 10.0);
        contract = contract.inverse().combineWith(new ZeroCouponBond(20.0, new Date(3011, 10, 11)));
        checkExpect(contract.moneyAmountUntil(this.date), -10.0);
    }
}
