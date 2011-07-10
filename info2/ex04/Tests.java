// Tests
public class Tests extends de.tuebingen.informatik.Test {
	IType i1 = new Integer();
	IType b1 = new Boolean();
	IType f1 = new FunctionType(i1, b1);
	IType f2 = new FunctionType(f1, i1);

	@org.junit.Test
	public void test1()
	{
		checkExpect(new Integer().equals(new Integer()), true);
		checkExpect(new Integer().equals(new Boolean()), false);
		checkExpect(i1.equals(i1), true);
		checkExpect(i1.equals(new Integer()), true);
		checkExpect(new Integer().equals(i1), true);
		checkExpect(i1.equals(b1), false);
		checkExpect(b1.equals(b1), true);
		checkExpect(f1.equals(f1), true);
		checkExpect(f1.equals(f2), false);
		checkExpect(f1.equals(b1), false);
		checkExpect(i1.equals(f2), false);
	}

	ILambdaTerm l1 = new IntValue(5);
	ILambdaTerm l2 = new BooleanValue(true);
	Variable l3 = new Variable("a");
	ILambdaTerm l4 = new Abstraction(l3, new Integer(), l2);
	ILambdaTerm l5 = new Application(l4, l1);
	ILambdaTerm l6 = new Abstraction(l3, new Boolean(), l5);

	@org.junit.Test
	public void test2()
	{
		checkExpect(l1.getType(), new Integer());
		checkExpect(l2.getType(), new Boolean());
		checkExpect(l4.getType(), new FunctionType(new Integer(), new Boolean()));
		checkExpect(l5.getType(), new Boolean());
		checkExpect(l6.getType(), new FunctionType(new Boolean(), new Boolean()));
	}

	ILambdaTerm l7 = new PrimitiveApplication(new POPlus(), new PairList<ILambdaTerm>(l1, new PairList<ILambdaTerm>(l1, new EmptyList<ILambdaTerm>())));
	ILambdaTerm l9 = new PrimitiveApplication(new POMinus(), new PairList<ILambdaTerm>(l1, new PairList<ILambdaTerm>(l1, new EmptyList<ILambdaTerm>())));
	ILambdaTerm l10 = new PrimitiveApplication(new POIsZero(), new PairList<ILambdaTerm>(l1, new EmptyList<ILambdaTerm>()));
	ILambdaTerm l8 = new PrimitiveApplication(new POIff(), new PairList<ILambdaTerm>(l5, new PairList<ILambdaTerm>(l1, new PairList<ILambdaTerm>(l1, new EmptyList<ILambdaTerm>()))));

	@org.junit.Test
	public void test3()
	{
		checkExpect(l7.getType(), new Integer());
		checkExpect(l8.getType(), new Integer());
		checkExpect(l9.getType(), new Integer());
		checkExpect(l10.getType(), new Boolean());
	}
}
