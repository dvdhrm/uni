// a rail line
public interface ILine
{
	// Add new station at tail of line
	public ILine addTail(String name);
	public ILine addTailHelper(String name, ILine passed);
}
