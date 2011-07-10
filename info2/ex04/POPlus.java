// Primitive operator PLUS
public class POPlus implements IPrimitiveOperator {

	public POPlus()
	{
	}

	// simplifies the PA type
	public IType simplify(IList<IType> types, int num)
	{
		if (num != 2 || !types.at(0).equals(new Integer()) || !types.at(1).equals(new Integer()))
			throw new AssertionError("Plus PO with invalid arguments");
		return new Integer();
	}
}
