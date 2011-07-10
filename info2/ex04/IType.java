// Representation of types in lambda calculus
public interface IType {
	// Compare two types
	// This uses the "instanceof" operation to avoid
	// declaring isInteger(), isBoolean(), isFunction()
	// for all inheriting types.
	public boolean equals(IType type);
}
