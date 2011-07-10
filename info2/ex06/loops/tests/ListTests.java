// Test lists extensively with large values
public class ListTests extends de.tuebingen.informatik.Test
{
	// Filters all integers lower than 10
	IFunction<Integer, Boolean> filter = new IFunction<Integer, Boolean>()
	{
		public Boolean apply(Integer value)
		{
			return value < 10;
		}
	};

	// Maps every integer to 0
	IFunction<Integer, Integer> mapping = new IFunction<Integer, Integer>()
	{
		public Integer apply(Integer value)
		{
			return 0;
		}
	};

	// Folder that multiplies all values
	IFolder<Integer, Integer> folder = new IFolder<Integer, Integer>()
	{
		public Integer apply(Integer value, Integer rest)
		{
			return value * rest;
		}
		public Integer start()
		{
			return 1;
		}
	};

	// Folder which returns the first value greater than or equal to 0
	IFolder<Integer, Integer> folder2 = new IFolder<Integer, Integer>()
	{
		public Integer apply(Integer value, Integer rest)
		{
			if (rest == -1)
				return value;
			else
				return rest;
		}
		public Integer start()
		{
			return -1;
		}
	};

	// Visitor which multiplies all values
	IListVisitor<Integer, Integer> visitor = new IListVisitor<Integer, Integer>()
	{
		public Integer visitEmptyList(EmptyList<Integer> list)
		{
			return 1;
		}
		public Integer visitPairList(PairList<Integer> list)
		{
			return list.head * list.tail.visit(this);
		}
	};

	// Visitor which returns the first value greater than or equal to 0
	IListVisitor<Integer, Integer> visitor2 = new IListVisitor<Integer, Integer>()
	{
		public Integer visitEmptyList(EmptyList<Integer> list)
		{
			return -1;
		}
		public Integer visitPairList(PairList<Integer> list)
		{
			int rest = list.tail.visit(this);

			if (rest == -1)
				return list.head;
			else
				return rest;
		}
	};

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
	public void testSort()
	{
		int len = 100;

		checkExpect(createListReversed(len).sort(new IntegerOrder()),
				createList(len));
	}

	@org.junit.Test (expected=java.lang.StackOverflowError.class)
	public void testSort2()
	{
		int len = 1000000;

		checkExpect(createListReversed(len).sort(new IntegerOrder()),
				createList(len));
	}

	@org.junit.Test
	public void testInsert()
	{
		int len = 100;

		checkExpect(createList(len).insert(new IntegerOrder(), len),
							createList(len + 1));
	}

	@org.junit.Test (expected=java.lang.StackOverflowError.class)
	public void testInsert2()
	{
		int len = 1000000;

		checkExpect(createList(len).insert(new IntegerOrder(), len),
							createList(len + 1));
	}

	@org.junit.Test
	public void testFilter()
	{
		int len = 100;

		checkExpect(createList(len).filter(filter),
						createList((len < 10)?len:10));
	}

	@org.junit.Test (expected=java.lang.StackOverflowError.class)
	public void testFilter2()
	{
		int len = 1000000;

		checkExpect(createList(len).filter(filter),
						createList((len < 10)?len:10));
	}

	@org.junit.Test
	public void testMap()
	{
		int len = 100;

		checkExpect(createList(len).map(mapping), createListZero(len));
	}

	@org.junit.Test (expected=java.lang.StackOverflowError.class)
	public void testMap2()
	{
		int len = 1000000;

		checkExpect(createList(len).map(mapping), createListZero(len));
	}

	@org.junit.Test
	public void testAny()
	{
		final int len = 100;

		// Succeeds if value is equal to len-1
		IFunction<Integer, Boolean> f = new IFunction<Integer, Boolean>()
		{
			public Boolean apply(Integer value)
			{
				return value == len - 1;
			}
		};

		checkExpect(createList(len).any(f), true);
	}

	@org.junit.Test (expected=java.lang.StackOverflowError.class)
	public void testAny2()
	{
		final int len = 1000000;

		// Succeeds if value is equal to len-1
		IFunction<Integer, Boolean> f = new IFunction<Integer, Boolean>()
		{
			public Boolean apply(Integer value)
			{
				return value == len - 1;
			}
		};

		checkExpect(createList(len).any(f), true);
	}

	@org.junit.Test
	public void testFold()
	{
		int len = 100;

		checkExpect(createList(len).fold(folder), 0);
		checkExpect(createList(len).fold(folder2), len - 1);
		checkExpect(createListReversed(len).fold(folder2), 0);
	}

	@org.junit.Test (expected=java.lang.StackOverflowError.class)
	public void testFold2()
	{
		int len = 1000000;

		checkExpect(createList(len).fold(folder), 0);
		checkExpect(createList(len).fold(folder2), len - 1);
		checkExpect(createListReversed(len).fold(folder2), 0);
	}

	@org.junit.Test
	public void testVisit()
	{
		int len = 100;

		checkExpect(createList(len).visit(visitor), 0);
		checkExpect(createList(len).visit(visitor2), len - 1);
		checkExpect(createListReversed(len).visit(visitor2), 0);
	}

	@org.junit.Test (expected=java.lang.StackOverflowError.class)
	public void testVisit2()
	{
		int len = 1000000;

		checkExpect(createList(len).visit(visitor), 0);
		checkExpect(createList(len).visit(visitor2), len - 1);
		checkExpect(createListReversed(len).visit(visitor2), 0);
	}
}
