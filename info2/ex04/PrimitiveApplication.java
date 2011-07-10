// Primitive application
public class PrimitiveApplication extends ALambdaTerm{
    IPrimitiveOperator operator; //Operator
    IList<ILambdaTerm> operands; //Operanden
     
    PrimitiveApplication(IPrimitiveOperator operator, IList<ILambdaTerm> operands){
        this.operator = operator;
        this.operands = operands;
    }

    // Visitor
	public <T> T visit(IVisitor<T> visitor)
	{
		return visitor.visitPrim(this);
	}
}
