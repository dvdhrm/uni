// Empty solid list
public class EmptySolidList extends ASolid implements ISolidList {
    public EmptySolidList(IScreen screen)
    {
        super(screen);
    }

    // Push element (This is only compatible with SolidListEntry, yet)
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

    // React on time event
    public ISolidList onTick(Frog frog)
    {
        return this;
    }
}
