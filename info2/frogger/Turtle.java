// A Turtle
public class Turtle extends ASwimmer {
    public Turtle(IScreen screen, int move, boolean right, int speed, boolean smooth)
    {
        super(screen, move, right, speed, smooth);
    }

    // Draw this object
    public boolean draw()
    {
        boolean ret = true;
        ret = ret && this.screen.drawLine(new Coord(0.5, 0.5), new Coord(0.1, 0.2), new Color(0));
        ret = ret && this.screen.drawLine(new Coord(0.5, 0.5), new Coord(0.9, 0.8), new Color(0));
        ret = ret && this.screen.drawLine(new Coord(0.5, 0.5), new Coord(0.1, 0.8), new Color(0));
        ret = ret && this.screen.drawLine(new Coord(0.5, 0.5), new Coord(0.9, 0.2), new Color(0));
        ret = ret && this.screen.drawDisk(new Coord(0.5, 0.5), 0.3, new Color(10, 200, 5));
        ret = ret && this.screen.drawDisk(new Coord(0.8, 0.5), 0.2, new Color(23, 240, 10));
        return ret;
    }

    // React on ticks
    public Turtle onTick(Frog frog)
    {
        int x = 0;
        if (this.right)
            x = this.speed;
        else
            x = -this.speed;
        Turtle turtle = new Turtle(this.screen, x, this.right, this.speed, this.smooth); 

        frogMove(turtle, frog);
        return turtle;
    }
}
