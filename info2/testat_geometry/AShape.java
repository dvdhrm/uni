// Kurzbeschreibung
public abstract class AShape implements IShape {
    // Returns the position of the shape (the lower left corner of the bounding box)
    public Point position()
    {
        return this.boundingBox().lowLeft();
    }

    // Returns distance between the shape's position and origin
    public Length originDistance()
    {
        return position().distanceTo(new Point());
    }
}
