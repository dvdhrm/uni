// Common swimmer class
public abstract class ASwimmer extends ASolid {
    // Direction
    boolean right;
    // Speed
    int speed;
    // Smooth moves
    boolean smooth;

    public ASwimmer(IScreen screen, int move, boolean right, int speed, boolean smooth)
    {
        super(screen.moveRound(move, 0));
        this.right = right;
        this.speed = speed;
        this.smooth = smooth;
    }

    public ASwimmer(IScreen screen)
    {
        this(screen, 0, true, 5, true);
    }

    private void moveElewise(int xbefore, ISolid newpos, Frog frog)
    {
        int db = xbefore % frog.screen.getSize().x;
        int da = newpos.getScreen().getPos().x % frog.screen.getSize().x;
        if (this.right && (da == 0 || db > da))
            frog.moveThis(Direction.RIGHT);
        else if (!this.right && (db < da))
            frog.moveThis(Direction.LEFT);
    }

    // Move frog if we move
    protected void frogMove(ISolid newpos, Frog frog)
    {
        if (overlaps(frog)) {
            int xbefore = this.screen.getPos().x;
            if (this.smooth)
                frog.moveThis(newpos.getScreen().getPos().x - xbefore, 0);
            else
                moveElewise(xbefore, newpos, frog);
        }
    }
}
