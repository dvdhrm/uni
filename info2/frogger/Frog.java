// Kurzbeschreibung
public class Frog extends ASolid {
    public Frog(IScreen screen)
    {
        super(screen);
    }

    // Draw this object
    public boolean draw()
    {
        boolean ret = true;

        // Background
        //ret = ret && this.screen.drawRect(new Box(), new Color(200));

        // Jewels
        ret = ret && this.screen.drawLine(new Coord(0.5, 0.6), new Coord(0.3, 0.1), new Color(255, 255, 0));
        ret = ret && this.screen.drawLine(new Coord(0.3, 0.1), new Coord(0.442, 0.26), new Color(255, 255, 0));
        ret = ret && this.screen.drawLine(new Coord(0.442, 0.26), new Coord(0.44, 0.1), new Color(255, 255, 0));
        ret = ret && this.screen.drawLine(new Coord(0.5, 0.25), new Coord(0.44, 0.1), new Color(255, 255, 0));

        ret = ret && this.screen.drawLine(new Coord(0.5, 0.6), new Coord(0.7, 0.1), new Color(255, 255, 0));
        ret = ret && this.screen.drawLine(new Coord(0.7, 0.1), new Coord(0.558, 0.26), new Color(255, 255, 0));
        ret = ret && this.screen.drawLine(new Coord(0.558, 0.26), new Coord(0.56, 0.1), new Color(255, 255, 0));
        ret = ret && this.screen.drawLine(new Coord(0.5, 0.25), new Coord(0.56, 0.1), new Color(255, 255, 0));

        // Legs
        ret = ret && this.screen.drawLine(new Coord(0.35, 0.78), new Coord(0.25, 0.85), new Color(0));
        ret = ret && this.screen.drawDisk(new Coord(0.25, 0.85), 0.02, new Color(0));
        ret = ret && this.screen.drawLine(new Coord(0.35, 0.78), new Coord(0.22, 0.8), new Color(0));
        ret = ret && this.screen.drawDisk(new Coord(0.22, 0.8), 0.02, new Color(0));
        ret = ret && this.screen.drawLine(new Coord(0.35, 0.78), new Coord(0.30, 0.88), new Color(0));
        ret = ret && this.screen.drawDisk(new Coord(0.30, 0.88), 0.02, new Color(0));

        ret = ret && this.screen.drawLine(new Coord(0.65, 0.78), new Coord(0.75, 0.85), new Color(0));
        ret = ret && this.screen.drawDisk(new Coord(0.75, 0.85), 0.02, new Color(0));
        ret = ret && this.screen.drawLine(new Coord(0.65, 0.78), new Coord(0.78, 0.8), new Color(0));
        ret = ret && this.screen.drawDisk(new Coord(0.78, 0.8), 0.02, new Color(0));
        ret = ret && this.screen.drawLine(new Coord(0.65, 0.78), new Coord(0.70, 0.88), new Color(0));
        ret = ret && this.screen.drawDisk(new Coord(0.70, 0.88), 0.02, new Color(0));

        // Arms
        ret = ret && this.screen.drawLine(new Coord(0.34, 0.7), new Coord(0.18, 0.6), new Color(0));
        ret = ret && this.screen.drawLine(new Coord(0.18, 0.6), new Coord(0.08, 0.58), new Color(0));
        ret = ret && this.screen.drawDisk(new Coord(0.08, 0.58), 0.02, new Color(0));
        ret = ret && this.screen.drawLine(new Coord(0.18, 0.6), new Coord(0.08, 0.54), new Color(0));
        ret = ret && this.screen.drawDisk(new Coord(0.08, 0.54), 0.02, new Color(0));
        ret = ret && this.screen.drawLine(new Coord(0.18, 0.6), new Coord(0.12, 0.51), new Color(0));
        ret = ret && this.screen.drawDisk(new Coord(0.12, 0.51), 0.02, new Color(0));

        ret = ret && this.screen.drawLine(new Coord(1 - 0.34, 0.7), new Coord(1 - 0.18, 0.6), new Color(0));
        ret = ret && this.screen.drawLine(new Coord(1 - 0.18, 0.6), new Coord(1 - 0.08, 0.58), new Color(0));
        ret = ret && this.screen.drawDisk(new Coord(1 - 0.08, 0.58), 0.02, new Color(0));
        ret = ret && this.screen.drawLine(new Coord(1 - 0.18, 0.6), new Coord(1 - 0.08, 0.54), new Color(0));
        ret = ret && this.screen.drawDisk(new Coord(1 - 0.08, 0.54), 0.02, new Color(0));
        ret = ret && this.screen.drawLine(new Coord(1 - 0.18, 0.6), new Coord(1 - 0.12, 0.51), new Color(0));
        ret = ret && this.screen.drawDisk(new Coord(1 - 0.12, 0.51), 0.02, new Color(0));

        // Body
        ret = ret && this.screen.drawDisk(new Coord(0.5, 0.6), 0.25, new Color("green"));
        ret = ret && this.screen.drawCircle(new Coord(0.5, 0.6), 0.25, new Color(0));
        ret = ret && this.screen.drawLine(new Coord(0.26, 0.57), new Coord(0.74, 0.57), new Color(0));

        // Eyes
        ret = ret && this.screen.drawDisk(new Coord(0.4, 0.38), 0.1, new Color(200));
        ret = ret && this.screen.drawDisk(new Coord(0.6, 0.38), 0.1, new Color(200));
        ret = ret && this.screen.drawDisk(new Coord(0.405, 0.3805), 0.03, new Color(0));
        ret = ret && this.screen.drawDisk(new Coord(0.605, 0.3805), 0.03, new Color(0));

        return ret;
    }

    // Returns true if this element doesn't allow the
    // frog to be at its position.
    // A frog ALWAYS blocks another frog. Two frogs can't
    // be at the same position.
    public boolean blocks(Frog frog)
    {
        return overlaps(frog);
    }

    // Move frog
    // A frog moves always as far as its own size. Returns
    // the new frog. The new position may be blocked by
    // other elements, so check after calling this.
    public Frog move(Direction dir)
    {
        double x = 0;
        double y = 0;

        if (dir == Direction.UP)
            y = -this.screen.getBox().size.y;
        else if (dir == Direction.DOWN)
            y = this.screen.getBox().size.y;
        else if (dir == Direction.LEFT)
            x = -this.screen.getBox().size.x;
        else
            x = this.screen.getBox().size.x;

        return new Frog(this.screen.move(x, y));
    }
}
