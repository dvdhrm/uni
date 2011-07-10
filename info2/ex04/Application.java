// Applikation
public class Application extends ALambdaTerm{

    ILambdaTerm e0; //Operator
    ILambdaTerm e1; //Operand
    
    Application(ILambdaTerm e0, ILambdaTerm e1){
        this.e0 = e0;
        this.e1 = e1;
    }
        
    // Visitor
	public <T> T visit(IVisitor<T> visitor)
	{
		return visitor.visitApplication(this);
	}
}
