// Boolean type
public class Boolean implements IType {
	// Compare two types
	public boolean equals(IType type)
	{
		return type instanceof Boolean;
	}
}
