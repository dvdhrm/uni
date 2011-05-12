import geometry.*;

// Subscreen of a parent screen
public class SubScreen implements IScreen {
    // Parent screen
    // The grand-grand-....-grand-grand-parent is
    // always a RealScreen, otherwise this screen
    // would be undrawable.
    IScreen parent;
    // Our screen as box relative of parent, that is, this is a
    // box inside the parent which specifies our screen. Drawing
    // to this screen is always done relative to this box and
    // always inside this box. We can't draw beyond this box.
    Box box;

    public SubScreen(IScreen parent, Box box)
    {
        this.parent = parent;
        this.box = box;
    }

    // Convert a relative size to an absolute size seen by the parent
    private Coord toParentSize(Coord size)
    {
        return new Coord(this.box.size.x * size.x, this.box.size.y * size.y);
    }

    // Convert a relative position to an absolute position seen by the parent
    private Coord toParentPos(Coord pos)
    {
        return this.box.pos.add(toParentSize(pos));
    }

    // Convert a relative box to an absolute box seen by the parent
    private Box toParentBox(Box box)
    {
        return new Box(toParentPos(box.pos), toParentSize(box.size));
    }

    // Returns the absolute box seen by the parent which is our screen
    public Box getBox()
    {
        return this.box;
    }

    // Move box relative to parent
    public IScreen move(double x, double y)
    {
        if (this.box.pos.x + this.box.size.x + x > 1.0)
            return this;
        if (this.box.pos.y + this.box.size.y + y > 1.0)
            return this;
        else
            return new SubScreen(parent, new Box(new Coord(this.box.pos.x + x, this.box.pos.y + y), this.box.size));
    }

    // Return absolute size of owner-canvas
    public Posn getSize()
    {
        return this.parent.getSize();
    }

    // Draw rectangle to our screen
    public boolean drawRect(Box box, Color col)
    {
        return this.parent.drawRect(toParentBox(box), col);
    }

    public boolean drawCircle(Coord center, double xradius, Color col)
    {
        // Since we can't scale the y-axis of our circle, this may draw beyond the
        // screen of the element that called this, but we ignore this here. We'd need
        // a function that can draw an ellipsis to fix this, but there ain't one.
        return this.parent.drawCircle(toParentPos(center), xradius * this.box.size.x, col);
    }

    public boolean drawDisk(Coord center, double xradius, Color col)
    {
        // Same problem as above
        return this.parent.drawDisk(toParentPos(center), xradius * this.box.size.x, col);
    }

    public boolean drawLine(Coord pos, Coord dest, Color col)
    {
        return this.parent.drawLine(toParentPos(pos), toParentPos(dest), col);
    }
}
