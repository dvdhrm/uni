// an entry in a patien list
public class PairPList implements IPList
{
	// Patient entry
	Patient head;
	// List tail
	IPList tail;

	public PairPList(Patient p, IPList rest)
	{
		this.head = p;
		this.tail = rest;
	}
}
