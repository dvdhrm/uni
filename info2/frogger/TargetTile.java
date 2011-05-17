// A target tile
public class TargetTile extends ASolid {
    boolean filled;
    boolean targeted;

    public TargetTile(IScreen screen, boolean filled)
    {
        super(screen);
        this.filled = filled;
        this.targeted = false;
    }

    public TargetTile(IScreen screen)
    {
        this(screen, false);
    }

    // Draw frog
    public boolean drawFrog()
    {
        return new Frog(this.screen).draw();
    }

    // Draw this object
    public boolean draw()
    {
        boolean ret = true;
        ret = ret && this.screen.drawRect(new Box(), new Color(10, 200, 10));
        ret = ret && this.getScreen().drawLine(new Coord(0.0, 0.0), new Coord(0.0, 1.0), new Color(0));
        ret = ret && this.getScreen().drawLine(new Coord(0.0, 0.0), new Coord(1.0, 0.0), new Color(0));
        ret = ret && this.getScreen().drawLine(new Coord(1.0, 0.0), new Coord(1.0, 1.0), new Color(0));
        if (this.filled)
            ret = ret && drawFrog();
        return ret;
    }

    // Block frog if this target is filled
    public boolean blocks(Frog frog)
    {
        if (this.filled && overlaps(frog))
            return true;
        else
            return false;
    }

    // React on ticks
    public TargetTile onTick(Frog frog)
    {
        if (overlaps(frog)) {
            this.targeted = true;
            frog.lock();
        }
        return this;
    }

    // Has open targets
    public int todos()
    {
        if (this.filled)
            return 0;
        return 1;
    }

    // Reset and make ready for next frog
    public void reset()
    {
        if (this.targeted)
            this.filled = true;
    }
}
