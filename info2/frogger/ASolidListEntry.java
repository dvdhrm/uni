// Abstract entry in solid list
public abstract class ASolidListEntry implements ISolidList {
    // entry
    ISolid head;
    // tail of list
    ISolidList tail;

    public ASolidListEntry(ISolid head, ISolidList tail)
    {
        this.head = head;
        this.tail = tail;
    }

    public ASolidListEntry(IScreen screen, ISolid head)
    {
        this.head = head;
        this.tail = new EmptySolidList(screen);
    }

    protected ASolidListEntry(IScreen screen)
    {
        this.tail = new EmptySolidList(screen);
        // head is set by inheriting class
    }

    // Push element (Only compatible with SolidListEntry)
    public ISolidList push(ISolid ele)
    {
        return new SolidListEntry(this.head, this.tail.push(ele));
    }

    // Draw this object
    public boolean draw()
    {
        return this.tail.draw() && this.head.draw();
    }

    // Returns the screen
    public IScreen getScreen()
    {
       return this.tail.getScreen();
    }

    // Returns true if this object overlaps with $other
    public boolean overlaps(IDrawable other)
    {
        return this.head.overlaps(other) || this.tail.overlaps(other);
    }

    // Returns true if this element doesn't allow the
    // frog to be at its position.
    public boolean blocks(Frog frog)
    {
        return this.head.blocks(frog) || this.tail.blocks(frog);
    }

    // Returns true if the element contains a start position
    public boolean isStart()
    {
        return this.head.isStart() || this.tail.isStart();
    }

    // Returns a possible start position
    public ISolid getStart()
    {
        if (this.head.isStart())
            return this.head.getStart();
        else
            return this.tail.getStart();
    }

    // React on time event
    public ISolidList onTick(Frog frog)
    {
        this.head = this.head.onTick(frog);
        this.tail = this.tail.onTick(frog);
        return this;
    }

    // Has open targets
    public int todos()
    {
        return this.head.todos() + this.tail.todos();
    }

    // Reset and make ready for next frog
    public void reset()
    {
        this.head.reset();
        this.tail.reset();
    }
}
