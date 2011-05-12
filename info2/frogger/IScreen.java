import geometry.*;

// A screen that can be drawn to
public interface IScreen {
    // Returns the box relative to the parent that is this screen
    public Box getBox();

    // Move box relative to parent
    public IScreen move(double x, double y);

    // Return absolute size of owner-canvas
    public Posn getSize();

    // Draws shapes to this screen
    public boolean drawRect(Box box, Color col);
    public boolean drawCircle(Coord center, double xradius, Color col);
    public boolean drawDisk(Coord center, double xradius, Color col);
    public boolean drawLine(Coord pos, Coord dest, Color col);
}
