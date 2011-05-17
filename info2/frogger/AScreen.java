import geometry.*;

// Common functions inside screen implementations
public abstract class AScreen implements IScreen {
    // Absolute position and size of this screen
    Posn pos;
    Posn size;

    public AScreen(Posn pos, Posn size)
    {
        this.pos = pos;
        this.size = size;
    }

    // Return absolute position
    private Posn absPos(Coord pos)
    {
        return pos.absPos(this.size);
    }

    // Return absolute size
    private Posn absSize(Coord size)
    {
        return size.absSize(this.size);
    }

    // Return true whether both screens overlap
    public boolean overlaps(IScreen other)
    {
        Posn tpos = getPos();
        Posn tsize = getSize();
        Posn opos = other.getPos();
        Posn osize = other.getSize();

        if (opos.x < tpos.x) {
            if (opos.x + osize.x <= tpos.x)
                return false;
        }
        else if (tpos.x + tsize.x <= opos.x)
            return false;
        if (opos.y < tpos.y) {
            if (opos.y + osize.y <= tpos.y)
                return false;
        }
        else if (tpos.y + tsize.y <= opos.y)
            return false;
        return true;
    }

    // Return sub screen with this screen as parent
    public IScreen subScreen(Posn pos, Posn size)
    {
        return new SubScreen(this, pos, size);
    }

    public IScreen subScreen(Posn pos, Coord size)
    {
        return subScreen(pos, absSize(size));
    }

    public IScreen subScreen(Box box)
    {
        return subScreen(absPos(box.pos), absSize(box.size));
    }

    // Draw scaled shapes
    public boolean drawRect(Box box, Color col)
    {
        return drawRect(absPos(box.pos), absSize(box.size), col);
    }

    public boolean drawCircle(Coord center, double yradius, Color col)
    {
        return drawCircle(absPos(center), (int)(yradius * this.size.y), col);
    }

    public boolean drawDisk(Coord center, double yradius, Color col)
    {
        return drawDisk(absPos(center), (int)(yradius * this.size.y), col);
    }

    public boolean drawLine(Coord pos, Coord dest, Color col)
    {
        return drawLine(absPos(pos), absPos(dest), col);
    }

    public boolean drawLine2(Coord pos, Coord size, Color col)
    {
        return drawLine(pos, new Coord(pos.x + size.x, pos.y + size.y), col);
    }
}
