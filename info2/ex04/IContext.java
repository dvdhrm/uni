// Type context with var->type relations
public interface IContext {
	// Returns the type of variable 'var'
	public IType getVariableType(Variable var);
}
