// Variablen
public class Variable extends ALambdaTerm {
    String name; //Name der Variablen
    
    Variable(String name){
        this.name = name ;
    }

    public boolean equals(Variable var)
    {
    	return this.name.equals(var.name);
    }

    // Visitor
	public <T> T visit(IVisitor<T> visitor)
	{
		return visitor.visitVariable(this);
	}
}
