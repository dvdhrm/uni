import draw.*;
import geometry.*;
import colors.*;
import java.awt.*;

// Real screen that draws to a canvas
public class RealScreen implements IScreen {
    // Canvas that we draw to
    private draw.Canvas canvas;
    // Size of the canvas
    private Posn size;

    public RealScreen(draw.Canvas canvas, Posn size)
    {
        this.canvas = canvas;
        this.size = size;
    }

    // Returns the box relative to the parent that is this screen
    public Box getBox()
    {
        // We have no parent so we occupy the whole parent
        return new Box();
    }

    // Move box relative to parent
    public IScreen move(double x, double y)
    {
        // Do nothing, we have no parent
        return this;
    }

    // Return absolute size of owner-canvas
    public Posn getSize()
    {
        return this.size;
    }

    // Round value to int
    public int round(double d)
    {
        return (int)Math.round(d);
    }

    // Draw rectangle to canvas
    // First convert relative Coord arguments into our
    // absolute coordinate system.
    public boolean drawRect(Box box, Color col)
    {
        Posn asize = box.size.abs(this.size);
        return this.canvas.drawRect(box.pos.abs(this.size), asize.x, asize.y, col.toAWT());
    }

    public boolean drawCircle(Coord center, double xradius, Color col)
    {
        return this.canvas.drawCircle(center.abs(this.size), round(xradius * this.size.x), col.toAWT());
    }

    public boolean drawDisk(Coord center, double xradius, Color col)
    {
        return this.canvas.drawDisk(center.abs(this.size), round(xradius * this.size.x), col.toAWT());
    }

    public boolean drawLine(Coord pos, Coord dest, Color col)
    {
        return this.canvas.drawLine(pos.abs(this.size), dest.abs(this.size), col.toAWT());
    }
}
