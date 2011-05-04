import draw.*;
import colors.*;

// Conunction of two shapes, that is, the intersection
public class Conjunction extends AShape {
    // Shape 1
    IShape shape1;
    // Shape 2
    IShape shape2;

    Conjunction(IShape shape1, IShape shape2)
    {
        this.shape1 = shape1;
        this.shape2 = shape2;
    }

    // Tests whether the given point is part of the shape
    public boolean contains(Point point)
    {
        if (this.shape1.contains(point) && this.shape2.contains(point))
            return true;
        else
            return false;
    }

    // Returns bounding box
    public IRectangle boundingBox()
    {
        return this.shape1.boundingBox().intersect(this.shape2.boundingBox());
    }

    // Draw to canvas
    public void draw(Canvas canvas, IColor col)
    {
        // We can't draw conjunctions efficiently here, so draw the disjunction
        this.shape1.draw(canvas, col);
        this.shape2.draw(canvas, col);
    }
}
