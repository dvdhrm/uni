// David Herrmann
// Tests
public class ContractTests extends de.tuebingen.informatik.Test {
	IObservable<Integer> o1 = new ConstObservable<Integer>(5);

	// Test 1
	@org.junit.Test
	public void testConstObservable()
	{
		checkExpect(o1.at(null), 5);
	}
}
