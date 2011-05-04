// A square parallel to x/y axes
public class Square extends ARectangle {
    // Length of a single edge
    Length len;

    Square(Point pos, Length len)
    {
        super(pos);
        this.len = len;
    }

    // Tests whether the given point is part of the shape
    public boolean contains(Point point)
    {
        if (point.x.smallerThan(this.pos.x))
            return false;
        if (!point.x.smallerThan(this.pos.x.extend(this.len)))
            return false;
        if (point.y.smallerThan(this.pos.y))
            return false;
        if (!point.y.smallerThan(this.pos.y.extend(this.len)))
            return false;
        return true;
    }

    // Return lower left position
    public Point lowLeft()
    {
        return pos;
    }

    // Return upper right position
    public Point upRight()
    {
        return new Point(this.pos.x.extend(len), this.pos.y.extend(len));
    }
}
