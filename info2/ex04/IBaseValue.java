// Basiswerte
// Achtung: Ein Interface kann ein anderes Interface nicht "implementieren",
//          sondern nur "erweitern".
public interface IBaseValue extends ILambdaTerm {
	// Returns the type of this base value
	public IType getBaseType();
}
