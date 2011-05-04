// A point in the coordinate system defined by Length
public class Point {
    // Distance from (0/0) in x and y direction
    Length x;
    Length y;

    Point()
    {
        this.x = new Length(0.0);
        this.y = new Length(0.0);
    }

    Point(Length x, Length y)
    {
        this.x = x;
        this.y = y;
    }

    // Get distance of both points (always positive)
    public Length distanceTo(Point other)
    {
        Length x;
        Length y;

        x = this.x.reduce(other.x).square();
        y = this.y.reduce(other.y).square();
        return x.extend(y).sqrt();
    }
}
