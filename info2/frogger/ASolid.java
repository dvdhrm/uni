// Common solid elements
public abstract class ASolid extends ADrawable implements ISolid {
    public ASolid(IScreen screen)
    {
        super(screen);
    }

    // Returns true if this element doesn't allow the
    // frog to be at its position.
    // Normal behavior is that an element always allows
    // the frog to be over it. Other classes may overwrite
    // this.
    // If an element kills the frog, it still allows the
    // frog to be at its position so it returns false here.
    // This is just about walls or other elements that
    // block the frog.
    public boolean blocks(Frog frog)
    {
        return false;
    }

    // Returns true if the element contains a start position
    // Standard element is no start place. Overwrite this
    // if you specify a start place.
    public boolean isStart()
    {
        return false;
    }

    // Returns a possible start position
    public ISolid getStart()
    {
        throw new AssertionError("No valid start position");
    }

    // React on time event
    public ISolid onTick(Frog frog)
    {
        // By default all solids are static; override this if not
        return this;
    }

    // Has open targets
    public int todos()
    {
        // By default no open targets
        return 0;
    }

    // Reset and make ready for next frog
    public void reset()
    {
        // By default do nothing
    }
}
