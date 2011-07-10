// abstract class for common lambda functions
public abstract class ALambdaTerm implements ILambdaTerm {
	// Return type of lambda term
	public IType getType()
	{
		TypeVisitor vis = new TypeVisitor(new EmptyContext());
		return visit(vis);
	}
}
