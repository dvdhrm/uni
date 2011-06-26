// A single station in a line
public class Station
{
	// Name of the station
	private String name;

	// Next and previous stations
	private Station prev;
	private Station next;

	// Construct every station as single unlinked station
	public Station(String name)
	{
		this.name = name;
		this.prev = null;
		this.next = null;
	}

	// Get name
	public String getName()
	{
		return this.name;
	}

	// Set/Get next
	public void setNext(Station s)
	{
		this.next = s;
	}

	public Station getNext()
	{
		return this.next;
	}

	// Set/Get prev
	public void setPrev(Station s)
	{
		this.prev = s;
	}

	public Station getPrev()
	{
		return this.prev;
	}

	// Add station next to this
	public void addNext(Station s)
	{
		s.setNext(this.next);
		s.setPrev(this);
		this.next = s;
	}

	// Add station previous to this
	public void addPrev(Station s)
	{
		s.setPrev(this.prev);
		s.setNext(this);
		this.prev = s;
	}

	// Add station at tail of line
	public void addTail(Station s)
	{
		if (this.next == null)
			addNext(s);
		else
			this.next.addTail(s);
	}

	// Add station at front of line
	public void addFront(Station s)
	{
		if (this.prev == null)
			addPrev(s);
		else
			this.prev.addFront(s);
	}
}
