// VIsitor to get lambda types
public class TypeVisitor implements IVisitor<IType> {
	// current context
	IContext context;

	public TypeVisitor(IContext context)
	{
		this.context = context;
	}
	
	public IType visitAbstraction(Abstraction var)
	{
		// We push new variable on top of our context instead of
		// replacing possible existing instances in the context.
		// This is possible because the IContext type searches from
		// the beginning of the context and hence always returns
		// the correct value here.
		return new FunctionType(var.type, var.body.visit(new TypeVisitor(new PairContext(var.parameter, var.type, this.context))));
	}
	public IType visitApplication(Application var)
	{
		return ((FunctionType)var.e0.visit(this)).simplify(var.e1.visit(this));
	}
	public IType visitPrim(PrimitiveApplication var)
	{
		IList<IType> types = var.operands.visit(this);
		return var.operator.simplify(types, types.length());
	}
	public IType visitVariable(Variable var)
	{
		return this.context.getVariableType(var);
	}
	public IType visitBoolean(BooleanValue var)
	{
		return var.getBaseType();
	}
	public IType visitInt(IntValue var)
	{
		return var.getBaseType();
	}
	public <Q> IList<IType> visitPrimEnd(EmptyList<Q> var)
	{
		return new EmptyList<IType>();
	}
	public <Q> IList<IType> visitPrimOp(PairList<Q> var)
	{
		return new PairList<IType>(((ILambdaTerm)var.head).visit(this), var.tail.visit(this));
	}
}
