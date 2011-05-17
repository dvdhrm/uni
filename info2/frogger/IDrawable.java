// A drawable object
public interface IDrawable {
    // Returns the screen of the object
    public IScreen getScreen();

    // Returns true if this object overlaps with $other
    public boolean overlaps(IDrawable other);

    // Draw this object
    public boolean draw();

    // React on time event (frog is passed as reference and may be modified)
    public IDrawable onTick(Frog frog);
}
