// Kurzbeschreibung
public abstract class ASolid extends ADrawable implements ISolid {
    public ASolid(IScreen screen)
    {
        super(screen);
    }

    // Returns true if this element kills the frog
    // Normal behaviour is "no killing". Other classes
    // may overwrite this.
    public boolean kills(Frog frog)
    {
        return false;
    }

    // Returns true if this element doesn't allow the
    // frog to be at its position.
    // Normal behavior is that an element always allows
    // the frog to be over it. Other classes may overwrite
    // this.
    // If an element kills the frog, it still allows the
    // frog to be at its position so it returns false here.
    // This is just about walls or other elements that
    // block the frog.
    public boolean blocks(Frog frog)
    {
        return false;
    }
}
