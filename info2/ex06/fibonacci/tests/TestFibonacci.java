// Test fibonacci factory
public class TestFibonacci extends de.tuebingen.informatik.Test
{
	@org.junit.Test
	public void testFibonacci()
	{
		checkExpect(Factory.fibonacci(-2), 0);
		checkExpect(Factory.fibonacci(-1), 0);
		checkExpect(Factory.fibonacci(0), 0);
		checkExpect(Factory.fibonacci(1), 1);
		checkExpect(Factory.fibonacci(2), 1);
		checkExpect(Factory.fibonacci(3), 2);
		checkExpect(Factory.fibonacci(4), 3);
		checkExpect(Factory.fibonacci(5), 5);
		checkExpect(Factory.fibonacci(6), 8);
		checkExpect(Factory.fibonacci(20), 6765);
	}

	@org.junit.Test
	public void testFibonacciList()
	{
		IList<Integer> empty = new EmptyList<Integer>();
		checkExpect(Factory.fibonacciList(-2), empty);
		checkExpect(Factory.fibonacciList(-1), empty);
		checkExpect(Factory.fibonacciList(0), new PairList<Integer>(0,
			empty));
		checkExpect(Factory.fibonacciList(1), new PairList<Integer>(0,
			new PairList<Integer>(1, empty)));
		checkExpect(Factory.fibonacciList(2), new PairList<Integer>(0,
			new PairList<Integer>(1,
			new PairList<Integer>(1, empty))));
		checkExpect(Factory.fibonacciList(3), new PairList<Integer>(0,
			new PairList<Integer>(1,
			new PairList<Integer>(1,
			new PairList<Integer>(2, empty)))));
		checkExpect(Factory.fibonacciList(4), new PairList<Integer>(0,
			new PairList<Integer>(1,
			new PairList<Integer>(1,
			new PairList<Integer>(2,
			new PairList<Integer>(3, empty))))));
		checkExpect(Factory.fibonacciList(5), new PairList<Integer>(0,
			new PairList<Integer>(1,
			new PairList<Integer>(1,
			new PairList<Integer>(2,
			new PairList<Integer>(3,
			new PairList<Integer>(5, empty)))))));
		checkExpect(Factory.fibonacciList(6), new PairList<Integer>(0,
			new PairList<Integer>(1,
			new PairList<Integer>(1,
			new PairList<Integer>(2,
			new PairList<Integer>(3,
			new PairList<Integer>(5,
			new PairList<Integer>(8, empty))))))));
	}
}
