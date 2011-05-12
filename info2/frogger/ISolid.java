// Kurzbeschreibung
public interface ISolid extends IDrawable {
    // Returns true if this element kills the frog
    public boolean kills(Frog frog);

    // Returns true if this element doesn't allow the
    // frog to be at its position.
    public boolean blocks(Frog frog);
}
