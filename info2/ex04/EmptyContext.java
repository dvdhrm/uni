// Empty context
public class EmptyContext implements IContext {
	public EmptyContext()
	{
	}

	// Returns the type of variable 'var'
	public IType getVariableType(Variable var)
	{
		throw new AssertionError("Cannot find variable in type context");
	}
}
