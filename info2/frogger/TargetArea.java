// A street area with cars killing the frog
public class TargetArea extends ASolidListEntry {
    public TargetArea(IScreen screen, int width)
    {
        this(screen, width, 3);
    }

    public TargetArea(IScreen screen, int width, int targets)
    {
        super(screen);

        if (width == 0 || targets == 0 || width < targets)
            throw new AssertionError("Cannot create empty TargetArea");

        double w = 1.0 / width;
        int num = width / targets;

        this.head = new TargetTile(screen.subScreen(new Box(new Coord((num - 1) * w, 0.0), new Coord(w, 1.0))));
        for (int i = 2; i <= targets; ++i) {
            this.tail = new SolidListEntry(this.head, this.tail);
            this.head = new TargetTile(screen.subScreen(new Box(new Coord((num * i - 1) * w, 0.0), new Coord(w, 1.0))));
        }
    }

    public TargetArea(ISolid head, ISolidList tail)
    {
        super(head, tail);
    }

    // Draw area
    public boolean draw()
    {
        return this.getScreen().drawRect(new Box(), new Color(200, 20, 10))
            && this.getScreen().drawLine(new Coord(0.0, 1.0), new Coord(1.0, 1.0), new Color(0))
            && super.draw();
    }

    // React on ticks
    // Kill frog if it is on our area but not in a valid target area
    public TargetArea onTick(Frog frog)
    {
        super.onTick(frog);
        if (this.getScreen().overlaps(frog.getScreen())) {
            if (!overlaps(frog))
                frog.kill();
        }
        return this;
    }
}
