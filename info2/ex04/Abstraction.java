// Abstraktion
public class Abstraction extends ALambdaTerm {
    Variable parameter; //Parameter
    IType type; // Parameter type
    ILambdaTerm body; //Rumpf
    
    Abstraction(Variable parameter, IType type, ILambdaTerm body){
        this.parameter = parameter;
        this.type = type;
        this.body = body;}

    // Visitor
	public <T> T visit(IVisitor<T> visitor)
	{
		return visitor.visitAbstraction(this);
	}
}
