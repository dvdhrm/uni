// A LKW
public class LKW extends ACar {
    public LKW(IScreen screen, int move, boolean right, int speed)
    {
        super(screen, move, right, speed);
    }

    // Draw this object
    public boolean draw()
    {
        boolean ret = true;
        ret = ret && this.screen.drawRect(new Box(new Coord(0.05, 0.05), new Coord(0.5, 0.75)), new Color(10, 10, 255));

        ret = ret && this.screen.drawRect(new Box(new Coord(0.6, 0.05), new Coord(0.20, 0.75)), new Color(10, 10, 255));
        ret = ret && this.screen.drawRect(new Box(new Coord(0.65, 0.13), new Coord(0.10, 0.2)), new Color(0));
        ret = ret && this.screen.drawRect(new Box(new Coord(0.75, 0.45), new Coord(0.15, 0.35)), new Color(10, 10, 255));
        ret = ret && this.screen.drawDisk(new Coord(0.575, 0.73), 0.03, new Color(0));

        ret = ret && this.screen.drawDisk(new Coord(0.15, 0.88), 0.08, new Color(0));
        ret = ret && this.screen.drawDisk(new Coord(0.45, 0.88), 0.08, new Color(0));
        ret = ret && this.screen.drawDisk(new Coord(0.65, 0.88), 0.08, new Color(0));
        ret = ret && this.screen.drawDisk(new Coord(0.85, 0.88), 0.08, new Color(0));
        return ret;
    }

    // React on ticks
    public LKW onTick(Frog frog)
    {
        LKW car;
        if (this.right)
            car = new LKW(this.screen, this.speed, this.right, this.speed);
        else
            car = new LKW(this.screen, -this.speed, this.right, this.speed);
        if (car.overlaps(frog))
            frog.kill();
        return car;
    }
}
