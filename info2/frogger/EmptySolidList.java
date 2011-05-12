// Empty solid list
public class EmptySolidList extends ASolid implements ISolidList {
    public EmptySolidList(IScreen screen)
    {
        super(screen);
    }

    // Push element to the top of the list and return new list
    public ISolidList push(ISolid ele)
    {
        return new SolidListEntry(ele, this);
    }

    // Draw this object
    public boolean draw()
    {
        return true;
    }

    // Returns true if this object overlaps with $other
    public boolean overlaps(IDrawable other)
    {
        return false;
    }
}
