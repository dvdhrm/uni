// A folding function
public interface IFolder<T, S>
{
	// Fold current value \value with dump \dump
	public S apply(T value, S dump);

	// Return start value
	public S start();
}
