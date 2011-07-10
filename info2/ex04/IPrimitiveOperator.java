// Primitiver Operator
public interface IPrimitiveOperator {
	// simplifies the PA type and performs type checks
	// "types" shall contain the types of all parameters
	// "num" shall be the number of arguments
	public IType simplify(IList<IType> types, int num);
}
