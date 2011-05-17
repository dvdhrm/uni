import geometry.*;

// Frog
public class Frog extends ASolid {
    // number of ticks since kill (0 if alive)
    int locked;
    // true if dead
    boolean dead;

    public Frog(IScreen screen, int locked, boolean dead)
    {
        super(screen);
        this.locked = locked;
        this.dead = dead;
    }

    public Frog(IScreen screen)
    {
        this(screen, 0, false);
    }

    // Draw frog
    public boolean drawFrog()
    {
        boolean ret = true;

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

    // Draw this object
    public boolean draw()
    {
        // Of frog is locked it blinks
        if (isLocked() && (this.locked / 10 % 2) == 0) {
            return true;
        }
        else
            return drawFrog();
    }

    // Move frog into direction
    private Posn moveDir(Direction dir)
    {
        Posn size = this.screen.getSize();
        int x = 0;
        int y = 0;

        if (!isLocked()) {
            if (dir == Direction.UP)
                y = -size.y;
            else if (dir == Direction.DOWN)
                y = size.y;
            else if (dir == Direction.LEFT)
                x = -size.x;
            else
                x = size.x;
        }
        return new Posn(x, y);
    }

    // Move this frog instead of creating new moved frog
    public void moveThis(Direction dir)
    {
        Posn len = moveDir(dir);
        this.screen = this.screen.limitedMove(len.x, len.y);
    }

    public void moveThis(int x, int y)
    {
        this.screen = this.screen.limitedMove(x, y);
    }

    // Move frog
    // A frog moves always as far as its own size. Returns
    // the new frog. The new position may be blocked by
    // other elements, so check after calling this.
    public Frog move(Direction dir)
    {
        Posn len = moveDir(dir);
        return new Frog(this.screen.limitedMove(len.x, len.y), this.locked, this.dead);
    }

    // Returns true if frog is locked
    private boolean isLocked()
    {
        return this.locked != 0;
    }

    // Returns true if the frog got killed
    public boolean isDead()
    {
        return this.dead;
    }

    // Lock frog
    public void lock()
    {
        if (!isLocked())
            this.locked = 1;
    }

    // Kill frog
    public void kill()
    {
        this.dead = true;
        lock();
    }

    // Returns true if the frog shall be resetted
    public boolean needsReset()
    {
        if (this.locked > 50)
            return true;
        return false;
    }

    // Reacts on ticks
    public Frog onTick(Frog frog)
    {
        if (isLocked())
            this.locked += 1;
        return this;
    }
}
