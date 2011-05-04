import draw.*;
import geometry.*;
import colors.*;

// A circle
public class Circle extends AShape {
    // Position of the center of the circle
    Point center;
    // Radius of the circle
    Length radius;

    Circle(Point center, Length radius)
    {
        this.center = center;
        this.radius = radius;
    }

    // Tests whether the given point is part of the shape
    public boolean contains(Point point)
    {
        return this.center.distanceTo(point).smallerThan(this.radius);
    }

    // Returns bounding box
    public IRectangle boundingBox()
    {
        // Bounding box is a square at center - radius with length radius
        Point point = new Point(this.center.x.reduce(this.radius), this.center.y.reduce(this.radius));
        return new Square(point, this.radius.multiply(2));
    }

    // Draw to canvas
    public void draw(Canvas canvas, IColor col)
    {
        canvas.drawCircle(new Posn((int)this.center.x.length, (int)this.center.y.length),
            (int)this.radius.length, col);
    }
}
