import draw.*;
import geometry.*;
import colors.*;
import java.awt.*;

// Real screen that draws to a canvas
public class RealScreen extends AScreen {
    // Canvas that we draw to
    private draw.Canvas canvas;

    public RealScreen(draw.Canvas canvas, Posn size)
    {
        super(new Posn(0, 0), size);
        this.canvas = canvas;
    }

    // Return absolute size/position of our screen
    public Posn getSize()
    {
        return this.size;
    }

    public Posn getPos()
    {
        return this.pos;
    }

    // Move screen relative to parent
    public IScreen moveTo(int x, int y)
    {
        // Cannot move, we have no parent
        return this;
    }

    public IScreen move(int x, int y)
    {
        return moveTo(x, y);
    }

    public IScreen limitedMove(int x, int y)
    {
        return moveTo(x, y);
    }

    public IScreen moveRound(int x, int y)
    {
        return moveTo(x, y);
    }

    // Draw rectangle
    public boolean drawRect(Posn pos, Posn size, Color col)
    {
        return this.canvas.drawRect(pos, size.x, size.y, col.toAWT());
    }

    public boolean drawCircle(Posn pos, int radius, Color col)
    {
        return this.canvas.drawCircle(pos, radius, col.toAWT());
    }

    public boolean drawDisk(Posn pos, int radius, Color col)
    {
        return this.canvas.drawDisk(pos, radius, col.toAWT());
    }

    public boolean drawLine(Posn pos, Posn dest, Color col)
    {
        return this.canvas.drawLine(pos, dest, col.toAWT());
    }

    public boolean drawString(Posn pos, String str)
    {
        return this.canvas.drawString(pos, str);
    }
}
