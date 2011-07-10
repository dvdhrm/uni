// lambda calculus visitor
public interface IVisitor<T> {
	// Visitor functions
	public T visitAbstraction(Abstraction var);
	public T visitApplication(Application var);
	public T visitPrim(PrimitiveApplication var);
	public T visitVariable(Variable var);
	public T visitBoolean(BooleanValue var);
	public T visitInt(IntValue var);
	public <Q> IList<T> visitPrimEnd(EmptyList<Q> var);
	public <Q> IList<T> visitPrimOp(PairList<Q> var);
}
