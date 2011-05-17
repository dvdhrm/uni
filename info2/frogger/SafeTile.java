// Safe area tile
public class SafeTile extends ASolid {
    // Is this a start position?
    boolean isStart;

    public SafeTile(IScreen screen, boolean isStart)
    {
        super(screen);
        this.isStart = isStart;
    }

    // Draw this object
    public boolean draw()
    {
        boolean ret = true;
        ret = ret && this.screen.drawRect(new Box(), new Color(10, 200, 10));
        return ret;
    }

    // Returns true if the element contains a start position
    public boolean isStart()
    {
        return this.isStart;
    }

    // Returns a possible start position
    public ISolid getStart()
    {
        if (isStart())
            return this;
        throw new AssertionError("No valid start position");
    }
}
