// Factory class
public class Factory
{
	// Compute \num'th fibonacci number
	// If \num is smaller than 1 then 0 is returned.
	public static int fibonacci(int num)
	{
		int prev = 0; // previous fibonacci number
		int fib = 1; // current fibonacci number
		int t; // temporary buffer

		if (num < 1)
			return 0;

		for (; num > 1; --num) {
			t = fib;
			fib += prev;
			prev = t;
		}

		return fib;
	}

	// Return list of the fibonacci numbers 0 till \num
	// If \num is smaller 0, then an empty list is returned.
	public static IList<Integer> fibonacciList(int num)
	{
		IList<Integer> list = new EmptyList<Integer>();

		for (; num >= 0; --num) {
			list = new PairList<Integer>(fibonacci(num), list);
		}

		return list;
	}
}
