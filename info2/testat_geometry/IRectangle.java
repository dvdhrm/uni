// Kurzbeschreibung
public interface IRectangle extends IShape {
    // Creates a new square that is the bounding box of two squares
    public IRectangle include(IRectangle other);
    // Returns the intersection of two squares
    public IRectangle intersect(IRectangle other);
    // Return lower left position
    public Point lowLeft();
    // Return upper right position
    public Point upRight();
}
