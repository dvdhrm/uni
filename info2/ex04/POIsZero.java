// Primitive Operator that checks whether argument is zero
public class POIsZero implements IPrimitiveOperator {

	public POIsZero()
	{
	}

	// simplifies the PA type
	public IType simplify(IList<IType> types, int num)
	{
		if (num != 1 || !types.at(0).equals(new Integer()))
			throw new AssertionError("IsZero PO with invalid arguments");
		return new Boolean();
	}
}
