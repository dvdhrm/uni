// Test list hashes
public class HashTest extends de.tuebingen.informatik.Test
{
	// hash function for integer lists
	private IHashFunction<IList<Integer>> h = new ListHash<Integer>();
	// empty list of integers
	private IList<Integer> empty = new EmptyList<Integer>();

	// Create long list with values 0 till n-1 but reversed
	public IList<Integer> createListReversed(int max)
	{
		IList<Integer> ret = new EmptyList<Integer>();

		for (int i = 0; i < max; ++i)
			ret = new PairList<Integer>(i, ret);

		return ret;
	}

	// Create long list with values 0 till n-1
	public IList<Integer> createList(int max)
	{
		IList<Integer> ret = new EmptyList<Integer>();

		while (max > 0)
			ret = new PairList<Integer>(--max, ret);

		return ret;
	}

	// Same as createList() but sets all values to 0
	public IList<Integer> createListZero(int max)
	{
		IList<Integer> ret = new EmptyList<Integer>();

		while (max-- > 0)
			ret = new PairList<Integer>(0, ret);

		return ret;
	}

	@org.junit.Test
	public void testSame()
	{
		checkExpect(h.same(empty, new EmptyList<Integer>()), true);
		checkExpect(h.same(empty, empty), true);
		checkExpect(h.same(createList(10), createList(10)), true);
		checkExpect(h.same(createList(10), createListReversed(10)), false);
		checkExpect(h.same(createList(10), createList(11)), false);
		checkExpect(h.same(empty, createListZero(1)), false);
		checkExpect(h.same(createListZero(1), createListZero(1)), true);
	}

	@org.junit.Test
	public void testHash()
	{
		checkExpect(h.hashCode(empty), h.hashCode(new EmptyList<Integer>()));
		checkExpect(h.hashCode(empty), h.hashCode(empty));
		checkExpect(h.hashCode(createList(10)), h.hashCode(createList(10)));
		checkExpect(h.hashCode(createList(10)) == h.hashCode(createListReversed(10)), false);
		checkExpect(h.hashCode(createList(10)) == h.hashCode(createList(11)), false);
		checkExpect(h.hashCode(empty) == h.hashCode(createListZero(1)), false);
		checkExpect(h.hashCode(createListZero(1)), h.hashCode(createListZero(1)));
	}
}
