// Ganzzahlige Werte
public class IntValue extends ALambdaTerm implements IBaseValue {
    int value; //Wert, auch der wird eingeschachtelt
    
    IntValue(int value){
        this.value = value;
    }

    // Returns the type of this base value
	public IType getBaseType()
	{
		return new Integer();
	}

	// Visitor
	public <T> T visit(IVisitor<T> visitor)
	{
		return visitor.visitInt(this);
	}
}
