// Test lists extensively with large values
public class ListTests2 extends de.tuebingen.informatik.Test
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

	// Visitor which multiplies all values but imperatively
	IListVisitor<Integer, Integer> visitorI2 = new IListVisitor<Integer, Integer>()
	{
		public Integer visitEmptyList(EmptyList<Integer> list)
		{
			return 1;
		}
		public Integer visitPairList(PairList<Integer> list)
		{
			Integer ret = new EmptyList<Integer>().visit(this);
			IList<Integer> iter = list.reverse();

			while (iter instanceof PairList) {
				PairList<Integer> t = (PairList<Integer>)iter;
				ret = t.head * ret;
				iter = t.tail;
			}

			return ret;
		}
	};

	// Visitor which multiplies all values but imperatively
	IListVisitor<Integer, Integer> visitorI3 = new IListVisitor<Integer, Integer>()
	{
		public Integer visitEmptyList(EmptyList<Integer> list)
		{
			return 1;
		}
		public Integer visitPairList(PairList<Integer> list)
		{
			Integer ret = new EmptyList<Integer>().visit(this);

			for (Integer v : list.reverse())
				ret = v * ret;

			return ret;
		}
	};

	// Visitor which returns the first value greater than or equal to 0 imperatively
	IListVisitor<Integer, Integer> visitorII2 = new IListVisitor<Integer, Integer>()
	{
		public Integer visitEmptyList(EmptyList<Integer> list)
		{
			return -1;
		}
		public Integer visitPairList(PairList<Integer> list)
		{
			Integer ret = new EmptyList<Integer>().visit(this);
			IList<Integer> iter = list.reverse();

			while (iter instanceof PairList) {
				PairList<Integer> t = (PairList<Integer>)iter;

				if (ret == -1)
					ret = t.head;

				iter = t.tail;
			}

			return ret;
		}
	};

	// Visitor which returns the first value greater than or equal to 0 imperatively
	IListVisitor<Integer, Integer> visitorII3 = new IListVisitor<Integer, Integer>()
	{
		public Integer visitEmptyList(EmptyList<Integer> list)
		{
			return -1;
		}
		public Integer visitPairList(PairList<Integer> list)
		{
			Integer ret = new EmptyList<Integer>().visit(this);

			for (Integer v : list.reverse()) {
				if (ret == -1)
					ret = v;
			}

			return ret;
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

		checkExpect(createListReversed(len).sort2(new IntegerOrder()),
				createList(len));
		checkExpect(createListReversed(len).sort3(new IntegerOrder()),
				createList(len));

		len = 10000;
		createListReversed(len).sort2(new IntegerOrder());
		createListReversed(len).sort3(new IntegerOrder());
	}

	@org.junit.Test
	public void testInsert()
	{
		int len = 100;

		checkExpect(createList(len).insert2(new IntegerOrder(), len),
					createList(len + 1));
		checkExpect(createList(len).insert3(new IntegerOrder(), len),
					createList(len + 1));

		len = 10000;
		createList(len).insert2(new IntegerOrder(), len);
		createList(len).insert3(new IntegerOrder(), len);
	}

	@org.junit.Test
	public void testFilter()
	{
		int len = 100;

		checkExpect(createList(len).filter2(filter),
						createList((len < 10)?len:10));
		checkExpect(createList(len).filter3(filter),
						createList((len < 10)?len:10));

		len = 10000;
		createList(len).filter2(filter);
		createList(len).filter3(filter);
	}

	@org.junit.Test
	public void testMap()
	{
		int len = 100;

		checkExpect(createList(len).map2(mapping), createListZero(len));
		checkExpect(createList(len).map3(mapping), createListZero(len));

		len = 10000;
		createList(len).map2(mapping);
		createList(len).map3(mapping);
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

		checkExpect(createList(len).any2(f), true);
		checkExpect(createList(len).any3(f), true);

		int len2 = 10000;
		createList(len).any2(f);
		createList(len).any3(f);
	}

	@org.junit.Test
	public void testFold()
	{
		int len = 100;

		checkExpect(createList(len).fold2(folder), 0);
		checkExpect(createList(len).fold3(folder), 0);
		checkExpect(createList(len).fold2(folder2), len - 1);
		checkExpect(createList(len).fold3(folder2), len - 1);
		checkExpect(createListReversed(len).fold2(folder2), 0);
		checkExpect(createListReversed(len).fold3(folder2), 0);

		len = 10000;
		createList(len).fold2(folder);
		createList(len).fold3(folder);
	}

	@org.junit.Test
	public void testVisit()
	{
		int len = 100;

		checkExpect(createList(len).visit(visitorI2), 0);
		checkExpect(createList(len).visit(visitorI3), 0);
		checkExpect(createList(len).visit(visitorII2), len - 1);
		checkExpect(createList(len).visit(visitorII3), len - 1);
		checkExpect(createListReversed(len).visit(visitorII2), 0);
		checkExpect(createListReversed(len).visit(visitorII3), 0);

		len = 10000;
		createList(len).visit(visitorI2);
		createList(len).visit(visitorI3);
		createListReversed(len).visit(visitorII2);
		createListReversed(len).visit(visitorII3);
	}
}
