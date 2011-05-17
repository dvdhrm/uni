// A solid object in the world
public interface ISolid extends IDrawable {
    // Returns true if this element doesn't allow the
    // frog to be at its position.
    public boolean blocks(Frog frog);

    // Returns true if the element contains a start position
    public boolean isStart();

    // Returns a possible start position
    public ISolid getStart();

    // React on time event
    public ISolid onTick(Frog frog);

    // Has open targets
    public int todos();

    // Reset and make ready for next frog
    public void reset();
}
