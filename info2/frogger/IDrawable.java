// A drawable object
public interface IDrawable {
    // Returns the bounding box of the object
    public Box getBox();

    // Draw this object
    public boolean draw();

    // Returns true if this object overlaps with $other
    public boolean overlaps(IDrawable other);

    // Returns the screen
    public IScreen getScreen();
}
