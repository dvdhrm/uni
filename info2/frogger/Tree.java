// A tree
public class Tree extends ASwimmer {
    public Tree(IScreen screen, int move, boolean right, int speed, boolean smooth)
    {
        super(screen, move, right, speed, smooth);
    }

    // Draw this object
    public boolean draw()
    {
        boolean ret = true;
        ret = ret && this.screen.drawRect(new Box(new Coord(0.05, 0.05), new Coord(0.9, 0.9)), new Color(160, 82, 45));
        ret = ret && this.screen.drawDisk(new Coord(0.05, 0.5), 0.45, new Color(160, 82, 45));
        ret = ret && this.screen.drawDisk(new Coord(0.95, 0.5), 0.45, new Color(205, 133, 63));
        ret = ret && this.screen.drawCircle(new Coord(0.95, 0.5), 0.40, new Color(160, 82, 45));
        ret = ret && this.screen.drawCircle(new Coord(0.95, 0.5), 0.36, new Color(160, 82, 45));
        ret = ret && this.screen.drawCircle(new Coord(0.94, 0.49), 0.30, new Color(160, 82, 45));
        ret = ret && this.screen.drawCircle(new Coord(0.95, 0.51), 0.20, new Color(160, 82, 45));
        ret = ret && this.screen.drawCircle(new Coord(0.94, 0.5), 0.10, new Color(160, 82, 45));
        ret = ret && this.screen.drawLine2(new Coord(0.20, 0.2), new Coord(0.55, 0.0), new Color(205, 133, 63));
        ret = ret && this.screen.drawLine2(new Coord(0.05, 0.4), new Coord(0.55, 0.0), new Color(205, 133, 63));
        ret = ret && this.screen.drawLine2(new Coord(0.20, 0.6), new Coord(0.55, 0.0), new Color(205, 133, 63));
        ret = ret && this.screen.drawLine2(new Coord(0.05, 0.8), new Coord(0.55, 0.0), new Color(205, 133, 63));
        return ret;
    }

    // React on ticks
    public Tree onTick(Frog frog)
    {
        int x = 0;
        if (this.right)
            x = this.speed;
        else
            x = -this.speed;
        Tree tree = new Tree(this.screen, x, this.right, this.speed, this.smooth); 

        frogMove(tree, frog);
        return tree;
    }
}
