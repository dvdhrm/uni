// Primitive Operator minus
public class POMinus implements IPrimitiveOperator {
	
	public POMinus()
	{
	}

	// simplifies the PA type
	public IType simplify(IList<IType> types, int num)
	{
		if (num != 2 || !types.at(0).equals(new Integer()) || !types.at(1).equals(new Integer()))
			throw new AssertionError("Minus PO with invalid arguments");
		return new Integer();
	}
}
