// Rectangle
public class Rectangle extends ARectangle {
    // Length of a single edge in x and y direction
    Length x;
    Length y;

    Rectangle(Point pos, Length x, Length y)
    {
        super(pos);
        this.x = x;
        this.y = y;
    }

    // Tests whether the given point is part of the shape
    public boolean contains(Point point)
    {
        if (point.x.smallerThan(this.pos.x))
            return false;
        if (!point.x.smallerThan(this.pos.x.extend(this.x)))
            return false;
        if (point.y.smallerThan(this.pos.y))
            return false;
        if (!point.y.smallerThan(this.pos.y.extend(this.y)))
            return false;
        return true;
    }

    // Return lower left position
    public Point lowLeft()
    {
        return this.pos;
    }

    // Return upper right position
    public Point upRight()
    {
        return new Point(this.pos.x.extend(this.x), this.pos.y.extend(this.y));
    }
}
