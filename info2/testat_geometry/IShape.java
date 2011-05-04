import draw.*;
import colors.*;

// A shape at a specific position
public interface IShape {
    // Tests whether the given point is part of the shape
    public boolean contains(Point point);
    // Returns bounding box
    public IRectangle boundingBox();
    // Returns the position of the shape (the lower left corner of the bounding box)
    public Point position();
    // Returns distance between the shape's position and origin
    public Length originDistance();
    // Draw to canvas
    public void draw(Canvas canvas, IColor col);
}
