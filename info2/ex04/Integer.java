// Integer type
public class Integer implements IType {
	// Compare two types
	public boolean equals(IType type)
	{
		return type instanceof Integer;
	}
}
