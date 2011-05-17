// A PKW
public class PKW extends ACar {
    public PKW(IScreen screen, int move, boolean right, int speed)
    {
        super(screen, move, right, speed);
    }

    // Draw this object
    public boolean draw()
    {
        boolean ret = true;
        ret = ret && this.screen.drawRect(new Box(new Coord(0.25, 0.4), new Coord(0.4, 0.3)), new Color(200, 0, 0));
        ret = ret && this.screen.drawRect(new Box(new Coord(0.05, 0.55), new Coord(0.9, 0.25)), new Color(200, 0, 0));
        ret = ret && this.screen.drawDisk(new Coord(0.8, 0.88), 0.08, new Color(0));
        ret = ret && this.screen.drawDisk(new Coord(0.2, 0.88), 0.08, new Color(0));
        return ret;
    }

    // React on ticks
    public PKW onTick(Frog frog)
    {
        PKW car;
        if (this.right)
            car = new PKW(this.screen, this.speed, this.right, this.speed);
        else
            car = new PKW(this.screen, -this.speed, this.right, this.speed);
        if (car.overlaps(frog))
            frog.kill();
        return car;
    }
}
