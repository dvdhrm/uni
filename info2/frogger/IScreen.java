import geometry.*;

// A screen that can be drawn to
public interface IScreen {
    // Return absolute size and position of this canvas
    public Posn getSize();
    public Posn getPos();

    // Returns true if both screens overlap
    public boolean overlaps(IScreen other);

    // Move screen relative to parent
    // Limited move forbids the element to leave the paren
    // screen.
    // Round move will make the element reapper on the opposing
    // side if it leaves the parent element.
    public IScreen moveTo(int x, int y);
    public IScreen move(int x, int y);
    public IScreen limitedMove(int x, int y);
    public IScreen moveRound(int x, int y);

    // Return sub screen with this screen as parent
    public IScreen subScreen(Posn pos, Posn size);
    public IScreen subScreen(Posn pos, Coord size);
    public IScreen subScreen(Box box);

    // Draws (scaled) shapes to this screen
    public boolean drawRect(Posn pos, Posn size, Color col);
    public boolean drawRect(Box box, Color col);
    public boolean drawCircle(Posn pos, int radius, Color col);
    public boolean drawCircle(Coord center, double yradius, Color col);
    public boolean drawDisk(Posn pos, int radius, Color col);
    public boolean drawDisk(Coord center, double yradius, Color col);
    public boolean drawLine(Posn pos, Posn size, Color col);
    public boolean drawLine(Coord pos, Coord dest, Color col);
    public boolean drawLine2(Coord pos, Coord size, Color col);
    public boolean drawString(Posn pos, String str);
}
