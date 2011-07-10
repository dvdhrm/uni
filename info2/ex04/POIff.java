// Primitive Operator that checks whether argument is true
// and then performs either argument
// Iff (boolean) (then) (else)
// The whole expressen has the type of (then).
// (else) must have the same type as (then).
public class POIff implements IPrimitiveOperator {
	public POIff()
	{
	}

	// simplifies the PA type
	public IType simplify(IList<IType> types, int num)
	{
		if (num != 3 || !types.at(0).equals(new Boolean()) || !types.at(1).equals(types.at(2)))
			throw new AssertionError("Iff PO with invalid arguments");
		return types.at(1);
	}
}
