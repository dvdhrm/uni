// A drawable object
public abstract class ADrawable implements IDrawable {
    // The screen we draw to
    IScreen screen;

    public ADrawable(IScreen screen)
    {
        this.screen = screen;
    }

    // Returns the bounding box of the object.
    public Box getBox()
    {
        return this.screen.getBox();
    }

    // Returns true if this object overlaps with $other. In easiest case
    // this is equal to the overlap of both bounding boxes.
    // Other objects shall overwrite this method.
    public boolean overlaps(IDrawable other)
    {
        return this.getBox().overlaps(other.getBox());
    }

    // Returns the screen
    public IScreen getScreen()
    {
       return this.screen;
    }
}
