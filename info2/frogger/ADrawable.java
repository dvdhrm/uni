// A drawable object
public abstract class ADrawable implements IDrawable {
    // The screen we draw to
    IScreen screen;

    public ADrawable(IScreen screen)
    {
        this.screen = screen;
    }

    // Returns the screen
    public IScreen getScreen()
    {
       return this.screen;
    }

    // Returns true if this object overlaps with $other. In easiest case
    // this is equal to the overlap of both screens.
    // Other objects shall overwrite this method.
    public boolean overlaps(IDrawable other)
    {
        return this.screen.overlaps(other.getScreen());
    }

    // React on time event
    public IDrawable onTick(Frog frog)
    {
        // By default all objects are static; override this if not
        return this;
    }
}
