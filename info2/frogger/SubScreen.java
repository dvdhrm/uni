import geometry.*;

// Subscreen of a parent screen
public class SubScreen extends AScreen {
    // Parent screen
    IScreen parent;

    public SubScreen(IScreen parent, Posn pos, Posn size)
    {
        super(pos, size);
        this.parent = parent;
    }

    // Convert position inside this screen into position inside parent screen
    private Posn posToParent(Posn pos)
    {
        return new Posn(this.pos.x + pos.x, this.pos.y + pos.y);
    }

    // Return absolute size/position of our screen
    public Posn getSize()
    {
        return this.size;
    }

    public Posn getPos()
    {
        Posn pPos = this.parent.getPos();
        return new Posn(pPos.x + this.pos.x, pPos.y + this.pos.y);
    }

    // Move screen relative to parent
    public IScreen moveTo(int x, int y)
    {
        return new SubScreen(this.parent, new Posn(x, y), this.size);
    }

    // Move element in relative space to parent
    public IScreen move(int x, int y)
    {
        return moveTo(this.pos.x + x, this.pos.y + y);
    }

    // Move element only if it fits into the parent
    public IScreen limitedMove(int x, int y)
    {
        Posn psize = this.parent.getSize();
        int tx = this.pos.x + x;
        int ty = this.pos.y + y;

        if (tx < 0)
            tx = 0;
        else if (tx + this.size.x > psize.x)
            tx = psize.x - this.size.x;
        if (ty < 0)
            ty = 0;
        else if (ty + this.size.y > psize.y)
            ty = psize.y - this.size.y;
        return moveTo(tx, ty);
    }

    // Move element around parent screen
    public IScreen moveRound(int x, int y)
    {
        Posn psize = this.parent.getSize();
        int tx = this.pos.x + (x % psize.x);
        int ty = this.pos.y + (y % psize.y);

        if (tx > psize.x)
            tx = -this.size.x + tx - psize.x;
        if (tx + this.size.x < 0)
            tx = psize.x + (tx + this.size.x);
        if (ty > psize.y)
            ty = -this.size.y + ty - psize.y;
        if (ty + this.size.y < 0)
            ty = psize.y + (ty + this.size.y);
        return moveTo(tx, ty);
    }

    // Forward drawings to parent
    public boolean drawRect(Posn pos, Posn size, Color col)
    {
        return this.parent.drawRect(posToParent(pos), size, col);
    }

    public boolean drawCircle(Posn pos, int radius, Color col)
    {
        return this.parent.drawCircle(posToParent(pos), radius, col);
    }

    public boolean drawDisk(Posn pos, int radius, Color col)
    {
        return this.parent.drawDisk(posToParent(pos), radius, col);
    }

    public boolean drawLine(Posn pos, Posn dest, Color col)
    {
        return this.parent.drawLine(posToParent(pos), posToParent(dest), col);
    }

    public boolean drawString(Posn pos, String str)
    {
        return this.parent.drawString(posToParent(pos), str);
    }
}
