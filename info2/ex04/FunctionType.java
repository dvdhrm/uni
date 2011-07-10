// A function type
public class FunctionType implements IType {
	// Functions of this type convert data from
	// type "source" to type "dest".
	IType source;
	IType dest;

	public FunctionType(IType source, IType dest)
	{
		this.source = source;
		this.dest = dest;
	}

	// Compare two functions
	public boolean equals(FunctionType type)
	{
		if (!this.source.equals(type.source))
			return false;
		if (!this.dest.equals(type.dest))
			return false;
		return true;
	}

	// Compare two types
	public boolean equals(IType type)
	{
		if (type instanceof FunctionType)
			return equals((FunctionType)type);
		else
			return false;
	}

	// simplifies the function type by reducing it by one layer
	// (application)
	public IType simplify(IType type)
	{
		if (type.equals(this.source))
			return this.dest;
		else
			throw new AssertionError("Type violation");
	}
}
