// Kurzbeschreibung
public class BooleanValue extends ALambdaTerm implements IBaseValue{
    boolean value; // Wert
    
    BooleanValue(boolean value){
        this.value = value; //ist das nicht ein schönes Beispiel von Realsatire? ;-)
    }

    // Returns the type of this base value
	public IType getBaseType()
	{
		return new Boolean();
	}

	// Visitor
	public <T> T visit(IVisitor<T> visitor)
	{
		return visitor.visitBoolean(this);
	}
}
