// Context entry
public class PairContext implements IContext {
	// Variable Type relation
	Variable var;
	IType type;

	// Rest of context
	IContext rest;

	public PairContext(Variable var, IType type, IContext rest)
	{
		this.var = var;
		this.type = type;
		this.rest = rest;
	}

	// Returns the type of variable 'var'
	public IType getVariableType(Variable var)
	{
		if (this.var.equals(var))
			return this.type;
		else
			return this.rest.getVariableType(var);
	}
}
