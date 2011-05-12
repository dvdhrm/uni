// Entry in solid list
public class SolidListEntry extends ASolid implements ISolidList {
    // entry
    ISolid head;
    // tail of list
    ISolidList tail;

    public SolidListEntry(ISolid head, ISolidList tail)
    {
        super(tail.getScreen());
        this.head = head;
        this.tail = tail;
    }

    // Push element to the top of the list and return new list
    public ISolidList push(ISolid ele)
    {
        return new SolidListEntry(ele, this);
    }

    // Draw this object
    public boolean draw()
    {
        return this.tail.draw() && this.head.draw();
    }

    // Returns true if this object overlaps with $other
    public boolean overlaps(IDrawable other)
    {
        return this.head.overlaps(other) || this.tail.overlaps(other);
    }

    // Returns true if this element kills the frog
    public boolean kills(Frog frog)
    {
        return this.head.kills(frog) || this.tail.kills(frog);
    }

    // Returns true if this element doesn't allow the
    // frog to be at its position.
    public boolean blocks(Frog frog)
    {
        return this.head.blocks(frog) || this.tail.blocks(frog);
    }
}
