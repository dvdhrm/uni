import geometry.*;

// Coordinate consisting of x and y pair in percent of parent element
public class Coord {
    // x and y coordinate in percent
    // x and y are always 0 <= x,y <= 1
    public double x;
    public double y;

    // Trims c between 0 and 1
    private static double trim(double c)
    {
        if (c >= 1.0)
            return 1.0;
        else if (c <= 0.0)
            return 0.0;
        else
            return c;
    }

    // Creates a new Coordinate at (0/0)
    public Coord()
    {
        this.x = 0;
        this.y = 0;
    }

    // Creates a new Coordinate at (x/y) (x and y are truncated if out of bounds)
    public Coord(double x, double y)
    {
        this.x = Coord.trim(x);
        this.y = Coord.trim(y);
    }

    // Return sum of $other and us
    public Coord add(Coord other)
    {
        return new Coord(Coord.trim(this.x + other.x), Coord.trim(this.y + other.y));
    }

    // Subtract $other from this and return result
    public Coord sub(Coord other)
    {
        return new Coord(Coord.trim(this.x - other.x), Coord.trim(this.y - other.y));
    }

    // Returns absolute x position for absolute scale d
    private int absX(int d)
    {
        return (int)(this.x * d);
    }

    // Returns absolute y position for absolute scale d
    private int absY(int d)
    {
        return (int)(this.y * d);
    }

    // Creates an absolute coordinate depending on the given border
    public Posn absSize(Posn size)
    {
        return new Posn(absX(size.x), absY(size.y));
    }

    // Creates an absolute coordinate depending on the given border
    public Posn absPos(Posn size)
    {
        Posn pos = absSize(size);
        int x = pos.x;
        int y = pos.y;
        if (pos.x >= size.x)
            x = size.x - 1;
        if (pos.y >= size.y)
            y = size.y - 1;
        return new Posn(x, y);
    }
}
