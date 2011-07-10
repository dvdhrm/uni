// Terme des angewandten Lambda-Kalküls
public interface ILambdaTerm {
	// Return type of lambda term
	public IType getType();

	// Visitor
	public <T> T visit(IVisitor<T> visitor);
}
